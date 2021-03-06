package com.cloudinvoke.mancala.dto;

/**
 * The Mancala board composed of 2 sections.
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class Board {
	public BoardSection northSection;
	public BoardSection southSection;
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("[")
			.append(northSection)
			.append(" | ")
			.append(southSection)
			.append("]")
			.toString();
	}
}