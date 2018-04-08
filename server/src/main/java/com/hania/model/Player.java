package com.hania.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Player extends UnicastRemoteObject implements User, Serializable {

    private final String name;

    private PanelType panelType;

    public Player(String name, PanelType panelType) throws RemoteException {
        super();
        this.name = name;
        this.panelType = panelType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PanelType getPanelType() {
        return panelType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Player player = (Player) o;

        if (name != null ? !name.equals(player.name) : player.name != null) return false;
        return panelType == player.panelType;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (panelType != null ? panelType.hashCode() : 0);
        return result;
    }
}
