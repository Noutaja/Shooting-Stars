package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Elements drawn on MyScreen.
 */
public interface ScreenElement {
    boolean update(float delta);
    void draw(Batch batch);
}
