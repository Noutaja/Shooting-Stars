package com.jarvinen.jyri.ss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;
import java.util.Random;

enum Direction{
    LEFT, RIGHT, UP, DOWN
}

class Explosion implements Cosmetics {
    Sprite sprite;
    Sound explosionSound;

    float explosionTimer;
    float size;
    float x,y;

    /**
     * @param ship The ship that's exploding.
     */
    public Explosion(Ship ship) {
        sprite = new Sprite(SS.GetAsset(SS.explosionFile));
        sprite.setOriginCenter();
        x = ship.x+(ship.sprite.getWidth()/2);
        y = ship.y+(ship.sprite.getHeight()/2);
        sprite.setCenter(x, y);

        explosionTimer = 1;
        size = 1 - explosionTimer;
        sprite.setScale(size);
        sprite.setAlpha(explosionTimer);

        Random rng = new Random();
        int soundSelection = rng.nextInt(2) + 1;
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion"+soundSelection+".wav"));
    }

    /**
     * This method is called every frame. Scales up the size and decreases the alpha of the sprite
     * @param delta The time between each frame.
     * @return True if the timer(and thus alpha) is 0.
     */
    public boolean update(float delta) {
        if(explosionTimer > 0){
            explosionTimer -= delta;
            if(explosionTimer < 0)
                explosionTimer = 0;
        }
        size = 1 - explosionTimer;
        sprite.setScale(size);
        sprite.setAlpha(explosionTimer);

        if(explosionTimer <= 0)
            return true;
        return false;
    }

    /**
     * Draws the sprite.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    public void draw(Batch batch) {
        sprite.draw(batch);
    }
}

/**
 * Both player and enemy ships are derived from this.
 */
abstract class Ship {
    float x, y;
    Vector2 speed;
    Team team;
    int hp;
    Sprite sprite;
    int spriteBorder;
    Rectangle hitbox;
    boolean invulnerable;
    float invulnTimer, invulnTimerReset;

    Random rng;

    Stage stage;

    float reloadTimer;

    /**
     * This method is called every frame. Checks for death and decreases both reload timer and invulnerability timer.
     * @param delta The time between each frame.
     * @return Not used.
     */
    public boolean update(float delta) {
        collisionCheck();
        if(hp <= 0){
            Explosion e = new Explosion(this);
            e.explosionSound.play(0.25f);
            stage.cosmetics.add(e);

        }
        if(invulnTimer > 0) {
            invulnTimer -= delta;
            if(invulnTimer <= 0)
                invulnerable = false;
        }
        if (reloadTimer > 0) {
            reloadTimer -= delta;
        }
        sprite.setPosition(x, y);
        return false;
    }

    /**
     * Draws the sprite.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    /**
     * @param direction The direction of movement.
     * @param delta The time between each frame.
     * @param outOfBoundsAllowed True if this ship is allowed out of the screen. True for enemy, false for the player.
     */
    public void move(Direction direction, float delta, boolean outOfBoundsAllowed) {
        if (direction == Direction.LEFT) {
            if(outOfBoundsAllowed || x > 0)
                x -= Math.abs(speed.x) * delta; //absolute of speed used because of different speed handling of player and enemy
        }
        if (direction == Direction.RIGHT) {
            if(outOfBoundsAllowed || x < Stage.STAGE_WIDTH - sprite.getWidth())
                x += Math.abs(speed.x) * delta;
        }
        if (direction == Direction.UP) {
            if(outOfBoundsAllowed || y < Stage.STAGE_HEIGHT - sprite.getHeight())
                y += Math.abs(speed.y) * delta;
        }
        if (direction == Direction.DOWN) {
            if(outOfBoundsAllowed || y > 0)
                y -= Math.abs(speed.y) * delta;
        }
    }

    /**
     * @param border The number of empty pixels before the ship begins proper.
     * @return Returns the coordinates to the top middle of the ship.
     */
    public Vector2 tipOfShip(int border) {
        Vector2 tmp;
        if(team == Team.UPWARDS)
            tmp = new Vector2(sprite.getWidth() / 2, sprite.getHeight() - border);
        else
           tmp = new Vector2(sprite.getWidth() / 2, 0 + border);
        return tmp;
    }

    /**
     * Checks for collision against bullets.
     */
    private void collisionCheck() {
        Iterator<Bullet> iter = stage.bullets.iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            if (bullet.hitbox.overlaps(this.hitbox)) {
                if (bullet.team != this.team) {
                    iter.remove();
                    if(!invulnerable)
                        hp--;
                }
            }
        }
    }
}
