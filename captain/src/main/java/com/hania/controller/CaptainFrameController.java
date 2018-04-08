package com.hania.controller;

import com.hania.CaptainClient;
import com.hania.model.User;
import com.hania.view.CaptainFrame;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
class CaptainFrameController {

    private CaptainClient captainClient;
    private CaptainFrame captainFrame;

    private JTextField taskTextField;
    private JTextField scoreTextField;
    private JList playerList;
    private JButton kickOutButton;
    private JButton nextTaskButton;
    private JButton refreshButton;

    private DefaultListModel<String> model;

    CaptainFrameController(CaptainClient captainClient) {
        this.captainClient = captainClient;
        initComponents();
        initListeners();
    }

    private void initComponents() {
        captainFrame = new CaptainFrame();
        taskTextField = captainFrame.getTaskTextField();
        scoreTextField = captainFrame.getScoreTextField();
        playerList = captainFrame.getPlayerList();
        kickOutButton = captainFrame.getKickOutButton();
        nextTaskButton = captainFrame.getNextTaskButton();
        refreshButton = captainFrame.getRefreshButton();
        initPlayerList();
    }

    private void initPlayerList() {
        model = new DefaultListModel<>();
        updatePlayerList();
        playerList.setModel(model);
    }

    private void initListeners() {
        nextTaskButton.addActionListener(e -> {
            String task = fetchTask();
            taskTextField.setText(task);
            updatePlayerList();
        });

        kickOutButton.addActionListener(e -> {
            String player = getSelectedPlayer();
            kickOutPlayer(player);
            updatePlayerList();
        });

        refreshButton.addActionListener(e -> {
            int currentScore = fetchScore();
            scoreTextField.setText(Integer.toString(currentScore));
        });
    }

    private String getSelectedPlayer() {
        String playerRow = playerList.getSelectedValue().toString();
        return playerRow.substring(playerRow.lastIndexOf('\t') + 1);
    }

    private void kickOutPlayer(String name) {
        try {
            captainClient.kickOutPlayer(name);
        } catch (RemoteException e) {
            showErrorMessage(e);
        }
    }

    private void updatePlayerList() {
        Set<User> players;
        try {
            players = captainClient.getPlayers();
            updateModel(players);
        } catch (RemoteException e) {
            showErrorMessage(e);
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
            showErrorMessage(e);
        }
        return currentScore;
    }

    private String fetchTask() {
        String task = "";
        try {
            task = captainClient.createTask();
        } catch (RemoteException e) {
            showErrorMessage(e);
        }
        return task;
    }

    private void showErrorMessage(RemoteException e) {
        String messageWrapped = "<html><body><p style='width: 400px;'>" + e.toString() + "</p></body></html>";
        String title = "Remote error!";
        JOptionPane.showMessageDialog(new Frame(), messageWrapped, title, JOptionPane.ERROR_MESSAGE);
    }
}
