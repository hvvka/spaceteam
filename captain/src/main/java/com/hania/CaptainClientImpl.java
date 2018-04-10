package com.hania;

import com.hania.model.Captain;
import com.hania.model.PanelType;
import com.hania.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collections;
import java.util.Set;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class CaptainClientImpl implements CaptainClient {

    private static final Logger LOG = LoggerFactory.getLogger(CaptainClientImpl.class);

    private String severName;

    private int score;

    public CaptainClientImpl(String serverName) {
        this.severName = serverName;
        this.score = 0;
    }

    @Override
    public Set<User> getPlayers() throws RemoteException {
        Server remoteServer = getServer();
        return fetchPlayers(remoteServer);
    }

    private Server getServer() throws RemoteException {
        Server remoteServer = null;
        Registry registry = getRegistry();
        try {
            remoteServer = (Server) registry.lookup(severName);
            LOG.info("Server {} lookup succeed. (captain)", severName);
        } catch (NotBoundException e) {
            LOG.error("", e);
        }
        return remoteServer;
    }

    private Registry getRegistry() throws RemoteException {
        return LocateRegistry.getRegistry(2099);
    }

    private Set<User> fetchPlayers(Server server) throws RemoteException {
        return server != null ? server.showPlayers() : Collections.emptySet();
    }

    @Override
    public void endGame() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int getScore() throws RemoteException {
        Server remoteServer = getServer();
        score = remoteServer == null ? score : remoteServer.getScore();
        return score;
    }

    @Override
    public void registerCaptain(String name) {
        try {
            Captain captain = new Captain(name);
            Server server = (Server) getRegistry().lookup(severName);
            server.register(captain);
            LOG.info("Captain {} registered! (captain)", name);
        } catch (RemoteException | NotBoundException e) {
            LOG.error("", e);
        }
    }

    @Override
    public void kickOut(String name) throws RemoteException {
        Server remoteServer = getServer();
        if (remoteServer != null) {
            remoteServer.kickOut(name);
            LOG.info("Player {} got kicked out.", name);
        } else {
            LOG.error("KickOut – server's null. (captain)");
        }
    }

    @Override
    public String createTask() throws RemoteException {
        Server remoteServer = getServer();
        return getTextTask(remoteServer);
    }

    private String getTextTask(Server remoteServer) throws RemoteException {
        TaskGenerator.SingleTask task = getTask(remoteServer);
        return getPanelTypeString(task).substring(0, 1).toUpperCase() + getPanelTypeString(task).substring(1).toLowerCase() + ", "
                + task.getDescription().toLowerCase() + " " + task.getAnswer().toLowerCase() + "!";
    }

    private String getPanelTypeString(TaskGenerator.SingleTask task) {
        return task.getPanelType().toString();
    }

    private TaskGenerator.SingleTask getTask(Server server) throws RemoteException {
        return server != null ? server.createNewTask() : getExceptionTask();
    }

    private TaskGenerator.SingleTask getExceptionTask() {
        return new TaskGenerator.SingleTask(PanelType.CAPTAIN, "Be patient,", "please");
    }
}
