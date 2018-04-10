package com.hania;

import com.hania.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public interface CaptainClient extends Remote {

    /**
     * @return a setCaptain of all players from server
     * @throws RemoteException if registry could not be contacted
     */
    Set<User> getPlayers() throws RemoteException;

    /**
     * Enables to end a game or a round.
     * @throws RemoteException if registry could not be contacted
     */
    void endGame() throws RemoteException;

    /**
     * Gathers information about current score
     * @return a number of points for the crew
     * @throws RemoteException if registry could not be contacted
     */
    int getScore() throws RemoteException;

    /**
     * @return task for the captain view
     * @throws RemoteException if registry could not be contacted
     */
    String createTask() throws RemoteException;

    /**
     * Registers captain on the server.
     * @throws RemoteException if registry could not be contacted
     * @param name captain's name
     */
    void registerCaptain(String name) throws RemoteException;

    void kickOut(String name) throws RemoteException;
}
