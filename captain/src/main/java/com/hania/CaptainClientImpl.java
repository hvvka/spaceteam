package com.hania;

import com.hania.model.Captain;
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

    //todo delete
    public static void main(String[] args) {
        LOG.info("players: {}", getPlayers());
    }

    //    @Override
    public static Set getPlayers() {
        Server server = getServer();
        LOG.info("lookup succeed");
        return fetchPlayers(server);
    }

    private static Server getServer() {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry();
        } catch (RemoteException e) {
            LOG.error("", e);
        }

        Server remoteServer = null;
        String serverName = "SpaceteamServer";
        try {
            remoteServer = (Server) registry.lookup(serverName);
        } catch (RemoteException | NotBoundException e) {
            LOG.error("", e);
        }
        return remoteServer;
    }

    private static Set fetchPlayers(Server server) {
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
    public void register() {
        try {
            Captain captain = new Captain("test");
            Captain remoteCaptain = (Captain) UnicastRemoteObject.exportObject(captain, 0);
            Server server = (Server) LocateRegistry.getRegistry().lookup("Server");
            server.register(remoteCaptain);
        } catch (RemoteException | NotBoundException e) {
            LOG.error("", e);
        }
    }

    @Override
    public String createTask() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
