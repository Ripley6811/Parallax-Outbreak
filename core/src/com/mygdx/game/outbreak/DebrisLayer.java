package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by Jay on 12/23/2015.
 */
public class DebrisLayer {

    Viewport viewport;
    float debrisScrollPosition; // horizontal displacement
    int debrisViewWidth;

    Array<Vector2> stars;

    public DebrisLayer(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {
        int N_STARS = 6;
        debrisScrollPosition = 0.0f;
        stars = new Array<Vector2>(N_STARS);
        debrisViewWidth = (int)(viewport.getWorldWidth() * Constants.STARSCAPE_WIDTH_MULTIPLIER);
        Random random = new Random(Constants.SEED_LVL_1);
        for (int i = 0; i < N_STARS; i++) {
            int x = random.nextInt(debrisViewWidth);
            int y = random.nextInt((int)viewport.getWorldHeight());
            stars.add(new Vector2(x, y));
        }
    }

    public void update(float deltaTime, float scrollVelocity) {
        // Update star position based on player velocity.
        debrisScrollPosition += scrollVelocity *
                Constants.STARSCAPE_SPEED_MULTIPLIER / 2;
        // Keep star field as a high positive value.
        if (debrisScrollPosition < debrisViewWidth) {
            debrisScrollPosition += debrisViewWidth;
        }
    }

    public void render(ShapeRenderer renderer) {
        // Draws each star in "stars" array.
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.identity();
        for (Vector2 star : stars){
            renderer.setColor(1,0,0,0.2f);
            renderer.circle((star.x + debrisScrollPosition) % debrisViewWidth,
                    star.y, 15, 50);
            renderer.circle((star.x + debrisScrollPosition) % debrisViewWidth - debrisViewWidth,
                    star.y, 15, 50);
        }
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
