package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * The bullet shot by ships.
 */
public class Bullet {
    float x,y;
    Sprite sprite;
    Vector2 speed;
    BulletPattern pattern, nextPattern;
    float patternTime, spawnDelay;

    Team team;
    Rectangle hitbox;
    int hitboxOffset; //hitbox is slightly smaller than the sprite


    /**
     * @param pattern The pattern for this bullet to follow.
     * @param i The index number for fetching the correct BulletInitials from the pattern.
     * @param ship The firing ship.
     */
    public Bullet(BulletPattern pattern, int i, Ship ship){
        this.pattern = pattern;
        team = pattern.team;

        if(team == Team.DOWNWARDS)
            sprite = new Sprite(SS.GetAsset(SS.bulletDFile));
        else
            sprite = new Sprite(SS.GetAsset(SS.bulletUFile));

        if(team == Team.UPWARDS)
            sprite.setAlpha(0.5f);
        patternTime = pattern.patternTime;
        spawnDelay = pattern.initials.get(i).spawnDelay;

        //set the coordinates taking into account the pattern offset, top middle of the sprite and "empty space" around the ship
        x = ship.x + ship.tipOfShip(ship.spriteBorder).x + pattern.initials.get(i).offset.x;
        y = ship.y + ship.tipOfShip(ship.spriteBorder).y + pattern.initials.get(i).offset.y;
        speed = pattern.initials.get(i).speed;

        hitboxOffset = 1;
        hitbox = new Rectangle(x+hitboxOffset,y+hitboxOffset,
                                sprite.getWidth()-hitboxOffset*2, sprite.getHeight()-hitboxOffset*2);
        sprite.setPosition(x,y);
    }

    /**
     * This method is called every frame. Decreases the timer on the current pattern and changing it if necessary,
     * and moves the bullet
     * @param delta The time between each frame.
     * @return True if off the screen.
     */
    public boolean update(float delta){
        if(!pattern.finalPattern && patternTime <= 0){
            pattern = nextPattern;
            patternTime = pattern.patternTime;
        }
        if(patternTime > 0)
            patternTime -= delta;

        x += speed.x*delta;
        y += speed.y*delta;
        hitbox.setPosition(x+hitboxOffset,y+hitboxOffset);

        if(x<0 || x>Stage.STAGE_WIDTH || y<0 || y>Stage.STAGE_HEIGHT){
            return true;
        }

        sprite.setPosition(x,y);

        return false;
    }

    /**
     * Draws the sprite.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    public void draw(Batch batch){
        sprite.draw(batch);
    }
}
