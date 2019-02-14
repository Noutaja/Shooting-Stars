package com.jarvinen.jyri.ss;

/**
 * Player of the game.
 */
public class Player {
    PlayerShip ship;
    ShipController controller;

    int lives;
    int score;

    String name;

    public Player(){
        lives = 3;

        ship = new PlayerShip(this);
        controller = new ShipController(this);

        name = "";
    }

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     * @return True if lives are less than 0.
     */
    public boolean update(float delta){
        controller.update(delta);
        if(lives < 0)
            return true;

        return false;
    }
}
