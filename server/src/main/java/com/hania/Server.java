package com.hania;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public interface Server extends Remote {

    /** todo delete?
     * Accepts players' connections to server.
     * @param player that want to be added to the list
     */
//    void acceptConnection(Player player) throws RemoteException;

    /**
     * @return the list of all the current players
     */
    Set<Player> showList() throws RemoteException;

    /**
     * @param player to be deleted from the list
     */
    void kickOut(Player player) throws RemoteException;

    /** todo delete?
     * Accepts players' or capitan's connections to server.
     * @param object that want to be registered
     */
    void register(Object object) throws RemoteException;
}
