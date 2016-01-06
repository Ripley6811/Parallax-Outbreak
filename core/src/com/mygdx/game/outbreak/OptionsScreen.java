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
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Jay on 1/5/2016.
 */
public class OptionsScreen extends InputAdapter implements Screen {
    private static final String TAG = OptionsScreen.class.getName();

    // TODO: Use existing images to create initial screen.
    /**
     * Rotate 20 degrees and have paddle flying towards right ascending and
     * between blocks. Use stars and nebula images.
      */


    // TODO: Add score and lives at top
    OutbreakGame game;

    ShapeRenderer renderer;
    FitViewport actionViewport;
    SpriteBatch gameBatch;
    SpriteBatch fontRenderer;
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

    public OptionsScreen(OutbreakGame game) {
        this.game = game;
        gameBatch = new SpriteBatch();
        fontRenderer = new SpriteBatch();
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

        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        renderer.setProjectionMatrix(actionViewport.getCamera().combined);
        gameBatch.setProjectionMatrix(actionViewport.getCamera().combined);
        Gdx.app.log(TAG, "Default SpriteBatch projection matrix for font rendering:\n" + fontRenderer.getProjectionMatrix());


//        double r = -Math.PI/20.0;
//        float[] mArray = {(float)Math.cos(r), (float)-Math.sin(r), 0, 0,
//                (float)(Math.sin(r)), (float)Math.cos(r), 0, 0,
//                0, 0, 1, 0,
//                0, 0, 0, 1};
//        renderer.setTransformMatrix(new Matrix4(mArray));
        float WORLD_HALF = Constants.WORLD_SIZE / 2;
        renderer.identity();
        float scale = 4f;
        Vector2 trans = new Vector2(scale*(-WORLD_HALF + Constants.PLAYER_WIDTH), -Constants.PLAYER_Y_POSITION - Constants.PLAYER_HEIGHT);
        renderer.translate(trans.x, trans.y, 0);
        renderer.rotate(0f, 0f, 1f, 10f);
        renderer.scale(scale,scale,1f);
        renderer.translate(1f, -8f, 0);
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
        blocks.init("OptionsScreen");
        player.init("OptionsScreen");
        balls.init();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        gameBatch.dispose();
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
    public boolean keyDown(int keycode) {
        // TODO: Also add touch ball launching for Android
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.gotoGameScreen();
        }
        return super.keyDown(keycode);
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

        starScape.render(renderer);
        debrisLayer.render(renderer);
        blocks.render(renderer);
        player.render(renderer);
        balls.render(renderer);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0f, 0f, 0f, 0.5f);
        renderer.rect(0f, 0f, Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        fontRenderer.begin(); // 650 x 500
        font.getData().setScale(2.5f);
        font.draw(fontRenderer, Constants.GAME_TITLE, 0f, 420f, 650f, Align.center, false);
        font.getData().setScale(1.5f);
        String instructions = Constants.GAME_INSTRUCTIONS;
        font.draw(fontRenderer, instructions, 75f, 350f, 500f, Align.center, true);
        fontRenderer.end();
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
