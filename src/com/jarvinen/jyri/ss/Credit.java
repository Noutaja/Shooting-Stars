package com.jarvinen.jyri.ss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;



/**
 * The main gameplay class.
 */
public class Credit implements ScreenElement {
    static Credit Instance;

    Music music;

    Menu menu;

    Stage stage;
    int stages, currentStage;
    Player player;
    ArrayList<Integer> pressedKeys;

    /**
     *
     */
    public Credit(){
        Instance = this;

        MyScreen ms = new MyScreen();
        ms.elements.add(this);
        ms.elements.add(new UI(this));
        GameController.Instance.setScreen(ms);

        stages = 2;
        currentStage = 1;

        player = new Player();

        pressedKeys = GameController.Instance.pressedKeys;

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/NES_WANNABE4.mp3"));
        music.setLooping(true);
        music.play();
    }

    /**
     * Sets the player for this credit and the stage
     * @param player
     */
    void setPlayer(Player player){
        this.player = player;
        stage.setPlayer(player);
    }

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     * @return True if the game is over.
     */
    public boolean update(float delta){
        //check for pause menu and update it if in it.
        if(pressedKeys.contains(Input.Keys.ESCAPE) && menu == null) {
            menu = new Menu(200, 50, 500, 16, MenuType.PAUSEMENU);
        }
        if(menu != null) {
            menu.update(delta);
        }
        //otherwise update the stage
        else {
            boolean gameEnd = updateStage(delta);
            if(gameEnd){
                music.stop();
            }
            return gameEnd;
        }
        return false;
    }

    /**
     * Draws the sprite.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    public void draw(Batch batch){
        if(stage != null){
            stage.draw(batch);
        }
        if(menu != null) {
            menu.draw(batch);
        }
    }

    /**
     * This method is called every frame. Updates and switches the current stage.
     * @param delta The time between each frame.
     * @return True if the stage is over and it's the last stage or when the player runs out of lives.
     */
    private boolean updateStage(float delta){
        boolean gameEnd = false;
        Stage prevStage = null;
        if(stage != null){
            //update the stage.
            if(stage.update(delta)){
                //stage is over.
                prevStage = stage;
                stage = null;
                currentStage++;
                if(currentStage > stages){
                    gameEnd = true;
                }
            }
            if(player.update(delta))
                gameEnd = true;
        }
        if(stage == null && currentStage <= stages){
            if(prevStage != null){
                //create a new stage copying over fields from the last.
                startStage(currentStage, prevStage);
            }
            else {
                //create a stage from scratch. Usually on new game.
                startStage(currentStage);
            }
        }
        return gameEnd;
    }

    /**
     * Starts a new stage from scratch.
     * @param stageNumber Number of the stage to look for the correct stage file.
     */
    public void startStage(int stageNumber){
        stage = new Stage(stageNumber);
            setPlayer(new Player());
            stage.spawnShip(player.ship);

    }

    /**
     * Starts a new stage keeping the previous stage's stars, ships and bullets and so on intact.
     * @param stageNumber Number of the stage to look for the correct stage file
     * @param prevStage The previous stage. Its fields are copied over.
     */
    void startStage(int stageNumber, Stage prevStage){
        stage = new Stage(stageNumber, prevStage);
        stage.player = player;
        if(player.ship.bombs < 4)
            player.ship.bombs++;
    }
}
