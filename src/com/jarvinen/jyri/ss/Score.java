package com.jarvinen.jyri.ss;

/**
 * Holds the points and the name of the high score entry.
 */
public class Score {
    String name;
    int points;

    /**
     * @param name Name of the entry
     * @param points Points of the entry.
     */
    public Score(String name, int points) {
        this.name = name;
        this.points = points;
    }
}
