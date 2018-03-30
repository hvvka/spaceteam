package com.hania;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class CaptainClientImpl implements CaptainClient {

    private static final String HOST = "host";
    private static final int PORT = 1099;

    @Override
    public List<PlayerClient> getPlayers() {
        try {
            Server server = (Server) LocateRegistry.getRegistry(HOST, PORT).lookup("Server");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
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

    @Override
    public void register() {
        try {
            CaptainClient captainClient = (CaptainClient) UnicastRemoteObject.exportObject(this, 0);
            Server server = (Server) LocateRegistry.getRegistry(HOST, PORT).lookup("Server");
//            server.register(captainClient);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
