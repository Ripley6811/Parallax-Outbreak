package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by Jay on 12/18/2015.
 */
public class GameScreen  extends InputAdapter implements Screen {
    // TODO: Start by adding player and parallax movement with accelerometer
    // TODO: Paint a background with red and blue nebulas.
    // TODO: Parallax 3 levels: Stars, Nebulas, (other debris), blocks.

    OutbreakGame game;

    ShapeRenderer renderer;
    ExtendViewport actionViewport;

    Player player;

    public GameScreen(OutbreakGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        actionViewport = new ExtendViewport(
                Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

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

        player.init();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        actionViewport.apply(true);

        player.update(delta);

        Color BG_COLOR = Constants.BACKGROUND_COLOR;
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, 135, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(actionViewport.getCamera().combined);


        player.render(renderer);


        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(255,0,0,1);
        renderer.identity();
        renderer.circle(0,0,5);
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0,255,0,1);
        renderer.identity();
        renderer.translate(actionViewport.getWorldWidth(),5,0);
        renderer.circle(0,0,5);
        renderer.end();
    }
}
