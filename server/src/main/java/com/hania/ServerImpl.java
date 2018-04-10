package com.hania;

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
        return team.getCrew();
    }

    @Override
    public void kickOut(String name) {
        team.removeUser(name);
        LOG.info("Kicked out player.name={}", name);
    }

    @Override
    public void register(User user) throws RemoteException {
        if (user.getPanelType() == PanelType.CAPTAIN) {
            registerCaptain(user);
            LOG.info("New Captain (name={}) registered. (server)", user.getName());
        } else if (!team.contains(user)) {
            registerPlayer(user);
            createPlayerTasks(user.getPanelType());
            LOG.info("New Player (name={}, panel={}) registered. (server)", user.getName(), user.getPanelType());
        }
    }

    private void registerCaptain(User user) {
        team.setCaptain(user);
    }

    private void registerPlayer(User user) {
        team.addPlayer(user);
    }

    private void createPlayerTasks(PanelType panelType) {
        taskGenerator.initTasks(panelType);
    }

    @Override
    public TaskGenerator.SingleTask createNewTask() {
        currentTask = taskGenerator.getTask();
        LOG.info("SendTask – panel={} desc={} ans={}. (server)",
                currentTask.getPanelType(), currentTask.getDescription(), currentTask.getAnswer());
        return currentTask;
    }

    @Override
    public TaskGenerator.SingleTask sendCurrentTask() {
        return currentTask;
    }

    @Override
    public void updateScore(int isTaskCorrect) {
        if (isTaskCorrect == 1) score++;
        else score--;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ServerImpl server = (ServerImpl) o;

        if (score != server.score) return false;
        if (taskGenerator != null ? !taskGenerator.equals(server.taskGenerator) : server.taskGenerator != null)
            return false;
        if (team != null ? !team.equals(server.team) : server.team != null) return false;
        return currentTask != null ? currentTask.equals(server.currentTask) : server.currentTask == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (taskGenerator != null ? taskGenerator.hashCode() : 0);
        result = 31 * result + (team != null ? team.hashCode() : 0);
        result = 31 * result + (currentTask != null ? currentTask.hashCode() : 0);
        result = 31 * result + score;
        return result;
    }
}
