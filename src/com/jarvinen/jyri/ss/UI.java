package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * UI of the main game. Holds a reference to Credit and has a SideBoard.
 */
public class UI implements ScreenElement {
    Credit credit;
    SideBoard sideBoard;

    public UI(Credit credit){
        this.credit = credit;
        sideBoard = new SideBoard(this);

    }

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     * @return Not used.
     */
    public boolean update(float delta){
        sideBoard.update(delta);
        return false;
    }

    /**
     * Draws the screen.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    public void draw(Batch batch){
        sideBoard.draw(batch);
    }

    /**
     * @return Returns the x coordinate where the stage ends.
     */
    float getSideBoardStart(){
        return credit.stage.STAGE_WIDTH;
    }
}
