package com.cloudinvoke.mancala.gamelogic;

import com.cloudinvoke.mancala.dto.BoardSection;
import com.cloudinvoke.mancala.dto.Game;
import com.cloudinvoke.mancala.dto.Move;
import com.cloudinvoke.mancala.dto.Pit;

/**
 * Takes a {@link Game} and transform it to represent its state when the current player makes the specified {@link Move}.
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class MoveApplier {

	private Game game;
	private Move move;
	private BoardSection boardSection;
	private PitNode pitRingPtr;
	
	public Game applyMove(Game game, Move move) {
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
		boardSection = game.currentPlayerId == 0 ? game.board.northSection : game.board.southSection;
	}

	private void initPitRing() {
		Pit startPit = boardSection.pits.get(move.pitIndex);
		if (startPit.stoneCount == 0)
			throw new IllegalArgumentException("Pit is empty");
		pitRingPtr = PitRingBuilder.build(game);
		while (pitRingPtr.pit != startPit) {
			pitRingPtr = pitRingPtr.next;
		}
	}

	private void distributeStones() {
		Pit startPit = pitRingPtr.pit;
		int stonesInHand = startPit.stoneCount;
		startPit.stoneCount = 0;
		while (stonesInHand > 0) {
			pitRingPtr = pitRingPtr.next;
			pitRingPtr.pit.stoneCount += 1;
			stonesInHand--;
		}
	}

	private void tryCaptureStones() {
		if (pitRingPtr.isCurrentPlayerOwned && !pitRingPtr.isMancala && pitRingPtr.pit.stoneCount == 1) {
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
			pitStonesToMancala(game.board.northSection);
			pitStonesToMancala(game.board.southSection);
			game.winnerPlayerId = game.board.northSection.mancala.stoneCount > game.board.southSection.mancala.stoneCount ? 0 : 1; 
		}
	}

	private void setPlayer() {
		if (!pitRingPtr.isMancala)
			otherPlayersTurn(game);
	}
	
	private void otherPlayersTurn(Game game) {
		game.currentPlayerId = (game.currentPlayerId + 1) % 2;
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
