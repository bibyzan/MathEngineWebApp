package com.ben.javacalculator;

import java.util.stream.Collectors;

/**
 * Expressions are the individual building blocks for
 * functions, and eventually Equations as indicated in those classes
 * @author Ben Rasmussen
 */
public class Expression implements FunctionPart{
	private double value;
	private String functionIdentity;
	private ArrayListMod<Variable> variables;
	private boolean variable, e, carrot, function, factor, numerator;
	private Function power, innerFunction;

	//default setter
	public Expression() {
		super();
		value = 1;
		variable = false;
		e = false;
		carrot = false;
		function = false;
		variables = new ArrayListMod<>();
		innerFunction = new Function();
		power = new Function();
		functionIdentity = "";
	}

	//good for the power expression
	public Expression(double pow) {
		value = pow;
	}

	//copy constructor
	public Expression(Expression e) {
		value = e.getValue();
		variable = e.isVariable();
		this.e = e.isE();
		function = e.isFunction();
		innerFunction = e.getInnerFunction();
		functionIdentity = e.getFunctionIdentity();
		carrot = e.isCarrot();
		power = e.power;
		numerator = e.isNumerator();
		factor = e.isFactor();
		variables = e.getVariables();
	}

	public ArrayListMod<String> getDimensions() {
		return variables.stream().map(Variable::getLetter)
				.collect(Collectors.toCollection(ArrayListMod::new));
	}

	@Override
	public boolean isFactor() {
		return factor;
	}

	@Override
	public void setFactor(boolean factor) {
		this.factor = factor;
	}

	@Override
	public boolean isNumerator() {
		return numerator;
	}

	@Override
	public void setNumerator(boolean numerator) {
		this.numerator = numerator;
	}

	public Expression(String text) {
		this(StringParser.getExpressionFromString(text));
	}

	public boolean isShortMultiplication() {
		return variable || e || function;
	}

	public boolean isRegularFunction() {
		return !variable && !e && !function && !carrot;
	}

	@Override
	public String calcValue(String input) {
		double number = value;
		String str = "";
		
		if (variable)
			for (Variable v: variables)
				try {
					number *= Double.parseDouble(v.calcValue(input));
				} catch (NumberFormatException n) {
					str += v;
				}
		if (e)
			number *= Math.E;
		try {
			if (carrot)
				number = Math.pow(number, Double.parseDouble(power.calcValue(input)));
			if (function) {
				double innerValue = Double.parseDouble(innerFunction.calcValue(input));
				if (functionIdentity.equals("sin")) number *= Math.sin(innerValue);
				else if (functionIdentity.equals("cos")) number *= Math.cos(innerValue);
				else if (functionIdentity.equals("tan")) number *= Math.tan(innerValue);
				else if (functionIdentity.equals("sec")) number *= 1 / Math.cos(innerValue);
				else if (functionIdentity.equals("csc")) number *= 1 / Math.sin(innerValue);
				else if (functionIdentity.equals("cot")) number *= 1 / Math.tan(innerValue);
				else if (functionIdentity.equals("log")) number *= Math.log(innerValue);
				else if (functionIdentity.equals("sqrt"))number *= Math.sqrt(innerValue);
				else if (functionIdentity.equals("abs")) number *= Math.abs(innerValue);
				else if (functionIdentity.equals("arcsin"))number *= Math.acos(innerValue);
				else if (functionIdentity.equals("arccos"))number *= Math.acos(innerValue);
				else if (functionIdentity.equals("arctan"))number *= Math.atan(innerValue);
				else if (functionIdentity.equals("ln"))number *= Math.log(innerValue);
				else if (functionIdentity.equals("log"))number *= Math.log10(innerValue);
			}
		} catch (NumberFormatException n) {
			if (carrot)
				str += "^(" + power + ")";
			else if (function) {
				str += functionIdentity + "(" + innerFunction + ")";
			}
		}

		return number + str;
	}

	public ArrayListMod<FunctionPart> multiply(FunctionPart f) {
		ArrayListMod<FunctionPart> product = new ArrayListMod<FunctionPart>();

		if (f.getClass().equals(Expression.class)) {
			Expression e = ((Expression)f);


		}

		return product;
	}

	@Override
	public boolean isAlgebraic() {
		return innerFunction.isAlgebraic() || power.isAlgebraic() || variable;
	}

	public ArrayListMod<FunctionPart> calcDerivative(){
		return CalculusEngine.calcDerivative(this);
	}

	@Override
	public String toString() {
		String temp = "";

		if (value > 0 && !factor)
			temp += "+";
		if (value != 1 && value != -1)
			if (value == (int)value)
				temp += (int)value;
			else
				temp += String.format("%-2.2f", value);
		if (value == -1)
			temp += "-";
		if (variable)
			for (Variable v: variables)
				temp += v;
		if (e)
			temp += "e";
		if (function)
			temp += functionIdentity + "(";



		if (innerFunction != null)
			if (innerFunction.toString().length() > 0)
				if (function)
					if (innerFunction.toString().substring(0,1).equals("+"))
						temp += innerFunction.toString().substring(1) + ")";
					else
						temp += innerFunction + ")";

		if (carrot && !power.calcValue("").equals("1"))
			if (power.toString().substring(0,1).equals("+"))
				temp += "^" + power.toString().substring(1);
			else
				temp += "^" + power.toString();

		if (temp.equals("-"))
			temp = "-1";
		if (temp.equals("+"))
			temp = "";
		if (temp.equals(""))
			temp += "1";
		if (factor)
			temp += "*";
		if (numerator)
			temp += "/";

		return temp;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getFunctionIdentity() {
		return functionIdentity;
	}

	public void setFunctionIdentity(String functionIdentity) {
		this.functionIdentity = functionIdentity;
	}

	public boolean isVariable() {
		return variable;
	}

	public void setVariable(boolean variable) {
		this.variable = variable;
	}

	public boolean isE() {
		return e;
	}

	public void setE(boolean e) {
		this.e = e;
	}

	public boolean isCarrot() {
		return carrot;
	}

	public void setCarrot(boolean carrot) {
		this.carrot = carrot;
	}

	public boolean isFunction() {
		return function;
	}

	public void setFunction(boolean function) {
		this.function = function;
	}

	public Function getPower() {
		return power;
	}

	public void setPower(Function power) {
		this.power = power;
	}

	public Function getInnerFunction() {
		return innerFunction;
	}

	public void setInnerFunction(Function innerFunction) {
		this.innerFunction = innerFunction;
	}
	
	public void addVariable(Variable v) {
		variables.add(v);
	}

	public ArrayListMod<Variable> getVariables() {
		return variables;
	}

	public void setVariables(ArrayListMod<Variable> variables) {
		this.variables = variables;
	}
}
