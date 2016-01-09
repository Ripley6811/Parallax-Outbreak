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

    OutbreakGame game;
    Viewport viewport;
    float scrollVelocity;
    int worldWidth;

    Array<SingleBall> balls;

    public Balls(OutbreakGame game, Viewport viewport) {
        this.game = game;
        this.viewport = viewport;
        balls = new Array<SingleBall>(0);
    }

    public void init() {
        scrollVelocity = 0.0f;
        worldWidth = (int)viewport.getWorldWidth();
        resetBalls();
    }

    /**
     * Updates the position of all balls in play and returns true if any ball is lost.
     *
     * @param delta time since last render
     * @param scrollVelocity horizontal scroll velocity
     * @param playerPosition Vector2 of player position
     * @return true if any ball is lost
     */
    public boolean update(float delta, float scrollVelocity, Vector2 playerPosition){
        this.scrollVelocity = scrollVelocity;

        boolean ballDied = false;

        for (SingleBall ball: balls) {
            if (ball.onPlayer) {
                ball.setPosition(
                        playerPosition.x + Constants.PLAYER_WIDTH / 2 + Constants.BALL_ON_PADDLE_OFFSET,
                        playerPosition.y + Constants.PLAYER_HEIGHT + Constants.BALL_RADIUS
                );
            }
            if (ball.update(delta, scrollVelocity)) {
                ballDied = true;
            }
        }
        return ballDied;
    }

    /**
     * Checks if all balls have left play.
     *
     * @return true if all balls have left game area
     */
    public boolean allDead() {
        for (SingleBall ball: balls) {
            if (!ball.isDead) return false;
        }
        return true;
    }

    /**
     * Returns the count of the number of live balls.
     *
     * @return integer number of live balls in play
     */
    public int numberAlive() {
        int total = 0;
        for (SingleBall ball: balls) {
            if (!ball.isDead) total++;
        }
        return total;
    }

    /**
     * Clears "balls" array and initializes a single ball.
     */
    public void resetBalls() {
        balls.clear();
        SingleBall ball = new SingleBall(10, 10);
        ball.init(game.getDifficulty());
        balls.add(ball);
    }

    public void setFree(float velocity) {
        for (SingleBall ball: balls) {
            if (ball.onPlayer) ball.setFree(velocity);
        }
    }

    public int checkCollision(Player player) {
        int collisionCount = 0;
        for (SingleBall ball: balls) {
            if (ball.checkCollision(player)) collisionCount += 1;
        }
        return collisionCount;
    }

    /**
     * Doubles each live ball and adds them to "balls" array.
     */
    public void splitBalls() {
        Gdx.app.log(TAG, "Splitting balls");
        Array<SingleBall> newBalls = new Array<SingleBall>();
        int numberAlive = numberAlive();
        for (SingleBall ball: balls) {
            if (!ball.isDead) {
                if (numberAlive < Constants.MAX_NUMBER_BALLS) {
                    SingleBall newBall = new SingleBall(ball.position.x, ball.position.y);
                    newBall.init(game.getDifficulty());
                    newBall.onPlayer = false;
                    newBall.velocity = new Vector2(ball.velocity);
                    ball.velocity.rotate(-Constants.BALL_SPLIT_ANGLE / 2f);
                    newBall.velocity.rotate(Constants.BALL_SPLIT_ANGLE / 2f);
                    newBalls.add(newBall);
                    newBalls.add(ball);
                    Gdx.app.log(TAG, "Made new ball: " + ball.velocity + " - " + newBall.velocity);
                    numberAlive++;
                } else {
                    newBalls.add(ball);
                }
            }
        }
        balls.clear();
        balls.addAll(newBalls);
        Gdx.app.log(TAG, "Number of live balls: " + balls.size);
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

    public void render(ShapeRenderer renderer){
        for (SingleBall ball: balls) {
            if (!ball.isDead) {
                ball.render(renderer);
            }
        }
    }
}
