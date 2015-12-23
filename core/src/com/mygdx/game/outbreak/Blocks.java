package com.mygdx.game.outbreak;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by Jay on 12/23/2015.
 */
public class Blocks {

    Viewport viewport;
    float scrollPosition;
    int worldWidth;

    int N_BLOCKS = 10;
    Array<SingleBlock> blocks;

    public Blocks(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {
        scrollPosition = 0.0f;
        blocks = new Array<SingleBlock>(N_BLOCKS);
        worldWidth = (int)viewport.getWorldWidth();
        Random random = new Random();
        for (int i = 0; i < N_BLOCKS; i++) {
            int x = (int)(i * Constants.BLOCK_WIDTH + i);
            int y = (int)(50 + Constants.BLOCK_HEIGHT);
            blocks.add(new SingleBlock(x, y));
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