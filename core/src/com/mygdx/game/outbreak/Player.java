package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Jay on 12/18/2015.
 */
public class Player {

    Vector2 position;
    float velocity;
    float MAX_DISPLACEMENT = 2.0f;

    Viewport viewport;

    int deaths;

    float accelerometerInput;

    public Player(Viewport viewport) {
        this.viewport = viewport;
        velocity = 0.0f;
        deaths = 0;
        init();
    }

    public void init() {
        // Centered starting position.
        position = new Vector2(
                viewport.getWorldWidth() / 2,
                Constants.PLAYER_Y_POSITION
        );
    }

    public void update(float delta, float scrollVelocity) {
        this.velocity = -scrollVelocity;
//        // Accelerometer input
//        accelerometerInput = Gdx.input.getAccelerometerY() /
//                                 (Constants.GRAVITATIONAL_ACCELERATION *
//                                  Constants.ACCELEROMETER_SENSITIVITY);
//        // Key input
//        if (Math.abs(accelerometerInput) > 0.2f) {
//            velocity += accelerometerInput / 2.0f;
//        } else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
//            velocity -= 0.2;
//        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
//            velocity += 0.2;
//        } else {
//            velocity *= 0.8;
//        }
////        System.out.println(velocity);
//
//        if (velocity < -1 * MAX_DISPLACEMENT) {
//            velocity = -1 * MAX_DISPLACEMENT;
//        } else if (velocity > MAX_DISPLACEMENT) {
//            velocity = MAX_DISPLACEMENT;
//        }
    }

    public void render(ShapeRenderer renderer) {
        renderer.begin(ShapeType.Filled);
        renderer.identity();
        renderer.translate(position.x, 0, 0);

        // TODO: Replace with engines firing to the sides.
        // Draw red player reference
        renderer.setColor(Color.RED);
        renderer.rect(-Constants.PLAYER_WIDTH / 2, Constants.PLAYER_HEIGHT / 2,
                Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);

        // Draw player
        renderer.setColor(Constants.PLAYER_COLOR);
        renderer.rect(-Constants.PLAYER_WIDTH / 2 + velocity, Constants.PLAYER_HEIGHT / 2,
                Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        renderer.end();
    }
}
