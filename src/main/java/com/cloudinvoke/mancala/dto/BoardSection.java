package com.cloudinvoke.mancala.dto;

import java.util.List;

/**
 * A section in a Mancala game consist of a Big Pit (Mancala) and a set of small pits potentially with stones.
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class BoardSection {
	public Mancala mancala;
	public List<Pit> pits;
}