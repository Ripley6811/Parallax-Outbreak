package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Ripley on 12/28/2015.
 */
public class Balls {
    private static final String TAG = Balls.class.getName();

    Viewport viewport;
    float scrollVelocity;
    int worldWidth;

    int nBalls;
    Array<SingleBall> balls;

    public Balls(Viewport viewport) {
        this.viewport = viewport;
        nBalls = 1;
        balls = new Array<SingleBall>(0);
        init();
    }

    public void init() {
        scrollVelocity = 0.0f;
        worldWidth = (int)viewport.getWorldWidth();
    }

    public void update(float delta, float scrollVelocity, Vector2 playerPosition){
        this.scrollVelocity = scrollVelocity;
        if (allDead()) {
            resetBalls();
        }

        for (SingleBall ball: balls) {
            if (ball.onPlayer) {
                ball.setPosition(
                        playerPosition.x + Constants.PLAYER_WIDTH / 2,
                        playerPosition.y + Constants.PLAYER_HEIGHT + Constants.BALL_RADIUS
                );
            }
            ball.update(delta, scrollVelocity);
        }
    }

    public boolean allDead() {
        Gdx.app.debug(TAG, "Number of Balls: " + balls.size);
        for (SingleBall ball: balls) {
            if (!ball.isDead) return false;
        }
        Gdx.app.debug(TAG, "Balls all dead!!!!!");
        return true;
    }

    public void resetBalls() {
        balls.clear();
        balls.add(new SingleBall(10, 10));
    }

    public void setFree(float velocity) {
        for (SingleBall ball: balls) {
            if (ball.onPlayer) ball.setFree(velocity);
        }
    }

    public void checkCollision(Player player) {
        // TODO: Add platform velocity and placement into bounce.
        // TODO: Add collision effect between multiple balls.
        for (SingleBall ball: balls) {
            ball.checkCollision(player);
        }
    }

    /**
     * Checks for overlap of each ball on the rectangle parameter and adjusts
     * the balls movement in response to a hit.
     *
     * @param blocks blocks array
     */
    public void checkCollision(Array<SingleBlock> blocks) {
        for (SingleBall ball: balls) {
            ball.checkCollision(blocks);
        }
    }

    public void setVelocity(int ball_number, float newVx, float newVy) {
        if (ball_number < nBalls) {
            balls.get(ball_number).velocity.set(newVx, newVy);
        }
    }

    public void render(ShapeRenderer shapeRenderer){
        for (int i=0; i < nBalls; i++){
            balls.get(i).render(shapeRenderer);
        }
    }
}
