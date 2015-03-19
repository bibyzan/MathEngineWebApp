package com.ben.javacalculator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class made to parse an equation
 * made up of Expressions with infinite recursiveness
 * @author Ben Rasmussen
 */
public class Function extends ArrayListMod<FunctionPart> implements FunctionPart{
	private boolean inverse, carrot, numerator, factor;
	private Function power;

	public Function() {
		super();
		numerator = false;
		factor = false;
		carrot = false;
		inverse = false;
	}

	public Function(Expression expression) {
		this();
		super.add(expression);
	}

	public Function(ArrayListMod<FunctionPart> f) {
		super();
		super.addAll(f);
	}

	public Function(Function g) {
		this();
		super.addAll(g);
		inverse = g.isInverse();
		numerator = g.isNumerator();
		factor = g.isFactor();
		carrot = g.isCarrot();
		power = g.getPower();
	}

	//input listener
	public Function(String text) {
		this(StringParser.getGroupFromString(text));
	}

	public ArrayListMod<String> getDimensions() {
		ArrayListMod<String> vars = new ArrayListMod<>();

		for (FunctionPart f: this) {
			vars.addAll(f.getDimensions());
		}

		List<String> listWithoutDuplicates =
				vars.parallelStream().distinct().collect(Collectors.toList());

		vars = new ArrayListMod<>(listWithoutDuplicates);

		return vars;
	}

	public boolean isAlgebraic() {
		boolean algebraic = false;

		for (FunctionPart f: this)
			algebraic = f.isAlgebraic();

		return algebraic;
	}

	public ArrayListMod<FunctionPart> calcDerivative() {
		return CalculusEngine.calcDerivative(this);
	}

	public String calcValue(String input) {
		double value = 0;
		boolean error = false, nextCheck = false;
		String str = "";

		for (int i = 0;i < super.size(); i++) {
			try {
				double first = Double.parseDouble(super.get(i).calcValue(input));
				double second = 0;
				if (i < super.size() - 1)
					second = Double.parseDouble(super.get(i + 1).calcValue(input));
				if (super.get(i).isFactor()) {
					value += (first * second);
					nextCheck = true;
				} else if (super.get(i).isNumerator()) {
					value += (first / second);
					nextCheck = true;
				} else {
					if (!nextCheck)
						value += first;
					nextCheck = false;
				}
			} catch (NumberFormatException n) {
				str += value + super.get(i).toString();
				error = true;
				value = 0;
			}
		}

		try {
			if (carrot)
				value = Math.pow(value, Double.parseDouble(power.calcValue(input)));
		} catch (NumberFormatException n) {
			str += value + "^" + power.toString();
			error = true;
			value = 0;
		}

		if (error) {
			if (value != 0)
				if (value > 0)
					str += "+" + value;
				else
					str += value;
			return str;
		} else {
			if (inverse) {
				value *= -1;
				return "" + value;
			} else {
				return "" + value;
			}
		}
	}

	public boolean isInverse() {
		return inverse;
	}

	public void setInverse(boolean inverse) {
		this.inverse = inverse;
	}

	public boolean isCarrot() {
		return carrot;
	}

	public void setCarrot(boolean carrot) {
		this.carrot = carrot;
	}

	public Function getPower() {
		return power;
	}

	public void setPower(Function power) {
		this.power = power;
	}

	@Override
	public boolean isNumerator() {
		return numerator;
	}

	@Override
	public void setNumerator(boolean numerator) {
		this.numerator = numerator;
	}

	@Override
	public boolean isFactor() {
		return factor;
	}

	@Override
	public void setFactor(boolean factor) {
		this.factor = factor;
	}

	public Function simplify() {
		Function simplification = new Function();

		for (FunctionPart ip: this) {
			if (ip.getClass().equals(Expression.class)) {
				Expression e = ((Expression)ip);
				if (!(e.isRegularFunction() && e.getValue() == 1))
					simplification.add(e);
			} else {
				Function g = ((Function)ip);
				if (g.size() == 1) {
					FunctionPart f = g.get(0);
					if (f.getClass().equals(Expression.class)) {
						if (!(((Expression) f).isRegularFunction() && ((Expression) f).getValue() == 1)) {
							f.setFactor(g.isFactor());
							f.setNumerator(g.isNumerator());
							simplification.add(f);
						} else if (g.isNumerator()) {
							f.setNumerator(true);
							simplification.add(f);
						}
					} else
						simplification.add(f);
				} else
					simplification.addAll(g);
			}
		}

		//if (super.size() == 1 && super.get(0).getClass().equals(Function.class))
			//this(new ArrayListMod<FunctionPart>((super.get(0))));

		return simplification;
	}

	@Override
	public String toString() {
		String str = "";

		for (FunctionPart o: this) {
			String temp = o.toString();

			if (str.length() > 0) {
				char lastChar = str.toCharArray()[str.toCharArray().length - 1];
				if (lastChar == '*' || lastChar == '/')
					if (temp.substring(0, 1).equals("+"))
						temp = temp.substring(1);
				if (lastChar == '*' && o.getClass().equals(Expression.class))
					if (((Expression)o).isShortMultiplication())
						str = str.substring(0,str.length()-1);

			}

			if (o.getClass().equals(Function.class)) {
				temp = "(" + temp + ")";
				if (((Function)o).isInverse())
					temp = "-" + temp;
				else if (str.length() > 0)
					if (!str.substring(str.length()-1,str.length()).equals("/") &&
							!str.substring(str.length()-1,str.length()).equals("*") &&
							!str.substring(str.length()-1,str.length()).equals(")"))
						temp = "+" + temp;
				if (o.isNumerator()) temp += "/";

				if (str.length() > 0)
					if (str.substring(str.length()-1,str.length()).equals("*"))
						str = str.substring(0,str.length()-1);
			}

			str += temp;
		}

		if (carrot)
			if (power.toString().substring(0,1).equals("+"))
				str += "^" + power.toString().substring(1);
			else
				str += "^" + power.toString();

		if (str.length() > 1)
			if (str.substring(0,1).equals("+"))
				str = str.substring(1);

		if (str.equals(""))
			str += "1";

		return str;
	}
}
