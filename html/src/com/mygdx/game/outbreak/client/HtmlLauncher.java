package com.mygdx.game.outbreak.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mygdx.game.outbreak.Constants;
import com.mygdx.game.outbreak.OutbreakGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(
                        Constants.TEXT_VIEWPORT_SIZE[0],
                        Constants.TEXT_VIEWPORT_SIZE[1]
                );
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new OutbreakGame();
        }
}