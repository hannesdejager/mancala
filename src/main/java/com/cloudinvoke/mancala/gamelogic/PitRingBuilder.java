package com.cloudinvoke.mancala.gamelogic;

import java.util.List;

import com.cloudinvoke.mancala.dto.BoardSection;
import com.cloudinvoke.mancala.dto.Game;
import com.cloudinvoke.mancala.dto.Pit;

/**
 * Builds a circular linked list (or ring) of {@link PitNode}s representing the pits that the current player can use to distribute stones in. Links are also
 * established to directly opposite pits. Once built, the pointer to the ring will point the the leftmost pit of the current player.
 *  
 * @author Hannes de Jager
 * @since 25 April 2018
 */
class PitRingBuilder {
	
	private final Game game;
	private PitNode northHead;
	private PitNode southHead;
	private PitNode head;
	
	/**
	 * Builds the Pit Ring for the specified game data.
	 *  
	 * @param game The game. Can't be null.
	 * @return The node representing the leftmost pit of the current player.
	 * @throws IllegalArgumentException if game is null
	 */
	public static PitNode build(Game game) {
		PitRingBuilder ring = new PitRingBuilder(game);
		return ring.build();
	}
	
	public PitRingBuilder(Game game) {
		if (game == null) 
			throw new IllegalArgumentException("game can't be null.");
		this.game = game;
	}
	
	public PitNode build() {
		buildSectionLists();
		addCurrentPlayerMancala();
		connectTailToHead();
		return head;
	}
	
	private void buildSectionLists() {
		northHead = new PitNode();
		southHead = new PitNode();
		PitNode northCurrent = northHead;
		PitNode southCurrent = southHead;
		List<Pit> northPits = game.board.northSection.pits;
		List<Pit> southPits = game.board.southSection.pits;
		for (int i = 0; i < game.boardSetup.pitCount; i++) {
			northCurrent.isMancala = false;
			northCurrent.pit = northPits.get(i);
			northCurrent.opposite = southCurrent;
			northCurrent.isCurrentPlayerOwned = game.currentPlayerId == 0;
			southCurrent.isMancala = false;
			southCurrent.pit = southPits.get(i);
			southCurrent.opposite = northCurrent;
			southCurrent.isCurrentPlayerOwned = ! northCurrent.isCurrentPlayerOwned;
			if (i < game.boardSetup.pitCount - 1) { 
				northCurrent.next = new PitNode();
				northCurrent = northCurrent.next;
				southCurrent.next = new PitNode();
				southCurrent = southCurrent.next;
			}
		}
		northHead = reverse(northHead);		
	}
		
	private void addCurrentPlayerMancala() {
		if (game.currentPlayerId == 0) {
			head = northHead;
			PitNode northTail = southHead.opposite;
			addMancala(northTail, game.board.northSection);
			northTail.next.next = southHead;
		}
		else {
			head = southHead;
			PitNode southTail = northHead.opposite;
			addMancala(southTail, game.board.southSection);
			southTail.next.next = northHead;
		}
	}
	
	private void addMancala(PitNode sectionTail, BoardSection section) {
		sectionTail.next = new PitNode();
		sectionTail.next.isMancala = true;
		sectionTail.next.isCurrentPlayerOwned = true;
		sectionTail.next.pit = section.mancala;
	}	
	
	private void connectTailToHead() {
		PitNode tail = head.opposite;
		tail.next = head;		
	}
	
	private PitNode reverse(PitNode head) {
		PitNode curNode = head;
		PitNode prevNode = null;
		while (curNode != null) {
			PitNode nextNode = curNode.next;
		    curNode.next = prevNode;
		    prevNode = curNode;
		    curNode = nextNode;
		}
		return prevNode;
	}	

}

