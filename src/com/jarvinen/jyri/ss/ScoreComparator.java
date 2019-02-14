package com.jarvinen.jyri.ss;

import java.util.Comparator;

/**
 * Compares scores for the Comparator interface.
 */
public class ScoreComparator implements Comparator<Score> {
    /**
     * @param score1 Score1
     * @param score2 Score2
     * @return -1 if score1 is greater, 1 if score2 is greater, 0 if equal.
     */
    @Override
    public int compare(Score score1, Score score2) {

        int points1 = score1.points;
        int points2 = score2.points;

        if (points1 > points2){
            return -1;
        }
        else if (points1 < points2){
            return +1;
        }
        else{
            return 0;
        }
    }
}
