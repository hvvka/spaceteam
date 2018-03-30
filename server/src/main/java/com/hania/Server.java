package com.hania;

import com.hania.model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public interface Server extends Remote {

    /**
     * @return the list of all the current players
     */
    Set<Player> showList() throws RemoteException;

    /**
     * @param player to be deleted from the list
     */
    void kickOut(Player player) throws RemoteException;

    /**
     * Accepts players' or capitan's connections to server.
     * @param player that want to be registered
     */
    void register(Player player) throws RemoteException;
}
