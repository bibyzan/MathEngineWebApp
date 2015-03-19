package com.ben.javacalculator;

import java.util.List;

/**
 * Class made to return a message to
 * notify of invalid input
 * @author Ben Rasmussen
 */
public class FunctionMessage implements FunctionPart {
	private String message;

	public FunctionMessage(String m) {
		message = m;
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public List<String> getDimensions() {
		return null;
	}

	@Override
	public boolean isAlgebraic() {
		return false;
	}

	@Override
	public String calcValue(String input) {
		return null;
	}

	@Override
	public boolean isFactor() {
		return false;
	}

	@Override
	public void setFactor(boolean factor) {

	}

	@Override
	public boolean isNumerator() {
		return false;
	}

	@Override
	public void setNumerator(boolean numerator) {

	}
}
