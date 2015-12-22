package com.mygdx.game.outbreak;

import com.badlogic.gdx.Game;

public class OutbreakGame extends Game {


	@Override
	public void create () {
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setScreen(new GameScreen(this));
	}
}
