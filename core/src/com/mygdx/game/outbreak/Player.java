package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Jay on 12/18/2015.
 */
public class Player {

    Vector2 position;

    Viewport viewport;

    int deaths;

    float accelerometerInput;


    public Player(Viewport viewport) {
        this.viewport = viewport;
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

    public void update(float delta) {
        // Key input
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
//            position.x -= delta * Constants.PLAYER_MOVEMENT_SPEED;
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
//            position.x += delta * Constants.PLAYER_MOVEMENT_SPEED;
        }

        // Accelerometer input
        accelerometerInput = Gdx.input.getAccelerometerY() /
                (Constants.GRAVITATIONAL_ACCELERATION *
                        Constants.ACCELEROMETER_SENSITIVITY);

//        position.x += -delta * accelerometerInput *
//                        Constants.PLAYER_MOVEMENT_SPEED;
    }

    public void render(ShapeRenderer renderer) {
        renderer.begin(ShapeType.Filled);
        renderer.setColor(Constants.PLAYER_COLOR);
        renderer.identity();
        renderer.translate(position.x + accelerometerInput, 0, 0);
        renderer.rect(Constants.PLAYER_WIDTH / 2, Constants.PLAYER_HEIGHT / 2,
                Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        renderer.end();

        // Temp vector arrow for showing accelerometer input;
        float displacement = 10.0f;
        renderer.begin(ShapeType.Line);
        Vector2 accelStart = new Vector2(position.x, position.y + displacement);
        Vector2 accelEnd = new Vector2(position.x + accelerometerInput,
                position.y + displacement);
        renderer.rectLine(accelStart, accelEnd, 0);
        renderer.end();
    }
}
