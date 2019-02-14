package com.jarvinen.jyri.ss;

/**
 * Item in a menu
 */
public class MenuItem {
    String text;
    MenuAction action;

    /**
     * @param text The text that is shown.
     * @param action The action of the MenuItem
     */
    public MenuItem(String text, MenuAction action){
        this.text = text;
        this.action = action;
    }

}
