package com.cloudinvoke.mancala;

import static spark.Spark.get;
import static spark.Spark.patch;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Response;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import com.cloudinvoke.mancala.dto.ErrorMessage;
import com.cloudinvoke.mancala.dto.Game;
import com.cloudinvoke.mancala.dto.Move;
import com.cloudinvoke.mancala.gamelogic.GameBuilder;
import com.cloudinvoke.mancala.gamelogic.MoveApplier;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * A REST resource representing the Mancala Game.
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class GameResource {
	
	public static final String RESOURCE_PATH = "/";
	
	/**
	 * The main screen / single page web-application / board.
	 */
	public static final String GAME_HTML = "text/html";

	/**
	 * Basically a JSON representation of the {@link Game} structure
	 */
	public static final String GAME_JSON = "application/vnd.cloudinvoke-mancala.game+json";
		
	/**
	 * Registers the REST resource with the webserver.
	 */
	public static void register() {
		registerGameHtmlHandler();	
		registerGetGameJsonHandler();	
		registerMoveHandler();
	}

	private static void registerGameHtmlHandler() {
		get(RESOURCE_PATH, GAME_HTML, (request, response) -> { 
			Game game = SessionAccess.setGame(request, GameBuilder.buildDefault());
			return gameHtmlView(game);
		}, new FreeMarkerEngine());
	}

	private static void registerGetGameJsonHandler() {
		Gson gson = new Gson();
		get(RESOURCE_PATH, GAME_JSON, (request, response) -> {
			response.type(GAME_JSON);
			return SessionAccess.getGame(request);
		}, gson::toJson);
	}

	private static void registerMoveHandler() {
		Gson gson = new Gson();
		patch(RESOURCE_PATH, GAME_JSON, (request, response) -> { 			
			response.type(GAME_JSON);
			Move move = null;
			try {
				move = gson.fromJson(request.body(), Move.class);
				if (move == null) {
					response.status(Response.SC_BAD_REQUEST);
					return new ErrorMessage("Invalid 'Move' object.");
				}
				Game game = SessionAccess.getGame(request);
				return new MoveApplier().applyMove(game, move);
			} catch (JsonSyntaxException | IllegalArgumentException e) {
				response.status(Response.SC_BAD_REQUEST);
				return new ErrorMessage(e.getMessage());
			}			
		}, gson::toJson);
	}

	private static ModelAndView gameHtmlView(Game game) {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("playerA", game.playerA.name);
		attributes.put("playerB", game.playerB.name);
		attributes.put("mancalaACount", game.board.northSection.mancala.stoneCount);
		attributes.put("mancalaBCount", game.board.southSection.mancala.stoneCount);
		attributes.put("northPits", game.board.northSection.pits);
		attributes.put("southPits", game.board.southSection.pits);
		return new ModelAndView(attributes, "board.ftl");
	}
	
	private GameResource() {
	}
	
}
