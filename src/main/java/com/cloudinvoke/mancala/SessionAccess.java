package com.cloudinvoke.mancala;

import spark.Request;

import com.cloudinvoke.mancala.dto.Game;

/**
 * Groups concerns related to the access of the user session.
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
class SessionAccess {
	
	private static final String GAME_KEY = "game";

	private SessionAccess() {
	}
	
	static Game setGame(Request request, Game game) {
		request.session(true) 
		   .attribute(GAME_KEY, game);
		return game;
	}
	
	static Game getGame(Request request) {
		return request.session().attribute(GAME_KEY);
	}
	

}
