package com.hania;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class CaptainImpl implements Captain {

    private static final String HOST = "host";
    private static final int PORT = 1099;

    @Override
    public List<Player> getPlayers() {
        try {
            Server server = (Server) LocateRegistry.getRegistry(HOST, PORT).lookup("Server");
        } catch (RemoteException | NotBoundException e1) {
            e1.printStackTrace();
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
            Captain captain = (Captain) UnicastRemoteObject.exportObject(this, 0);
            Server server = (Server) LocateRegistry.getRegistry(HOST, PORT).lookup("Server");
            server.register(captain);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
