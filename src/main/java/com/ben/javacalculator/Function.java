package com.ben.javacalculator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class made to parse an equation
 * made up of Expressions with infinite recursiveness
 * @author Ben Rasmussen
 */
public class Function extends FunctionPart{
	private ArrayListMod<FunctionPart> innerParts;
	private boolean inverse, carrot;
	private Function power;

	public Function() {
		super();
		carrot = false;
		inverse = false;
		innerParts = new ArrayListMod<FunctionPart>();
	}

	public Function(Expression expression) {
		this();
		innerParts.add(expression);
	}

	public Function(ArrayListMod<FunctionPart> f) {
		super();
		innerParts = f;
	}

	public Function(Function g) {
		innerParts = g.getInnerParts();
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

	public Function performTask() {
		if (task.equals("d/dx")) {
			return new Function(calcDerivative()).simplify();
		} else {
			return new Function("" + new Function(this).calcValue());
		}
	}
	public ArrayListMod<String> getDimensions() {
		ArrayListMod<String> vars = new ArrayListMod<>();

		for (FunctionPart f: innerParts) {
			vars.addAll(f.getDimensions());
		}

		List<String> listWithoutDuplicates =
				vars.parallelStream().distinct().collect(Collectors.toList());

		vars = new ArrayListMod<>(listWithoutDuplicates);

		return vars;
	}

	public boolean isAlgebraic() {
		boolean algebraic = false;

		for (FunctionPart f: innerParts)
			algebraic = f.isAlgebraic();

		return algebraic;
	}

	public ArrayListMod<FunctionPart> calcDerivative() {
		return CalculusEngine.calcDerivative(this);
	}

	public String calcValue() {
		return calcValue("x=" + Main.variables[(int)'x']);
	}

	public String calcValue(String input) {
		double value = 0;
		boolean error = false, nextCheck = false;
		String str = "";

		for (int i = 0;i < innerParts.size(); i++) {
			try {
				double first = Double.parseDouble(innerParts.get(i).calcValue(input));
				double second = 0;
				if (i < innerParts.size() - 1)
					second = Double.parseDouble(innerParts.get(i + 1).calcValue(input));
				if (innerParts.get(i).isFactor()) {
					value += (first * second);
					nextCheck = true;
				} else if (innerParts.get(i).isNumerator()) {
					value += (first / second);
					nextCheck = true;
				} else {
					if (!nextCheck)
						value += first;
					nextCheck = false;
				}
			} catch (NumberFormatException n) {
				str += value + innerParts.get(i).toString();
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

	public void add(FunctionPart object) {
		innerParts.add(object);
	}

	public void add(ArrayListMod<FunctionPart> objects) {
		for (FunctionPart f: objects)
			innerParts.add(f);
	}

	public ArrayListMod<FunctionPart> getInnerParts() {
		return innerParts;
	}

	public void setInnerParts(ArrayListMod<FunctionPart> innerParts) {
		this.innerParts = innerParts;
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

	public Function simplify() {
		Function simplification = new Function();

		for (FunctionPart ip: innerParts) {
			if (ip.getClass().equals(Expression.class)) {
				Expression e = ((Expression)ip);
				if (!(e.isRegularFunction() && e.getValue() == 1))
					simplification.add(e);
			} else {
				Function g = ((Function)ip);
				if (g.getInnerParts().size() == 1) {
					FunctionPart f = g.getInnerParts().get(0);
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
					simplification.add(g.simplify());
			}
		}

		if (innerParts.size() == 1 && innerParts.get(0).getClass().equals(Function.class))
			innerParts = new ArrayListMod<FunctionPart>(((Function)innerParts.get(0)).getInnerParts());

		return simplification;
	}

	@Override
	public String toString() {
		String str = "";

		if (hasTask())
			str += task + "(";

		for (FunctionPart o: innerParts) {
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

		if (hasTask())
			str += ")";

		return str;
	}

	public ArrayListMod<FunctionPart> toArrayListMod() {
		return new ArrayListMod<FunctionPart>(this);
	}
}
