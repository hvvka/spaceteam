package com.hania.view;

import com.hania.PlayerClient;
import com.hania.TaskGenerator;
import com.hania.model.PanelType;
import com.hania.model.Task;
import com.hania.model.User;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PlayerPanelBean extends JPanel {

    private PlayerClient playerClient;
    private User player;

    private List<JPanel> panels;
    private Map<PanelType, JPanel> panelComponents;

    public PlayerPanelBean() {
        int panelsNumber = (int) (Arrays.stream(PanelType.values()).count() - 1);
        panels = new ArrayList<>(panelsNumber);
        panelComponents = new EnumMap<>(PanelType.class);

        this.setLayout(new GridLayout(1, panelsNumber));

        initTasks();
        initPanels();
    }

    private void initPanels() {
        for (PanelType panelType : PanelType.values()) {
            if (panelType == PanelType.CAPTAIN) continue;
            JPanel panel = new JPanel();
            JPanel componentsPanel = panelComponents.get(panelType);
            panels.add(panel);
            this.add(panel);
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
        panel.setLayout(new GridLayout(7, 1));
        panel.add(new JLabel(task.getPanelType().toString()));
        List<String> descriptionKeys = new ArrayList<>(task.getDescriptionAnswer().keySet());
        for (String descriptionKey : descriptionKeys) {
            addLabel(panel, descriptionKey);
            addComponent(task, panel, descriptionKey);
        }
        return panel;
    }

    private void addLabel(JPanel panel, String descriptionKey) {
        JLabel descriptionLabel = new JLabel(descriptionKey);
        panel.add(descriptionLabel);
    }

    private void addComponent(Task task, JPanel panel, String descriptionKey) {
        List<String> answers = task.getDescriptionAnswer().get(descriptionKey);
        Component component = createComponent(answers);
        panel.add(component);
    }

    private Component createComponent(List<String> answers) {
        if (StringUtils.isNumeric(answers.get(0))) {
            return createJSlider(answers);
        }
        //todo
//        else if () {}
        return new JLabel("yet empty");
    }

    private JSlider createJSlider(List<String> answers) {
        JSlider slider = new JSlider();
        for (String answer : answers) {
            int value = Integer.parseInt(answer);
            slider.setMajorTickSpacing(20);
            slider.setMinorTickSpacing(5);
            slider.setValue(value);
        }
        return slider;
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
    }
}
