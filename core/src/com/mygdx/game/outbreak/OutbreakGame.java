package com.mygdx.game.outbreak;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class OutbreakGame extends Game {

	@Override
	public void create () {
		Gdx.app.setLogLevel(Constants.LOG_LEVEL);
		setScreen(new GameScreen(this));
	}
}
