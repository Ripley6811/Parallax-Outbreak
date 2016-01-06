package com.mygdx.game.outbreak;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class OutbreakGame extends Game {
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
		setScreen(optionsScreen);
	}

	public void gotoGameScreen() {
		setScreen(gameScreen);
	}
}
