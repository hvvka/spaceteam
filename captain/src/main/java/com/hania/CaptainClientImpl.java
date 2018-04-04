package com.hania;

import com.hania.model.Captain;
import com.hania.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.Set;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class CaptainClientImpl implements CaptainClient {

    private static final Logger LOG = LoggerFactory.getLogger(CaptainClientImpl.class);

    private String severName;

    CaptainClientImpl(String serverName) {
        this.severName = serverName;
    }

    @Override
    public Set getPlayers() {
        Server remoteServer = getServer();
        return fetchPlayers(remoteServer);
    }

    private Server getServer() {
        Server remoteServer = null;
        Registry registry = getRegistry();
        try {
            remoteServer = (Server) registry.lookup(severName); //FIXME nullpointer
            LOG.info("Server {} lookup succeed. (captain side)", severName);
        } catch (RemoteException | NotBoundException e) {
            LOG.error("", e);
        }
        return remoteServer;
    }

    private Registry getRegistry() {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry();
        } catch (RemoteException e) {
            LOG.error("", e);
        }
        return registry;
    }

    private Set fetchPlayers(Server server) {
        try {
            return server != null ? server.showPlayers() : Collections.emptySet();
        } catch (RemoteException e) {
            LOG.error("", e);
        }
        return Collections.emptySet();
    }

    @Override
    public void startGame() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void endGame() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int countPoints() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void registerCaptain(String name) {
        try {
            User captain = new Captain(name);
            User remoteCaptain = (User) UnicastRemoteObject.exportObject(captain, 0);
            Server server = (Server) LocateRegistry.getRegistry().lookup(severName);
            server.register(remoteCaptain);
            LOG.info("Captain {} registered! (captain side)", name);
        } catch (RemoteException | NotBoundException e) {
            LOG.error("", e);
        }
    }

    @Override
    public String createTask() {
        Server remoteServer = getServer();
        return getTask(remoteServer);
    }

    private String getTask(Server server) {
        try {
            return server != null ? server.sendTask() : "Be patient, please.";
        } catch (RemoteException e) {
            LOG.error("", e);
        }
        return "";
    }
}
