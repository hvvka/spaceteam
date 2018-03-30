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

    public boolean contains(Object object) {
        return !crew.contains(object) && !captain.equals(object);
    }
}
