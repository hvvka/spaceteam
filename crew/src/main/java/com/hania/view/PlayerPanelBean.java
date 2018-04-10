package com.hania.view;

import com.hania.PlayerClient;
import com.hania.TaskGenerator;
import com.hania.model.PanelType;
import com.hania.model.Task;
import com.hania.model.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PlayerPanelBean extends JPanel {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerPanelBean.class);

    private PlayerClient playerClient;
    private User player;

    private Map<PanelType, JPanel> panelComponents;
    private TaskGenerator.SingleTask selectedAnswer;

    public PlayerPanelBean() {
        panelComponents = new EnumMap<>(PanelType.class);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        initTasks();
        initPanels();
    }

    private void initPanels() {
        for (PanelType panelType : PanelType.values()) {
            if (panelType == PanelType.CAPTAIN) continue;
            JPanel componentsPanel = panelComponents.get(panelType);
            this.add(componentsPanel);
        }
    }

    private void initTasks() {
        List<Task> tasks = TaskGenerator.getAllTask();
        for (Task task : tasks) {
            PanelType panelType = task.getPanelType();
            panelComponents.put(panelType, createPlayerPanel(task));
        }
    }

    private JPanel createPlayerPanel(Task task) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(new JLabel(task.getPanelType().toString()));
        List<String> descriptionKeys = new ArrayList<>(task.getDescriptionAnswer().keySet());
        for (String descriptionKey : descriptionKeys) {
            Container container = new Container();
            container.setLayout(new BoxLayout(container, BoxLayout.LINE_AXIS));
            addLabel(container, descriptionKey);
            container.add(Box.createRigidArea(new Dimension(30, 0)));
            addComponent(task, container, descriptionKey);
            panel.add(container);
        }
        return panel;
    }

    private void addLabel(Container container, String descriptionKey) {
        JLabel descriptionLabel = new JLabel(descriptionKey);
        container.add(descriptionLabel);
    }

    private void addComponent(Task task, Container container, String descriptionKey) {
        List<String> answers = task.getDescriptionAnswer().get(descriptionKey);
        Component component = createComponent(answers, descriptionKey, task.getPanelType());
        container.add(component);
    }

    private Component createComponent(List<String> answers, String descriptionKey, PanelType panelType) {
        if (StringUtils.isNumeric(answers.get(0))) {
            return createJSlider(answers, descriptionKey, panelType);
        } else if (answers.size() == 2) {
            return createRadioButtons(answers, descriptionKey, panelType);
        } else if (answers.size() == 3) {
            return createButtons(answers, descriptionKey, panelType);
        } else {
            return createList(answers, descriptionKey, panelType);
        }
    }

    private JSlider createJSlider(List<String> answers, String description, PanelType panelType) {
        List<Integer> numericAnswers = answers.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int minAnswer = numericAnswers.get(0);
        int maxAnswer = numericAnswers.get(numericAnswers.size() - 1);
        int initialValue = numericAnswers.get(1);
        int step = numericAnswers.get(1) - numericAnswers.get(0);

        JSlider slider = new JSlider(minAnswer, maxAnswer, initialValue);
        slider.setMajorTickSpacing(step);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setPaintLabels(true);

        //FIXME nullpointer!!!
        slider.addChangeListener(e -> {
            Integer value = slider.getValue();
            setSelectedAnswer(value.toString(), description, panelType);
        });

        return slider;
    }

    private Container createRadioButtons(List<String> answers, String description, PanelType panelType) {
        Container container = new Container();
        for (String answer : answers) {
            JRadioButton radioButton = new JRadioButton(answer);

            //FIXME nullpointer!!!
            radioButton.addActionListener(getAbstractButtonListener(answer, description, panelType, radioButton));

            container.add(radioButton);
        }
        container.setLayout(new BoxLayout(container, BoxLayout.LINE_AXIS));
        return container;
    }

    private Container createButtons(List<String> answers, String description, PanelType panelType) {
        Container container = new Container();
        for (String answer : answers) {
            JButton button = new JButton(answer);
            button.addActionListener(getAbstractButtonListener(answer, description, panelType, button));
            container.add(button);
        }
        container.setLayout(new BoxLayout(container, BoxLayout.LINE_AXIS));
        return container;
    }

    private ActionListener getAbstractButtonListener(String answer, String description,
                                                     PanelType panelType, AbstractButton abstractButton) {
        return actionEvent -> {
            if (abstractButton.isSelected()) {
                setSelectedAnswer(answer, description, panelType);
            }
        };
    }

    private JList<String> createList(List<String> answers, String description, PanelType panelType) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String answer : answers) {
            model.addElement(answer);
        }
        JList<String> list = new JList<>(model);
        list.addListSelectionListener(e -> {
            String answer = list.getSelectedValue();
            setSelectedAnswer(answer, description, panelType);
            LOG.info("SELECTED: A={}, D={}, P={}", selectedAnswer.getAnswer(), selectedAnswer.getDescription(), selectedAnswer.getPanelType());
        });

        return list;
    }

//    private class ComponentActionListener implements ActionListener {
//        private String answer;
//        private String description;
//        private PanelType panelType;
//
//        ComponentActionListener(String answer, String description, PanelType panelType) {
//            this.answer = answer;
//            this.description = description;
//            this.panelType = panelType;
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            setSelectedAnswer(answer, description, panelType);
//        }
//
//        private void setSelectedAnswer(String answer, String description, PanelType panelType) {
//            LOG.info("A={}, D={}, P={}", answer, description, panelType);
//            selectedAnswer.setAnswer(answer);
//            selectedAnswer.setDescription(description);
//            selectedAnswer.setPanelType(panelType);
//        }
//    }

    private void setSelectedAnswer(String answer, String description, PanelType panelType) {
        //FIXME nullpointer!!!
        LOG.info("A={}, D={}, P={}", answer, description, panelType);
        selectedAnswer.setAnswer(answer);
        selectedAnswer.setDescription(description);
        selectedAnswer.setPanelType(panelType);
    }

    public PlayerClient getPlayerClient() {
        return playerClient;
    }

    public void setPlayerClient(PlayerClient playerClient) {
        this.playerClient = playerClient;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
        try {
            lockOtherPanels(player.getPanelType());
        } catch (RemoteException e) {
            ErrorMessageUtil.show(e);
        }
    }

    private void lockOtherPanels(PanelType panelType) {
        for (Map.Entry<PanelType, JPanel> entry : panelComponents.entrySet()) {
            if (entry.getKey() == panelType) continue;
            Component[] components = entry.getValue().getComponents();
            for (Component component : components) {
                component.setEnabled(false);
                lockComponentsInContainer(component);
            }
        }
    }

    private void lockComponentsInContainer(Component component) {
        if (component instanceof Container) {
            Container container = (Container) component;
            Queue<Component> queue = new LinkedList<>(Arrays.asList(container.getComponents()));
            while (!queue.isEmpty()) {
                Component head = queue.poll();
                if (head != null) {
                    head.setEnabled(false);
                }
                if (head instanceof Container) {
                    Container headCast = (Container) head;
                    queue.addAll(Arrays.asList(headCast.getComponents()));
                }
            }
        }
    }

    public Map<PanelType, JPanel> getPanelComponents() {
        return panelComponents;
    }

    public void setPanelComponents(Map<PanelType, JPanel> panelComponents) {
        this.panelComponents = panelComponents;
    }

    public TaskGenerator.SingleTask getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(TaskGenerator.SingleTask selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
