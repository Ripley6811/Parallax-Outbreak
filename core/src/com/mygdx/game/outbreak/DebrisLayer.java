package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Jay on 12/23/2015.
 */
public class DebrisLayer {
    // TODO: Change to individual nebula images and randomize position.
    // TODO: New nebula images may need opacity decreased.
    Viewport viewport;
    float debrisScrollPosition; // horizontal displacement
    int debrisViewWidth, debrisViewHeight;
    Texture texture;
    TextureRegion region;
    private SpriteBatch batch;

    public DebrisLayer(Viewport viewport) {
        this.viewport = viewport;
        texture = new Texture(Gdx.files.internal("nebula1.png"));
        debrisViewWidth = texture.getWidth();
        debrisViewHeight = texture.getHeight();
        batch = new SpriteBatch();
        init();
    }

    public void init() {
        debrisScrollPosition = 0.0f;
        System.out.println("Width:" + debrisViewWidth + " " + debrisScrollPosition);
    }

    public void update(float deltaTime, float scrollVelocity) {
        // Update star position based on player velocity.
        debrisScrollPosition += scrollVelocity * deltaTime * Constants.DEBRIS_SCROLL_MULTIPLIER;
        // Keep star field as a high positive value.
        if (debrisScrollPosition < debrisViewWidth) {
            debrisScrollPosition += debrisViewWidth;
        }
    }

    public void render(ShapeRenderer renderer) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();
        batch.draw(texture, debrisScrollPosition % debrisViewWidth, 0);
        batch.draw(texture, debrisScrollPosition % debrisViewWidth - debrisViewWidth, 0);
        batch.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
