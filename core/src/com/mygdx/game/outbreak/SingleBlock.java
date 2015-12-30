package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


/**
 * Created by Jay on 12/23/2015.
 */
public class SingleBlock extends Constants{
    Rectangle rectangle;
    float x;
    float y;
    int strength;
    int colSpan;
    String type;

    public SingleBlock(float x, float y, int strength) {
        rectangle = new Rectangle(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
        this.x = x;
        this.y = y;
        // Strength determines how many hits it can take and the color.
        this.strength = strength;
        init();
    }

    public void init() {

    }

    public void render(ShapeRenderer renderer, float xOffset) {
        // TODO: Create Pixmaps for each kind of block.
        float xShifted = (x + xOffset) % WORLD_SIZE;
        rectangle.setX(xShifted);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.identity();
        renderer.rect(xShifted,
                y,
                BLOCK_WIDTH,
                BLOCK_HEIGHT,
                BLOCK_COLOR_1[strength], BLOCK_COLOR_1[strength],
                BLOCK_COLOR_1[strength], BLOCK_COLOR_2[strength]);
        // Draw again to ensure block visible on both ends of screen
        float xLeft = xShifted - WORLD_SIZE;
        if (xLeft < 0 && xLeft >= -BLOCK_WIDTH) {
            renderer.rect(xLeft,
                    y,
                    BLOCK_WIDTH,
                    BLOCK_HEIGHT,
                    BLOCK_COLOR_1[strength], BLOCK_COLOR_1[strength],
                    BLOCK_COLOR_1[strength], BLOCK_COLOR_2[strength]);
        }
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
