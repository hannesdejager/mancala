package com.cloudinvoke.mancala.gamelogic;

import java.util.Arrays;
import java.util.List;

import com.cloudinvoke.mancala.dto.Board;
import com.cloudinvoke.mancala.dto.BoardSection;
import com.cloudinvoke.mancala.dto.BoardSetup;
import com.cloudinvoke.mancala.dto.Game;
import com.cloudinvoke.mancala.dto.Mancala;
import com.cloudinvoke.mancala.dto.Pit;
import com.cloudinvoke.mancala.dto.Player;

/**
 * A helper class to build a {@link Game} data structure.
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class GameBuilder {

	private static final int DEFAULT_PIT_COUNT_PER_SECTION = 6;
	private static final int DEFAULT_STONE_PER_PIT_COUNT = 6;

	/**
	 * Builds the default structure used when the game and board is initialized.
	 * 
	 * @return An instance of {@link Game} with all fields populated and valid.
	 */
	public static Game buildDefault() {
		Game game = new Game();
		game.boardSetup = new BoardSetup();
		game.boardSetup.pitCount = DEFAULT_PIT_COUNT_PER_SECTION;
		game.boardSetup.stoneCountPerPit = DEFAULT_STONE_PER_PIT_COUNT;
		game.board = new Board();
		game.board.northSection = defaultSection();
		game.board.southSection = defaultSection();
		game.playerA = new Player();
		game.playerA.name = "North Player";
		game.playerA.id = 0;
		game.playerB = new Player();
		game.playerB.name = "South Player";
		game.playerB.id = 1;
		game.currentPlayerId = 0;
		game.gameOver = false;
		game.winnerPlayerId = -1;		
		return game;
	}

	private static BoardSection defaultSection() {
		BoardSection section = new BoardSection();
		section.mancala = new Mancala();
		section.mancala.stoneCount = 0;
		section.pits = defaultSectionPits();
		return section;
	}

	private static List<Pit> defaultSectionPits() {
		return Arrays.asList(defaultPit(), defaultPit(), defaultPit(),
				defaultPit(), defaultPit(), defaultPit());
	}

	private static Pit defaultPit() {
		return new Pit(DEFAULT_STONE_PER_PIT_COUNT);
	}

	private GameBuilder() {
	}
}
