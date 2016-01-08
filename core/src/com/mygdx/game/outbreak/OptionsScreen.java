package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Jay on 1/5/2016.
 */
public class OptionsScreen extends InputAdapter implements Screen {
    private static final String TAG = OptionsScreen.class.getName();

    OutbreakGame game;

    FitViewport actionViewport;
    ShapeRenderer bgRenderer;  // Background renderer
    SpriteBatch fgBatch; // Foreground batch renderer
    Texture scoreboard;
    private BitmapFont font;

    float scrollPosition;
    float scrollVelocity;
    float scrollAcceleration;

    StarScape starScape;
    DebrisLayer debrisLayer;
    Blocks blocks;
    Player player;
    Balls balls;

    Array<Button> buttons;

    public OptionsScreen(OutbreakGame game) {
        this.game = game;
        fgBatch = new SpriteBatch();
        scoreboard = new Texture(createScoreboardPixmap());
        font = new BitmapFont();
        font.setColor(Color.YELLOW);
        font.getData().setScale(Constants.FONT_SCALE);
        font.getRegion().getTexture().setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        init();
    }

    public void init() {
        scrollPosition = Constants.WORLD_SIZE;
        scrollVelocity = -Constants.MAX_SCROLL_SPEED;
        scrollAcceleration = 0.0f;
        actionViewport = new FitViewport(
                Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        actionViewport.apply(true);

        starScape = new StarScape(actionViewport);
        debrisLayer = new DebrisLayer(actionViewport);
        blocks = new Blocks(actionViewport);
        player = new Player(actionViewport);
        balls = new Balls(actionViewport);
        buttons = new Array<Button>();

        // Set up background renderer
        bgRenderer = new ShapeRenderer();
        bgRenderer.setAutoShapeType(true);
        bgRenderer.setProjectionMatrix(actionViewport.getCamera().combined);

        // Change projection matrix for background animation
        float WORLD_HALF = Constants.WORLD_SIZE / 2;
        bgRenderer.identity();
        float scale = 4f;
        float tx = scale*(-WORLD_HALF + Constants.PLAYER_WIDTH);
        float ty = -Constants.PLAYER_Y_POSITION - Constants.PLAYER_HEIGHT;
        bgRenderer.translate(tx, ty, 0);
        bgRenderer.rotate(0f, 0f, 1f, 10f);
        bgRenderer.scale(scale, scale, 1f);
        bgRenderer.translate(1f, -8f, 0);

        // Initialize buttons
        int buttonY = 10;
        int buttonSpacing = 1;
        for (String diff: Constants.DIFFICULTY_NAMES) {
            buttons.add(
                    new Button(diff,
                            (650 - Constants.BUTTON_WIDTH) / 2f, buttonY,
                            Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT)
            );
            buttonY += buttonSpacing + Constants.BUTTON_HEIGHT;
        }
    }

    @Override
    public void show() {

        starScape.init();
        debrisLayer.init();
        blocks.init("OptionsScreen");
        player.init("OptionsScreen");
        balls.init();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {

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
    }

    @Override
    public void dispose() {
        bgRenderer.dispose();
        font.dispose();
    }

    public void updateScroll(float delta) {
        // Update position based on velocity
        scrollPosition += scrollVelocity;

        // Wrap around
        if (scrollPosition < 0) {
            scrollPosition += Constants.WORLD_SIZE;
        }
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector2 pt = actionViewport.unproject(new Vector2(screenX, screenY));
        // Scale the pt to "fgBatch" coordinate system.
        pt.scl(6.5f, 5f);
        for (Button b: buttons) b.mouseMoved(pt);
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean keyDown(int keycode) {
        // TODO: Also add touch ball launching for Android
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.gotoGameScreen();
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (Button b: buttons) {
            if (b.isMouseover()) {
                game.setDifficulty(b.getText());
                game.gotoGameScreen();
            }
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void render(float delta) {
        updateScroll(delta);

        starScape.update(delta, scrollVelocity);
        debrisLayer.update(delta, scrollVelocity);
        blocks.update(delta, scrollPosition);
        player.update(delta, scrollVelocity);
        balls.update(delta, scrollVelocity, player.position);

        // Background color fill
        Color BG_COLOR = Constants.BACKGROUND_COLOR;
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        starScape.render(bgRenderer);
        debrisLayer.render(bgRenderer);
        blocks.render(bgRenderer);
        player.render(bgRenderer);
        balls.render(bgRenderer);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        bgRenderer.begin(ShapeRenderer.ShapeType.Filled);
        bgRenderer.setColor(0f, 0f, 0f, 0.3f);
        bgRenderer.rect(0f, 0f, Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        bgRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        fgBatch.begin(); // 650 x 500
        font.getData().setScale(2.5f);
        font.draw(fgBatch, Constants.GAME_TITLE,
                0f, 420f, 650f, Align.center, false);
        font.getData().setScale(1.5f);
        font.draw(fgBatch, Constants.GAME_INSTRUCTIONS,
                75f, 350f, 500f, Align.center, true);
        fgBatch.end();

        for (Button b: buttons) {
            b.render(fgBatch);
        }
    }

    private Pixmap createScoreboardPixmap() {
        int W = 1;
        int H = 64;
        // NOTE: Coordinate origin for Pixmap is top-left.
        Pixmap pixmap = new Pixmap(W, H, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.LIGHT_GRAY);
        pixmap.fill();
        pixmap.setColor(0,0,0,0.02f);
        for (int i = 0; i < H; i++) {
            pixmap.drawLine(0, 0, 0, i);
        }
        pixmap.setColor(Color.ORANGE);
        pixmap.drawLine(0, H - 7, 0, H - 2);
        pixmap.setColor(Color.YELLOW);
        pixmap.drawPixel(0, H - 5);
        return pixmap;
    }
}
