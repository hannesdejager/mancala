package com.cloudinvoke.mancala.gamelogic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.cloudinvoke.mancala.dto.BoardSection;
import com.cloudinvoke.mancala.dto.Game;
import com.cloudinvoke.mancala.dto.Move;
import com.cloudinvoke.mancala.dto.Pit;

/**
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class MoveApplierTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void nullGameThrowsException() {
		new MoveApplier().applyMove(null, new Move(1));
	}

	@Test(expected=IllegalArgumentException.class)
	public void nullMoveThrowsException() {
		new MoveApplier().applyMove(GameBuilder.buildDefault(), null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void negativeIndexThrowsException() {
		new MoveApplier().applyMove(GameBuilder.buildDefault(), new Move(-1));
	}

	@Test(expected=IllegalArgumentException.class)
	public void invalidIndexThrowsException() {
		Game game = GameBuilder.buildDefault();
		game.boardSetup.pitCount = 6;
		new MoveApplier().applyMove(game, new Move(6));
	}
	
	@Test
	public void otherPlayerGetsTurn() {
		Game game = GameBuilder.buildDefault();
		Game result = new MoveApplier().applyMove(game, new Move(1));
		assertEquals(1, result.currentPlayerId);
		result = new MoveApplier().applyMove(game, new Move(1));
		assertEquals(0, result.currentPlayerId);
	}
	
	@Test
	public void lastStoneInMancalaGivesAnotherTurn() {
		Game game = GameBuilder.buildDefault();
		assertEquals(0, game.currentPlayerId);
		Game result = new MoveApplier().applyMove(game, new Move(5));
		result = new MoveApplier().applyMove(game, new Move(1));
		assertEquals(1, result.currentPlayerId);
	}	
	
	@Test(expected=IllegalArgumentException.class)
	public void emptyPitSelectThrowsException() {
		Game game = GameBuilder.buildDefault();
		MoveApplier moveApplier = new MoveApplier();
		game = moveApplier.applyMove(game, new Move(0));
		game = moveApplier.applyMove(game, new Move(0));
		game = moveApplier.applyMove(game, new Move(0));
	}
	
	@Test
	public void captureHappensOnlyInOwnPit() {
		Game game = GameBuilder.buildDefault();
		MoveApplier moveApplier = new MoveApplier();
		game = moveApplier.applyMove(game, new Move(2));
		game = moveApplier.applyMove(game, new Move(0));
		game = moveApplier.applyMove(game, new Move(5));
		assertEquals(1, game.board.southSection.pits.get(0).stoneCount);
		assertEquals(8, game.board.northSection.pits.get(0).stoneCount);
	}
		
	@Test
	public void captureHappens() {
		Game game = GameBuilder.buildDefault();
		MoveApplier moveApplier = new MoveApplier();
		game = moveApplier.applyMove(game, new Move(2));
		game = moveApplier.applyMove(game, new Move(2));
		game = moveApplier.applyMove(game, new Move(3));
		game = moveApplier.applyMove(game, new Move(3));
		game = moveApplier.applyMove(game, new Move(2));
		int mancalaCountBefore = game.board.southSection.mancala.stoneCount;
		assertEquals(2, mancalaCountBefore);
		int oppenentPitCountBefore = game.board.northSection.pits.get(3).stoneCount;
		game = moveApplier.applyMove(game, new Move(2)); 
		int mancalaCountAfter = game.board.southSection.mancala.stoneCount;
		assertEquals(4, mancalaCountAfter);		
		assertEquals(mancalaCountAfter, oppenentPitCountBefore + 1 + mancalaCountBefore);
	}
	
	@Test
	public void gameOverHandled() {
		Game game = GameBuilder.buildDefault();
		MoveApplier moveApplier = new MoveApplier();
		int count = 0;
		while (! game.gameOver) {
			game = moveApplier.applyMove(game, findNextMove(game));
			if (count > 1000)
				throw new IllegalStateException("Endless loop");
		}
		assertEquals(true, game.gameOver);
		assertEquals(true, game.winnerPlayerId != -1);
		int pitStoneCount = 0;
		for (Pit pit : game.board.northSection.pits) {
			pitStoneCount += pit.stoneCount;
		}
		assertEquals(0, pitStoneCount);
		for (Pit pit : game.board.southSection.pits) {
			pitStoneCount += pit.stoneCount;
		}
		assertEquals(0, pitStoneCount);
	}
	
	private Move findNextMove(Game game) {
		BoardSection section = game.currentPlayerId == 0 ? game.board.northSection : game.board.southSection;
		for (int i = 0; i < section.pits.size(); i++) {
			if (section.pits.get(i).stoneCount > 0)
				return new Move(i);  
		}
		return null;
	}
	
}
