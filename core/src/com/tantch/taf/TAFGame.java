package com.tantch.taf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tantch.taf.screens.GameScreen;

public class TAFGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("font/roboto.fnt"),false);
		font.setColor(Color.BLACK);
		this.setScreen(new GameScreen(this));

	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {

	}
}
