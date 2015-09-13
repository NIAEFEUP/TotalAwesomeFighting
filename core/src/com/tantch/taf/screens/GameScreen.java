package com.tantch.taf.screens;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.tantch.taf.TAFGame;
import com.tantch.taf.entities.Fighter;
import com.tantch.taf.inputs.GameProcessor;
import com.tantch.taf.server.GameServer;
import com.tantch.taf.server.GameServerHandler;

public class GameScreen implements Screen {
	final TAFGame game;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	OrthographicCamera camera;
	private static HashMap<String, Fighter> fighters;
	private GameServer server;

	public GameScreen(final TAFGame gam) throws IOException {
		server = new GameServer(7777, "/fighter", new GameServerHandler(this));

		GameServer.init();
		game = gam;
		Fighter fighter = new Fighter(game);
		fighters= new HashMap<String,Fighter>();
		fighters.put("mau", fighter);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		Gdx.input.setInputProcessor(new GameProcessor(this));
	}

	@Override
	public void show() {
		map = new TmxMapLoader().load("maps/map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);

		camera = new OrthographicCamera();

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.8f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		renderer.setView(camera);
		renderer.render();

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

			fighters.forEach((k, v) -> v.draw(delta));
		}
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportHeight = height;
		camera.viewportWidth = width;
		camera.update();

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
		dispose();

	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		server.dispose();

	}

	public Fighter getFighter() {
		return fighters.get("mau");
	}

}
