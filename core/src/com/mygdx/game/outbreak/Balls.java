package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    /**
     * Updates the position of all balls in play and returns true if any ball is lost.
     * @param delta time since last render
     * @param scrollVelocity horizontal scroll velocity
     * @param playerPosition Vector2 of player position
     * @return true if any ball is lost
     */
    public boolean update(float delta, float scrollVelocity, Vector2 playerPosition){
        this.scrollVelocity = scrollVelocity;
        if (allDead()) {
            resetBalls();
        }
        boolean ballDied = false;

        for (SingleBall ball: balls) {
            if (ball.onPlayer) {
                ball.setPosition(
                        playerPosition.x + Constants.PLAYER_WIDTH / 2,
                        playerPosition.y + Constants.PLAYER_HEIGHT + Constants.BALL_RADIUS
                );
            }
            if (ball.update(delta, scrollVelocity)) {
                ballDied = true;
            }
        }
        return ballDied;
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

    public int checkCollision(Player player) {
        // TODO: Add collision effect between multiple balls.
        int collisionCount = 0;
        for (SingleBall ball: balls) {
            if (ball.checkCollision(player)) collisionCount += 1;
        }
        return collisionCount;
    }

    /**
     * Checks for overlap of each ball on the rectangle parameter and adjusts
     * the balls movement in response to a hit.
     *
     * @param blocks blocks array
     */
    public int checkCollision(Array<SingleBlock> blocks) {
        int collisionCount = 0;
        for (SingleBall ball: balls) {
            if (ball.checkCollision(blocks)) collisionCount += 1;
        }
        return collisionCount;
    }

    public void setVelocity(int ball_number, float newVx, float newVy) {
        if (ball_number < nBalls) {
            balls.get(ball_number).velocity.set(newVx, newVy);
        }
    }

    public void render(ShapeRenderer renderer){
        for (int i=0; i < nBalls; i++){
            balls.get(i).render(renderer);
        }
    }
}
