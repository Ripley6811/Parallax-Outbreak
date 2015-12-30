package com.mygdx.game.outbreak;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Jay on 12/23/2015.
 */
public class Blocks {

    Viewport viewport;
    float scrollPosition;
    int worldWidth;

    float BLOCK_SPACING = 0.2f;
    float VERTICAL_OFFSET = Constants.WORLD_SIZE - Constants.HUD_HEIGHT - Constants.BLOCK_HEIGHT - BLOCK_SPACING;
    Array<SingleBlock> blocks;

    public Blocks(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {
        scrollPosition = 0.0f;
        worldWidth = (int)viewport.getWorldWidth();

        int nCols = Levels.L1_COLS;
        int rowWidth = (int)(nCols * (Constants.BLOCK_WIDTH + BLOCK_SPACING) - BLOCK_SPACING);
        int xOffset = (worldWidth - rowWidth) / 2;
        blocks = new Array<SingleBlock>();
        for (int i = 0; i < Levels.L1.length; i++) {
            int strength = Levels.L1[i];
            if (strength == 0) continue;
            int row = i / nCols;
            int col = i % nCols;
            float x = col * (Constants.BLOCK_WIDTH + BLOCK_SPACING);
            float y = VERTICAL_OFFSET - row * (Constants.BLOCK_HEIGHT + BLOCK_SPACING);
            blocks.add(new SingleBlock(x+xOffset, y, strength));
        }
    }

    public void update (float deltaTime, float scrollPosition) {
        this.scrollPosition = scrollPosition;

    }

    public void render(ShapeRenderer renderer) {
        for (SingleBlock block : blocks){
            block.render(renderer, scrollPosition);
        }
    }
}