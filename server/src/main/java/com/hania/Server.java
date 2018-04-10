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
     * @param name to be deleted from the list
     * @throws RemoteException if registry could not be contacted
     */
    void kickOut(String name) throws RemoteException;

    /**
     * Accepts players' or captain's connections to server.
     * @param user that want to be registered
     * @throws RemoteException if registry could not be contacted
     */
    void register(User user) throws RemoteException;

    /**
     * @return task for the captain
     * @throws RemoteException if registry could not be contacted
     */
    TaskGenerator.SingleTask createNewTask() throws RemoteException;

    /**
     * @return task for the crew
     * @throws RemoteException if registry could not be contacted
     */
    TaskGenerator.SingleTask sendCurrentTask() throws RemoteException;

    void updateScore(int isTaskCorrect) throws RemoteException;

    int getScore() throws RemoteException;
}
