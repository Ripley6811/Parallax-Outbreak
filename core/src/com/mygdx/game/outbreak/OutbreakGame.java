package com.mygdx.game.outbreak;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class OutbreakGame extends Game {
	private static final String TAG = OutbreakGame.class.getName();

	OptionsScreen optionsScreen;
	GameScreen gameScreen;
	EndScreen endScreen;
	private int difficulty = 0;
	private int lastScore = 0;
	private int livesRemaining = 0;
	boolean blocksRegenerate = false;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Constants.LOG_LEVEL);
		optionsScreen = new OptionsScreen(this);
		gameScreen = new GameScreen(this);
		endScreen = new EndScreen(this);
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

	public void gotoEndScreen() {
		Gdx.app.log(TAG, "Switching to end screen");
		setScreen(endScreen);
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = Constants.DIFFICULTY.indexOf(difficulty, false);
	}

	public int getDifficulty() {
		return this.difficulty;
	}

	public void setRegenerate(boolean onOff) {
		this.blocksRegenerate = onOff;
	}

	public void setLastScore(int score) {
		this.lastScore = score;
	}

	public int getLastScore() {
		return this.lastScore;
	}

	public void setLivesRemaining(int lives) { this.livesRemaining = lives; }

	public int getLivesRemaining() { return this.livesRemaining; }
}
