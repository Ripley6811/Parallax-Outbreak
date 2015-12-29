package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Ripley on 12/28/2015.
 */
public class SingleBall extends Constants {
    Circle circle;
    Vector2 position;
    Vector2 velocity;
    float ball_glow_radius = 0f;
    boolean onPlayer = true;
    boolean isDead = false;
    float time;
    Array<Vector2> trail;

    Array<SingleBlock> collisions;
    boolean willCollide = false;
    Vector2 reflectVector = new Vector2();

    public SingleBall(float x, float y) {
        circle = new Circle(x, y, BALL_RADIUS);
        position = new Vector2(x, y);
        velocity = new Vector2(0f, 0f);
        time = 0f;
        trail = new Array<Vector2>(0);
        collisions = new Array<SingleBlock>(0);
        init();
    }

    public void init() {

    }

    public void checkCollision (Array<SingleBlock> blocks) {
        Vector2 futurePos = new Vector2(position.x + velocity.x, position.y + velocity.y);
        Circle ballCopy = new Circle(futurePos, circle.radius);

        // Find all future overlaps and put in array
        // TODO: may speed up if check distance from block center first.
        collisions.clear(); // Empty previous results.
        for (SingleBlock block: blocks) {
            if (block.strength > 0) {
                if (Intersector.overlaps(ballCopy, block.rectangle)) {
                    collisions.add(block);
                }
            }
        }

        System.out.println(collisions.size);
        if (collisions.size > 0) {
            // Backtrack to find position just before collision
            do {
                ballCopy.x -= velocity.x * COLLISION_DETECTION_PRECISION;
                ballCopy.y -= velocity.y * COLLISION_DETECTION_PRECISION;
            } while (anyCollision(ballCopy));

            // Find closest rectangle segment to collision
            float closestDist = 100f;
            SingleBlock hitBlock = collisions.get(0);
//            Vector2 pointA = new Vector2();
//            Vector2 pointB = new Vector2();
            Vector2 bCenter = new Vector2(ballCopy.x, ballCopy.y);
            Vector2 pointCollision = new Vector2();
            for (SingleBlock collisionBlock: collisions) {
                Vector2 bl, br, tr, tl;
                Rectangle rect = collisionBlock.rectangle;
                bl = new Vector2(rect.x, rect.y);
                br = new Vector2(rect.x + rect.getWidth(), rect.y);
                tr = new Vector2(rect.x + rect.getWidth(), rect.y + rect.getHeight());
                tl = new Vector2(rect.x, rect.y + rect.getHeight());

                Vector2 a = new Vector2();
                Vector2 b = new Vector2();
                Vector2 c = new Vector2();
                Vector2 d = new Vector2();
                Intersector.nearestSegmentPoint(bl, br, bCenter, a);
                Intersector.nearestSegmentPoint(br, tr, bCenter, b);
                Intersector.nearestSegmentPoint(tr, tl, bCenter, c);
                Intersector.nearestSegmentPoint(tl, bl, bCenter, d);

                System.out.println(" ");
                System.out.println(bl + " " + br);
                System.out.println(a + ", " + b + ", " + c);
                System.out.println(br.x == b.x);
                System.out.println(br.y == b.y);

                float diffA = bCenter.dst2(a);
                float diffB = bCenter.dst2(b);
                float diffC = bCenter.dst2(c);
                float diffD = bCenter.dst2(d);

                if (diffA < closestDist) {
                    closestDist = diffA;
                    pointCollision.set(a);
                    hitBlock = collisionBlock;
                }

                if (diffB < closestDist) {
                    closestDist = diffB;
                    pointCollision.set(b);
                    hitBlock = collisionBlock;
                }

                if (diffC < closestDist) {
                    closestDist = diffC;
                    pointCollision.set(c);
                    hitBlock = collisionBlock;
                }

                if (diffD < closestDist) {
                    closestDist = diffD;
                    pointCollision.set(d);
                    hitBlock = collisionBlock;
                }
            }

            // Resolve collision with point on segment
            // Normalized collision vector from ball center to contact point.
            reflectVector.set(bCenter).sub(pointCollision).rotate90(1).nor();
            System.out.println("Norm: " + reflectVector);
            Vector2 reverseVelocity = new Vector2(-velocity.x, -velocity.y);
//            velNorm.nor();

//            System.out.println("arccos: " + Math.acos(velNorm.dot(collisionVector)));
//            Vector2 perpVector = new Vector2(-reflectVector.y, reflectVector.x);
            reverseVelocity.sub(reflectVector.scl(reverseVelocity.dot(reflectVector)).scl(2f));
            System.out.println("New vec: " +  reverseVelocity);

            //TODO: following is test. Improve this
            velocity.set(reverseVelocity);
            hitBlock.strength -= 1;
        }


    }

    /**
     * Use in backtracking a position until no collisions occur.
     * @param ballCopy predicted ball position
     * @return true if overlaps with any blocks in collision array.
     */
    public boolean anyCollision (Circle ballCopy) {
        for (SingleBlock collisionBlock: collisions) {
            if (Intersector.overlaps(ballCopy, collisionBlock.rectangle)) {
                return true;
            }
        }
        return false; // No overlaps.
    }

    public void update (float deltaTime, float scrollVelocity) {
        if (!onPlayer) {
            position.add(velocity);
            position.x += scrollVelocity;
            trail.insert(0, new Vector2(-velocity.x, -velocity.y));
            if (position.y + BALL_RADIUS > WORLD_SIZE - HUD_HEIGHT) {
                velocity.y = -velocity.y;
                position.y += velocity.y;
                trail.get(0).y = 0f;
            }
        }
        velocity.clamp(BALL_MAX_VELOCITY / 2, BALL_MAX_VELOCITY);

        // Drop oldest trail vector.
        if (trail.size > BALL_TRAIL_LENGTH) {
            trail.pop();
        }

        // Keep ball within world boundaries.
        if (position.x < 0) {
            position.x += WORLD_SIZE;
        } else if (position.x > WORLD_SIZE) {
            position.x -= WORLD_SIZE;
        }

        // Set as dead if ball falls below world.
        if (position.y < 0) {
            isDead = true;
        }

        // Update circle
        circle.setPosition(position);

        // Alter ball glow radius.
        time += deltaTime * 4f;
        ball_glow_radius = (Math.abs(MathUtils.sin(time))) * 0.5f;
    }

    /**
     * Sets the ball free of the player paddle (no longer follows)
     *
     * @param xVelocity The horizontal velocity of player at release
     */
    public void setFree(float xVelocity) {
        onPlayer = false;
        velocity.set(
                xVelocity * BALL_LAUNCH_VELOCITY_X_MULTIPLIER,
                BALL_MAX_VELOCITY
        );
        velocity.clamp(BALL_MAX_VELOCITY / 2, BALL_MAX_VELOCITY);
    }

    public void setPosition(float newX, float newY) {
        position.set(newX, newY);
    }

    public void render(ShapeRenderer renderer) {
        float adjustedXPos = position.x % WORLD_SIZE;

        // Enable alpha blending.
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // Draw ball trail.
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.identity();
        renderer.translate(adjustedXPos, position.y, 0);
        Vector2 trailTotal = new Vector2(0,0);
        float trailRadius = BALL_RADIUS;
        for (Vector2 t: trail) {
            trailRadius *= 0.9f;
            trailTotal.add(t);
            // Keep trail within view boundary
            if (adjustedXPos + trailTotal.x < 0) {
                trailTotal.x += WORLD_SIZE;
            } else if (adjustedXPos + trailTotal.x > WORLD_SIZE) {
                trailTotal.x -= WORLD_SIZE;
            }

            renderer.setColor(new Color(1,1,1,0.2f));
            renderer.circle(trailTotal.x, trailTotal.y,
                    trailRadius, BALL_SEGMENTS / 2);
        }
        renderer.end();


        // Draw ball.
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.identity();
        renderer.translate(adjustedXPos, position.y, 0);
        renderer.setColor(Color.LIGHT_GRAY);
        renderer.circle(0, 0, BALL_RADIUS, BALL_SEGMENTS);
        renderer.end();

        // Add ball inner gradient and outer glow.
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.identity();
        renderer.translate(adjustedXPos, position.y, 0);
        for (float r=BALL_RADIUS; r > 0f; r -= 0.1) {
            renderer.setColor(new Color(1f,1f,1f,r));
            renderer.circle(0, 0, r, BALL_SEGMENTS);
        }
        for (float r=0f; r < ball_glow_radius; r += 0.1) {
            renderer.setColor(new Color(1f,1f,1f, ball_glow_radius -r));
            renderer.circle(0, 0, BALL_RADIUS+r, BALL_SEGMENTS);
        }
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
