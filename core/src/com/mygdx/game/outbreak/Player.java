package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by Jay on 12/18/2015.
 */
public class Player extends Constants {
    Rectangle rectangle;
    Vector2 home; // Starting position
    Vector2 position; // Actual position with displacement
    float velocity;
    float MAX_DISPLACEMENT = 2.0f;

    SpriteBatch batch;
    Texture paddle;

    Viewport viewport;

    int deaths;

    public Player(Viewport viewport) {
        this.viewport = viewport;
        velocity = 0.0f;
        deaths = 0;
        paddle = new Texture(createPlayerPixmap());
        batch = new SpriteBatch();
        init();
    }

    public void init() {
        // Centered starting position.
        home = new Vector2(
                viewport.getWorldWidth() / 2 - PLAYER_WIDTH / 2,
                PLAYER_Y_POSITION
        );
        position = new Vector2(
                home.x,
                home.y
        );
        rectangle = new Rectangle(position.x, position.y, PLAYER_WIDTH, PLAYER_HEIGHT);

        batch.setProjectionMatrix(viewport.getCamera().combined);
    }

    public void update(float delta, float scrollVelocity) {
        this.velocity = -scrollVelocity;
        position.x = home.x + velocity;
        rectangle.setPosition(position);
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
            float offset = (velocity < 0 ? PLAYER_WIDTH - 1 : 1);
            for (float i = -0.9f; i < 1.0f; i += 0.1) {
                // Calculate endpoints along a hemisphere.
                float x2 = (float) Math.sqrt(1 - i * i);
                float multiplier = (0.8f + 0.2f*random.nextFloat()) * Math.abs(velocity) * EXHAUST_LENGTH_MULTIPLIER;
                renderer.line(offset, PLAYER_HEIGHT / 2,
                        offset + x2 * 2f * (velocity < 0 ? 1 : -1) * multiplier,
                        PLAYER_HEIGHT / 2 + i * multiplier,
                        Color.RED,
                        new Color(1, random.nextFloat(), 0, random.nextFloat()));
            }
            renderer.end();
        }
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Draw player paddle
        batch.begin();
        batch.draw(paddle, position.x, position.y, PLAYER_WIDTH, PLAYER_HEIGHT);
        batch.end();
    }

    private Pixmap createPlayerPixmap() {
        int W = 256;
        int H = 64;
        // NOTE: Coordinate origin for Pixmap is top-left.
        Pixmap pixmap = new Pixmap(W, H, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.LIGHT_GRAY);
        pixmap.fill();
        pixmap.setColor(0,0,0,0.05f);
        for (int i = 0; i < H / 2; i++) {
            pixmap.fillRectangle(i, i, W-2*i, H-2*i);
        }
        return pixmap;
    }
}
