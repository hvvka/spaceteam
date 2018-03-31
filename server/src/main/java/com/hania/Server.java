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
     * @throws RemoteException if registry could not be contacted
     */
    Set<Player> showPlayers() throws RemoteException;

    /**
     * @param player to be deleted from the list
     * @throws RemoteException if registry could not be contacted
     */
    void kickOut(Player player) throws RemoteException;

    /**
     * Accepts players' or captain's connections to server.
     * @param player that want to be registered
     * @throws RemoteException if registry could not be contacted
     */
    void register(Player player) throws RemoteException;
}
