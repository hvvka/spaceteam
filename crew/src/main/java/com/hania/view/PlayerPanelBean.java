package com.hania.view;

import com.hania.PlayerClient;
import com.hania.TaskGenerator;
import com.hania.model.PanelType;
import com.hania.model.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</currentAnswer>
 */
public class PlayerPanelBean extends JPanel implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerPanelBean.class);

    private PlayerClient playerClient;
    private String playerName;

    private PanelType playerPanelType;
    private Map<PanelType, JPanel> panelComponents;
    private TaskGenerator.SingleTask selectedAnswer;

    public PlayerPanelBean() {
        panelComponents = new EnumMap<>(PanelType.class);
        selectedAnswer = new TaskGenerator.SingleTask(playerPanelType, "", "");
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
            radioButton.addActionListener(e -> setSelectedAnswer(answer, description, panelType));
            container.add(radioButton);
        }
        container.setLayout(new BoxLayout(container, BoxLayout.LINE_AXIS));
        return container;
    }

    private Container createButtons(List<String> answers, String description, PanelType panelType) {
        Container container = new Container();
        for (String answer : answers) {
            JButton button = new JButton(answer);
            button.addActionListener(e -> setSelectedAnswer(answer, description, panelType));
            container.add(button);
        }
        container.setLayout(new BoxLayout(container, BoxLayout.LINE_AXIS));
        return container;
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
        });

        return list;
    }

    private void setSelectedAnswer(String answer, String description, PanelType panelType) {
        LOG.info("A={}, D={}, P={}", answer, description, panelType);
        selectedAnswer.setPanelType(panelType);
        selectedAnswer.setDescription(description);
        selectedAnswer.setAnswer(answer);
    }

    public PlayerClient getPlayerClient() {
        return playerClient;
    }

    public void setPlayerClient(PlayerClient playerClient) {
        this.playerClient = playerClient;
    }

    public void setPlayer(String name, PanelType panelType) {
        this.playerName = name;
        this.playerPanelType = panelType;
        lockOtherPanels(panelType);
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public PanelType getPlayerPanelType() {
        return playerPanelType;
    }

    public void setPlayerPanelType(PanelType playerPanelType) {
        this.playerPanelType = playerPanelType;
    }
}
