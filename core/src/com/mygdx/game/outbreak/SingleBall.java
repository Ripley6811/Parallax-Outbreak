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
    private static final String TAG = SingleBall.class.getName();

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
    Vector2 normalizedRotationVector = new Vector2();

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

    public void checkCollision (Player player) {
        Vector2 futurePos = new Vector2(position.x + velocity.x, position.y + velocity.y);
        Circle ballCopy = new Circle(futurePos, circle.radius);
        Rectangle rect = player.rectangle;

        if (Intersector.overlaps(ballCopy, rect)) {
            Gdx.app.debug(TAG, "Ball collided with paddle.");

            Vector2 tr, tl; // Top-right and top-left corner of paddle
            Vector2 pointCollision = new Vector2();

            // Rewind to point of collision
            do {
                ballCopy.x -= velocity.x * COLLISION_DETECTION_PRECISION;
                ballCopy.y -= velocity.y * COLLISION_DETECTION_PRECISION;
            } while (Intersector.overlaps(ballCopy, rect));

            // Only test collision across paddle top
            futurePos.set(ballCopy.x, ballCopy.y);
            tr = new Vector2(rect.x + rect.getWidth(), rect.y + rect.getHeight());
            tl = new Vector2(rect.x, rect.y + rect.getHeight());
            Intersector.nearestSegmentPoint(tr, tl, futurePos, pointCollision);

            // Resolve collision with point on segment
            // Normalized vector for mirroring velocity vector.
            normalizedRotationVector.set(futurePos).sub(pointCollision).rotate90(1).nor();
            Gdx.app.debug(TAG, "Rotation vector: " + normalizedRotationVector);
            Vector2 newVelocity = new Vector2(-velocity.x, -velocity.y);
            newVelocity.sub(normalizedRotationVector.scl(newVelocity.dot(normalizedRotationVector)).scl(2f));
            Gdx.app.debug(TAG, "New vec: " + newVelocity);

            //TODO: following is test. Improve this
            velocity.set(newVelocity);
        }
    }

    public void checkCollision (Array<SingleBlock> blocks) {
        // TODO: Blocks overhanging left side do not collide with ball
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

        if (collisions.size > 0) {
            Gdx.app.log(TAG, "Number of collisions: " + collisions.size);
            // Backtrack to find position just before collision
            do {
                ballCopy.x -= velocity.x * COLLISION_DETECTION_PRECISION;
                ballCopy.y -= velocity.y * COLLISION_DETECTION_PRECISION;
            } while (anyCollision(ballCopy));

            // Find closest rectangle segment to collision
            SingleBlock hitBlock = collisions.get(0);
            Vector2 bCenter = new Vector2(ballCopy.x, ballCopy.y);
            Vector2 pointCollision = new Vector2();
            for (SingleBlock collisionBlock: collisions) {
                boolean isHit = getCollisionPoint(bCenter, collisionBlock.rectangle, pointCollision);
                if (isHit) hitBlock = collisionBlock;
            }

            // Resolve collision with point on segment
            // Normalized vector for mirroring velocity vector.
            normalizedRotationVector.set(bCenter).sub(pointCollision).rotate90(1).nor();
            Gdx.app.debug(TAG, "Rotation vector: " + normalizedRotationVector);
            Vector2 newVelocity = new Vector2(-velocity.x, -velocity.y);
            newVelocity.sub(normalizedRotationVector.scl(newVelocity.dot(normalizedRotationVector)).scl(2f));
            Gdx.app.debug(TAG, "New vec: " + newVelocity);

            //TODO: following is test. Improve this
            velocity.set(newVelocity);
            hitBlock.strength -= 1;
        }


    }

    /**
     * Calculates closest point of contact on rectangle edge. Updates vector
     * and returns boolean indicating vector was updated.
     * @param ballPosition
     * @param rectangle
     * @param vector Existing vector to update if closer point found
     * @return True if closer point found (vector updated).
     */
    public boolean getCollisionPoint (Vector2 ballPosition, Rectangle rectangle, Vector2 vector) {
        boolean foundCloser = false;
        Vector2 bl, br, tr, tl;
        Rectangle rect = rectangle;
        bl = new Vector2(rect.x, rect.y);
        br = new Vector2(rect.x + rect.getWidth(), rect.y);
        tr = new Vector2(rect.x + rect.getWidth(), rect.y + rect.getHeight());
        tl = new Vector2(rect.x, rect.y + rect.getHeight());
        Gdx.app.log(TAG, "Testing Block: ( " + bl + " " + br + " " + tr + " " + tl + " )");

        Array<Vector2> points = new Array<Vector2>();
        for (int i=0; i<4; i++) points.add(new Vector2());

        Intersector.nearestSegmentPoint(bl, br, ballPosition, points.get(0));
        Intersector.nearestSegmentPoint(br, tr, ballPosition, points.get(1));
        Intersector.nearestSegmentPoint(tr, tl, ballPosition, points.get(2));
        Intersector.nearestSegmentPoint(tl, bl, ballPosition, points.get(3));

        for (Vector2 pt: points) {
            if (ballPosition.dst2(pt) < ballPosition.dst2(vector)) {
                vector.set(pt);
                foundCloser = true;
            }
        }

        return foundCloser;
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
        if (isDead) return;

        if (!onPlayer) {
            position.add(velocity);
            position.x += scrollVelocity;
            velocity.y -= BALL_GRAVITY * deltaTime;
            trail.insert(0, new Vector2(-velocity.x, -velocity.y));
            if (position.y + BALL_RADIUS > WORLD_SIZE - HUD_HEIGHT) {
                velocity.y = -velocity.y;
                position.y += velocity.y;
                trail.get(0).y = 0f;
            }
        }
        velocity.clamp(BALL_MIN_VELOCITY, BALL_MAX_VELOCITY);

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
        if (position.y < -20) {
            Gdx.app.debug(TAG, "Ball died: " + position);
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
