package com.hania;

import com.hania.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Team extends UnicastRemoteObject implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(Team.class);

    private Set<User> crew;

    private User captain;

    Team() throws RemoteException {
        captain = null;
        crew = new HashSet<>();
    }

    Set<User> getCrew() {
        return crew;
    }

    public void setCaptain(User user) {
        this.captain = user;
    }

    void addPlayer(User user) {
        crew.add(user);
    }

    void removeUser(String name) {
        if (captain != null) {
            String captainName = getCaptainName();
            if (captainName.equals(name)) {
                captain = null;
            }
        } else {
            removePlayer(name);
        }
    }

    private void removePlayer(String name) {
        Optional<User> user = crew.stream().filter(p -> checkPlayerPresence(name, p)).findFirst();
        if (user.isPresent()) {
            crew.remove(user.get());
        } else {
            throw new NoSuchElementException("Selected player doesn't exist.");
        }
    }

    private String getCaptainName() {
        String captainName = "";
        try {
            captainName = captain.getName();
        } catch (RemoteException e) {
            LOG.error("", e);
        }
        return captainName;
    }

    private boolean checkPlayerPresence(String name, User p) {
        try {
            return p.getName().equals(name);
        } catch (RemoteException e) {
            LOG.error("", e);
        }
        return false;
    }

    boolean contains(User user) {
        return user.equals(captain) || crew.contains(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Team team = (Team) o;

        return (crew != null ? crew.equals(team.crew) : team.crew == null) && (captain != null ? captain.equals(team.captain) : team.captain == null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (crew != null ? crew.hashCode() : 0);
        result = 31 * result + (captain != null ? captain.hashCode() : 0);
        return result;
    }
}
