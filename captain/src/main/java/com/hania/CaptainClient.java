package com.hania;

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
    Set getPlayers() throws RemoteException;

    /**
     * Enables to start a game or a round.
     * @throws RemoteException if registry could not be contacted
     */
    void startGame() throws RemoteException;

    /**
     * Enables to end a game or a round.
     * @throws RemoteException if registry could not be contacted
     */
    void endGame() throws RemoteException;

    /**
     * Gathers information about application's performance.
     * @return a number of points for the crew
     * @throws RemoteException if registry could not be contacted
     */
    int countPoints() throws RemoteException;

    /**
     * @return executable task for the crew
     * @throws RemoteException if registry could not be contacted
     */
    TaskGenerator.SingleTask createTask() throws RemoteException;

    /**
     * Registers captain on the server.
     * @throws RemoteException if registry could not be contacted
     * @param name captain's name
     */
    void registerCaptain(String name) throws RemoteException;
}
