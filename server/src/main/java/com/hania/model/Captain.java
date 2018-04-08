package com.hania.model;

import java.rmi.RemoteException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Captain extends Player {

    public Captain(String name) throws RemoteException {
        super(name, PanelType.CAPTAIN);
    }
}
