package com.mygdx.game.outbreak;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Ripley on 12/28/2015.
 */
public class Balls {

    Viewport viewport;
    float scrollPosition;
    int worldWidth;

    int MAX_NUMBER_BALLS = 5;
    int nBalls;
    Array<SingleBall> balls;

    public Balls(Viewport viewport) {
        this.viewport = viewport;
        nBalls = 1;
        balls = new Array<SingleBall>(MAX_NUMBER_BALLS);
        balls.add(new SingleBall(10, 10));
        init();
    }

    public void init() {
        scrollPosition = 0.0f;
        worldWidth = (int)viewport.getWorldWidth();

    }

    public void update(float delta, float scrollPosition){
        this.scrollPosition = scrollPosition;
    }

    public void render(ShapeRenderer shapeRenderer){
        for (int i=0; i < nBalls; i++){
            balls.get(i).render(shapeRenderer);
        }
    }
}
