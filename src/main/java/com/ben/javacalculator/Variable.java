package com.ben.javacalculator;

/**
 * Class made to separate what
 * could possibly be multiple unknowns
 * variables in an Expression.
 * @author Ben Rasmussen
 */
public class Variable {
	private String letter;
	private Function power;
	private boolean carrot;

	public Variable() {
		letter = "";
		power = new Function();
		carrot = false;
	}

	public String calcValue(String input) {
		double tester = 1;
		boolean containsVariable = false;
		for (String i: input.split(",")) {
			String[] choice = i.split("=");
			if (choice[0].equals(letter)) {
				tester = Double.parseDouble(choice[1]);
				containsVariable = true;
			}
		}

		if (carrot) {
			try {
				if (containsVariable) {
					tester = Math.pow(tester, Double.parseDouble(power.calcValue(input)));
					return "" + tester;
				} else
					return this.toString();
			} catch (NumberFormatException n) {
				return tester + "^(" + power + ")";
			}
		} else {
			if (containsVariable)
				return "" + tester;
			else
				return letter;
		}
	}

	public char getLetterChar() {
		return letter.toCharArray()[0];
	}

	public boolean isCarrot() {
		return carrot;
	}

	public void setCarrot(boolean carrot) {
		this.carrot = carrot;
	}

	public Variable(String l) {
		this();

		letter = l;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public Function getPower() {
		return power;
	}

	public void setPower(Function power) {
		this.power = power;
	}

	@Override
	public String toString() {
		if (carrot)
			return letter + "^" + power;
		else
			return letter;
	}
}
