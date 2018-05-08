package com.cloudinvoke.mancala.gamelogic;

import com.cloudinvoke.mancala.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Takes a {@link Game} and transform it to represent its state when the current player makes the specified {@link Move}.
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class MoveApplier {
	
	private static final Logger LOG = LoggerFactory.getLogger(MoveApplier.class);

	private Game game;
	private Move move;
	private BoardSection boardSection;
	private PitNode pitRingPtr;
	
	public Game applyMove(Game game, Move move) {
		LOG.info("Applying move {} to game {}", move, game);
		this.game = game;
		this.move = move;
		validateArgs();		
		findBoardSection();
		initPitRing();
		distributeStones();
		tryCaptureStones();
		setPlayer();
		checkGameOver();
		countUp();
		LOG.info("Resulting game state: {}", game);
		return game;
	}

	private void validateArgs() {
		if (game == null)
			throw new IllegalArgumentException("Game can't be null.");
		if (move == null)
			throw new IllegalArgumentException("Move needs to be specified.");
		if (move.pitIndex < 0)
			throw new IllegalArgumentException("Pit index can't be negative.");
		if (move.pitIndex >= game.boardSetup.pitCount)
			throw new IllegalArgumentException("Pit index too high.");
	}

	private void findBoardSection() {
		boardSection = game.currentPlayerId == PlayerId.NORTH_PLAYER.getIntValue() ? game.board.northSection : game.board.southSection;
	}

	private void initPitRing() {
		Pit startPit = boardSection.pits.get(move.pitIndex);
		if (startPit.stoneCount == 0)
			throw new IllegalArgumentException("Pit is empty.");
		pitRingPtr = PitRingBuilder.build(game);
		while (pitRingPtr.pit != startPit) {
			pitRingPtr = pitRingPtr.next;
		}
	}

	private void distributeStones() {
		Pit startPit = pitRingPtr.pit;
		int stonesInHand = startPit.stoneCount;
		LOG.info("Distributing {} stones", stonesInHand);
		startPit.stoneCount = 0;
		while (stonesInHand > 0) {
			pitRingPtr = pitRingPtr.next;
			pitRingPtr.pit.stoneCount += 1;
			stonesInHand--;
		}
	}

	private void tryCaptureStones() {
		if (pitRingPtr.isCurrentPlayerOwned && !pitRingPtr.isMancala && pitRingPtr.pit.stoneCount == 1) {
			LOG.info("Capturing {} stones.", pitRingPtr.opposite.pit.stoneCount);
			boardSection.mancala.stoneCount += 1;
			pitRingPtr.pit.stoneCount = 0;
			boardSection.mancala.stoneCount += pitRingPtr.opposite.pit.stoneCount;
			pitRingPtr.opposite.pit.stoneCount = 0;
		}
	}

	private void checkGameOver() { 
		game.gameOver = pitsInSectionEmpty(game.board.northSection) || pitsInSectionEmpty(game.board.southSection);
	}
	
	private void countUp() {
		if (game.gameOver) {
			LOG.info("Gameover detected. Gathering leftover stones.");
			pitStonesToMancala(game.board.northSection);
			pitStonesToMancala(game.board.southSection);
			game.winnerPlayerId = game.board.northSection.mancala.stoneCount > game.board.southSection.mancala.stoneCount ? PlayerId.NORTH_PLAYER.getIntValue() : PlayerId.SOUTH_PLAYER.getIntValue();
		}
	}

	private void setPlayer() {
		if (!pitRingPtr.isMancala)
			otherPlayersTurn(game);
		else
			LOG.info("Player {} goes again.", game.currentPlayerId);
	}
	
	private void otherPlayersTurn(Game game) {
		game.currentPlayerId = PlayerId.valueOf(game.currentPlayerId).otherPlayer().getIntValue();
	}

	private boolean pitsInSectionEmpty(BoardSection section) {
		boolean empty = true;
		for (Pit pit : section.pits) {
			if (pit.stoneCount > 0) {
				empty = false;
				break;
			}
		}
		return empty;
	}

	private void pitStonesToMancala(BoardSection section) {
		for (Pit pit : section.pits) {
			section.mancala.stoneCount += pit.stoneCount;
			pit.stoneCount = 0;
		}
	}
}
