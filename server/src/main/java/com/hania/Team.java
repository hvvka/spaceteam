package com.hania;

import com.hania.model.User;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Team extends UnicastRemoteObject implements Serializable {

    private Set<User> crew;

    private User captain;

    Team() throws RemoteException {
        crew = new HashSet<>();
    }

    public Set<User> getCrew() {
        return crew;
    }

    public void setCaptain(User user) {
        this.captain = user;
    }

    public void addPlayer(User user) {
        crew.add(user);
    }

    public void removePlayer(User user) {
        crew.remove(user);
    }

    public boolean contains(User user) {
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
