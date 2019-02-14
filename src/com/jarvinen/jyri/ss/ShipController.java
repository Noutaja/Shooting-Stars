package com.jarvinen.jyri.ss;

import com.badlogic.gdx.Input;

import java.util.ArrayList;

/**
 * Controller for the PlayerShip.
 */
public class ShipController {
    Player player;
    ArrayList<Integer> pressedKeys;
    PlayerShip ship;

    /**
     * @param player Controlling player.
     */
    public ShipController(Player player) {
        this.player = player;
        ship = player.ship;
        pressedKeys = GameController.Instance.pressedKeys;
    }

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     */
    public void update(float delta){
        if(pressedKeys.contains(Input.Keys.LEFT)){
            ship.move(Direction.LEFT, delta, false);
        }
        if(pressedKeys.contains(Input.Keys.RIGHT)){
            ship.move(Direction.RIGHT, delta, false);
        }
        if(pressedKeys.contains(Input.Keys.UP)){
            ship.move(Direction.UP, delta, false);
        }
        if(pressedKeys.contains(Input.Keys.DOWN)){
            ship.move(Direction.DOWN, delta, false);
        }
        if(pressedKeys.contains(Input.Keys.Z)){
            ship.fire();
        }
        if(pressedKeys.contains(Input.Keys.X)){
            ship.bomb();
        }

        if(pressedKeys.contains(Input.Keys.SHIFT_LEFT)){
            ship.focused = true;
        }
        else
            ship.focused = false;
    }
}
