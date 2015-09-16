package com.tantch.taf.screens;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.tantch.taf.TAFGame;
import com.tantch.taf.entities.Fighter;
import com.tantch.taf.entities.Projectile;
import com.tantch.taf.server.GameServer;
import com.tantch.taf.server.GameServerHandler;

public class GameScreen implements Screen {
	final TAFGame game;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	OrthographicCamera camera;
	private static HashMap<String, Fighter> fighters;
	public ConcurrentLinkedDeque<Projectile> projectiles;
	private GameServer server;
	private ArrayList<String> newfighters;

	public GameScreen(final TAFGame gam) throws IOException {
		server = new GameServer(7777, "/fighter", new GameServerHandler(this));
		newfighters = new ArrayList<String>();
		GameServer.init();
		game = gam;
		projectiles = new ConcurrentLinkedDeque<Projectile>();

	}

	@Override
	public void show() {
		map = new TmxMapLoader().load("maps/map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);

		camera = new OrthographicCamera();
		camera.position.set(
				(((TiledMapTileLayer) map.getLayers().get(0)).getWidth()
						* ((TiledMapTileLayer) map.getLayers().get(0)).getTileWidth()) / 2,
				(((TiledMapTileLayer) map.getLayers().get(0)).getHeight()
						* ((TiledMapTileLayer) map.getLayers().get(0)).getTileHeight()) / 2,
				0);
		fighters = new HashMap<String, Fighter>();
		Fighter fighter = new Fighter(game, (TiledMapTileLayer) map.getLayers().get(0), "1", fighters, projectiles);
		fighter.setPosition((int) (5 * fighter.getCollisionLayer().getTileWidth()),
				(fighter.getCollisionLayer().getHeight() - 19) * fighter.getCollisionLayer().getHeight());
		fighters.put("1", fighter);
		Gdx.input.setInputProcessor(fighter);

		Fighter fighter1 = new Fighter(game, (TiledMapTileLayer) map.getLayers().get(0), "2", fighters, projectiles);
		fighter1.setPosition((int) (7 * fighter.getCollisionLayer().getTileWidth()),
				(fighter.getCollisionLayer().getHeight() - 19) * fighter.getCollisionLayer().getHeight());
		fighters.put("2", fighter1);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		renderer.setView(camera);
		renderer.render();
		//
		// renderer.getBatch().begin();
		// dummy.draw(renderer.getBatch());
		// renderer.getBatch().end();

		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		{
			for (Projectile projectile : projectiles) {
				projectile.draw(delta);
			}
			fighters.forEach((k, v) -> v.draw(delta));
			for (String string : game.dead) {
				fighters.remove(string);
				System.out.println("killed : " + string);
			}
			game.dead = new ArrayList<String>();
			ArrayList<String> newTemp = (ArrayList<String>) newfighters.clone();
			newfighters = new ArrayList<String>();

			for (String string : newTemp) {
				Fighter temp = new Fighter(game, (TiledMapTileLayer) map.getLayers().get(0), string, fighters, projectiles);
				Random rd= new Random();

				temp.setPosition(rd.nextInt(200)+200, 200);
				fighters.put(string, temp);
				System.out.println("LOG fighters sze : " + fighters.size());

			}
			//
		}
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportHeight = height;
		camera.viewportWidth = width;
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
		// dummy.getTexture().dispose();

	}

	public Fighter getFighter() {
		return fighters.get("1");
	}

	public Fighter getFighter(String nick) {

		return fighters.get(nick);
	}

	public void addFighter(String nick) {
		newfighters.add(nick);


	}

}
