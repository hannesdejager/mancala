package com.cloudinvoke.mancala.dto;

/**
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class ErrorMessage {
	private final String message;
	
	public ErrorMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
