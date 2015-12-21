package com.mygdx.game.outbreak;

import com.badlogic.gdx.Game;

public class OutbreakGame extends Game {
	@Override
	public void create () {
        setScreen(new GameScreen(this));
	}
}
