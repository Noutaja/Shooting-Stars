package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Batch;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The high score screen.
 */
public class HighScores implements ScreenElement {
    HighScoreList highScoreList;
    Menu menu;

    /**
     * Used when the list isn't going to be modified.
     */
    public HighScores(){
        highScoreList = new HighScoreList(300, 120, 420);

        menu = new Menu(150,25,100,2,MenuType.HIGH_SCORE);
    }

    /**
     * Used when a game is finished. Updates the HighScoreList if the points exceed the lowest entry.
     * @param score Compared with the HighScoreList, changing it if it's higher than the lowest entry.
     */
    public HighScores(int score) {
        this();

        if(highScoreList.dbr.connected) {
            ArrayList<Score> scoreList = highScoreList.scoreList;
            if (scoreList.size() < 10 || score > scoreList.get(scoreList.size() - 1).points) {
                String name;
                boolean nameIllegal;
                do {
                    //copied from: http://stackoverflow.com/questions/30141737/input-dialog-without-icon-and-only-ok-option
                    JTextField field = new JTextField(20);
                    JLabel label = new JLabel("A-Z, 0-9, 10 characters");
                    JPanel p = new JPanel(new BorderLayout(5, 2));
                    p.add(label, BorderLayout.WEST);
                    p.add(field);
                    JOptionPane.showMessageDialog(null, p, "A New High Score!", JOptionPane.PLAIN_MESSAGE, null);
                    name = field.getText();
                    //copypaste ends.

                    if (!name.matches("[A-Za-z0-9]+") || name.length() > 10 || name.length() == 0) {
                        nameIllegal = true;
                    } else
                        nameIllegal = false;

                } while (nameIllegal);

                highScoreList.updateScores(name, score);
            }
        }

    }

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     * @return Not used.
     */
    public boolean update(float delta) {
        menu.update(delta);
        return false;
    }

    /**
     * Draws the screen.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    public void draw(Batch batch) {
        menu.draw(batch);
        highScoreList.draw(batch);
    }
}
