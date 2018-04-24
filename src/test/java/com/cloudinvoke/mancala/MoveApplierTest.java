package com.cloudinvoke.mancala;

import org.junit.Test;

import com.cloudinvoke.mancala.dto.Game;
import com.cloudinvoke.mancala.dto.Move;
import com.cloudinvoke.mancala.gamelogic.GameBuilder;
import com.cloudinvoke.mancala.gamelogic.MoveApplier;

/**
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class MoveApplierTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void nullMoveThrowsException() {
		new MoveApplier().applyMove(GameBuilder.buildDefault(), null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void negativeIndexThrowsException() {
		new MoveApplier().applyMove(GameBuilder.buildDefault(), new Move() {{ pitIndex = -1; }});
	}

	@Test(expected=IllegalArgumentException.class)
	public void invalidIndexThrowsException() {
		Game game = GameBuilder.buildDefault();
		game.boardSetup.pitCount = 6;
		new MoveApplier().applyMove(game, new Move() {{ pitIndex = 6; }});
	}
}
