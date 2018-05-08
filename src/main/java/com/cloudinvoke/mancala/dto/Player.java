package com.cloudinvoke.mancala.dto;

/**
 * Represents the details of a player in the game.
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class Player {
	public String name;
	public int id;

	public Player() {
	}

	public Player(PlayerId playerId) {
		this.name = playerId.getDefaultName();
		this.id = playerId.getIntValue();
	}
}