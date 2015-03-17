package com.ben.javacalculator;

import java.awt.*;

/**
 * Class containing as many Points needed
 * in order to draw a graph as indicated by
 * the window size passed in by the Graph object.
 * @author Ben Rasmussen
 */
public class Plot {
	private ArrayListMod<Point> curve;
	private Function function;
	private Graph graph;
	private boolean enabled, threeDimensional;

	public Plot(Function function, Graph graph) {
		this.function = function;
		this.graph = graph;
		this.curve = new ArrayListMod<>();
		this.enabled = true;
		this.threeDimensional = function.getDimensions().size() == 2;
		calcCurve();
	}

	public Plot(Plot p) {
		function = p.getFunction();
		graph = p.getGraph();
		curve = p.getCurve();
		enabled = p.isEnabled();
	}

	public void calcCurve() {
		curve.clear();
		if (threeDimensional) {
			for (double x = graph.getLeftBound(); x <= graph.getRightBound(); x += .0001) {
				for (double y = getGraph().getBottomBound(); y <= graph.getTopBound(); y += .0001) {
					curve.add(new Point(x, y, Double.parseDouble(
							function.calcValue("x=" + Double.toString(x) + ",y=" + Double.toString(y)))));
				}
			}
		} else {
			for (double x = graph.getLeftBound(); x <= graph.getRightBound(); x += .0001) {
				Point newPoint = new Point(x, Double.parseDouble(function.calcValue("x=" + Double.toString(x))));
				curve.add(newPoint);
			}
		}
	}

	public ArrayListMod<Point> getTablePoints() {
		ArrayListMod<Point> tablePoints = new ArrayListMod<Point>();

		for (double x = graph.getLeftBound(); x <= graph.getRightBound(); x += graph.getxScale()) {
			Point newPoint = new Point(x, Double.parseDouble(function.calcValue("x=" + Double.toString(x))));
			tablePoints.add(newPoint);
		}

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

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public Graph getGraph() {
		return graph;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
		calcCurve();
	}
}
