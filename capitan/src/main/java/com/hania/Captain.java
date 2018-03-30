package com.hania;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public interface Captain extends Remote {

    //pobiera listę graczy z serwera
    List<Player> getPlayers() throws RemoteException;

    //umożliwia rozpoczęcie
    void startGame() throws RemoteException;

    // i kończenie gry
    void endGame() throws RemoteException;

    //zbiera informacje o działaniu aplikacji załogi podliczając punkty
    int countPoints() throws RemoteException;

    //generuje kolejne (wykonywalne komendy)
    String createTask() throws RemoteException;

    void register();
}
