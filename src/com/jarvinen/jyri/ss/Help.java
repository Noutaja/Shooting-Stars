package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * The help screen. Consists of one image.
 */
public class Help implements ScreenElement {
    Sprite spriteHelp;

        public Help() {
            SS.LoadAsset(SS.helpImageFile);
            spriteHelp = new Sprite(SS.GetAsset(SS.helpImageFile));
            spriteHelp.setPosition(0, 0);
        }

    /**
     * This method is called every frame. Checks if any key is pressed.
     * @param delta The time between each frame.
     * @return True if a key is pressed.
     */
    public boolean update(float delta) {
        if(!GameController.Instance.pressedKeys.isEmpty()) {
            MyScreen ms = new MyScreen();
            ms.elements.add(new MainMenu());
            GameController.Instance.setScreen(ms);
        }

        return false;
    }

    /**
     * Draws the sprite.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    public void draw(Batch batch) {
        spriteHelp.draw(batch);
    }
}
