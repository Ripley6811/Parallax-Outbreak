package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Jay on 12/18/2015.
 */
public class GameScreen  extends InputAdapter implements Screen {

    OutbreakGame game;

    ShapeRenderer renderer;
    FitViewport actionViewport;
    Intersector intersector;

    float scrollPosition;
    float scrollVelocity;
    float scrollAcceleration;

    StarScape starScape;
    DebrisLayer debrisLayer;
    Blocks blocks;
    Player player;
    Balls balls;

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
        balls = new Balls(actionViewport);

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
        balls.init();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    public void updateScroll(float delta) {
        // Slow down
        scrollVelocity *= 0.8;
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
        scrollVelocity += Constants.ACCELERATION_MULTIPLIER * (
                Math.abs(scrollAcceleration)
                        > Constants.STATIC_FRICTION ?
                        scrollAcceleration : 0);
        // Max velocity
        if (Math.abs(scrollVelocity) > Constants.MAX_SCROLL_SPEED) {
            scrollVelocity = Math.signum(scrollVelocity)
                    * Constants.MAX_SCROLL_SPEED;
        }

        // Update position based on velocity
        scrollPosition += scrollVelocity;

        // Wrap around
        if (scrollPosition < 0) {
            scrollPosition += Constants.WORLD_SIZE;
        }
    }

    public void checkCollisions() {
        // TODO: Check collision with player and adjust x-velocity
        // TODO: Hand off to Balls class.
        // TODO: Paddle collision handle differently because where it hits affects trajectory
        balls.checkCollision(player);

        // TODO: Check collision with all remaining blocks
        balls.checkCollision(blocks.blocks);
    }

    @Override
    public boolean keyDown(int keycode) {
        // TODO: Also add touch ball launching for Android
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            balls.setFree(player.velocity);
        }
        return super.keyDown(keycode);
    }

    @Override
    public void render(float delta) {
        actionViewport.apply(true);

        updateScroll(delta);

        // TODO: collision works but check the math and angles again
        checkCollisions();

        starScape.update(delta, scrollVelocity);
        debrisLayer.update(delta, scrollVelocity);
        blocks.update(delta, scrollPosition);
        player.update(delta, scrollVelocity);
        balls.update(delta, scrollVelocity, player.position);


        // Background color fill
        Color BG_COLOR = Constants.BACKGROUND_COLOR;
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(actionViewport.getCamera().combined);


        starScape.render(renderer);
        debrisLayer.render(renderer);
        blocks.render(renderer);
        player.render(renderer);
        balls.render(renderer);

        // Top border
        float WORLD_SIZE = Constants.WORLD_SIZE;
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.identity();
        renderer.rect(
                0f, WORLD_SIZE - Constants.HUD_HEIGHT,
                WORLD_SIZE, Constants.HUD_HEIGHT,
                Color.LIGHT_GRAY, Color.LIGHT_GRAY,
                Color.DARK_GRAY, Color.DARK_GRAY
        );
        renderer.rect(
                0f, WORLD_SIZE - (Constants.HUD_HEIGHT - 0.2f),
                WORLD_SIZE, 0.4f,
                Color.YELLOW, Color.YELLOW,
                Color.ORANGE, Color.ORANGE
        );
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
