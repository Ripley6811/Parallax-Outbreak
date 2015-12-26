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

    int VERTICAL_OFFSET = 50;
    Array<SingleBlock> blocks;

    public Blocks(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {
        scrollPosition = 0.0f;
        worldWidth = (int)viewport.getWorldWidth();

        int nRows = Levels.L1_ROWS;
        int nCols = Levels.L1_COLS;
        blocks = new Array<SingleBlock>(nRows * nCols);
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                float x = j * Constants.BLOCK_WIDTH + j * 0.1f;
                float y = VERTICAL_OFFSET + Constants.BLOCK_HEIGHT * i + i * 0.1f;
                int strength = Levels.L1[i*nCols + j];
                blocks.add(new SingleBlock(x, y, strength));
            }
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