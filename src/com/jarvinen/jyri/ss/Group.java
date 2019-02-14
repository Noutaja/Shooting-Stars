package com.jarvinen.jyri.ss;

/**
 * A group of enemies read from the stage file.
 */
public class Group {
    int size;
    int spawnPoint;

    /**
     * @param numberOfEnemies How large the group is.
     * @param x Spawn location.
     */
    public Group(int numberOfEnemies, int x) {

        this.size = numberOfEnemies;
        this.spawnPoint = x;
    }
}
