package com.tantch.taf;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tantch.taf.screens.GameScreen;

public class TAFGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public ArrayList<String> dead;

	@Override
	public void create() {
		batch = new SpriteBatch();
		dead = new ArrayList<String>();
		font = new BitmapFont(Gdx.files.internal("font/roboto.fnt"),false);
		font.setColor(Color.BLACK);
		try {
			this.setScreen(new GameScreen(this));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {

	}
}
