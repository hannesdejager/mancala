package com.cloudinvoke.mancala.gamelogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cloudinvoke.mancala.dto.Game;

public class PitRingBuilderTest {

	@Test(expected = IllegalArgumentException.class)
	public void nullGameThrowsException() {
		PitRingBuilder.build(null);
	}
	
	@Test
	public void northThenMancalaThenSouthThenNorth() {
		Game game = GameBuilder.buildDefault();
		pitsMancalaPitsLoop(game);
	}

	@Test
	public void southThenMancalaThenNorthThenSouth() {
		Game game = GameBuilder.buildDefault();
		game.currentPlayerId = 1;
		pitsMancalaPitsLoop(game);
	}

	private void pitsMancalaPitsLoop(Game game) {
		PitNode node = PitRingBuilder.build(game);
		PitNode firstNode = node;
		assertFalse(node.isMancala);		
		for (int i = 0; i < game.boardSetup.pitCount; i++) {
			node = node.next;
		}
		assertTrue(node.isMancala);
		for (int i = 0; i < game.boardSetup.pitCount; i++) {
			node = node.next;
		}
		assertFalse(node.isMancala);
		node = node.next;
		assertFalse(node.isMancala);
		assertEquals(firstNode, node);
	}	
	
	@Test
	public void onlyOneMancala() {
		Game game = GameBuilder.buildDefault();
		PitNode startNode = PitRingBuilder.build(game);
		PitNode node = startNode;
		int mancalaCount = 0;
		do {
			if (node.isMancala)
				mancalaCount++;
			node = node.next;
		} while (node != startNode && node != null);
		assertEquals(1, mancalaCount);
		assertNotNull(node);
	}	
}
