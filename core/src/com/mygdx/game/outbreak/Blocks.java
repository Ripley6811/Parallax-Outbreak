package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by Jay on 12/23/2015.
 */
public class Blocks {
    private static final String TAG = Blocks.class.getName();

    OutbreakGame game;
    Viewport viewport;
    SpriteBatch batch;
    float scrollPosition;
    int worldWidth;
    float regenerateCountdown = Constants.BLOCK_REGENERATION_RATE;

    float VERTICAL_OFFSET = Constants.WORLD_SIZE - Constants.HUD_HEIGHT - Constants.BLOCK_HEIGHT - Constants.BLOCK_SPACING;
    Array<SingleBlock> blocks;
    Array<Texture> blockTextures;

    int[] LEVEL_LAYOUT;

    public Blocks(OutbreakGame game, Viewport viewport) {
        Gdx.app.debug(TAG, "Blocks(OutbreakGame, Viewport)");
        this.game = game;
        this.viewport = viewport;
        batch = new SpriteBatch();
        blockTextures = new Array<Texture>();
        for (int i=0; i<=4; i++) {
            blockTextures.add(new Texture(createBlock(i)));
        }
    }

    /**
     * Initializes the "GameScreen" with the given level data
     * @param level Index for loading blocks layout from Levels.java
     */
    public void init(int level) {
        Gdx.app.debug(TAG, "init(int)");
        scrollPosition = 0.0f;
        worldWidth = (int)viewport.getWorldWidth();
        LEVEL_LAYOUT = Levels.LEVEL_LAYOUTS[level];
        int nCols = Levels.LEVEL_WIDTH;
        int rowWidth = (int)(nCols * (Constants.BLOCK_WIDTH + Constants.BLOCK_SPACING) - Constants.BLOCK_SPACING);
        int xOffset = (worldWidth - rowWidth) / 2;
        blocks = new Array<SingleBlock>();
        for (int i = 0; i < LEVEL_LAYOUT.length; i++) {
            int strength = LEVEL_LAYOUT[i];
            int row = i / nCols;
            int col = i % nCols;
            float x = col * (Constants.BLOCK_WIDTH + Constants.BLOCK_SPACING);
            float y = VERTICAL_OFFSET - row * (Constants.BLOCK_HEIGHT + Constants.BLOCK_SPACING);
            blocks.add(new SingleBlock(viewport, x+xOffset, y, strength));
        }
    }

    /**
     * Initialization for start screen background animation
     * @param screenName Reference to the "OptionsScreen"
     */
    public void init(String screenName) {
        Gdx.app.debug(TAG, "init(String)");
        if (!screenName.equals("OptionsScreen")) return;

        scrollPosition = 0.0f;
        worldWidth = (int)viewport.getWorldWidth();

        int nCols = Levels.LEVEL_WIDTH;
        int rowWidth = (int)(nCols * (Constants.BLOCK_WIDTH + Constants.BLOCK_SPACING) - Constants.BLOCK_SPACING);
        int xOffset = (worldWidth - rowWidth) / 2;
        blocks = new Array<SingleBlock>();
        for (int i = 0; i < Levels.SPLASH.length; i++) {
            int strength = Levels.SPLASH[i];
            int row = i / nCols;
            int col = i % nCols;
            float x = col * (Constants.BLOCK_WIDTH + Constants.BLOCK_SPACING);
            float y = Constants.PLAYER_Y_POSITION - row * (Constants.BLOCK_HEIGHT + Constants.BLOCK_SPACING) + 4.5f * (Constants.BLOCK_HEIGHT + Constants.BLOCK_SPACING);
            blocks.add(new SingleBlock(viewport, x+xOffset, y, strength));
        }
    }

    public void update (float deltaTime, float scrollPosition) {
        if (game.blocksRegenerate) {
            regenerateCountdown -= deltaTime;
            if (regenerateCountdown <= 0f) {
                regenerateBlock();
                regenerateCountdown = Constants.BLOCK_REGENERATION_RATE;
            }
        }
        this.scrollPosition = scrollPosition;
    }

    public boolean allBlocksDestroyed() {
        for (SingleBlock block: blocks) {
            if (block.getStrength() > 0) return false;
        }
        return true;
    }

    public void regenerateBlock() {
        Random random = new Random();
        int i = random.nextInt(LEVEL_LAYOUT.length);
        int currStrength = blocks.get(i).getStrength();
        if (currStrength < Constants.BLOCK_MAX_STRENGTH) {
            blocks.get(i).setStrength(currStrength + 1);
        } else {
            // If block wasn't strengthen then shorten time to next regen.
            regenerateCountdown /= 10f;
        }
    }

    public void render(ShapeRenderer renderer) {
        batch.setProjectionMatrix(renderer.getProjectionMatrix());
        batch.setTransformMatrix(renderer.getTransformMatrix());

        batch.begin();
        for (SingleBlock block : blocks){
            if (block.getStrength() > 0) {
                float xShifted = (block.x + scrollPosition) % Constants.WORLD_SIZE;
                block.rectangle.setX(xShifted);

                // Draw block
                batch.draw(blockTextures.get(block.getStrength()),
                        xShifted, block.y, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
                // Draw again to ensure block visible on both ends of screen
                float xLeft = xShifted - Constants.WORLD_SIZE;
                if (xLeft < 0 && xLeft >= -Constants.BLOCK_WIDTH) {
                    batch.draw(blockTextures.get(block.getStrength()),
                            xLeft, block.y, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
                }
            }
        }
        batch.end();
    }

    public Pixmap createBlock(int strength) {
        int W = 64;
        int H = 64;
        // NOTE: Coordinate origin for Pixmap is top-left.
        Pixmap pixmap = new Pixmap(W, H, Pixmap.Format.RGBA8888);
        if (strength == 4) pixmap.setColor(Color.GOLD);
        if (strength == 3) pixmap.setColor(Color.RED);
        if (strength == 2) pixmap.setColor(Color.GREEN);
        if (strength == 1) pixmap.setColor(Color.BLUE);
        pixmap.fill();
        // Inner color is the next lower strength
        if (strength == 4) pixmap.setColor(1,0,0,0.02f);
        if (strength == 3) pixmap.setColor(0,1,0,0.02f);
        if (strength == 2) pixmap.setColor(0,0,1,0.02f);
        if (strength == 1) pixmap.setColor(0,0,0,0.02f);
        for (int i = 0; i < H / 2; i++) {
            pixmap.fillRectangle(0,0, W-2*i, H-2*i);
            pixmap.fillRectangle(4*i, 2*i, W, H);
        }
        return pixmap;
    }
}