package com.hania.view;

import com.hania.PlayerClient;
import com.hania.model.User;

import javax.swing.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PlayerFrame extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;

    private PlayerClient playerClient;
    private User player;

    public PlayerFrame(PlayerClient playerClient, User player) {
        super("Listen to captain's commands");
        this.playerClient = playerClient;
        this.player = player;
        initFrameProperties();
    }

    private void initFrameProperties() {
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initPlayerPanelBean();
        setVisible(true);
    }

    private void initPlayerPanelBean() {
        PlayerPanelBean playerPanelBean = new PlayerPanelBean();
        playerPanelBean.setPlayer(player);
        playerPanelBean.setPlayerClient(playerClient);
        setContentPane(playerPanelBean);
    }
}
