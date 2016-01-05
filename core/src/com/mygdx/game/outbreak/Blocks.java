package com.mygdx.game.outbreak;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Jay on 12/23/2015.
 */
public class Blocks extends Constants {

    Viewport viewport;
    float scrollPosition;
    int worldWidth;

    float VERTICAL_OFFSET = WORLD_SIZE - HUD_HEIGHT - BLOCK_HEIGHT - BLOCK_SPACING;
    Array<SingleBlock> blocks;
    Array<Texture> blockTextures;

    public Blocks(Viewport viewport) {
        this.viewport = viewport;
        blockTextures = new Array<Texture>();
        for (int i=0; i<=4; i++) {
            blockTextures.add(new Texture(createBlock(i)));
        }
        init();
    }

    public void init() {
        scrollPosition = 0.0f;
        worldWidth = (int)viewport.getWorldWidth();

        int nCols = Levels.L3_COLS;
        int rowWidth = (int)(nCols * (BLOCK_WIDTH + BLOCK_SPACING) - BLOCK_SPACING);
        int xOffset = (worldWidth - rowWidth) / 2;
        blocks = new Array<SingleBlock>();
        for (int i = 0; i < Levels.L3.length; i++) {
            int strength = Levels.L3[i];
            if (strength == 0) continue;
            int row = i / nCols;
            int col = i % nCols;
            float x = col * (BLOCK_WIDTH + BLOCK_SPACING);
            float y = VERTICAL_OFFSET - row * (BLOCK_HEIGHT + BLOCK_SPACING);
            blocks.add(new SingleBlock(viewport, x+xOffset, y, strength));
        }
    }

    public void update (float deltaTime, float scrollPosition) {
        this.scrollPosition = scrollPosition;

    }

    public void render(SpriteBatch batch) {
        batch.begin();
        for (SingleBlock block : blocks){
            float xShifted = (block.x + scrollPosition) % WORLD_SIZE;
            block.rectangle.setX(xShifted);

            // Draw block
            batch.draw(blockTextures.get(block.getStrength()),
                    xShifted, block.y, BLOCK_WIDTH, BLOCK_HEIGHT);
            // Draw again to ensure block visible on both ends of screen
            float xLeft = xShifted - WORLD_SIZE;
            if (xLeft < 0 && xLeft >= -BLOCK_WIDTH) {
                batch.draw(blockTextures.get(block.getStrength()),
                        xLeft, block.y, BLOCK_WIDTH, BLOCK_HEIGHT);
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