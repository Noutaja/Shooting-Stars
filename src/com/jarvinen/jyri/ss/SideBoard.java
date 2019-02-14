package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;

import static com.jarvinen.jyri.ss.SideBoardTextElement.*;


enum SideBoardTextElement{
    SCORE, BOMBS, LIVES
}

/**
 * Keeps track of UI visuals in credit.
 */
public class SideBoard {

    Credit credit;
    UI ui;
    Sprite spriteLife, spriteBomb, spriteLogo, spriteBorder, spriteTextBg;
    int bombs, lives, score;
    ArrayList<Integer> lines;

    float startWidth;

    BitmapFont font;
    ObjectMap<SideBoardTextElement, String> texts;

    /**
     * @param ui UI this Sideboard is a part of.
     */
    public SideBoard(UI ui){
        this.ui = ui;
        credit = ui.credit;

        SS.LoadAsset(SS.lifeFile);
        SS.LoadAsset(SS.bombFile);
        SS.LoadAsset(SS.sbLogoFile);
        SS.LoadAsset(SS.sbBorderFile);
        SS.LoadAsset(SS.sbTextBgFile);


        font = SS.GenerateFont(SS.fontFile, 24);
        texts = new ObjectMap<>();
        texts.put(SCORE, "Score:");
        texts.put(LIVES, "Lives:");
        texts.put(BOMBS, "Bombs:");

        lines = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            //create lines from top down.
            lines.add(credit.stage.STAGE_HEIGHT-i*30);
        }

        startWidth = ui.getSideBoardStart() + 20;

        spriteLife = new Sprite(SS.GetAsset(SS.lifeFile));
        spriteBomb = new Sprite(SS.GetAsset(SS.bombFile));
        spriteBorder = new Sprite(SS.GetAsset(SS.sbBorderFile));
        spriteBorder.setPosition(ui.getSideBoardStart(), 0);
        spriteLogo = new Sprite(SS.GetAsset(SS.sbLogoFile));
        spriteLogo.setPosition(ui.getSideBoardStart()+10, lines.get(18));
        spriteTextBg = new Sprite(SS.GetAsset(SS.sbTextBgFile));
    }

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     */
    public void update(float delta){
        lives = credit.player.lives;
        bombs = credit.player.ship.bombs;
        score = credit.player.score;
    }

    /**
     * Draws the SideBoard.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    public void draw(Batch batch){
        drawCosmetics(batch);
        drawScore(batch);
        drawLives(batch);
        drawBombs(batch);
    }

    private void drawScore(Batch batch){
        drawText(batch, SCORE, 1);
        drawText(batch, ""+score, 2);
    }

    private void drawLives(Batch batch){
        drawText(batch, LIVES, 4);
        for (int i = 0; i < lives; i++){
            spriteLife.setPosition(startWidth+(i*35), lines.get(6));
            spriteLife.draw(batch);
        }
    }

    private void drawBombs(Batch batch){
        drawText(batch, BOMBS, 7);
        for (int i = 0; i < bombs; i++){
            spriteBomb.setPosition(startWidth+(i*35), lines.get(9));
            spriteBomb.draw(batch);
        }
    }

    private void drawCosmetics(Batch batch){
        spriteBorder.draw(batch);
        spriteLogo.draw(batch);
        spriteTextBg.setPosition(startWidth, lines.get(2));
        spriteTextBg.draw(batch);
        spriteTextBg.setPosition(startWidth, lines.get(5));
        spriteTextBg.draw(batch);
        spriteTextBg.setPosition(startWidth, lines.get(8));
        spriteTextBg.draw(batch);
    }

    /**
     * @param batch SpriteBatch the sprite is drawn into.
     * @param text Predetermined text to draw.
     * @param line Vertical position for the text.
     */
    private void drawText(Batch batch, SideBoardTextElement text, int line){
        font.draw(batch, texts.get(text), startWidth, lines.get(line));
    }

    /**
     * @param batch SpriteBatch the sprite is drawn into.
     * @param text Any String of text.
     * @param line Vertical position for the text.
     */
    private void drawText(Batch batch, String text, int line){
        font.draw(batch, text, startWidth, lines.get(line));
    }
}
