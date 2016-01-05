package com.mygdx.game.outbreak;

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

    public SingleBlock(Viewport viewport, float x, float y, int strength) {
        this.viewport = viewport;
        rectangle = new Rectangle(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
        this.x = x;
        this.y = y;
        // Strength determines how many hits it can take and the color.
        this.strength = strength;
        init();
    }

    public void init() {

    }

    public void hit() {
        strength -= 1;
    }

    public int getStrength() {
        return strength;
    }
}
