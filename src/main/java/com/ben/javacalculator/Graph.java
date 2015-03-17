package com.ben.javacalculator;

import javax.swing.*;
import java.awt.*;

/**
 * Class that includes a converter from
 * the cartesian plane, to the screen coordinate system.
 * @author Ben Rasmussen
 */
public class Graph {
	private ArrayListMod<Plot> plots;
	private ScreenConverter currentScreen;
	private double leftBound, rightBound, topBound, bottomBound, yScale, xScale;
	
	public Graph() {
		leftBound = -10;
		rightBound = 10;
		topBound = 10;
		bottomBound = -10;
		yScale = 1;
		xScale = 1;
		plots = new ArrayListMod<Plot>();
	}

	public Graph(int width, int height) {
		this();
		currentScreen = new ScreenConverter(width,height);
	}

	public void setSettings(double[] settings) {
		leftBound = settings[0];
		rightBound = settings[1];
		xScale = settings[2];
		bottomBound = settings[3];
		topBound = settings[4];
		yScale = settings[5];
		for (Plot p: plots)
			p.setGraph(this);
	}
	
	public void addPlot(Function f) {
		plots.add(new Plot(f, this));
	}

	public void drawSwingGraphics(Graphics g) {
		for (Plot p: plots)
			if (p.isEnabled())
				currentScreen.convertPlot(p).drawSwingGraphics(g);

		currentScreen.convertLineSegment(
			new LineSegment(new Point(leftBound, 0), new Point(rightBound,0))).drawSwingGraphic(g);
		currentScreen.convertLineSegment(
			new LineSegment(new Point(0, topBound), new Point(0, bottomBound))).drawSwingGraphic(g);

		for (double x = leftBound; x <= rightBound; x+= xScale)
			currentScreen.convertLineSegment(
				new LineSegment(new Point(x, .25), new Point(x, -.25))).drawSwingGraphic(g);

		for (double y = bottomBound; y <= topBound; y+= yScale)
			currentScreen.convertLineSegment(
				new LineSegment(new Point(.25,y), new Point(-.25, y))).drawSwingGraphic(g);
	}

	public double xAxisRange() {
		return rightBound - leftBound;
	}

	public double yAxisRange() {
		return topBound - bottomBound;
	}

	public ArrayListMod<Plot> getPlots() {
		return plots;
	}

	public void setPlots(ArrayListMod<Plot> plots) {
		this.plots = plots;
	}

	public ScreenConverter getCurrentScreen() {
		return currentScreen;
	}

	public void setCurrentScreen(int width, int height) {
		this.currentScreen = new ScreenConverter(width,height);
	}

	public double getLeftBound() {
		return leftBound;
	}

	public void setLeftBound(double leftBound) {
		this.leftBound = leftBound;
	}

	public double getRightBound() {
		return rightBound;
	}

	public void setRightBound(double rightBound) {
		this.rightBound = rightBound;
	}

	public double getTopBound() {
		return topBound;
	}

	public void setTopBound(double topBound) {
		this.topBound = topBound;
	}

	public double getBottomBound() {
		return bottomBound;
	}

	public void setBottomBound(double bottomBound) {
		this.bottomBound = bottomBound;
	}

	public double getyScale() {
		return yScale;
	}

	public void setyScale(double yScale) {
		this.yScale = yScale;
	}

	public double getxScale() {
		return xScale;
	}

	public void setxScale(double xScale) {
		this.xScale = xScale;
	}

	private class ScreenConverter {
		private int width, height;

		public ScreenConverter(int width, int height) {
			this.width = width;
			this.height = height;
		}

		public ScreenConverter(JFrame frame) {
			width = frame.getWidth();
			height = frame.getHeight();
		}

		public Point convertPoint(Point p) {
			double newX = (p.getX() * (width / xAxisRange())) + (width / 2);
			double newY = (p.getY() * (height / yAxisRange()) - (height / 2)) * -1;

			return new Point(newX, newY);
		}

		public LineSegment convertLineSegment(LineSegment l) {
			return new LineSegment(convertPoint(l.getBp()),convertPoint(l.getEp()));
		}

		public Plot convertPlot(Plot p) {
			ArrayListMod<Point> oldPoints = new ArrayListMod<Point>(p.getCurve());
			ArrayListMod<Point> newPoints = new ArrayListMod<Point>();

			for (Point l: oldPoints)
				newPoints.add(convertPoint(l));

			Plot newPlot = new Plot(p);
			newPlot.setCurve(newPoints);

			return newPlot;
		}
	}
}
