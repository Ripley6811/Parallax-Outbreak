package com.mygdx.game.outbreak;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class OutbreakGame extends Game {
	private static final String TAG = OutbreakGame.class.getName();

	OptionsScreen optionsScreen;
	GameScreen gameScreen;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Constants.LOG_LEVEL);
		optionsScreen = new OptionsScreen(this);
		gameScreen = new GameScreen(this);
		setScreen(optionsScreen);
	}

	public void gotoOptionsScreen() {
		Gdx.app.log(TAG, "Switching to options screen");
		setScreen(optionsScreen);
	}

	public void gotoGameScreen() {
		Gdx.app.log(TAG, "Switching to game screen");
		setScreen(gameScreen);
	}
}
