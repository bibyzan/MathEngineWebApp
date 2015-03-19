package com.ben.javacalculator;

public class VariableBound {
	private String variable;
	private double lowerBound, upperBound, scale;

	public VariableBound(String variable) {
		this.variable = variable;
		lowerBound = -10;
		upperBound = 10;
		scale = 1;
	}

	public double getAxisRange() {
		return upperBound - lowerBound;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public double getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}

	public double getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}
}