package com.cloudinvoke.mancala.dto;

/**
 * Represents a move of the current {@link Player} in a {@link Game}.
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class Move {
	
	/** The index of the Pit where the player takes stones from. */
	public int pitIndex;
	
	public Move() {
	}
	
	public Move(int pitIndex) {
		this.pitIndex = pitIndex;
	}
	
	@Override
	public String toString() {
		return String.valueOf(pitIndex);
	}
}