package com.hania.view;

import javax.swing.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class LoginFrame extends JFrame {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private JPanel loginPanel;
    private JTextField name;
    private JButton registerButton;

    public LoginFrame() {
        super("Hello, Captain!");
        setSize(WIDTH, HEIGHT);
        setContentPane(loginPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JTextField getNameField() {
        return name;
    }
}
