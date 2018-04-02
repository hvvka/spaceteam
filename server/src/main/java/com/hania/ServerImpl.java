package com.hania;

import com.hania.model.Captain;
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
    public Set<User> showPlayers() {
        LOG.info("showPlayers fun");
        return team.getCrew();
    }

    @Override
    public void kickOut(User user) {
        team.remove(user);
    }

    @Override
    public void register(User user) {
        if (user instanceof Captain && !team.contains(user)) {
            registerCaptain(user);
        } else if (!team.contains(user)) {
            registerPlayer(user);
        }
    }

    private void registerCaptain(User user) {
        team.setCaptain(user);
    }

    private void registerPlayer(User user) {
        team.addPlayer(user);
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
