package com.jarvinen.jyri.ss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

/**
 * Keeps track of pressed keys.
 */
public class MyInputProcessor implements InputProcessor {
    ArrayList<Integer> pressedKeys;

    public MyInputProcessor(){
        pressedKeys = new ArrayList<>();
        Gdx.input.setInputProcessor(this);
    }

    /**
     * Adds the keycode of the pressed key to pressedKeys.
     * @param keycode keycode of the pressed key.
     * @return Not used.
     */
    @Override
    public boolean keyDown(int keycode) {
        pressedKeys.add(keycode);
        return true;
    }

    /**
     * Removes the keycode of the pressed key from pressedKeys.
     * @param keycode keycode of the pressed key
     * @return Not used.
     */
    @Override
    public boolean keyUp(int keycode) {
        for (int i = 0; i < pressedKeys.size(); i++) {
            int key = pressedKeys.get(i);
            if (key == keycode) {
                pressedKeys.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
