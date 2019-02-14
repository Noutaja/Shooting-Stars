package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

enum MenuType{
    MAINMENU, HIGH_SCORE, PAUSEMENU}

/**
 * A basic menu
 */
public class Menu implements ScreenElement {
    MenuController controller;


    ArrayList<MenuItem> menuItems;
    MenuItem selectedItem;
    ArrayList<Integer> lines;
    float x,y;
    float height;

    int fontSize;
    BitmapFont font;
    Sprite spriteSelected, spriteScreenFade;

    /**
     * @param x Horizontal location of the list, bottom left corner.
     * @param y Vertical location of the list, bottom left corner.
     * @param height Height of the list.
     * @param size Double the size of the list.
     * @param type Type of the menu.
     */
    public Menu(float x, float y, float height, int size, MenuType type) {

        SS.LoadAsset(SS.menuSelectedFile);
        SS.LoadAsset(SS.screenFadeFile);

        setX(x);
        setY(y);

        this.height = height;

        fontSize = (int) (height/size);

        lines = new ArrayList<>();
        for(int i = 0; i < size; i++){
            //create lines top to bottom, taking into account top left origin of text.
            lines.add((int)(y+height - i*fontSize));
        }

        font = SS.GenerateFont(SS.fontFile, fontSize);

        menuItems = new ArrayList<>();

        switch (type) {
            case PAUSEMENU:
                createPauseMenu();
                break;

            case MAINMENU:
                createMainMenu();
                break;

            case HIGH_SCORE:
                createHighScoreMenu();
                break;
        }
        selectedItem = menuItems.get(0);

        controller = new MenuController(this);
        spriteSelected = new Sprite(SS.GetAsset(SS.menuSelectedFile));
        spriteScreenFade = new Sprite(SS.GetAsset(SS.screenFadeFile));
        spriteScreenFade.setSize(800,600);
        spriteScreenFade.setAlpha(0.75f);
    }

    public void setX(float x) {
        MyScreen ms = GameController.Instance.screen;
        if(x < 0){
            this.x = 0;
        }
        else if(x > ms.WORLD_WIDTH) {
            this.x = ms.WORLD_WIDTH;
        }
        else{
            this.x = x;
        }
    }

    public void setY(float y) {
        MyScreen ms = GameController.Instance.screen;
        if(y < 0){
            this.y = 0;
        }
        else if(y > ms.WORLD_WIDTH) {
            this.y = ms.WORLD_WIDTH;
        }
        else{
            this.y = y;
        }
    }

    private void createHighScoreMenu() {
        menuItems.add(new MenuItem("Return to Main Menu", new MenuAction(Actions.RETURN_TO_MAIN_MENU)));
    }

    private void createMainMenu() {
        menuItems.add(new MenuItem("New game", new MenuAction(Actions.START_GAME)));
        menuItems.add(new MenuItem("Help", new MenuAction(Actions.HELP)));
        menuItems.add(new MenuItem("High Scores", new MenuAction(Actions.SHOW_SCORES)));
        menuItems.add(new MenuItem("Credits", new MenuAction(Actions.CREDITS)));
        menuItems.add(new MenuItem("Exit", new MenuAction(Actions.EXIT_GAME)));
    }

    private void createPauseMenu() {
        menuItems.add(new MenuItem("Resume", new MenuAction(Actions.RESUME_GAME)));
        menuItems.add(new MenuItem("Restart stage", new MenuAction(Actions.RESTART_STAGE)));
        menuItems.add(new MenuItem("Retry", new MenuAction(Actions.RETRY)));
        menuItems.add(new MenuItem("Return to Main Menu", new MenuAction(Actions.RETURN_TO_MAIN_MENU)));

    }

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     * @return Not used.
     */
    public boolean update(float delta){
        controller.update(delta);
        return false;
    }

    /**
     * Draws the screen.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    public void draw(Batch batch){
        spriteScreenFade.draw(batch);
        for(int i = 0; i < menuItems.size(); i++){
            MenuItem menuItem = menuItems.get(i);
            font.draw(batch,menuItem.text,x,lines.get(i*2));
            if(menuItem == selectedItem){
                spriteSelected.setPosition(x-spriteSelected.getWidth(), lines.get(i*2)-(fontSize/1.5f));
                spriteSelected.draw(batch);
            }
        }

    }

    void activateSelected(){
        selectedItem.action.activate();
    }
}
