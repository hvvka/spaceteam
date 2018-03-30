package com.hania;

import com.hania.model.Captain;
import com.hania.model.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ServerImpl extends UnicastRemoteObject implements Server {

    private Team team;

    public ServerImpl() throws RemoteException {
        super();
        initTeam();
    }

    private void initTeam() {
        try {
            team = new Team();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Player> showList() {
        return team.getCrew();
    }

    @Override
    public void kickOut(Player player) {
        team.remove(player);
    }

    @Override
    public void register(Player player) {
        if (player instanceof Captain && !team.contains(player)) {
            registerCaptain(player);
        } else if (!team.contains(player)) {
            registerPlayer(player);
        }
    }

    private void registerCaptain(Player player) {
        team.set((Captain) player);
    }

    private void registerPlayer(Player player) {
        team.add(player);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ServerImpl server = (ServerImpl) o;

        return team != null ? team.equals(server.team) : server.team == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (team != null ? team.hashCode() : 0);
        return result;
    }
}

/*
    private static final String HOST = "host";
    private static final int PORT = 1099;

        try {
            LocateRegistry.createRegistry(PORT);
            Server server = new ServerImpl();
            Server remoteServer = (Server) UnicastRemoteObject.exportObject(server, PORT);
            LocateRegistry.getRegistry(HOST, PORT).rebind("Server", remoteServer);
        } catch (RemoteException e) {
            e.printStackTrace();
            exit(1);
        }
 */