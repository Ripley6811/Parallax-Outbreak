package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by Jay on 12/23/2015.
 */
public class SingleBlock extends Constants{
    Viewport viewport;
    Rectangle rectangle;
    float x;
    float y;
    private int strength;
    int colSpan;
    String type;

    SpriteBatch batch;

    public SingleBlock(Viewport viewport, float x, float y, int strength) {
        this.viewport = viewport;
        rectangle = new Rectangle(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
        this.x = x;
        this.y = y;
        // Strength determines how many hits it can take and the color.
        this.strength = strength;
        batch = new SpriteBatch();
        init();
    }

    public void init() {
        batch.setProjectionMatrix(viewport.getCamera().combined);
    }

    public void render(Texture blockImage, float xOffset) {
        // TODO: Create Pixmaps for each kind of block.
        float xShifted = (x + xOffset) % WORLD_SIZE;
        rectangle.setX(xShifted);

        // Draw block
        batch.begin();
        batch.draw(blockImage, xShifted, y, BLOCK_WIDTH, BLOCK_HEIGHT);
        // Draw again to ensure block visible on both ends of screen
        float xLeft = xShifted - WORLD_SIZE;
        if (xLeft < 0 && xLeft >= -BLOCK_WIDTH) {
            batch.draw(blockImage, xLeft, y, BLOCK_WIDTH, BLOCK_HEIGHT);
        }
        batch.end();
    }

    public void hit() {
        strength -= 1;
    }

    public int getStrength() {
        return strength;
    }
}
