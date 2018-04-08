package com.hania.controller;

import com.hania.CaptainClient;
import com.hania.CaptainClientImpl;
import com.hania.view.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class LoginFrameController {

    static final String SEVER_NAME = "SpaceteamServer";

    private LoginFrame loginFrame;
    private JButton registerButton;
    private CaptainClient captainClient;

    public LoginFrameController() {
        initComponents();
        initListeners();
    }

    private void initComponents() {
        loginFrame = new LoginFrame();
        registerButton = loginFrame.getRegisterButton();
    }

    private void initListeners() {
        registerButton.addActionListener(e -> {
            String name = loginFrame.getNameField().getText();
            registerCaptain(name);
            new CaptainFrameController(captainClient);
            loginFrame.dispose();
        });
    }

    private void registerCaptain(String name) {
        captainClient = new CaptainClientImpl(SEVER_NAME);
        try {
            captainClient.registerCaptain(name);
        } catch (RemoteException e1) {
            showErrorRegistryMessage(e1);
        }
    }

    private void showErrorRegistryMessage(RemoteException e) {
        String messageWrapped = "<html><body><p style='width: 400px;'>" + e.toString() + "</p></body></html>";
        String title = "Registry error!";
        JOptionPane.showMessageDialog(new Frame(), messageWrapped, title, JOptionPane.ERROR_MESSAGE);
    }
}
