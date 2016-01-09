package com.mygdx.game.outbreak;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Jay on 1/7/2016.
 */
public class Button extends Rectangle {
    private static final String TAG = Button.class.getName();

    private String text = "";
    private boolean mouseover = false;
    private Texture normalButton;
    private Texture highlightButton;
    private Color baseColor = Constants.BUTTON_COLOR;
    private BitmapFont font;

    public Button(String text, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.text = text;
        this.init();
    }

    private void init() {
        font = new BitmapFont();
        font.getData().setScale(Constants.FONT_SCALE);
        font.getRegion().getTexture().setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        generateButtonTextures();
    }

    public String getText() {
        return this.text;
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        if (mouseover) {
            batch.draw(highlightButton, x, y, width, height);
        } else {
            batch.draw(normalButton, x, y, width, height);
        }

        font.setColor(Color.BLACK);
        font.getData().setScale(1.5f);
        Vector2 rCenter = new Vector2();
        this.getCenter(rCenter);
        font.draw(batch, getText(),
                rCenter.x, rCenter.y + font.getCapHeight() / 2f,
                0, Align.center, false);
        batch.end();
    }

    public void setColor(Color color) {
        this.baseColor = color;
        generateButtonTextures();
    }

    /**
     * Checks if point overlaps button and sets mouseover boolean.
     *
     * @param pt The point to check
     */
    public void mouseMoved(Vector2 pt) {
        if (Intersector.overlaps(new Circle(pt,1), this)) {
            mouseover = true;
        } else {
            mouseover = false;
        }
    }

    public boolean isMouseover() {
        return mouseover;
    }

    /**
     * Creates the two button textures. One for normal and one for highlighted.
     */
    private void generateButtonTextures() {
        int W = (int)this.width;
        int H = (int)this.height;
        Pixmap pixmap = new Pixmap(W, H, Pixmap.Format.RGBA8888);

        pixmap.setColor(baseColor);
        pixmap.fillRectangle(4, 4, W-8, H-8);
        normalButton = new Texture(pixmap);

        // Outer-ring for mouseover button
        pixmap.fillRectangle(0, 0, W, H);
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(4, 4, W - 8, H - 8);
        pixmap.setColor(baseColor);
        pixmap.fillRectangle(8, 8, W-16, H-16);
        highlightButton = new Texture(pixmap);
    }
}
