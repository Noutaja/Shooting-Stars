package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;

/**
 * Created by Noutaja on 5.12.2016.
 */
public class Credits implements ScreenElement {
    float x;
    ArrayList<Integer> lines;

    ArrayList<String> titles;
    ArrayList<String> names;

    int fontSize;
    BitmapFont font;
    public Credits(float x, float y, float height){
        this.x = x;

        titles = new ArrayList<>();
        titles.add("Game design:");
        titles.add("Programming:");
        titles.add("Graphics: ");
        titles.add("Music:");
        titles.add("Sounds:");

        names = new ArrayList<>();
        names.add("Jyri Järvinen");
        names.add("Jyri Järvinen");
        names.add("Jyri Järvinen");
        names.add("Antti Nyholm");
        names.add("http://www.bfxr.net/");

        int size = titles.size()*2;
        lines = new ArrayList<>();
        for(int i = 0; i < size; i++){
            //create lines accounting for the top-left-origin of text drawing, from top down.
            lines.add((int) (height-(height/size*(i+1))+y));
        }

        fontSize = 36;
        font = SS.GenerateFont(SS.fontFile, fontSize);


    }
    @Override
    public boolean update(float delta) {
        if(!GameController.Instance.pressedKeys.isEmpty()) {
            MyScreen ms = new MyScreen();
            ms.elements.add(new MainMenu());
            GameController.Instance.setScreen(ms);
        }

        return false;
    }

    @Override
    public void draw(Batch batch) {
        font.draw(batch, "Credits", 320, 550);

        for (int i = 0; i < titles.size(); i++) {
            String text = titles.get(i);
            font.draw(batch, text, x, lines.get(i*2));
        }

        for (int i = 0; i < names.size(); i++) {
            String text = names.get(i);
            font.draw(batch, text, x+300, lines.get(i*2));
        }

        font.draw(batch, "Press any key to return.", 200, 50);
    }
}
