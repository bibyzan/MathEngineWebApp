package com.ben.javacalculator;

import java.util.ArrayList;

/**
 * Parent class of the Function and Expression classes,
 * made for the possibility of having functions within
 * functions to the extent of which the user needs.
 * @author Ben Rasmussen
 */
public abstract class FunctionPart {
	protected boolean factor, numerator;
	protected String task;

	public FunctionPart() {
		factor = false;
		numerator = false;
		task = "";
	}

	public java.util.List<String> getDimensions() { return null; }

	public boolean isAlgebraic() {
		return false;
	}

	public String calcValue() {
		return calcValue("x=" + Main.variables[(int)'x']);
	}

	public String calcValue(String input) {
		return "0";
	}

	public ArrayListMod<FunctionPart> calcDerivative() {
		return null;
	}

	public ArrayListMod<FunctionPart> multiply(FunctionPart f) {
		return null;
	}

	public boolean isFactor() {
		return factor;
	}

	public void setFactor(boolean factor) {
		this.factor = factor;
	}

	public boolean isNumerator() {
		return numerator;
	}

	public void setNumerator(boolean numerator) {
		this.numerator = numerator;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public boolean hasTask() {
		return !task.equals("");
	}
}
