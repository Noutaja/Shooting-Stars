package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Holds the high scores of the game.
 */
public class HighScoreList {
    DataBaseReader dbr;

    ArrayList<Score> scoreList;
    ArrayList<Integer> lines;

    float x,y;
    float height;
    int size;
    int fontSize;
    
    BitmapFont font;

    /**
     * @param x Horizontal location of the list, bottom left corner.
     * @param y Vertical location of the list, bottom left corner.
     * @param height Height of the list.
     */
    public HighScoreList(int x, int y, int height){
        this.x = x;
        this.y = y;
        this.height = height;

        dbr = new DataBaseReader();

        size = 10;
        
        fontSize = height/size-4;
        font = SS.GenerateFont(SS.fontFile, fontSize);

        scoreList = createScoreList(dbr.getHighScores());
        sort();
        lines = new ArrayList<>();
        for(int i = 0; i < size; i++){
            //create lines accounting for the top-left-origin of text drawing, from top down.
            lines.add(height-(height/size*(i+1))+y);
        }
    }

    /**
     * Draws the list.
     * @param batch SpriteBatch the list is drawn into.
     */
    public void draw(Batch batch) {
        font.draw(batch, "TOP 10", 320, 580);
        Iterator<Score> iter = scoreList.iterator();
        int i = 0;
        while (iter.hasNext()) {
            Score score = iter.next();
            String s = i+1 +".  "+score.name+ " " + score.points;
            font.draw(batch, s, x , lines.get(i));
            i++;
        }
    }

    /**
     * Updates the database with the new high score.
     * @param name The name of the entry. 10 or less characters.
     * @param score Points of the entry.
     */
    public void updateScores(String name, int score) {
        if(listContains(name)){

            //get the existing score.
            ResultSet rs = dbr.selectQuery("select idname from tblname where namecol=\"" + name + "\";");
            int id = 0;
            try {
                rs.next();
                id = rs.getInt("idname");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = dbr.selectQuery("select scorecol from tblscore where name_id=" + id +";");
            int points = 0;
            try {
                rs.next();
                points = rs.getInt("scorecol");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //check if the new score is greater
            if(score > points) {
                dbr.updateQuery("update tblscore set scorecol=" + score + " where name_id=" + id + ";");
            }
            scoreList = createScoreList(dbr.getHighScores());
        }
        else{
            if(scoreList.size() == 10) {
                //get the name of the worst entry on the list
                String worstEntry = scoreList.get(scoreList.size()-1).name;

                ResultSet rs = dbr.selectQuery("select idname from tblname where namecol=\"" + worstEntry + "\";");
                int id = 0;
                try {
                    rs.next();
                    id = rs.getInt("idname");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                //delete worst entry from the list
                dbr.deleteQuery("delete from tblscore where name_id=\"" + id + "\";");
                dbr.deleteQuery("delete from tblname where idname=\"" + id + "\";");
            }
            //insert the new score
            dbr.insertQuery("insert into tblname (namecol) value (\"" + name + "\");");
            dbr.insertQuery("insert into tblScore (name_id, scorecol)" +
                            " select idname,"+ score +
                            " from tblname" +
                            " where namecol=\"" + name + "\";");


        }
        scoreList = createScoreList(dbr.getHighScores());
        sort();
    }

    /**
     * @param name The name to be searched for.
     * @return True if name found.
     */
    private boolean listContains(String name){
        Iterator<Score> iter = scoreList.iterator();
        while(iter.hasNext()){
            Score score = iter.next();
            if(score.name.equals(name))
                return true;
        }
        return false;
    }

    /**
     * Queries the database and creates the unordered list.
     * @param highScores ResultSet with the database entries.
     * @return Returns the unordered high score list.
     */
    private ArrayList<Score> createScoreList(ResultSet highScores) {
        ArrayList<Score> list = new ArrayList();
        if(highScores == null)
            return list;


        try {
            while(highScores.next()){
                String name = highScores.getString("namecol");
                int score = highScores.getInt("scorecol");

                list.add(new Score(name, score));
            }
            highScores.close();
            dbr.connection.close();
            dbr.statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Sorts the high score list. Highest first.
     */
    private void sort(){
        if(scoreList.isEmpty()) {
            System.out.println("");
        }
            ScoreComparator c = new ScoreComparator();
            scoreList.sort(c);

    }
}
