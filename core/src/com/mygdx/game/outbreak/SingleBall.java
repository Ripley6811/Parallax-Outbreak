package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Ripley on 12/28/2015.
 */
public class SingleBall extends Constants {
    float x;
    float y;
    float vx;
    float vy;
    float scrollPosition;
    float BALL_GLOW = 0.5f;

    public SingleBall(float x, float y) {
        this.x = x;
        this.y = y;
        scrollPosition = 0f;
        init();
    }

    public void init() {

    }

    public void update (float deltaTime, float scrollPosition) {
        this.scrollPosition = scrollPosition;

    }

    public void render(ShapeRenderer renderer) {

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.identity();
        renderer.translate(x, y, 0);
        renderer.setColor(Color.LIGHT_GRAY);
        renderer.circle(0, 0, BALL_RADIUS, BALL_SEGMENTS);
        renderer.end();

        // Add ball inner gradient and outer glow
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.identity();
        renderer.translate(x, y, 0);
        for (float r=BALL_RADIUS; r > 0f; r -= 0.1) {
            renderer.setColor(new Color(1f,1f,1f,r));
            renderer.circle(0, 0, r, BALL_SEGMENTS);
        }
        for (float r=0f; r <BALL_GLOW; r += 0.1) {
            renderer.setColor(new Color(1f,1f,1f,BALL_GLOW-r));
            renderer.circle(0, 0, BALL_RADIUS+r, BALL_SEGMENTS);
        }
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
