package com.hania.view;

import javax.swing.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class LoginFrame extends JFrame {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private JPanel loginPanel;
    private JTextField nameTextField;
    private JButton registerButton;
    private JComboBox panelComboBox;

    public LoginFrame() {
        super("Hello, Player!");
        setSize(WIDTH, HEIGHT);
        setContentPane(loginPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JComboBox getPanelComboBox() {
        return panelComboBox;
    }
}
