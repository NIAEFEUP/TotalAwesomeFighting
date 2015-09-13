package com.tantch.taf.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class GameServer {
	public static HttpServer server;

	public GameServer(int port, String context, GameServerHandler handler) throws IOException {

		// configuring server
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext(context, handler);

			// creates a default executor
			server.setExecutor(null);

		} catch (IOException e) {
			System.out.println("Exception caught: " + e.getMessage()
					+ " in Server.contructor");
		}
	}

	/**
	 * starts the server
	 */
	public static void init() {
		server.start();
	}

	public void dispose() {
		server.stop(0);
	}

}
