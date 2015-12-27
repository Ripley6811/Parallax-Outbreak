package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by Jay on 12/23/2015.
 */
public class DebrisLayer {

    Viewport viewport;
    float debrisScrollPosition; // horizontal displacement
    int debrisViewWidth, debrisViewHeight;
    Texture texture;
    TextureRegion region;
    private SpriteBatch batch;

//    Array<Vector2> stars;

    public DebrisLayer(Viewport viewport) {
        this.viewport = viewport;
        texture = new Texture(Gdx.files.internal("nebula1.png"));
        debrisViewWidth = texture.getWidth();
        debrisViewHeight = texture.getHeight();
        batch = new SpriteBatch();
        init();
    }

    public void init() {
        int N_STARS = 6;
        debrisScrollPosition = 0.0f;
//        stars = new Array<Vector2>(N_STARS);
//        debrisViewWidth = (int)(viewport.getWorldWidth() * Constants.STARSCAPE_WIDTH_MULTIPLIER);

        System.out.println("Width:" + debrisViewWidth + " " + debrisScrollPosition);
//        Random random = new Random(Constants.SEED_LVL_1);
//        for (int i = 0; i < N_STARS; i++) {
//            int x = random.nextInt(debrisViewWidth);
//            int y = random.nextInt((int)viewport.getWorldHeight());
//            stars.add(new Vector2(x, y));
//        }
    }

    public void update(float deltaTime, float scrollVelocity) {
        // Update star position based on player velocity.
        debrisScrollPosition += scrollVelocity;
        // Keep star field as a high positive value.
        if (debrisScrollPosition < debrisViewWidth) {
            debrisScrollPosition += debrisViewWidth;
        }
    }

    public void render(ShapeRenderer renderer) {
        // Draws each star in "stars" array.
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        renderer.begin(ShapeRenderer.ShapeType.Filled);
//        renderer.identity();
//        for (Vector2 star : stars){
//            renderer.setColor(1,0,0,0.2f);
//            renderer.circle((star.x + debrisScrollPosition) % debrisViewWidth,
//                    star.y, 15, 50);
//            renderer.circle((star.x + debrisScrollPosition) % debrisViewWidth - debrisViewWidth,
//                    star.y, 15, 50);
//        }
//        renderer.end();
//        region = new TextureRegion(texture, (int)debrisScrollPosition, 0, 1000, debrisViewHeight);
        batch.begin();
        batch.draw(texture, debrisScrollPosition % debrisViewWidth, 0);
        batch.draw(texture, debrisScrollPosition % debrisViewWidth - debrisViewWidth, 0);
        batch.end();


        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
