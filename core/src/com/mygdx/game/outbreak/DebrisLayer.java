package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Jay on 12/23/2015.
 */
public class DebrisLayer {
    private static final String TAG = DebrisLayer.class.getName();
    // TODO: Change to individual nebula images and randomize position.
    // TODO: New nebula images may need opacity decreased.
    Viewport viewport;
    float debrisScrollPosition; // horizontal displacement
    int debrisViewWidth, debrisViewHeight;
    Texture texture;
    int debrisDrawWidth;
    int debrisDrawHeight;

    public DebrisLayer(Viewport viewport) {
        this.viewport = viewport;
        texture = new Texture(Gdx.files.internal("nebula1.png"));
        debrisViewWidth = texture.getWidth();
        debrisViewHeight = texture.getHeight();

        debrisDrawHeight = 100;
        debrisDrawWidth = debrisViewWidth * 100 / debrisViewHeight;
        Gdx.app.log(TAG, "Nebula image size: " + debrisViewWidth + ", " + debrisViewHeight);
        init();
    }

    public void init() {
        debrisScrollPosition = 0.0f;
    }

    public void update(float deltaTime, float scrollVelocity) {
        // Update scroll position based on player velocity.
        debrisScrollPosition += scrollVelocity * deltaTime * Constants.DEBRIS_SCROLL_MULTIPLIER;
        // Keep position as a positive value.
        if (debrisScrollPosition < debrisDrawWidth) {
            debrisScrollPosition += debrisDrawWidth;
        }
    }

    public void render(SpriteBatch batch) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();
        batch.draw(texture,
                debrisScrollPosition % debrisDrawWidth, 0,
                debrisDrawWidth, debrisDrawHeight);
        batch.draw(texture,
                debrisScrollPosition % debrisDrawWidth - debrisDrawWidth, 0,
                debrisDrawWidth, debrisDrawHeight);
        batch.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
