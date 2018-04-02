package com.hania;

import com.hania.model.User;

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
    Set<User> showPlayers() throws RemoteException;

    /**
     * @param user to be deleted from the list
     * @throws RemoteException if registry could not be contacted
     */
    void kickOut(User user) throws RemoteException;

    /**
     * Accepts players' or captain's connections to server.
     * @param user that want to be registered
     * @throws RemoteException if registry could not be contacted
     */
    void register(User user) throws RemoteException;
}
