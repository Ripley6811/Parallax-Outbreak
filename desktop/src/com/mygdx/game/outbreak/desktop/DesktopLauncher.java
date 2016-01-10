package com.mygdx.game.outbreak.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.outbreak.Constants;
import com.mygdx.game.outbreak.OutbreakGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Parallax Outbreak";
		config.width = Constants.TEXT_VIEWPORT_SIZE[0];
		config.height = Constants.TEXT_VIEWPORT_SIZE[1];
		new LwjglApplication(new OutbreakGame(), config);
	}
}
