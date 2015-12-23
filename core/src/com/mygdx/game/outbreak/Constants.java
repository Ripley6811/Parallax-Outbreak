package com.mygdx.game.outbreak;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Jay on 12/18/2015.
 */
public class Constants {
    public static final float WORLD_SIZE = 100.0f;
    public static final Color BACKGROUND_COLOR = Color.BLACK;

    public static final float PLAYER_Y_POSITION = 5.0f;
    public static final Color PLAYER_COLOR = Color.LIGHT_GRAY;
    public static final float PLAYER_WIDTH = 10.0f;
    public static final float PLAYER_HEIGHT = 1.0f;

    public static final float GRAVITATIONAL_ACCELERATION = 9.8f;
    public static final float STATIC_FRICTION = 0.2f;
    public static final float MAX_SCROLL_SPEED = 5.0f;
    public static final float KEYPRESS_ACCELERATION = 0.8f;

    public static final float STAR_RADIUS = 0.25f;
    // Starscape width relative to world size.
    public static final float STARSCAPE_WIDTH_MULTIPLIER = 3.0f;
    // Starscape speed relative to player speed.
    public static final float STARSCAPE_SPEED_MULTIPLIER = 0.1f;
    public static final int STARSCAPE_NUMBER_OF_STARS = 200;

    public static final float BLOCK_WIDTH = 5.0f;
    public static final float BLOCK_HEIGHT = 3.0f;

}
