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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;


/**
 * Created by Jay on 12/18/2015.
 */
public class GameScreen  extends InputAdapter implements Screen {
    private static final String TAG = GameScreen.class.getName();

    // TODO: Add score and lives at top
    OutbreakGame game;

    ShapeRenderer renderer;
    FitViewport actionViewport;
    SpriteBatch gameBatch;
    SpriteBatch hudBatch;
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

    int lives = Constants.INITIAL_LIVES;
    int score = 0;
    int streak = 0;

    public GameScreen(OutbreakGame game) {
        this.game = game;
        gameBatch = new SpriteBatch();
        hudBatch = new SpriteBatch();
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
        scrollVelocity = 0.0f;
        scrollAcceleration = 0.0f;
        actionViewport = new FitViewport(
                Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        actionViewport.apply(true);

        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        renderer.setProjectionMatrix(actionViewport.getCamera().combined);
        gameBatch.setProjectionMatrix(actionViewport.getCamera().combined);
        Gdx.app.log(TAG, "Default SpriteBatch projection matrix for font rendering:\n" + hudBatch.getProjectionMatrix());
    }

    @Override
    public void show() {

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
        gameBatch.dispose();
        font.dispose();
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

        // Slow down if not accelerating
        if (Math.abs(scrollAcceleration) < Constants.STATIC_FRICTION) {
            scrollVelocity *= Constants.KINETIC_FRICTION;
        }

        // Adjust velocity
        scrollVelocity += delta * Constants.ACCELERATION_MULTIPLIER * (
                Math.abs(scrollAcceleration) > Constants.STATIC_FRICTION ?
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
        // Check collision with player
        score -= balls.checkCollision(player);

        // Check collision with all blocks
        int hits = balls.checkCollision(blocks.blocks);
        score += hits * Constants.POINTS_PER_BLOCK;
        streak += hits;
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

        updateScroll(delta);

        // TODO: Test game a lot to see if collisions look correct.
        checkCollisions();

        starScape.update(delta, scrollVelocity);
        debrisLayer.update(delta, scrollVelocity);
        blocks.update(delta, scrollPosition);
        player.update(delta, scrollVelocity);
        boolean ballDied = balls.update(delta, scrollVelocity, player.position);
        if (ballDied) streak = 0;

        // Background color fill
        Color BG_COLOR = Constants.BACKGROUND_COLOR;
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        double r = -Math.PI/10;
//        float[] mArray = {(float)Math.cos(r), (float)-Math.sin(r), 0, 0,
//                (float)(Math.sin(r)), (float)Math.cos(r), 0, 0,
//                          0, 0, 1, 0,
//                          0, 0, 0, 1};
//        gameBatch.setTransformMatrix(new Matrix4(mArray));

        starScape.render(gameBatch);
        debrisLayer.render(gameBatch);
        blocks.render(gameBatch);
        player.render(renderer);
        balls.render(renderer);

        // Draw top scoreboard area
        gameBatch.begin();
        gameBatch.draw(scoreboard, 0, Constants.WORLD_SIZE - Constants.HUD_HEIGHT,
                Constants.WORLD_SIZE, Constants.HUD_HEIGHT);
        gameBatch.end();

        hudBatch.begin(); // 500 x 650
        font.draw(hudBatch, "SCORE: " + score, 10f, 470f);
        font.draw(hudBatch, "STREAK: " + streak + " hits", 100f, 470f);
        hudBatch.end();
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
