package com.hania.controller;

import com.hania.PlayerClient;
import com.hania.PlayerClientImpl;
import com.hania.model.PanelType;
import com.hania.model.Player;
import com.hania.model.User;
import com.hania.view.ErrorMessageUtil;
import com.hania.view.LoginFrame;
import com.hania.view.PlayerFrame;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.Arrays;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class LoginFrameController {

    static final String SEVER_NAME = "SpaceteamServer";

    private LoginFrame loginFrame;
    private JTextField nameTextField;
    private JButton registerButton;
    private JComboBox panelComboBox;
    private PlayerClient playerClient;
    private User player;

    public LoginFrameController() {
        initComponents();
        initListeners();
    }

    private void initComponents() {
        player = null;
        loginFrame = new LoginFrame();
        nameTextField = loginFrame.getNameTextField();
        registerButton = loginFrame.getRegisterButton();
        initPanelComBox();
    }

    private void initPanelComBox() {
        panelComboBox = loginFrame.getPanelComboBox();
        Arrays.stream(PanelType.values()).forEach(p -> {
            if (p != PanelType.CAPTAIN) panelComboBox.addItem(p);
        });
    }

    private void initListeners() {
        registerButton.addActionListener(e -> {
            String name = loginFrame.getNameTextField().getText();
            PanelType panel = (PanelType) panelComboBox.getSelectedItem();
            registerPlayer(name, panel);
            new PlayerFrame(playerClient, player);
            loginFrame.dispose();
        });
    }

    private void registerPlayer(String name, PanelType panel) {
        playerClient = new PlayerClientImpl(SEVER_NAME);
        try {
            player = new Player(name, panel);
            playerClient.registerPlayer(name, panel);
        } catch (RemoteException e) {
            ErrorMessageUtil.show(e);
        }
    }
}
