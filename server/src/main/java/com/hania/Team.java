package com.hania;

import com.hania.model.Captain;
import com.hania.model.Player;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Team extends UnicastRemoteObject implements Serializable {

    private Set<Player> crew;

    private Captain captain;

    Team() throws RemoteException {
        crew = new HashSet<>();
    }

    public Set<Player> getCrew() {
        return crew;
    }

    public void set(Captain captain) {
        this.captain = captain;
    }

    public void add(Player player) {
        crew.add(player);
    }

    public void remove(Player player) {
        crew.remove(player);
    }

    public boolean contains(Player player) {
        return crew.contains(player) || player.equals(captain);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Team team = (Team) o;

        if (crew != null ? !crew.equals(team.crew) : team.crew != null) return false;
        return captain != null ? captain.equals(team.captain) : team.captain == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (crew != null ? crew.hashCode() : 0);
        result = 31 * result + (captain != null ? captain.hashCode() : 0);
        return result;
    }
}
