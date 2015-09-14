package com.tantch.taf.server;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.tantch.taf.screens.GameScreen;

public class GameServerHandler implements HttpHandler {

	private GameScreen screen;

	public GameServerHandler(GameScreen screen) {
		this.screen = screen;
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
		System.out.println("LOG" + t.getRequestURI().getPath());
		System.out.println("LOG" + t.getRequestURI().getQuery());
		String path = t.getRequestURI().getPath();
		String[] paths = path.split("/");// paths[0] é vazio , paths[1] é sempre
											// canvas senão não chega a esta
											// função
		switch (paths[2]) {
		case "newPlayer":
			addNewPlayer(t);
			break;
		case "leftDown":
			// screen.getFighter(name).start(left);
			break;
		default:
			break;
		}

	}

	private void addNewPlayer(HttpExchange t) throws IOException {

		String nick;
		String[] queries = t.getRequestURI().getQuery().split("&");
		String[] temp = queries[0].split("=");
		nick = temp[1];
		String response = "success";
		t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		
		os.close();

	}

}
