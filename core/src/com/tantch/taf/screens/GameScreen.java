package com.tantch.taf.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.tantch.taf.TAFGame;
import com.tantch.taf.entities.Fighter;
import com.tantch.taf.inputs.GameProcessor;

public class GameScreen implements Screen {
	final TAFGame game;
	Fighter fighter;
	OrthographicCamera camera;

	public GameScreen(final TAFGame gam) {
		game = gam;
		fighter = new Fighter(game);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);

		Gdx.input.setInputProcessor(new GameProcessor(this));
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.8f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		{
			// game.font.draw(game.batch, "Dummy Fighter", 300, 580);
			// for (int i = 0; i < Stats.names.length; i++) {
			// String name = Stats.names[i];
			// int[] temp = dummy.getAtrib(name);
			//
			// if (temp[2] != -1) {
			// game.font.draw(game.batch, name + " : " + temp[2] + "/" + temp[1]
			// + " -- " + temp[0], 100,
			// 500 - 50 * i);
			//
			// } else {
			// game.font.draw(game.batch, name + " : " + temp[1] + " -- " +
			// temp[0], 100, 500 - 50 * i);
			// }
			//
			// }

			fighter.draw(200, 200, delta);
		}
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public Fighter getFighter() {
		return fighter;
	}

}
