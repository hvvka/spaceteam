package com.hania;

import com.hania.model.PanelType;
import com.hania.model.Player;
import com.hania.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PlayerClientImpl implements PlayerClient {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerClientImpl.class);

    private String severName;

    public PlayerClientImpl(String serverName) {
        this.severName = serverName;
    }

    @Override
    public void registerPlayer(String name, PanelType panel) {
        try {
            User player = new Player(name, panel);
            Server server = (Server) getRegistry().lookup(severName);
            server.register(player);
            LOG.info("Player {} registered! (player)", name);
        } catch (RemoteException | NotBoundException e) {
            LOG.error("", e);
        }
    }

    @Override
    public boolean verifyTask(TaskGenerator.SingleTask selectedTask) throws RemoteException {
        Server remoteServer = getServer();
        TaskGenerator.SingleTask serverTask = remoteServer == null ? getExceptionTask() : remoteServer.sendCurrentTask();
        LOG.info("Selected task: panel={}, desc={}, ans={}", selectedTask.getPanelType(), selectedTask.getDescription(), selectedTask.getAnswer());
        LOG.info("Server task: panel={}, desc={}, ans={}", serverTask.getPanelType(), serverTask.getDescription(), serverTask.getAnswer());
        boolean isTaskCorrect = selectedTask.equals(serverTask);
        if (remoteServer != null) remoteServer.updateScore(isTaskCorrect);
        return isTaskCorrect;
    }

    private Server getServer() throws RemoteException {
        Server remoteServer = null;
        Registry registry = getRegistry();
        try {
            remoteServer = (Server) registry.lookup(severName);
            LOG.info("Server {} lookup succeed. (player)", severName);
        } catch (NotBoundException e) {
            LOG.error("", e);
        }
        return remoteServer;
    }

    private Registry getRegistry() throws RemoteException {
        return LocateRegistry.getRegistry(2099);
    }

    private TaskGenerator.SingleTask getExceptionTask() {
        return new TaskGenerator.SingleTask(PanelType.CAPTAIN, "Be patient,", "please");
    }
}
