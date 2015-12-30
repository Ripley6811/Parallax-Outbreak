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
        // TODO: Maybe remove adding platform velocity and just use placement.
        // TODO: Fix this to be similar to block collision.
        // TODO: Add collision effect between multiple balls.
        boolean isHit = false;
        boolean isCorner = false;
        Rectangle rectangle = player.rectangle;
        for (SingleBall ball: balls) {
            Vector2 displacement = new Vector2(0f, 0f);
            Vector2 dispTotal = new Vector2(0f, 0f);
            float a, b, c, d;
            if (Intersector.overlaps(ball.circle, rectangle)) {
                System.out.println("Start");
                // Bottom test
                a = Intersector.intersectSegmentCircleDisplace(
                        rectangle.getPosition(new Vector2()),
                        rectangle.getPosition(new Vector2()).add(rectangle.getWidth(), 0),
                        ball.position, ball.circle.radius,
                        displacement
                );
                dispTotal.add(displacement);
                displacement.set(0f, 0f);
                // Right side test
                b = Intersector.intersectSegmentCircleDisplace(
                        rectangle.getPosition(new Vector2()).add(rectangle.getWidth(), 0),
                        rectangle.getPosition(new Vector2()).add(rectangle.getWidth(), rectangle.getHeight()),
                        ball.position, ball.circle.radius,
                        displacement
                );
                dispTotal.add(displacement);
                displacement.set(0f, 0f);
                // Top test
                c = Intersector.intersectSegmentCircleDisplace(
                        rectangle.getPosition(new Vector2()).add(rectangle.getWidth(), rectangle.getHeight()),
                        rectangle.getPosition(new Vector2()).add(0, rectangle.getHeight()),
                        ball.position, ball.circle.radius,
                        displacement
                );
                dispTotal.add(displacement);
                displacement.set(0f, 0f);
                // Left side test
                d = Intersector.intersectSegmentCircleDisplace(
                        rectangle.getPosition(new Vector2()).add(0, rectangle.getHeight()),
                        rectangle.getPosition(new Vector2()),
                        ball.position, ball.circle.radius,
                        displacement
                );
                dispTotal.add(displacement);
                displacement.set(0f, 0f);

                float dist = Math.min(Math.min(a, b), Math.min(c, d));
                System.out.println("a,b,c,d: " + a + "," + b + "," + c + "," + d);
                System.out.println("End: (" + dispTotal.x + "," + dispTotal.y + ")" + dist);
                if (Math.abs(dispTotal.y) > 0) {
                    ball.velocity.y *= -1f;
                    ball.position.y += 2 * dist * dispTotal.y;
                    isHit = true;
                    // Adds platform velocity to ball hit.
                    ball.velocity.x += (player.velocity - ball.velocity.x) * 0.1f;
                }
                if (Math.abs(dispTotal.x) > 0) {
                    ball.velocity.x *= -1f;
                    ball.position.x += 2 * dist * dispTotal.x;
                    isHit = true;
                }
            }
        }
    }

    /**
     * Checks for overlap of each ball on the rectangle parameter and adjusts
     * the balls movement in response to a hit.
     *
     * @param rectangle The rectangle object to test.
     * @param velocity Horizontal velocity of rectangle object.
     * @return True if a hit occurred, otherwise false.
     */

    /**
     * Checks for overlap of each ball on the rectangle parameter and adjusts
     * the balls movement in response to a hit.
     *
     * @param blocks
     * @return
     */
    public boolean checkCollision(Array<SingleBlock> blocks) {
        boolean isHit = false;

        for (SingleBall ball: balls) {
            ball.checkCollision(blocks);
        }
        return isHit;
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
