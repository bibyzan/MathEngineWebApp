package com.ben.javacalculator;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Class containing as many Points needed
 * in order to draw a graph as indicated by
 * the window size passed in by the Graph object.
 * @author Ben Rasmussen
 */
public class Plot {
	private ArrayListMod<Point> curve;
	private Equation function;
	private HashMap<String, VariableBound> windowSettings;
	private boolean enabled, threeDimensional;

	public Plot(Equation function, HashMap<String, VariableBound> windowSettings) {
		this.function = function;
		this.windowSettings = windowSettings;
		this.curve = new ArrayListMod<>();
		this.enabled = true;
		this.threeDimensional = function.getFunctionOf().getDimensions().size() == 2;
		calcCurve();
	}

	public Plot(Plot p) {
		function = p.getFunction();
		curve = p.getCurve();
		enabled = p.isEnabled();
	}

	public void calcCurve() {
		curve.clear();
		if (threeDimensional) {
			/*for (double x = graph.getLeftBound(); x <= graph.getRightBound(); x += .0001) {
				for (double y = getGraph().getBottomBound(); y <= graph.getTopBound(); y += .0001) {
					curve.add(new Point(x, y, Double.parseDouble(
							function.getRightSide().calcValue("x=" + Double.toString(x) + ",y=" + Double.toString(y)))));
				}
			}*/
		} else {
			for (double i = windowSettings.get(function.getInTermsOf()).getLowerBound();
				 i <= windowSettings.get(function.getInTermsOf()).getUpperBound(); i += .0001) {
				Point newPoint;
				if (function.getInTermsOf().equals("y"))
					newPoint = new Point(i, Double.parseDouble(function.getFunctionOf().calcValue("x=" + Double.toString(i))));
				else
					newPoint = new Point(Double.parseDouble(function.getFunctionOf().calcValue("y=" + Double.toString(i))),i);
				curve.add(newPoint);
			}
		}
	}

	public ArrayListMod<Point> getTablePoints() {
		ArrayListMod<Point> tablePoints = new ArrayListMod<Point>();

		/*for (double x = graph.getLeftBound(); x <= graph.getRightBound(); x += graph.getxScale()) {
			Point newPoint = new Point(x, Double.parseDouble(function.getRightSide().calcValue("x=" + Double.toString(x))));
			tablePoints.add(newPoint);
		}*/

		return tablePoints;
	}

	public void drawSwingGraphics(Graphics g) {
		for (Point p: curve)
			p.swingGraphic(g);
	}

	public ArrayListMod<Point> getCurve() {
		return curve;
	}

	public void setCurve(ArrayListMod<Point> curve) {
		this.curve = curve;
	}

	public Equation getFunction() {
		return function;
	}

	public void setFunction(Equation function) {
		this.function = function;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
