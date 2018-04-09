package com.hania.view;

import com.hania.PlayerClient;
import com.hania.model.PanelType;
import com.hania.model.User;

import javax.swing.*;
import java.rmi.RemoteException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PlayerFrame extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private PlayerClient playerClient;
    private User player;

    public PlayerFrame(PlayerClient playerClient, User player) {
        super("Listen to captain's commands");
        this.playerClient = playerClient;
        this.player = player;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initPlayerPanelBean();
        setVisible(true);
    }

    private void initPlayerPanelBean() {
        String name = "";
        PanelType panelType = null;
        try {
            name = player.getName();
            panelType = player.getPanelType();
        } catch (RemoteException e) {
            ErrorMessageUtil.show(e);
        }

//        setContentPane();
    }
}
