package com.jarvinen.jyri.ss;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import static com.jarvinen.jyri.ss.Team.*;

/**
 * Pattern of movement a group of bullets will follow.
 */
public class BulletPattern {

    ArrayList<BulletInitials> initials;
    Team team;
    float reloadTime;
    BulletPattern nextPattern;
    float patternTime;
    boolean finalPattern;

    /**
     * @param name Name of the pattern.
     */
    public BulletPattern(PatternName name){
        initials = new ArrayList<>();
        patternTime = 0;
        startingPattern(name);
    }

    /**
     * Creates a BulletInitial with a set spawn delay.
     * @param xSpeed Horizontal speed
     * @param ySpeed Vertical speed
     * @param xOffset Horizontal offset from the firing ship
     * @param yOffset Vertical offset from the firing ship
     * @param spawnDelay Delay in seconds before the bullet is spawned
     * @return the completed BulletInitial
     */
    BulletInitials createInitials(float xSpeed, float ySpeed, float xOffset, float yOffset, float spawnDelay){
        BulletInitials tmpInitials = new BulletInitials(new Vector2(xSpeed, ySpeed), new Vector2(xOffset, yOffset), spawnDelay);
        return tmpInitials;
    }

    /**
     * Creates a BulletInitial without a spawn delay.
     * @param xSpeed Horizontal speed
     * @param ySpeed Vertical speed
     * @param xOffset Horizontal offset from the firing ship
     * @param yOffset Vertical offset from the firing ship
     * @return the completed BulletInitial
     */
    BulletInitials createInitials(float xSpeed, float ySpeed, float xOffset, float yOffset){
        BulletInitials tmpInitials = createInitials(xSpeed, ySpeed, xOffset, yOffset, 0);
        return tmpInitials;
    }

    /**
     * Creates BulletInitials for the pattern
     * @param name Name of the pattern to be created.
     */
    private void startingPattern(PatternName name){
        switch (name) {

            case playerNarrow:
                initials.add(createInitials(0, 400, -20, 4));
                initials.add(createInitials(0, 400, -8, 7));
                initials.add(createInitials(0, 400, 4, 7));
                initials.add(createInitials(0, 400, -8, 7));
                initials.add(createInitials(0, 400, 4, 7));
                initials.add(createInitials(0, 400, 15, 4));
                reloadTime = 0.15f;
                team = UPWARDS;
                finalPattern = true;
                break;

            case playerWide:
                initials.add(createInitials(-100, 400, -20, 4));
                initials.add(createInitials(-10, 400, -8, 7));
                initials.add(createInitials(10, 400, 4, 7));
                initials.add(createInitials(100, 400, 15, 4));
                reloadTime = 0.15f;
                team = UPWARDS;
                finalPattern = true;
                break;

            case straightDown3:
                initials.add(createInitials(0, -150, -3, -5));
                initials.add(createInitials(0, -150, -3, -5, 0.1f));
                initials.add(createInitials(0, -150, -3, -5, 0.2f));
                reloadTime = 5;
                team = DOWNWARDS;
                finalPattern = true;
                break;

            case spread3:
                initials.add(createInitials(-100, -150, -3, -5));
                initials.add(createInitials(0, -150, -3, -5));
                initials.add(createInitials(100, -150, -3, -5));
                reloadTime = 5;
                team = DOWNWARDS;
                finalPattern = true;
                break;

            case spread3fast:
                initials.add(createInitials(-100, -150, -3, -5));
                initials.add(createInitials(0, -150, -3, -5));
                initials.add(createInitials(100, -150, -3, -5));
                reloadTime = 1;
                team = DOWNWARDS;
                finalPattern = true;
                break;

            case spread5:
                initials.add(createInitials(-150, -150, -3, -5));
                initials.add(createInitials(-100, -150, -3, -5));
                initials.add(createInitials(0, -150, -3, -5));
                initials.add(createInitials(100, -150, -3, -5));
                initials.add(createInitials(150, -150, -3, -5));
                reloadTime = 3;
                team = DOWNWARDS;
                finalPattern = true;
                break;
        }
    }
}
