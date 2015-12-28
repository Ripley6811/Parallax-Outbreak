package com.mygdx.game.outbreak;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Ripley on 12/28/2015.
 */
public class Balls {

    Viewport viewport;
    float scrollVelocity;
    int worldWidth;

    int MAX_NUMBER_BALLS = 5;
    int nBalls;
    Array<SingleBall> balls;

    public Balls(Viewport viewport) {
        this.viewport = viewport;
        nBalls = 1;
        balls = new Array<SingleBall>(0);
        balls.add(new SingleBall(10, 10));
        init();
    }

    public void init() {
        scrollVelocity = 0.0f;
        worldWidth = (int)viewport.getWorldWidth();

    }

    public void update(float delta, float scrollVelocity, Vector2 playerPosition){
        this.scrollVelocity = scrollVelocity;

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

    public void setFree(float velocity) {
        balls.get(0).setFree(velocity);
    }

    public boolean checkCollision(Rectangle rectangle) {
        return checkCollision(rectangle, 0f);
    }

    public boolean checkCollision(Rectangle rectangle, float velocity) {
        // TODO: Maybe remove adding platform velocity and just use placement
        boolean isHit = false;
        for (SingleBall ball: balls) {
            Vector2 displacement = new Vector2();
            Vector2 dispTotal = new Vector2();
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
                // Right side test
                b = Intersector.intersectSegmentCircleDisplace(
                        rectangle.getPosition(new Vector2()).add(rectangle.getWidth(), 0),
                        rectangle.getPosition(new Vector2()).add(rectangle.getWidth(), rectangle.getHeight()),
                        ball.position, ball.circle.radius,
                        displacement
                );
                dispTotal.add(displacement);
                // Top test
                c = Intersector.intersectSegmentCircleDisplace(
                        rectangle.getPosition(new Vector2()).add(rectangle.getWidth(), rectangle.getHeight()),
                        rectangle.getPosition(new Vector2()).add(0, rectangle.getHeight()),
                        ball.position, ball.circle.radius,
                        displacement
                );
                dispTotal.add(displacement);
                // Left side test
                d = Intersector.intersectSegmentCircleDisplace(
                        rectangle.getPosition(new Vector2()).add(0, rectangle.getHeight()),
                        rectangle.getPosition(new Vector2()),
                        ball.position, ball.circle.radius,
                        displacement
                );
                dispTotal.add(displacement);
                float dist = Math.min(Math.min(a, b), Math.min(c, d));
                System.out.println("End: (" + displacement.x + "," + displacement.y + ")" + dist);
                if (Math.abs(displacement.y) > 0) {
                    ball.velocity.y *= -1f;
                    ball.position.y += 2*dist * displacement.y;
                    isHit = true;
                    // Adds platform velocity to ball hit.
                    ball.velocity.x += (velocity - ball.velocity.x) * 0.1f;
                }
                if (Math.abs(displacement.x) > 0) {
                    ball.velocity.x *= -1f;
                    ball.position.x += 2*dist * displacement.x;
                    isHit = true;
                }
            }
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
