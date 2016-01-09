package com.mygdx.game.outbreak;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Jay on 12/18/2015.
 */
public class Constants {
    /* LOGGING LEVELS */
    // Application logging levels from lowest to highest. Choose one.
    public static final int LOG_LEVEL = Application.LOG_NONE;
//    public static final int LOG_LEVEL = Application.LOG_ERROR;
//    public static final int LOG_LEVEL = Application.LOG_INFO;
//    public static final int LOG_LEVEL = Application.LOG_DEBUG;

    /* WORLD & HUD */
    public static final String GAME_TITLE = "PARALLAX-OUTBREAK";
    public static final String GAME_INSTRUCTIONS = "Hello and welcome to my awesome game. I hope you enjoy the ride.\nSelect a difficulty to start playing.";
    public static final float WORLD_SIZE = 100.0f;
    public static final Color BACKGROUND_COLOR = Color.BLACK;
    public static final float HUD_HEIGHT = 7f;
    public static final float FONT_SCALE = 1f;
    public static final String[] DIFFICULTY_NAMES = {"Easy", "Hard", "Insane!"};
    public static final Array DIFFICULTY = new Array(DIFFICULTY_NAMES);
    public static final float BLOCK_REGENERATION_RATE = 6f;
    public static final int START_LEVEL = 1;  // "0" for single block testing level.

    /* PLAYER PADDLE */
    public static final float PLAYER_Y_POSITION = 5.0f;
    public static final float PLAYER_WIDTH = 12.0f;
    public static final float PLAYER_HEIGHT = 1.5f;
    public static final int INITIAL_LIVES = 5;

    /* VELOCITY & ACCELERATION */
    // Used in "normalizing" the device accelerometer data
    public static final float GRAVITATIONAL_ACCELERATION = 9.8f;
    // Adjust player rate of acceleration
    public static final float ACCELERATION_MULTIPLIER = 4f;
    // Used to prevent movement when user tries to hold device level
    public static final float STATIC_FRICTION = 0.2f;
    // Multiply to velocity to slow down
    public static final float KINETIC_FRICTION = 0.9f;
    // Player maximum velocity
    public static final float MAX_SCROLL_SPEED = 0.9f;
    public static final float KEYPRESS_ACCELERATION = 1.0f;
    // Adjust length of exhaust relative to player velocity
    public static final float EXHAUST_LENGTH_MULTIPLIER = 1.8f;

    /* STARS */
    // Starscape width relative to world size.
    public static final float STARSCAPE_WIDTH_MULTIPLIER = 3.0f;
    // Starscape speed relative to player speed.
    public static final float STARSCAPE_SPEED_MULTIPLIER = 10f;
    public static final float DEBRIS_SCROLL_MULTIPLIER = 20f;
    public static final int STARSCAPE_NUMBER_OF_STARS = 300;

    /* BLOCKS */
    public static final float BLOCK_WIDTH = 5.0f;
    public static final float BLOCK_HEIGHT = 3.0f;
    public static final float BLOCK_SPACING = 0.2f;
    public static final int BLOCK_MAX_STRENGTH = 4;
    public static final int POINTS_PER_BLOCK = 2;

    /* LEVEL RANDOM SEEDS */
    public static final long SEED_LVL_1 = 12345L;

    /* BALLS */
    public static final float BALL_ON_PADDLE_OFFSET = 0.001f;
    public static final float BALL_RADIUS = 1.0f;
    public static final int BALL_SEGMENTS = 50;
    // Max velocity for various difficulty levels
    public static final float[] BALL_MAX_VELOCITY = {0.7f, 0.8f, 0.95f};
    public static final float BALL_MIN_VELOCITY = 0.6f;
    public static final float BALL_GRAVITY = 0.02f;
    // Adjust ball launch velocity relative to player velocity
    public static final float BALL_LAUNCH_VELOCITY_X_MULTIPLIER = 0.5f;
    // Number of previous positions to display in trail
    public static final float BALL_TRAIL_LENGTH = 20;
    public static final int MAX_NUMBER_BALLS = 10;
    public static final int[] BALL_STREAK_DOUBLER = {16, 24, 32};
    public static final float BALL_SPLIT_ANGLE = 6f;

    /* COLLISION */
    // Precision (percent of velocity) in backtracking to the exact point of collision.
    public static final float COLLISION_DETECTION_PRECISION = 0.10f;
    public static final float ABSORB_PADDLE_ANGLE = 0.5f; // Alpha blending
    // Percentage of paddle velocity to add to ball
    public static final float ABSORB_VELOCITY_MULTIPLIER = 0.2f;

    /* BUTTONS */
    public static final float BUTTON_WIDTH = 200f;
    public static final float BUTTON_HEIGHT = 40f;
    public static final Color BUTTON_COLOR = Color.YELLOW;
}
