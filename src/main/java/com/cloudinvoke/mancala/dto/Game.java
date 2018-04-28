package com.cloudinvoke.mancala.dto;


/**
 * Top of the DTO structure as represented in this package. It captures all the details in a Mancala game.
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class Game {
	public Board board;
	public Player playerA;
	public Player playerB;
	public int currentPlayerId;
	public BoardSetup boardSetup;
	public boolean gameOver;
	public int winnerPlayerId;
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("Current Player: ").append(currentPlayerId)
			.append(", Winner: ").append(winnerPlayerId)
			.append(", Board: ").append(board)
			.toString();
	}
}