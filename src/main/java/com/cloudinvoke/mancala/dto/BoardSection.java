package com.cloudinvoke.mancala.dto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A section in a Mancala game consist of a Big Pit (Mancala) and a set of small pits potentially with stones.
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class BoardSection {
	public Mancala mancala;
	public List<Pit> pits;
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(mancala).append("::");
		if (pits == null) {
			b.append("null");
		}
		else {
			String pitsCsv = pits.stream().map(Object::toString).collect(Collectors.joining(","));
			b.append(pitsCsv);
		}
		return b.toString();		
	}
}