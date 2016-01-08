package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Jay on 1/8/2016.
 */
public class EndScreen extends InputAdapter implements Screen {
    private static final String TAG = GameScreen.class.getName();

    OutbreakGame game;

    SpriteBatch fontRenderer;
    FitViewport actionViewport;
    private BitmapFont font;

    public EndScreen(OutbreakGame game) {
        Gdx.app.debug(TAG, "EndScreen(OutbreakGame)");
        this.game = game;
        actionViewport = new FitViewport(
                Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        actionViewport.apply(true);
        fontRenderer = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.YELLOW);
        font.getData().setScale(Constants.FONT_SCALE);
        font.getRegion().getTexture().setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.gotoOptionsScreen();
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void resize(int width, int height) {
        actionViewport.update(width, height, true);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }
}
