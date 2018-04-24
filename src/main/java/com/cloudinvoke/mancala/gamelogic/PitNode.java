package com.cloudinvoke.mancala.gamelogic;

import com.cloudinvoke.mancala.dto.Pit;

/**
 * The nodes in a Pit Ring (my data structure to ease the game logic) looks like this. See {@link PitRingBuilder} for a bit more info on the data structure. 
 * 
 * @see PitRingBuilder
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
class PitNode {
	Pit pit;
	PitNode next;
	PitNode opposite;
	boolean isMancala;
	boolean isCurrentPlayerOwned;
}

