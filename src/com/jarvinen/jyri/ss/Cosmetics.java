package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Stage effects with no gameplay impact.
 */
public interface Cosmetics {

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     * @return True when no longer on the screen
     */
    boolean update(float delta);

    /**
     * Draws the sprite.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    void draw(Batch batch);
}
