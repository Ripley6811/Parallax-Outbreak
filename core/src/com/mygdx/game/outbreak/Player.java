package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by Jay on 12/18/2015.
 */
public class Player extends Constants {

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
                PLAYER_Y_POSITION
        );
    }

    public void update(float delta, float scrollVelocity) {
        this.velocity = -scrollVelocity;
    }

    public void render(ShapeRenderer renderer) {
        // Draw engine fire
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        if (Math.abs(velocity) > STATIC_FRICTION) {
            Random random = new Random();
            renderer.begin(ShapeType.Line);
            renderer.identity();
            renderer.translate(position.x, position.y, 0);
            float offset = velocity / 2 + (velocity < 0 ? PLAYER_WIDTH / 2 - 1 :
                    -PLAYER_WIDTH / 2 + 1);
            for (float i = -0.9f; i < 1.0f; i += 0.1) {
                // Calculate endpoints along a hemisphere.
                float x2 = (float) Math.sqrt(1 - i * i);
                float multiplier = (0.8f + 0.2f*random.nextFloat()) * Math.abs(velocity) * EXHAUST_LENGTH_MULTIPLIER;
                renderer.line(offset, 0,
                        offset + x2 * 2f * (velocity < 0 ? 1 : -1) * multiplier,
                        i * multiplier,
                        Color.RED,
                        new Color(1, random.nextFloat(), 0, random.nextFloat()));
            }
            renderer.end();
        }
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Draw player paddle
        renderer.begin(ShapeType.Filled);
        renderer.identity();
        renderer.translate(position.x, position.y, 0);
        renderer.rect(-PLAYER_WIDTH / 2 + velocity / 2,
                -PLAYER_HEIGHT,
                PLAYER_WIDTH, PLAYER_HEIGHT,
                Color.LIGHT_GRAY, Color.LIGHT_GRAY,
                Color.DARK_GRAY, Color.DARK_GRAY);
        renderer.rect(-PLAYER_WIDTH / 2 + velocity / 2,
                0,
                PLAYER_WIDTH, PLAYER_HEIGHT,
                Color.DARK_GRAY, Color.DARK_GRAY,
                Color.LIGHT_GRAY, Color.LIGHT_GRAY);
        renderer.end();
    }
}
