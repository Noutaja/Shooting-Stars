package com.jarvinen.jyri.ss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

/**
 * Creates a Stage's waves from a file.
 */
public class WaveCreator {
    int width, height;
    ArrayList<ArrayList<Group>> waves;

    public WaveCreator(){
        waves = new ArrayList<>();
    }

    /**
     * @param currentStage Used to look up the correct stage file.
     * @return Returns the completed list of waves.
     */
    public ArrayList<ArrayList<Group>> createWaves(int currentStage) {

        //find the map file
        FileHandle file = Gdx.files.internal("stages/stage"+currentStage+".txt");
        String tmp = file.readString();

        //and split it up by rows
        String[] text = tmp.split(System.getProperty("line.separator"));
        width = text[0].length();
        height = text.length;

        for(int y = 0; y < height; y++){
            ArrayList<Group> wave = new ArrayList<Group>();
            waves.add(wave);

            for(int x = 0; x < width; x++){
                int i = Character.getNumericValue(text[y].charAt(x));
                    if(i > 4){
                        i = 4;
                    }
                    Group group = new Group(i, x);
                    wave.add(group);
            }
        }
        return waves;
    }
}
