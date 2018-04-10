package com.hania;

import com.hania.model.PanelType;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public interface PlayerClient extends Remote {

    /**
     * @param name  player's name
     * @param panel player's panel
     * @throws RemoteException if registry could not be contacted
     */
    void registerPlayer(String name, PanelType panel) throws RemoteException;

    void verifyTask(TaskGenerator.SingleTask selectedTask) throws RemoteException;

    void kickOut(String name) throws RemoteException;
}
