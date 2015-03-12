package com.ben.javacalculator;

/**
 * Lowest tier class that utilizes FunctionPart
 * made to interpret the users request and
 * perform the necessary calculations
 * @author Ben Rasmussen
 */
public class Equation {
	private boolean algebraic;
	private ArrayListMod<FunctionPart> leftSide, rightSide;
	private String inputField;

	public Equation() {
		algebraic = false;
		inputField = "";
		leftSide = new ArrayListMod<FunctionPart>();
		rightSide = new ArrayListMod<FunctionPart>();
	}

	public Equation(Equation e) {
		algebraic = e.isAlgebraic();
		leftSide = e.getLeftSide();
		rightSide = e.getRightSide();
		inputField = e.getInputField();
	}

	public Equation(String text) {
		this(StringParser.getEquationFromString(text));
		performTasks();
	}

	private void performTasks() {
		if (rightSide.size() > 0 && !algebraic) {
			if (leftSide.calcValue(inputField).equals(rightSide.calcValue(inputField)))
				rightSide.add(new FunctionMessage(" >> correct"));
			else
				rightSide.add(new FunctionMessage(" >> wrong"));
		} else if (containsInput()) {
			rightSide.add(new Function(new Function(leftSide).calcValue(inputField)));
			rightSide.add(new FunctionMessage("; " + inputField));
		} else {
			rightSide.add(new Function(new Function(leftSide).calcValue("")));
		}
	}

	@Override
	public String toString() {
		return leftSide + " = " + rightSide;
	}

	public ArrayListMod<FunctionPart> getLeftSide() {
		return leftSide;
	}

	public void setLeftSide(ArrayListMod<FunctionPart> leftSide) {
		this.leftSide = leftSide;
	}

	public void addToLeft(FunctionPart f) {
		leftSide.add(f);
	}

	public ArrayListMod<FunctionPart> getRightSide() {
		return rightSide;
	}

	public void addToRight(FunctionPart f) {
		rightSide.add(f);
	}

	public void setRightSide(ArrayListMod<FunctionPart> rightSide) {
		this.rightSide = rightSide;
	}

	public String getInputField() {
		return inputField;
	}

	public void setInputField(String inputField) {
		this.inputField = inputField;
	}

	public boolean isAlgebraic() {
		return algebraic;
	}

	public void setAlgebraic(boolean algebraic) {
		this.algebraic = algebraic;
	}

	public boolean containsInput() {
		return !inputField.equals("");
	}
}
