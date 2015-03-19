package com.ben.javacalculator;

import java.util.ArrayList;

/**
 * Parent class of the Function and Expression classes,
 * made for the possibility of having functions within
 * functions to the extent of which the user needs.
 * @author Ben Rasmussen
 */
public interface FunctionPart {

	public java.util.List<String> getDimensions();

	public boolean isAlgebraic();

	public String calcValue(String input);

	public boolean isFactor();

	public void setFactor(boolean factor);

	public boolean isNumerator();

	public void setNumerator(boolean numerator);

}
