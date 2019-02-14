package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Main menu of the game.
 */
public class MainMenu implements ScreenElement {
    Menu menu;
    Sprite spriteLogo;

    public MainMenu(){
        SS.LoadAsset(SS.mainLogoFile);
        menu = new Menu(320, 50, 280, 10, MenuType.MAINMENU);
        spriteLogo = new Sprite(SS.GetAsset(SS.mainLogoFile));
        spriteLogo.setPosition(190,350);
    }

    /**
     * Draws the screen.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    public void draw(Batch batch){
        menu.draw(batch);
        spriteLogo.draw(batch);
    }

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     * @return Not used.
     */
    public boolean update(float delta){
        menu.update(delta);
        return false;
    }
}
