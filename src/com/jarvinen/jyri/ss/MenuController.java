package com.jarvinen.jyri.ss;

import com.badlogic.gdx.Input;

import java.util.ArrayList;

/**
 * Controller for navigating menus
 */
public class MenuController {
    ArrayList<Integer> pressedKeys;
    Menu menu;


    /**
     * @param menu The menu this MenuController is assigned to.
     */
    public MenuController(Menu menu){
        this.menu = menu;
        pressedKeys = GameController.Instance.pressedKeys;
    }

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     */
    public void update(float delta) {

        if(pressedKeys.contains(Input.Keys.Z) || pressedKeys.contains(Input.Keys.ENTER)){
            menu.activateSelected();
            pressedKeys.remove(new Integer(Input.Keys.Z));
            pressedKeys.remove(new Integer(Input.Keys.ENTER));
        }
        else if(pressedKeys.contains(Input.Keys.DOWN)){
            if(menu.menuItems.indexOf(menu.selectedItem) == menu.menuItems.size()-1)
                menu.selectedItem = menu.menuItems.get(0);
            else
                menu.selectedItem = menu.menuItems.get(menu.menuItems.indexOf(menu.selectedItem)+1);

            pressedKeys.remove(new Integer(Input.Keys.DOWN));
        }
        else if(pressedKeys.contains(Input.Keys.UP)){
            if(menu.menuItems.indexOf(menu.selectedItem) == 0)
                menu.selectedItem = menu.menuItems.get(menu.menuItems.size()-1);
            else
                menu.selectedItem = menu.menuItems.get(menu.menuItems.indexOf(menu.selectedItem)-1);

            pressedKeys.remove(new Integer(Input.Keys.UP));
        }
    }
}
