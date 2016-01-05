package com.mygdx.game.outbreak;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    SpriteBatch batch;
    float starScrollPosition; // horizontal displacement
    int starScapeWidth;

    Array<Vector2> stars;
    Texture starImage = new Texture(createStar());

    public StarScape(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {
        batch = new SpriteBatch();
        int N_STARS = Constants.STARSCAPE_NUMBER_OF_STARS;
        starScrollPosition = 0.0f;
        stars = new Array<Vector2>(N_STARS);
        starScapeWidth = (int)(viewport.getWorldWidth() * Constants.STARSCAPE_WIDTH_MULTIPLIER);
        Random random = new Random(Constants.SEED_LVL_1);
        for (int i = 0; i < N_STARS; i++) {
            int x = random.nextInt(starScapeWidth);
            int y = random.nextInt((int)viewport.getWorldHeight());
            stars.add(new Vector2(x,y));
        }
    }

    public void update(float deltaTime, float scrollVelocity) {
        // Update star position based on player velocity.
        starScrollPosition += deltaTime * scrollVelocity * Constants.STARSCAPE_SPEED_MULTIPLIER;
        // Keep star field as a high positive value.
        if (starScrollPosition < starScapeWidth) {
            starScrollPosition += starScapeWidth;
        }
    }

    public void render(ShapeRenderer renderer) {
        batch.setProjectionMatrix(renderer.getProjectionMatrix());
        batch.setTransformMatrix(renderer.getTransformMatrix());

        batch.begin();
        for (Vector2 star : stars){
            batch.draw(starImage,
                    (star.x + starScrollPosition) % starScapeWidth, star.y,
                    2, 2);
        }
        batch.end();
    }


    public Pixmap createStar() {
        int SIZE = 128;  // Square canvas
        int R = SIZE / 2;  // Radius of circle on canvas

        Pixmap pixmap = new Pixmap(SIZE, SIZE, Pixmap.Format.RGBA8888);
        // NOTE: fillCircle blending is a known bug. Turn off temporarily.
        Pixmap.setBlending(Pixmap.Blending.None);
        // Star outer glow
        for (float i=1f; i>0f; i-=0.1f) {
            pixmap.setColor(1, 1, 1, 1f - i);
            pixmap.fillCircle(R, R, (int) (R * Math.pow((double) i, 4.0)));
        }
        // Strong center white color
        pixmap.setColor(1, 1, 1, 1f);
        pixmap.fillCircle(R, R, 5);

        Pixmap.setBlending(Pixmap.Blending.SourceOver);
        return pixmap;
    }
}
