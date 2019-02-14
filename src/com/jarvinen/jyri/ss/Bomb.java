package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

/**
 * The bomb used by the player
 */
public class Bomb {
    float y;
    int pointPrice;
    Vector2 speed;
    Sprite sprite;
    Rectangle hitbox;
    Team team;
    Stage stage;

    /**
     * @param stage Used for collision checking with ships and bullets
     */
    public Bomb(Stage stage){
        y = 0;
        pointPrice = 40;
        speed = new Vector2(0, 200);
        sprite = new Sprite(SS.GetAsset(SS.starFile));
        sprite.setY(y);
        sprite.setSize(600, 10);
        hitbox = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        team = Team.UPWARDS;

        this.stage = stage;
    }

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     * @return True if off the screen from the top.
     */
    public boolean update(float delta){
        y += speed.y*delta;
        sprite.setY(y);
        hitbox.setY(y);
        if(y > Stage.STAGE_HEIGHT)
            return true;

        collisionCheck();

        return false;
    }

    /**
     * Iterates through every bullet and ship checking if they overlap. Removes ships and bullets when overlapping.
     */
    private void collisionCheck() {
        Iterator<Bullet> iterB = stage.bullets.iterator();
        while (iterB.hasNext()){
            Bullet bullet = iterB.next();
            if(bullet.hitbox.overlaps(this.hitbox)) {
                if(bullet.team != this.team) {
                    iterB.remove();
                }
            }
        }

        Iterator<Ship> iterS = stage.ships.iterator();
        while (iterS.hasNext()){
            Ship ship = iterS.next();
            if(ship.hitbox.overlaps(this.hitbox)) {
                if(ship.team != this.team) {
                    ship.hp = 0;
                }
            }
        }
    }

    /**
     * Draws the sprite.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    public void draw(Batch batch){
        sprite.draw(batch);
    }
}
