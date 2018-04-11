package com.hania.controller;

import com.hania.CaptainClient;
import com.hania.model.User;
import com.hania.view.CaptainFrame;
import com.hania.view.ErrorMessageUtil;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
class CaptainFrameController {

    private CaptainClient captainClient;
    private CaptainFrame captainFrame;
    private String name;

    private JTextField taskTextField;
    private JTextField scoreTextField;
    private JList playerList;
    private JButton kickOutButton;
    private JButton nextTaskButton;
    private JButton refreshButton;

    private DefaultListModel<String> model;
    private Set<User> players;

    CaptainFrameController(CaptainClient captainClient, String name) {
        this.name = name;
        this.captainClient = captainClient;
        initComponents();
        initListeners();
    }

    private void initComponents() {
        captainFrame = new CaptainFrame();
        captainFrame.addWindowListener(getWindowAdapter());
        taskTextField = captainFrame.getTaskTextField();
        scoreTextField = captainFrame.getScoreTextField();
        playerList = captainFrame.getPlayerList();
        kickOutButton = captainFrame.getKickOutButton();
        nextTaskButton = captainFrame.getNextTaskButton();
        refreshButton = captainFrame.getRefreshButton();
        initPlayerList();
    }

    private WindowAdapter getWindowAdapter() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Do you want to quit?");
                if (option == 0) {
                    kickYourselfOut();
                    System.exit(0);
                }
            }
        };
    }

    private void kickYourselfOut() {
        try {
            captainClient.kickOut(name);
        } catch (RemoteException e) {
            ErrorMessageUtil.show(e);
        }
    }

    private void initPlayerList() {
        model = new DefaultListModel<>();
        updatePlayerList();
        playerList.setModel(model);
    }

    private void initListeners() {
        nextTaskButton.addActionListener(e -> nextTask());

        kickOutButton.addActionListener(e -> {
            String player = getSelectedPlayer();
            kickYourselfOut(player);
            updatePlayerList();
        });

        refreshButton.addActionListener(e -> {
            int currentScore = fetchScore();
            scoreTextField.setText(Integer.toString(currentScore));
            updatePlayerList();
        });
    }

    private void nextTask() {
        if (!players.isEmpty()) {
            String task = fetchTask();
            taskTextField.setText(task);
        } else {
            initScheduler();
        }
        updatePlayerList();
    }

    private void initScheduler() {
        Runnable getSelectedAnswer = this::nextTask;
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(getSelectedAnswer, 20, 6, TimeUnit.SECONDS);
    }

    private String getSelectedPlayer() {
        String playerRow = playerList.getSelectedValue().toString();
        return playerRow.substring(playerRow.lastIndexOf('\t') + 1);
    }

    private void kickYourselfOut(String name) {
        try {
            captainClient.kickOut(name);
        } catch (RemoteException e) {
            ErrorMessageUtil.show(e);
        }
    }

    private void updatePlayerList() {
        try {
            players = captainClient.getPlayers();
            updateModel(players);
        } catch (RemoteException e) {
            ErrorMessageUtil.show(e);
        }
    }

    private void updateModel(Set<User> players) throws RemoteException {
        model.removeAllElements();
        for (User player : players) {
            model.addElement(player.getPanelType().toString().toLowerCase() + "\t\t\t" + player.getName());
        }
    }

    private int fetchScore() {
        int currentScore = -1;
        try {
            currentScore = captainClient.getScore();
        } catch (RemoteException e) {
            ErrorMessageUtil.show(e);
        }
        return currentScore;
    }

    private String fetchTask() {
        String task = "";
        try {
            task = captainClient.createTask();
        } catch (RemoteException e) {
            ErrorMessageUtil.show(e);
        }
        return task;
    }
}
