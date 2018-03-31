package com.hania;

import com.hania.model.Captain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.Set;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class CaptainClientImpl implements CaptainClient {

    private static final Logger LOG = LoggerFactory.getLogger(CaptainClientImpl.class);

    private static final String HOST = "192.168.101.137";
    private static final int PORT = 1099;

    //    @Override
    public static Set getPlayers() {
        Server server = null;
        try {
            server = (Server) LocateRegistry.getRegistry(HOST, PORT).lookup("Server");
        } catch (RemoteException | NotBoundException e) {
            LOG.error("", e);
        }

        try {
            return server != null ? server.showPlayersList() : Collections.emptySet();
        } catch (RemoteException e) {
            LOG.error("", e);
        }
        return Collections.emptySet();
    }

    @Override
    public void startGame() {

    }

    @Override
    public void endGame() {

    }

    @Override
    public int countPoints() {
        return 0;
    }

    @Override
    public String createTask() {
        return null;
    }

    public static void main(String[] args) {
        LOG.info("players: {}", getPlayers());
    }

    @Override
    public void register() {
        try {
            Captain captain = new Captain("test");
            Captain remoteCaptain = (Captain) UnicastRemoteObject.exportObject(captain, 0);
            Server server = (Server) LocateRegistry.getRegistry(HOST, PORT).lookup("Server");
            server.register(remoteCaptain);
        } catch (RemoteException | NotBoundException e) {
            LOG.error("", e);
        }
    }
}