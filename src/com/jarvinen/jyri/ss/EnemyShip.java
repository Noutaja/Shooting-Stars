package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

import static com.jarvinen.jyri.ss.Team.DOWNWARDS;

enum Shiptype{
    SMALL_SHIP, BIG_SHIP
}


public class EnemyShip extends Ship {
    MovementPattern movementPattern;
    float maxSpeed;
    /**
     * How much the ship is worth points
     */
    int score;

    ArrayList<BulletPattern> patterns;

    /**
     * @param type This ship's type.
     * @param movementPattern This ship's movementPattern.
     */
    public EnemyShip(Shiptype type, MovementPattern movementPattern){
        spriteBorder = 4;
        if(type == Shiptype.SMALL_SHIP)
            createSmallShip(movementPattern);
        else if(type == Shiptype.BIG_SHIP)
            createBigShip(movementPattern);
        invulnerable = false;
    }

    /**
     * @param movementPattern This ship's movementPattern.
     */
    void createSmallShip(MovementPattern movementPattern){
        sprite = new Sprite(SS.GetAsset(SS.smallEnemyFile));
        rng = new Random();
        this.movementPattern = movementPattern;
        maxSpeed = 160;
        score = 1;

        x = movementPattern.startPos.x;
        y = movementPattern.startPos.y;
        speed = movementPattern.speed;
        team = DOWNWARDS;
        hp = 12;

        hitbox = new Rectangle(x+spriteBorder, y+spriteBorder, sprite.getWidth(), sprite.getHeight());

        sprite.setPosition(x,y);

        patterns = new ArrayList<>();
        patterns.add(new BulletPattern(PatternName.straightDown3));
        patterns.add(new BulletPattern(PatternName.spread3));
    }

    /**
     * @param movementPattern This ship's movementPattern.
     */
    void createBigShip(MovementPattern movementPattern){
        sprite = new Sprite(SS.GetAsset(SS.bigEnemyFile));
        rng = new Random();
        this.movementPattern = movementPattern;
        maxSpeed = 140;
        score = 2;

        x = movementPattern.startPos.x-9;
        y = movementPattern.startPos.y;
        speed = movementPattern.speed;
        team = DOWNWARDS;
        hp = 24;
        reloadTimer = 0.5f;

        hitbox = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());

        sprite.setPosition(x,y);

        patterns = new ArrayList<>();
        patterns.add(new BulletPattern(PatternName.spread3fast));
        patterns.add(new BulletPattern(PatternName.spread5));
    }

    /**
     * Tells stage to spawn bullets and resets the reload timer. Uses a random BulletPattern every time.
     */
    public void fire() {
        if (reloadTimer <= 0) {
            ArrayList<Bullet> bullets = new ArrayList<>();
            BulletPattern pattern = randomPattern();
            for (int i = 0; i < pattern.initials.size(); i++) {
                bullets.add(new Bullet(pattern, i, this));
            }
            float reloadRandomization = rng.nextFloat();
            float min = 0.8f;
            float max = 1.5f;
            reloadRandomization *= (max-min)+min;
            reloadTimer = pattern.reloadTime*reloadRandomization; //adds randomization to the reload
            stage.spawnBullets(bullets);
        }
    }

    private BulletPattern randomPattern() {
        return patterns.get(rng.nextInt(patterns.size()));
    }

    @Override
    public boolean update(float delta){
        super.update(delta);
        if(hp <= 0) {
            stage.player.score += this.score;
            return true;
        }

        //change speed if the limit isn't exceeded.
        if(Math.abs(speed.x + movementPattern.xChange) + Math.abs(speed.y + movementPattern.yChange) < maxSpeed) {
            speed.x += movementPattern.xChange;
        }
        if(Math.abs(speed.x + movementPattern.xChange) + Math.abs(speed.y + movementPattern.yChange) < maxSpeed)
            speed.y += movementPattern.yChange;

        if(speed.x < 0)
            move(Direction.LEFT, delta, true);
        if(speed.x > 0)
            move(Direction.RIGHT, delta, true);
        if(speed.y < 0)
            move(Direction.DOWN, delta, true);
        if(speed.y > 0)
            move(Direction.UP, delta, true);

        hitbox.setPosition(x+spriteBorder,y+spriteBorder);

        //check if off the screen
        if(x<0-sprite.getWidth() || x>Stage.STAGE_WIDTH || y<0-sprite.getHeight())
            return true;

        if(reloadTimer <= 0)
            fire();

        return false;
    }
}
