package com.jarvinen.jyri.ss;

import com.badlogic.gdx.math.Vector2;

/**
 * Pattern for the movement of enemy ships.
 */
public class MovementPattern {
    Vector2 speed;
    float xChange, yChange;
    Vector2 startPos;

    /**
     * Used when the ship is supposed to move.
     * @param x Starting vertical position the ship.
     */
    public MovementPattern(float x){
        startPos = new Vector2();
        startPos.x = x;
        startPos.y = 600;

        speed = new Vector2();
        speed.x = -0;
        speed.y = -90;
        xChange = horizontalSpeedChange(x);
        yChange = 0.15f;
    }

    /**
     * Used then the ship is stationary. DEBUGGING ONLY.
     * @param x Starting vertical position of the ship.
     * @param y Starting horizontal position of the ship.
     */
    public MovementPattern(float x, float y){
        startPos = new Vector2();
        startPos.x = x;
        startPos.y = y;

        speed = new Vector2();
        speed.x = -0;
        speed.y = -0;
        xChange = 0;
        yChange = -0;
    }

    /**
     * Used to calculate the horizontal speed change.
     * @param x Starting horizontal position.
     * @return Returns the horizontal speed change.
     */
    float horizontalSpeedChange(float x){
        x /= 60;
        float xSpeedChange;

        if(x < 5){
            xSpeedChange = x-5;
        }
        else xSpeedChange = x-4; //only values of -5 to -1 and 1 to 5 are allowed.

        xSpeedChange *= -0.1;

        return xSpeedChange;
    }
}
