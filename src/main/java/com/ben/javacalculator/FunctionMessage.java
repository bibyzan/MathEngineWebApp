package com.ben.javacalculator;

/**
 * Class made to return a message to
 * notify of invalid input
 * @author Ben Rasmussen
 */
public class FunctionMessage extends FunctionPart {
	private String message;

	public FunctionMessage(String m) {
		message = m;
	}

	@Override
	public String toString() {
		return message;
	}
}
