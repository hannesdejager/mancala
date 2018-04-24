package com.cloudinvoke.mancala.dto;

/**
 * Represents a Pit in the Mancala game. A pit can contain stones.
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class Pit {
	public int stoneCount;
	
	public Pit(int stoneCount) {
		this.stoneCount = stoneCount;
	}

	public int getStoneCount() {
		return stoneCount;
	}

	public void setStoneCount(int stoneCount) {
		this.stoneCount = stoneCount;
	}
	
}