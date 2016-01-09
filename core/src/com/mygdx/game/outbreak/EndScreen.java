package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Jay on 1/8/2016.
 */
public class EndScreen extends InputAdapter implements Screen {
    private static final String TAG = GameScreen.class.getName();

    OutbreakGame game;

    SpriteBatch fgBatch;
    FitViewport actionViewport;
    private BitmapFont font;

    public EndScreen(OutbreakGame game) {
        Gdx.app.debug(TAG, "EndScreen(OutbreakGame)");
        this.game = game;
        actionViewport = new FitViewport(
                Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        actionViewport.apply(true);
        fgBatch = new SpriteBatch();
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
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        // Background color fill
        Color BG_COLOR = Constants.BACKGROUND_COLOR;
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        fgBatch.begin(); // 650 x 500
        font.getData().setScale(3f);
        if (game.getLivesRemaining() < 0) {
            font.draw(fgBatch, "Game Over",
                    0f, 360f, 650f, Align.center, false);
            font.draw(fgBatch, "Final score: " + game.getLastScore(),
                    0f, 240f, 650f, Align.center, false);
        } else {
            font.draw(fgBatch, "Congratulations!",
                    0f, 360f, 650f, Align.center, false);
            font.draw(fgBatch, "Remaining lives: " + game.getLivesRemaining(),
                    0f, 280f, 650f, Align.center, false);
            font.draw(fgBatch, "Final score: " + game.getLastScore(),
                    0f, 240f, 650f, Align.center, false);
        }
        font.getData().setScale(2f);
        font.draw(fgBatch, "Touch or click screen to play again",
                0f, 180f, 650f, Align.center, false);
        fgBatch.end();
    }
}
