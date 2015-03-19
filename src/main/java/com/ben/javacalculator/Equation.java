package com.ben.javacalculator;

/**
 * Lowest tier class that utilizes FunctionPart
 * made to interpret the users request and
 * perform the necessary calculations
 * @author Ben Rasmussen
 */
public class Equation {
	private boolean algebraic;
	private Function leftSide, rightSide, functionOf;
	private String inputField, inTermsOf;

	public Equation() {
		algebraic = false;
		inputField = "";
		leftSide = new Function();
		rightSide = new Function();
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
		} else if (rightSide.size() > 0 && algebraic) {
			String var = leftSide.getDimensions().get(0);
			if (var.equals("y")||var.equals("x")||var.equals("z")) {
				functionOf = new Function(rightSide);
				inTermsOf = var;
			}
			var = rightSide.toString();
			if (var.equals("y")||var.equals("x")||var.equals("z")) {
				inTermsOf = var;
				functionOf = new Function(leftSide);
			}
			Main.graph.addPlot(this);
			rightSide.add(new FunctionMessage(" >> added to graph"));
		} else {
			rightSide.add(new Function(new Function(leftSide).calcValue("")));
		}
	}

	@Override
	public String toString() {
		return leftSide + " = " + rightSide;
	}

	public Function getFunctionOf() {
		return functionOf;
	}

	public void setFunctionOf(Function functionOf) {
		this.functionOf = functionOf;
	}

	public String getInTermsOf() {
		return inTermsOf;
	}

	public void setInTermsOf(String inTermsOf) {
		this.inTermsOf = inTermsOf;
	}

	public Function getLeftSide() {
		return leftSide;
	}

	public void setLeftSide(Function leftSide) {
		this.leftSide = leftSide;
	}

	public void addToLeft(FunctionPart f) {
		leftSide.add(f);
	}

	public Function getRightSide() {
		return rightSide;
	}

	public void addToRight(FunctionPart f) {
		rightSide.add(f);
	}

	public void setRightSide(Function rightSide) {
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
