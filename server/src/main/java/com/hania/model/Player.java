package com.hania.model;

import java.io.Serializable;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Player implements User, Serializable {

    private final String name;

    private Panel panel;

    public Player(String name, Panel panel) {
        this.name = name;
        this.panel = panel;
    }
}
