package com.jarvinen.jyri.ss;

import com.badlogic.gdx.math.Vector2;

/**
 * Starting speed, offset and spawn delay.
 */
public class BulletInitials {
    Vector2 speed;
    Vector2 offset;
    float spawnDelay;

    /**
     * @param speed The starting speed of this bullet.
     * @param offset The offset from the firing ship's tip of this bullet.
     * @param spawnDelay Delay in seconds until this bullet is spawned.
     */
    public BulletInitials(Vector2 speed, Vector2 offset, float spawnDelay){
        this.speed = speed;
        this.offset = offset;
        this.spawnDelay = spawnDelay;
    }
}
