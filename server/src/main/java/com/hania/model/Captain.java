package com.hania.model;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Captain extends Player {

    private int points;

    public Captain(String name) {
        super(name, Panel.CAPTAIN);
        points = 0;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
