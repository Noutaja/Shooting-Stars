package com.jarvinen.jyri.ss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Screen used for drawing everything.
 */
public class MyScreen implements Screen {
    /**
     * ScreenElements are added to elements and then drawn in order.
     */
    ArrayList<ScreenElement> elements;
    SpriteBatch batch;
    Viewport viewport;

    static final int WORLD_HEIGHT = 600;
    static final int WORLD_WIDTH = 800;

    public MyScreen(){
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, new OrthographicCamera());
        viewport.apply();
        viewport.getCamera().position.set(WORLD_WIDTH/2f, WORLD_HEIGHT/2f, 0);

        batch = new SpriteBatch();

        elements = new ArrayList<>();
    }

    /**
     * This method is called every frame. Iterates through elements, calling its update method.
     * @param delta The time between each frame.
     */
    public void update(float delta){
        Iterator<ScreenElement> iter = elements.iterator();
        while(iter.hasNext()){
            ScreenElement element = iter.next();
            element.update(delta);
        }
        draw();
    }

    /**
     * This method is called every frame. Iterates through elements, calling its draw method.
     */
    public void draw(){
        batch.setProjectionMatrix(viewport.getCamera().combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.getCamera().update();


        batch.begin();
        Iterator<ScreenElement> iter = elements.iterator();
        while(iter.hasNext()){
            ScreenElement element = iter.next();
            element.draw(batch);
        }
        batch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
