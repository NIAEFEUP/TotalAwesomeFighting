package com.tantch.taf.server;

import java.io.IOException;
import java.io.OutputStream;

import com.badlogic.gdx.Input.Keys;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.tantch.taf.entities.Fighter;
import com.tantch.taf.screens.GameScreen;

public class GameServerHandler implements HttpHandler {

	private GameScreen screen;

	public GameServerHandler(GameScreen screen) {
		this.screen = screen;
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
		//System.out.println("LOG" + t.getRequestURI().getPath());
		System.out.println("LOG" + t.getRequestURI().getQuery());
		String path = t.getRequestURI().getPath();
		String[] paths = path.split("/");// paths[0] é vazio , paths[1] é sempre
											// canvas senão não chega a esta
											// função
		switch (paths[2]) {
		case "newPlayer":
			addNewPlayer(t);
			break;
		case "move":
			String nick;
			String[] queries = t.getRequestURI().getQuery().split("&");
			String[] temp = queries[0].split("=");
			nick = temp[1];
			temp= queries[1].split("=");
			String ac = temp[1];
			Fighter tempf;
			switch(ac){
			case "leftdown":
				tempf = screen.getFighter(nick);
				if(tempf != null){
					tempf.keyDown(Keys.A);
				}
				break;
			case "rightdown":
				tempf = screen.getFighter(nick);
				if(tempf != null){
					tempf.keyDown(Keys.D);
				}
				break;
			case "updown":
				tempf = screen.getFighter(nick);
				if(tempf != null){
					tempf.keyDown(Keys.W);
				}
				break;
			case "atackdown":
				tempf = screen.getFighter(nick);
				if(tempf != null){
					tempf.keyDown(Keys.SPACE);
				}
				break;
			case "leftup":
				tempf = screen.getFighter(nick);
				if(tempf != null){
					tempf.keyUp(Keys.A);
				}
				break;
			case "rightup":
				tempf = screen.getFighter(nick);
				if(tempf != null){
					tempf.keyUp(Keys.D);
				}
				break;
			case "upup":
				tempf = screen.getFighter(nick);
				if(tempf != null){
					tempf.keyUp(Keys.W);
				}
				break;
			case "atackup":
				tempf = screen.getFighter(nick);
				if(tempf != null){
					tempf.keyUp(Keys.SPACE);
				}
				break;
			}
			break;
		default:
			break;
		}

		String response = "success";

		t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());

		os.close();

	}

	private void addNewPlayer(HttpExchange t) throws IOException {
		String nick;
		String[] queries = t.getRequestURI().getQuery().split("&");
		String[] temp = queries[0].split("=");
		nick = temp[1];
		//System.out.println("here : " + nick);
		screen.addFighter(nick);

	}

}
