package com.hania;

import com.hania.model.Captain;
import com.hania.model.PanelType;
import com.hania.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ServerImpl extends UnicastRemoteObject implements Server {

    private static final Logger LOG = LoggerFactory.getLogger(ServerImpl.class);

    private TaskGenerator taskGenerator;
    private Team team;
    private TaskGenerator.SingleTask currentTask;
    private int score;

    ServerImpl() throws RemoteException {
        super();
        initTeam();
        taskGenerator = new TaskGenerator();
        currentTask = new TaskGenerator.SingleTask(PanelType.CAPTAIN, "Yet no task", "");
        this.score = 0;
    }

    private void initTeam() {
        try {
            team = new Team();
        } catch (RemoteException e) {
            LOG.error("", e);
        }
    }

    @Override
    public Set<User> showPlayers() {
        LOG.info("ShowPlayers function invocation. (server side)");
        return team.getCrew();
    }

    @Override
    public void kickOut(User user) {
        team.remove(user);
    }

    @Override
    public void register(User user) {
        if (user instanceof Captain && !team.contains(user)) {
            registerCaptain(user);
            LOG.info("New Captain registered. (server side)");
        } else if (!team.contains(user)) {
            registerPlayer(user);
            LOG.info("New Player registered. (server side)");
        }
    }

    private void registerCaptain(User user) {
        team.setCaptain(user);
    }

    private void registerPlayer(User user) {
        team.addPlayer(user);
    }

    @Override
    public TaskGenerator.SingleTask createNewTask() {
        LOG.info("SendTask function invocation. (server side)");
        currentTask = taskGenerator.getTask();
        return currentTask;
    }

    @Override
    public TaskGenerator.SingleTask sendCurrentTask() {
        return currentTask;
    }

    @Override
    public void updateScore(boolean isTaskCorrect) {
        if (isTaskCorrect) score++;
        else score--;
    }

    @Override
    public int getScore() {
        return score;
    }
}
