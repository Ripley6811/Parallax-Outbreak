package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by Jay on 12/21/2015.
 */
public class StarScape {

    Viewport viewport;
    float starScrollPosition; // horizontal displacement
    int starScapeWidth;

    Array<Vector2> stars;

    public StarScape(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {
        int N_STARS = Constants.STARSCAPE_NUMBER_OF_STARS;
        starScrollPosition = 0.0f;
        stars = new Array<Vector2>(N_STARS);
        starScapeWidth = (int)(viewport.getWorldWidth() * Constants.STARSCAPE_WIDTH_MULTIPLIER);
        Random random = new Random();
        for (int i = 0; i < N_STARS; i++) {
            int x = random.nextInt(starScapeWidth);
            int y = random.nextInt((int)viewport.getWorldHeight());
            stars.add(new Vector2(x,y));
        }
    }

    public void update(float deltaTime, float scrollVelocity) {
        // Update star position based on player velocity.
        starScrollPosition += scrollVelocity * Constants.STARSCAPE_SPEED_MULTIPLIER;
        // Keep star field as a high positive value.
        if (starScrollPosition < starScapeWidth) {
            starScrollPosition += starScapeWidth;
        }
    }

    public void render(ShapeRenderer renderer) {
        // Draws each star in "stars" array.
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.identity();
        for (Vector2 star : stars){
            renderer.setColor(1,1,1,0.5f);
            renderer.circle((star.x + starScrollPosition) % starScapeWidth,
                            star.y, Constants.STAR_RADIUS);
        }
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
