package com.hania;

import com.hania.model.Captain;
import com.hania.model.Player;
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

    private Team team;

    ServerImpl() throws RemoteException {
        super();
        initTeam();
    }

    private void initTeam() {
        try {
            team = new Team();
        } catch (RemoteException e) {
            LOG.error("", e);
        }
    }

    @Override
    public Set<Player> showPlayersList() {
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
