package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;
import java.util.Random;

import static com.jarvinen.jyri.ss.Team.*;

public class PlayerShip extends Ship {

    boolean focused;
    int bombs;
    float bombTimer, bombTimerReset;
    float blinkTimer, blinkTimerReset;
    boolean blinked;

    Player player;
    ObjectMap<PatternName, BulletPattern> patterns;
    Vector2 hitboxOffset;
    Sprite spriteHitbox;

    /**
     * @param player The player controlling the ship.
     */
    public PlayerShip(Player player){
        SS.LoadAsset(SS.playerShipFile);
        SS.LoadAsset(SS.playerHitboxFile);

        rng = new Random();

        sprite = new Sprite(SS.GetAsset(SS.playerShipFile));
        x = 300;
        y = 50;
        spriteBorder = 4;

        speed = new Vector2();
        speed.x = 180;
        speed.y = 180;

        team = UPWARDS;
        hp = 1;

        bombs = 2;
        bombTimer = 0;
        bombTimerReset = 3;

        invulnTimerReset = 2;

        blinkTimerReset = 0.2f;
        blinked = false;

        this.player = player;
        player.lives--;

        sprite.setOriginCenter();
        sprite.setPosition(x,y);

        //hitbox offsets based on the sprite.
        float xOffset = 12;
        float yOffset = 21;

        hitboxOffset = new Vector2(xOffset+spriteBorder,yOffset+spriteBorder);
        hitbox = new Rectangle(x+hitboxOffset.x,y+hitboxOffset.y,8,8);
        spriteHitbox = new Sprite(SS.GetAsset(SS.playerHitboxFile));
        spriteHitbox.setPosition(hitbox.x, hitbox.y);

        patterns = new ObjectMap<>();
        patterns.put(PatternName.playerNarrow, new BulletPattern(PatternName.playerNarrow));
        patterns.put(PatternName.playerWide, new BulletPattern(PatternName.playerWide));
    }

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     * @return Not used.
     */
    @Override
    public boolean update(float delta) {
        super.update(delta);
        //update blinking
        if(invulnerable) {
            if (blinkTimer > 0)
                blinkTimer -= delta;
            else {
                blinked = !blinked;
                blinkTimer = blinkTimerReset;
            }
        }

        //check if dead
        if(hp <= 0) {
            player.lives--;
            if(bombs < 2)
                bombs = 2;

            hp = 1;
            invulnTimer = invulnTimerReset;
            blinkTimer = blinkTimerReset;
            invulnerable = true;
        }

        hitbox.setPosition(x+hitboxOffset.x,y+hitboxOffset.y);
        spriteHitbox.setPosition(hitbox.x, hitbox.y);

        if(bombTimer > 0)
            bombTimer -= delta;

        //check for collision with enemy ships
        for (Ship ship :
                stage.ships) {
            if(ship != this){
                if(hitbox.overlaps(ship.hitbox)) {
                    if (!invulnerable)
                        hp--;
                }
            }
        }


        return false;
    }

    /**
     * Tells stage to spawn bullets and resets the reload timer. The pattern will change depending on focused.
     */
    public void fire() {
        if (reloadTimer <= 0) {
            ArrayList<Bullet> bullets = new ArrayList<>();

            if(focused) {
                BulletPattern pattern = patterns.get(PatternName.playerNarrow);
                for (int i = 0; i < pattern.initials.size(); i++) {
                    bullets.add(new Bullet(pattern, i, this));
                }
                reloadTimer = pattern.reloadTime;
            }
            else{
                BulletPattern pattern = patterns.get(PatternName.playerWide);
                for (int i = 0; i < pattern.initials.size(); i++) {
                    bullets.add(new Bullet(pattern, i, this));
                }
                reloadTimer = pattern.reloadTime;
            }
            stage.spawnBullets(bullets);

        }
    }

    /**
     * Tells stage to spawn a bomb and resets the bomb reload timer.
     */
    public void bomb(){
        if(bombs > 0 && bombTimer <= 0){
            Bomb b = new Bomb(stage);
            player.score -= b.pointPrice;
            if(player.score < 0)
                player.score = 0;

            stage.bombs.add(b);
            bombTimer = bombTimerReset;
            bombs--;
        }
    }

    /**
     * Moves at half speed if focused, otherwise calls Ship.move to move at full speed.
     * @param direction The direction of movement.
     * @param delta The time between each frame.
     * @param outOfBoundsAllowed True if this ship is allowed out of the screen. True for enemy, false for the player.
     */
    @Override
    public void move(Direction direction, float delta, boolean outOfBoundsAllowed){
        if(focused){
            if(direction == Direction.LEFT && x > 0)
                x -= speed.x/1.5f*delta;

            if(direction == Direction.RIGHT && x < Stage.STAGE_WIDTH-sprite.getWidth())
                x += speed.x/1.5f*delta;

            if(direction == Direction.UP && y < Stage.STAGE_HEIGHT-sprite.getHeight())
                y += speed.y/1.5f*delta;

            if(direction == Direction.DOWN && y > 0)
                y -= speed.y/1.5f*delta;
        }
        else
            super.move(direction, delta, outOfBoundsAllowed);
    }

    /**
     * Draws the sprite if not currently blinked.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    @Override
    public void draw(Batch batch){
        if (!blinked || !invulnerable) {
            super.draw(batch);
            if(focused)
                spriteHitbox.draw(batch);
        }
    }
}
