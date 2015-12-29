package com.mygdx.game.outbreak;

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

    public void checkCollision(Player player) {
        // TODO: Fix this to be similar to block collision.
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
    public boolean checkCollision(Array<SingleBlock> blocks) {
        // TODO: Maybe remove adding platform velocity and just use placement
        // TODO: Add potential hits to an Array to resolve later, interact with closest line or corner.
        // TODO: Store dist backtrack and line or point
        // TODO: (dist, vector_1, vector_2) if corner => vector_1 == vector_2.
        boolean isHit = false;
        boolean isCorner = false;
//        for (SingleBall ball: balls) {
//            Vector2 displacement = new Vector2(0f,0f);
//            Vector2 dispTotal = new Vector2(0f,0f);
//            float a, b, c, d;
//            if (Intersector.overlaps(ball.circle, rectangle)) {
//                System.out.println("Start");
//                // Bottom test
//                a = Intersector.intersectSegmentCircleDisplace(
//                        rectangle.getPosition(new Vector2()),
//                        rectangle.getPosition(new Vector2()).add(rectangle.getWidth(), 0),
//                        ball.position, ball.circle.radius,
//                        displacement
//                );
//                dispTotal.add(displacement);
//                displacement.set(0f,0f);
//                // Right side test
//                b = Intersector.intersectSegmentCircleDisplace(
//                        rectangle.getPosition(new Vector2()).add(rectangle.getWidth(), 0),
//                        rectangle.getPosition(new Vector2()).add(rectangle.getWidth(), rectangle.getHeight()),
//                        ball.position, ball.circle.radius,
//                        displacement
//                );
//                dispTotal.add(displacement);
//                displacement.set(0f, 0f);
//                // Top test
//                c = Intersector.intersectSegmentCircleDisplace(
//                        rectangle.getPosition(new Vector2()).add(rectangle.getWidth(), rectangle.getHeight()),
//                        rectangle.getPosition(new Vector2()).add(0, rectangle.getHeight()),
//                        ball.position, ball.circle.radius,
//                        displacement
//                );
//                dispTotal.add(displacement);
//                displacement.set(0f, 0f);
//                // Left side test
//                d = Intersector.intersectSegmentCircleDisplace(
//                        rectangle.getPosition(new Vector2()).add(0, rectangle.getHeight()),
//                        rectangle.getPosition(new Vector2()),
//                        ball.position, ball.circle.radius,
//                        displacement
//                );
//                dispTotal.add(displacement);
//                displacement.set(0f, 0f);
//
//                float dist = Math.min(Math.min(a, b), Math.min(c, d));
//                System.out.println("a,b,c,d: " + a + "," + b + "," + c + "," + d);
//                System.out.println("End: (" + dispTotal.x + "," + dispTotal.y + ")" + dist);
//                if (Math.abs(dispTotal.y) > 0) {
//                    ball.velocity.y *= -1f;
//                    ball.position.y += 2*dist * dispTotal.y;
//                    isHit = true;
//                    // Adds platform velocity to ball hit.
//                    ball.velocity.x += (velocity - ball.velocity.x) * 0.1f;
//                }
//                if (Math.abs(dispTotal.x) > 0) {
//                    ball.velocity.x *= -1f;
//                    ball.position.x += 2*dist * dispTotal.x;
//                    isHit = true;
//                }
//            }
//        }


        for (SingleBall ball: balls) {
            ball.checkCollision(blocks);

//            Circle ballCircleCopy = new Circle(ball.circle);
//            // If overlap, backtrack over trail until not overlap.
//            while (Intersector.overlaps(ballCircleCopy, rectangle)) {
//                isHit = true;
//                ballCircleCopy.x += ball.trail.get(0).x / 2f;
//                ballCircleCopy.y += ball.trail.get(0).y / 2f;
//            }
//
//            // Find point on segment closest to circle.
//            if (isHit) {
//                Vector2 bl, br, tr, tl, bCenter;
//                bl = new Vector2(rectangle.x, rectangle.y);
//                br = new Vector2(rectangle.x + rectangle.getWidth(), rectangle.y);
//                tr = new Vector2(rectangle.x + rectangle.getWidth(), rectangle.y + rectangle.getHeight());
//                tl = new Vector2(rectangle.x, rectangle.y + rectangle.getHeight());
//                bCenter = new Vector2(ballCircleCopy.x, ballCircleCopy.y);
//
//                Vector2 a = new Vector2();
//                Vector2 b = new Vector2();
//                Vector2 c = new Vector2();
//                Vector2 d = new Vector2();
//                Intersector.nearestSegmentPoint(bl, br, bCenter, a);
//                Intersector.nearestSegmentPoint(br, tr, bCenter, b);
//                Intersector.nearestSegmentPoint(tr, tl, bCenter, c);
//                Intersector.nearestSegmentPoint(tl, bl, bCenter, d);
//
//                System.out.println(" ");
//                System.out.println(bl + " " + br);
//                System.out.println(a + ", " + b + ", " + c);
//                System.out.println(br.x == b.x);
//                System.out.println(br.y == b.y);
//
//                float diffA = bCenter.dst2(a);
//                float diffB = bCenter.dst2(b);
//                float diffC = bCenter.dst2(c);
//                float diffD = bCenter.dst2(d);
//
//                if (diffA == diffB) {
//                    isCorner = true;
//
//                } else if (diffB == diffC) {
//                    isCorner = true;
//
//                } else if (diffC == diffD) {
//                    isCorner = true;
//
//                } else if (diffD == diffA) {
//                    isCorner = true;
//
//                }
//
//                System.out.println(diffA + ", " + diffB + ", " + diffC);
//                // If point is not at segment ends then it is a "normal" bounce
//
//                // If point is at corner need to calculate point bounce.
//            }
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
