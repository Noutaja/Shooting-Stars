package com.jarvinen.jyri.ss;

import com.badlogic.gdx.Screen;

import java.util.ArrayList;

/**
 * The main game controlling class.
 */
public class GameController {
    static GameController Instance;

    SS game;
    MyScreen screen;
    MyInputProcessor ip;
    ArrayList<Integer> pressedKeys;

    Credit credit;

    /**
     * @param game Used for changing screens.
     */
    public GameController(SS game) {
        Instance = this;
        this.game = game;
        ip = new MyInputProcessor();
        pressedKeys = ip.pressedKeys;
        MyScreen ms = new MyScreen();
        ms.elements.add(new MainMenu());
        setScreen(ms);
    }

    public void setScreen(MyScreen screen){
        this.screen = screen;
        game.setScreen(screen);
    }

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     */
    public void update(float delta){
        if(credit != null){
            boolean gameEnd = credit.update(delta);
            if(gameEnd){
                pressedKeys.clear();

                MyScreen ms = new MyScreen();
                ms.elements.add(new HighScores(credit.player.score));
                setScreen(ms);

                credit = null;
            }
        }
    }
}
