package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
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
    float starScrollPosition;
    int starScapeWidth;

    int N_STARS = 50;
    Array<Vector2> stars;

    public StarScape(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {
        starScrollPosition = 0.0f;
        stars = new Array<Vector2>(N_STARS);
        starScapeWidth = (int)viewport.getWorldWidth() * 2;
        Random random = new Random();
        for (int i = 0; i < N_STARS; i++) {
            int x = random.nextInt(starScapeWidth);
            int y = random.nextInt((int)viewport.getWorldHeight());
            stars.add(new Vector2(x,y));
        }
    }

    public void update (float deltaTime, float scrollVelocity) {
        starScrollPosition += scrollVelocity / 2;
        starScrollPosition %= starScapeWidth;
    }


//    public void updateScrollPosition (Vector2 camPosition) {
//        position.set(camPosition.x, position.y);
//    }

    public void render(ShapeRenderer renderer) {
        System.out.println(starScrollPosition % starScapeWidth);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.identity();
        renderer.setColor(255,255,255,1);
        for (Vector2 star : stars){
            renderer.circle(star.x + starScrollPosition, star.y, Constants.STAR_RADIUS);
        }
        renderer.end();
    }
}
