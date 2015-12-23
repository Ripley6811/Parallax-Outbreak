package com.mygdx.game.outbreak;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


/**
 * Created by Jay on 12/23/2015.
 */
public class SingleBlock {
    float x;
    float y;
    int colSpan;
    Color color1;
    Color color2;
    String type;

    public SingleBlock(float x, float y) {
        this.x = x;
        this.y = y;
        this.color1 = Color.BLUE;
        this.color2 = Color.FIREBRICK;
        init();
    }

    public void init() {

    }

    public void render(ShapeRenderer renderer, float xOffset) {
        float worldWidth = Constants.WORLD_SIZE;
        float xShifted = (x + xOffset) % worldWidth;
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.identity();
        renderer.rect(xShifted,
                y,
                Constants.BLOCK_WIDTH,
                Constants.BLOCK_HEIGHT,
                color1, color2,
                color1, color2);
        // Draw again to ensure block visible on both ends of screen
        float xLeft = xShifted - worldWidth;
        if (xLeft < 0 && xLeft >= -Constants.BLOCK_WIDTH) {
            renderer.rect(xLeft,
                    y,
                    Constants.BLOCK_WIDTH,
                    Constants.BLOCK_HEIGHT,
                    color1, color2,
                    color1, color2);
        }
        renderer.end();
    }
}
