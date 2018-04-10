package com.hania.view;

import com.hania.PlayerClient;
import com.hania.TaskGenerator;
import com.hania.model.PanelType;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PlayerFrame extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 850;

    private PlayerClient playerClient;
    //    private User player;
    private String name;
    private PanelType panelType;
    private PlayerPanelBean playerPanelBean;

    public PlayerFrame(PlayerClient playerClient, String name, PanelType panelType) {
        super("Listen to captain's commands");
        this.playerClient = playerClient;
        this.name = name;
        this.panelType = panelType;
        initFrameProperties();
    }

    private void initFrameProperties() {
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initPlayerPanelBean();
        setVisible(true);
        verifyTaskPeriodically();
    }

    private void verifyTaskPeriodically() {
        Runnable getSelectedAnswer = () -> {
            TaskGenerator.SingleTask task = playerPanelBean.getSelectedAnswer();
//            System.out.println("A=" + task.getAnswer() + ", D=" + task.getDescription() + ", P=" + task.getPanelType());
            verifyTask(task);
        };
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(getSelectedAnswer, 20, 5, TimeUnit.SECONDS);
    }

    private void verifyTask(TaskGenerator.SingleTask task) {
        try {
            playerClient.verifyTask(task);
        } catch (RemoteException e) {
            ErrorMessageUtil.show(e);
        }
    }

    private void initPlayerPanelBean() {
        playerPanelBean = new PlayerPanelBean();
        playerPanelBean.setPlayer(name, panelType);
        playerPanelBean.setPlayerClient(playerClient);
        setContentPane(playerPanelBean);
    }
}
