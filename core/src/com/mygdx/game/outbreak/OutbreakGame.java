package com.mygdx.game.outbreak;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class OutbreakGame extends Game {
	private static final String TAG = OutbreakGame.class.getName();

	OptionsScreen optionsScreen;
	GameScreen gameScreen;
	EndScreen endScreen;
	private Constants.Difficulty difficulty;
	private int lastScore = 0;
	private int livesRemaining = 0;
	boolean blocksRegenerate = false;
	int deviceWidth;
	int deviceHeight;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Constants.LOG_LEVEL);
		deviceWidth = Gdx.graphics.getWidth();
		deviceHeight = Gdx.graphics.getHeight();
		difficulty = Constants.Difficulty.EASY;
		Gdx.app.log(TAG, "Device size: " + deviceWidth + " x " + deviceHeight);
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

	public void setDifficulty(Constants.Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Constants.Difficulty getDifficulty() {
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
