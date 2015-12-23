package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Jay on 12/18/2015.
 */
public class GameScreen  extends InputAdapter implements Screen {
    // TODO: Start by adding player and parallax movement with accelerometer
    // TODO: Paint a background with red and blue nebulas.
    // TODO: Parallax 3 levels: Stars, Nebulas, (other debris), blocks.

    OutbreakGame game;

    ShapeRenderer renderer;
    FitViewport actionViewport;

    float scrollPosition;
    float scrollVelocity;
    float scrollAcceleration;

    StarScape starScape;
    DebrisLayer debrisLayer;
    Blocks blocks;
    Player player;

    public GameScreen(OutbreakGame game) {
        this.game = game;
        init();
    }

    public void init() {
        scrollPosition = Constants.WORLD_SIZE;
        scrollVelocity = 0.0f;
        scrollAcceleration = 0.0f;
    }

    @Override
    public void show() {
        actionViewport = new FitViewport(
                Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        starScape = new StarScape(actionViewport);
        debrisLayer = new DebrisLayer(actionViewport);
        blocks = new Blocks(actionViewport);
        player = new Player(actionViewport);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        renderer.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {
        actionViewport.update(width, height, true);

        starScape.init();
        debrisLayer.init();
        blocks.init();
        player.init();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    public void updateScroll(float delta) {
        // Accelerometer input
        scrollAcceleration = -3.0f * Gdx.input.getAccelerometerY() /
                             Constants.GRAVITATIONAL_ACCELERATION;
        // Key input
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            scrollAcceleration = Constants.KEYPRESS_ACCELERATION;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            scrollAcceleration = -Constants.KEYPRESS_ACCELERATION;
        }

        // Adjust velocity
        scrollVelocity += Math.abs(scrollAcceleration)
                          > Constants.STATIC_FRICTION ?
                          scrollAcceleration : 0;
        // Max velocity
        if (Math.abs(scrollVelocity) > Constants.MAX_SCROLL_SPEED) {
            scrollVelocity = Math.signum(scrollVelocity)
                    * Constants.MAX_SCROLL_SPEED;
        }
        // Slow down
        scrollVelocity *= 0.8;

        // Update position based on velocity
        scrollPosition += scrollVelocity;

        // Wrap around
        if (scrollPosition < 0) {
            scrollPosition += Constants.WORLD_SIZE;
        }

        System.out.println(scrollPosition + ", " + scrollVelocity + ", " + scrollAcceleration);
    }

    @Override
    public void render(float delta) {
        actionViewport.apply(true);

        updateScroll(delta);
        starScape.update(delta, scrollVelocity);
        debrisLayer.update(delta, scrollVelocity);
        blocks.update(delta, scrollPosition);
        player.update(delta, scrollVelocity);

        Color BG_COLOR = Constants.BACKGROUND_COLOR;
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(actionViewport.getCamera().combined);

        starScape.render(renderer);
        debrisLayer.render(renderer);
        blocks.render(renderer);
        player.render(renderer);

    }
}
