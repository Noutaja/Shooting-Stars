package com.jarvinen.jyri.ss;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Contains all background stars.
 */
class Starfield implements Cosmetics{
    ArrayList<Star> stars;
    float spawnTimer;


    public Starfield(){
        SS.LoadAsset(SS.starFile);

        spawnTimer = 0;


        stars = new ArrayList<>();
        for(int i = 0; i < 40; i++){
            stars.add(new Star());
        }
    }

    /**
     * Draws the sprite.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    public void draw(Batch batch){
        for (Star star :
                stars) {
            star.sprite.draw(batch);
        }
    }

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     * @return True if out of bounds.
     */
    public boolean update(float delta){
        spawnTimer += delta;
        if(spawnTimer > 0.5f){
            spawnNewStar();
            spawnTimer -= 0.5f;
        }
        Iterator<Star> iter = stars.iterator();
        while(iter.hasNext()){
            Star star = iter.next();

            if (star.update(delta)){
                iter.remove();
            }
        }
        return false;
    }

    private void spawnNewStar() {
        stars.add(new Star(true));
    }
}

/**
 * A background star
 */
class Star{
    float x,y;
    Sprite sprite;
    Random rng;
    float speed;


    /**
     * Used to spawn Stars anywhere on the Stage.
     */
    public Star(){
        sprite = new Sprite(SS.GetAsset(SS.starFile));
        rng = new Random();
        x = rng.nextInt((int) (Stage.STAGE_WIDTH-sprite.getWidth()));
        y = rng.nextInt(Stage.STAGE_HEIGHT);
        speed = rng.nextFloat();
        speed *= (0.8-0.1)+0.1;
        sprite.setPosition(x, y);
        sprite.setAlpha(speed);
    }

    /**
     * Used to spawn Stars at the top.
     * @param spawnAtTop
     */
    public Star(boolean spawnAtTop) {
        this();
        y = Stage.STAGE_HEIGHT;
        sprite.setY(y);
    }

    /**
     * This method is called every frame.
     * @param delta The time between each frame.
     * @return True if out of stage bounds
     */
    public boolean update(float delta){
        y -= speed;
        sprite.setY(y);
        if(y < 0){
            return true;
        }
        return false;
    }
}


/**
 * Creates, holds and updates everything a Stage.
 */
public class Stage {

    WaveCreator waveCreator;
    Player player;

    ArrayList<ArrayList<Group>> waves;
    ArrayList<Group> currentWave;
    ArrayList<Ship> ships;
    ArrayList<Bullet> bullets;
    ArrayList<Bullet> bulletsTBS;
    ArrayList<Bomb> bombs;
    ArrayList<Cosmetics> cosmetics;

    ArrayList<Float> spawnPoints;
    float waveTimer, waveTimerReset;
    float enemySpawnTimer, enemySpawnTimerReset;
    boolean spawningAllowed;


    static final int STAGE_WIDTH = 600;
    static final int STAGE_HEIGHT = 600;


    /**
     * Used to create a Stage from scratch.
     * @param currentStage Number of the stage. Used to find the correct stage file.
     */
    public Stage(int currentStage){
        SS.LoadAsset(SS.bulletDFile);
        SS.LoadAsset(SS.bulletUFile);
        SS.LoadAsset(SS.smallEnemyFile);
        SS.LoadAsset(SS.bigEnemyFile);
        SS.LoadAsset(SS.explosionFile);

        cosmetics = new ArrayList<>();
        cosmetics.add(new Starfield());
        ships = new ArrayList<>();
        bullets = new ArrayList<>();
        bulletsTBS = new ArrayList<>();
        bombs = new ArrayList<>();

        waveCreator = new WaveCreator();
        waves = waveCreator.createWaves(currentStage);
        waveTimerReset = 2;
        waveTimer = waveTimerReset;
        enemySpawnTimer = 0.0f;
        enemySpawnTimerReset = 0.4f;
        spawningAllowed = true;
        spawnPoints = createSpawnpoints();
    }

    /**
     * Used to copy over the previous Stage's fields for a smooth transition.
     * @param currentStage Number of the stage. Used to find the correct stage file.
     * @param prevStage The stage which fields are copied.
     */
    public Stage(int currentStage, Stage prevStage) {
        cosmetics = prevStage.cosmetics;
        ships = prevStage.ships;
        bullets = prevStage.bullets;
        bulletsTBS = new ArrayList<>();
        bombs = prevStage.bombs;
        waveCreator = prevStage.waveCreator;
        waves = waveCreator.createWaves(currentStage);
        waveTimerReset = prevStage.waveTimerReset;
        waveTimer = waveTimerReset;
        enemySpawnTimerReset = prevStage.enemySpawnTimerReset;
        enemySpawnTimer = 0;
        spawningAllowed = true;
        spawnPoints = createSpawnpoints();
    }

    /**
     * Creates enemy spawnpoints.
     * @return Returns a list of coordinates for the spawnpoints.
     */
    private ArrayList<Float> createSpawnpoints(){
        spawnPoints = new ArrayList<>();
        int width = waves.get(0).size();
        for(int i = 0; i < width; i++){
            spawnPoints.add(((i)*60f));
        }
        return spawnPoints;
    }

    /**
     * Used to spawn a ship.
     * @param ship The ship to be spawned
     */
    public void spawnShip(Ship ship){
        ship.stage = this;
        ships.add(ship);
    }


    /**
     * Draws the screen.
     * @param batch SpriteBatch the sprite is drawn into.
     */
    public void draw(Batch batch){
        drawCosmetics(batch);
        drawBullets(batch);
        drawShips(batch);
        drawBombs(batch);
    }

    /**
     * This method is called every frame. Updates all lists of objects.
     * @param delta The time between each frame.
     * @return True if stage is over.
     */
    public boolean update(float delta){
        if(updateWaves(delta))
            return true;

        updateCosmetics(delta);
        updateShips(delta);
        updateBullets(delta);
        updateBombs(delta);
        return false;
    }

    private void updateCosmetics(float delta){
        Iterator<Cosmetics> iter = cosmetics.iterator();
        while (iter.hasNext()){
            Cosmetics cosmetics = iter.next();
            if(cosmetics.update(delta)) {
                iter.remove();
            }
        }
    }

    private void updateBombs(float delta){
        Iterator<Bomb> iter = bombs.iterator();
        while (iter.hasNext()){
            Bomb bomb = iter.next();
            if(bomb.update(delta)) {
                iter.remove();
            }
        }
    }

    private boolean updateWaves(float delta){
        if(spawningAllowed) {
            if(currentWave == null){
                //try to get the next wave
                if(!waves.isEmpty()) {
                    currentWave = waves.get(0);
                    waves.remove(currentWave);
                }
                else
                    return true;
            }
            if(currentWave != null){
                if(enemySpawnTimer <= 0) {
                    Iterator<Group> iter = currentWave.iterator();
                    while (iter.hasNext()) {
                        //attempt to spawn a group.
                        Group group = iter.next();

                        if (group.size == 0)
                            iter.remove();
                        else if(group.size == 1){
                            //spawn a big ship if group is size 1.
                            Ship ship = new EnemyShip(Shiptype.BIG_SHIP, new MovementPattern(spawnPoints.get(group.spawnPoint)));
                            spawnShip(ship);
                            group.size--;
                            if (group.size == 0)
                                iter.remove();
                        }
                        else {
                            //otherwise spawn a small ship.
                            Ship ship = new EnemyShip(Shiptype.SMALL_SHIP, new MovementPattern(spawnPoints.get(group.spawnPoint)));
                            spawnShip(ship);
                            group.size--;
                        }
                    }
                    if(currentWave.isEmpty()) {
                        spawningAllowed = false;
                        currentWave = null;
                    }
                    enemySpawnTimer = enemySpawnTimerReset;
                }
                enemySpawnTimer -= delta;
            }
        }
        waveTimer -= delta;
        if(waveTimer <= 0){
            spawningAllowed = true;
            waveTimer = waveTimerReset;
        }
        return false;
    }

    private void updateShips(float delta){
        Iterator<Ship> iter = ships.iterator();
        while (iter.hasNext()){
            Ship ship = iter.next();
            if(ship.update(delta)) {
                iter.remove();
            }
        }
    }

    private void updateBullets(float delta){
        Iterator<Bullet> iter = bulletsTBS.iterator();
        while (iter.hasNext()){
            Bullet bullet = iter.next();
            bullet.spawnDelay -= delta;
            if (bullet.spawnDelay <= 0){
                bullets.add(bullet);
                iter.remove();
            }
        }

        iter = bullets.iterator();
        while (iter.hasNext()){
            Bullet bullet = iter.next();
            if(bullet.update(delta)){
                iter.remove();
            }
        }
    }

    private void drawCosmetics(Batch batch){
        Iterator<Cosmetics> iter = cosmetics.iterator();
        while (iter.hasNext()){
            Cosmetics cosmetics = iter.next();
            cosmetics.draw(batch);
        }
    }

    private void drawBombs(Batch batch){
        Iterator<Bomb> iter = bombs.iterator();
        while (iter.hasNext()){
            Bomb bomb = iter.next();
            bomb.draw(batch);
        }
    }

    private void drawShips(Batch batch){
        Iterator<Ship> iter = ships.iterator();
        while (iter.hasNext()){
            Ship ship = iter.next();
            ship.draw(batch);
        }
    }

    private void drawBullets(Batch batch){
        Iterator<Bullet> iter = bullets.iterator();
        while (iter.hasNext()){
            Bullet bullet = iter.next();
            bullet.draw(batch);
        }
    }

    public void spawnBullets(ArrayList<Bullet> bullets) {
        for (Bullet bullet :
                bullets) {
            if(bullet.spawnDelay > 0)
                bulletsTBS.add(bullet);
            else
                this.bullets.add(bullet);

        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
