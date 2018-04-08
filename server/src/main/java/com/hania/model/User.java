package com.hania.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public interface User extends Remote {

    String getName() throws RemoteException;

    PanelType getPanelType() throws RemoteException;
}
