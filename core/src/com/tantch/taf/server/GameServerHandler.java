package com.tantch.taf.server;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.tantch.taf.screens.GameScreen;

public class GameServerHandler implements HttpHandler {

	
	
	private GameScreen screen;
	
	public GameServerHandler(GameScreen screen){
		this.screen=screen;
	}
	@Override
	public void handle(HttpExchange t) throws IOException {
		System.out.println(t.getRequestURI().getPath());
		String path = t.getRequestURI().getPath();
		String[] paths = path.split("/");// paths[0] � vazio , paths[1] � sempre
											// canvas sen�o n�o chega a esta
											// fun��o
		switch (paths[2]) {
		case "addPlayer":
			addNewPlayer(paths);
			break;
		case "leftDown":
			//screen.getFighter(name).start(left);
			break;
		default:
			break;
		}

	}

	private void addNewPlayer(String[] paths) {
		System.out.println(paths.toString());
	}

}
