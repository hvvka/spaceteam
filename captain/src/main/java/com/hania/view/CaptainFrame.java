package com.hania.view;

import javax.swing.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class CaptainFrame extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JPanel captainPanel;
    private JTextField taskTextField;
    private JTextField scoreTextField;
    private JList playerList;
    private JButton kickOutButton;
    private JButton nextTaskButton;
    private JButton refreshButton;

    public CaptainFrame() {
        super("Manage your team");
        setSize(WIDTH, HEIGHT);
        setContentPane(captainPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        taskTextField.setEditable(false);
        scoreTextField.setEditable(false);
        setVisible(true);
    }

    public JTextField getTaskTextField() {
        return taskTextField;
    }

    public JTextField getScoreTextField() {
        return scoreTextField;
    }

    public JList getPlayerList() {
        return playerList;
    }

    public JButton getKickOutButton() {
        return kickOutButton;
    }

    public JButton getNextTaskButton() {
        return nextTaskButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }
}
