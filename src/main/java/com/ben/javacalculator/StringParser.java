package com.ben.javacalculator;

/**
 * Class created for the sole purpose of
 * parsing Strings into Function, Expression, and Equation Objects.
 * @author Ben Rasmussen
 */
public class StringParser {
	private static String[] functions = {"sin","cos","tan","csc","sec","cot","sqrt","abs","log",
			"arcsin", "arccos", "arctan", "ln"};

	public static Equation getEquationFromString(String text) {
		Equation result = new Equation();
		String[] noSpaces = text.split(" ");
		text = "";
		for (String s: noSpaces) text += s;

		String[] inputs = text.split(";");
		if (inputs.length > 1)
			result.setInputField(inputs[1]);

		String[] sides = inputs[0].split("=");
		result.setLeftSide(new Function(sides[0]));
		if (sides.length > 1)
			result.setRightSide(new Function(sides[1]));

		Function all = new Function(result.getLeftSide());
		all.add(result.getRightSide());
		all.stream().filter(FunctionPart::isAlgebraic).forEach(f -> result.setAlgebraic(true));

		return result;
	}
	
	public static Expression getExpressionFromString(String text) {
		Expression result = new Expression();
		
		if (text.length() == 1) {
			if (isNumber(text)) {
				result.setValue(Double.parseDouble(text));
			} else if (text.equals("e")) {
				result.setE(true);
			} else {
				result.setVariable(true);
				result.addVariable(new Variable(text));
			}
		} else {
			for (int i = 0; i < text.length(); i++) {
				boolean checker = false;

				//check special expression
				int marker = i;
				if (text.substring(i, i + 1).equals("e")) {
					result.setE(true);
					if (i != 0)
						checker = true;
					i++;
				} else if (i < text.length() - 3) {
					for (String f: functions) {
						try {
							if (text.substring(i, i + f.length()).equals(f)) {
								i += f.length() - 1;
								result.setFunction(true);
								result.setFunctionIdentity(f);
							}
						} catch (StringIndexOutOfBoundsException e) { /*next iteration*/ }
					}
				}
				if (i < text.length()){
					String[] notAllowed = {"+","-","/","*","e","s","i","n","c","o",
							"s","t","a","r","l","q","b","g","^", "(", ")"};
					boolean check = true;
					for (String n: notAllowed)
						if (text.substring(i,i+1).equals(n))
							check = false;

					if (check && !isNumber(text.substring(i,i+1))) {
						result.setVariable(true);
						result.getVariables().add(new Variable(text.substring(i,i+1)));
						if (i != 0 && result.getValue() == 1)
							checker = true;
					}
				}

				//fill value
				if (checker) {
					if (marker > 1) {
						if (text.substring(0, 1).equals("+"))
							result.setValue(Double.parseDouble(text.substring(1, marker)));
						else
							result.setValue(Double.parseDouble(text.substring(0, marker)));
					} else if (text.substring(0, 1).equals("-")) {
						result.setValue(-1);
					} else {
						if (!text.substring(0, 1).equals("+") && !text.substring(0,1).equals("/")
								&& !text.substring(0,1).equals("*"))
							result.setValue(Double.parseDouble(text.substring(0,1)));
					}
				}

				//check for carrot or inner expression
				if (i < text.length() - 1) {
					if (text.substring(i, i + 1).equals("(")) {
						i++;
						int startingIndex = i;
						i = skipParentheses(text, i);
						result.setInnerFunction(new Function(text.substring(startingIndex, i)));
					} else if (text.substring(i, i + 1).equals("^")) {
						if (result.isRegularFunction())
							result.setValue(Double.parseDouble(text.substring(0,i)));
						i++;
						result.setCarrot(true);
						if (text.substring(i, i + 1).equals("(")) {
							i++;
							int startingIndex = i;
							i = skipParentheses(text, i);
							if (result.isVariable())
								result.getVariables().get(result.getVariables().size()-1).setPower
										(new Function(text.substring(startingIndex, i)));
							else
								result.setPower(new Function(text.substring(startingIndex, i)));
						} else {
							result.setPower(new Function(text.substring(i, i + 1)));
						}
					}
				}
			}
			if (result.isRegularFunction())
				result.setValue(Double.parseDouble(text));
		}
		
		return result;
	}

	public static Function getGroupFromString(String text) {
		Function result = new Function();
		String[] splitters = {"+","-","/","*"};
		int startingIndex = 0;
		boolean nextInverse = false;

		for (int i = 0; i < text.length(); i++) {
			//regular splitters
			for (String s: splitters) {
				try {
					if (text.substring(i, i + 1).equals(s)) {
						startingIndex = expressionAdder(s, text, startingIndex, i, result);
						if (text.substring(i + 1, i + 2).equals("(")) {
							startingIndex++;
							if (s.equals("-")) nextInverse = true;
						} else if (!s.equals("-"))
							startingIndex++;
					}
				} catch (StringIndexOutOfBoundsException e) { /*next iteration*/ }
			}
			//functions
			for (String f: functions) {
				try {
					if (text.substring(i, i + f.length()).equals(f)) {
						startingIndex = expressionAdder("*", text, startingIndex, i, result);
						i += f.length() + 1;
						i = skipParentheses(text, i);
					}
				} catch (StringIndexOutOfBoundsException e) { /*next iteration*/ }
			}

			//inner parentheses
			if (i < text.length() - 1) {
				if (text.substring(i,  i+ 1).equals("(")) {
					startingIndex = expressionAdder("*", text, startingIndex, i, result);
					startingIndex++;
					i++;
					i = skipParentheses(text, i);
					Function temp = new Function(text.substring(startingIndex, i));
					temp.setInverse(nextInverse);
					nextInverse = false;
					try {
						String next = text.substring(i + 1, i + 2);
						temp.setFactor(next.equals("*") || next.equals("("));
						temp.setNumerator(next.equals("/"));
						for (String s: splitters) {
							if (next.equals(s)) {
								i++;
								startingIndex = i + 1;
								if(s.equals("-"))
									nextInverse = true;
							}
						}
						if (next.equals("(")) startingIndex = i + 1;
						if (next.equals("^")) {
							startingIndex = i + 2;
							result.setCarrot(true);
							if (text.substring(i+2,i+3).equals("(")) {
								i+=3;
								startingIndex = i;
								i = skipParentheses(text, i);
								result.setPower(new Function(text.substring(startingIndex, i)));
							} else {
								i += 3;
								result.setPower(new Function(text.substring(startingIndex, i)));
							}
						}
					} catch (StringIndexOutOfBoundsException e) {
						startingIndex = text.length();
					}
					result.add(temp);
				}
			}
		}

		if (startingIndex != text.length()) {
			result.add(new Expression(text.substring(startingIndex, text.length())));
		}

		return result;
	}

	private static int skipParentheses(String text, int i) {
		if (!text.substring(i, i + 1).equals(")")) {
			int lag = 0;
			for (char c : text.substring(i, text.length()).toCharArray()) {
				i++;
				if (c == ')' && lag == 0) break;
				else if (c == '(') lag++;
				else if (c == ')') lag--;
			}
			i--;
		} //else i++;

		return i;
	}

	private static boolean isNumber(String s) {
		boolean temp = false;
		String[] numbers = {"0","1","2","3","4","5","6","7","8","9","."};

		for (String n: numbers)
			if (n.equals(s))
				temp = true;

		return temp;
	}

	private static int expressionAdder(String sign, String text, int startingIndex, int i, Function g) {
		if (i - startingIndex > 0) {
			Expression temp = new Expression(text.substring(startingIndex, i));
			temp.setFactor(sign.equals("*"));
			temp.setNumerator(sign.equals("/"));
			g.add(temp);
			startingIndex = i;
		}

		return startingIndex;
	}
}
