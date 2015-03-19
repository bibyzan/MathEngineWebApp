package com.ben.javacalculator;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Class that includes a converter from
 * the cartesian plane, to the screen coordinate system.
 * @author Ben Rasmussen
 */
public class Graph extends ArrayListMod<Plot> {
	private ScreenConverter currentScreen;
	private HashMap<String, VariableBound> windowSettings;

	public Graph(int width, int height) {
		currentScreen = new ScreenConverter(width,height);
		windowSettings = new HashMap<>();
		windowSettings.put("x", new VariableBound("x"));
		windowSettings.put("y", new VariableBound("y"));
	}

	
	public void addPlot(Equation e) {
		super.add(new Plot(e, windowSettings));
	}

	public void drawSwingGraphics(Graphics g) {
		for (Object p: super.toArray())
			currentScreen.convertPlot((Plot)p).drawSwingGraphics(g);

		currentScreen.convertLineSegment(new LineSegment(
				new Point(windowSettings.get("x").getLowerBound(),0),
				new Point(windowSettings.get("x").getUpperBound(),0)
		)).drawSwingGraphic(g);

		currentScreen.convertLineSegment(new LineSegment(
				new Point(0, windowSettings.get("y").getLowerBound()),
				new Point(0, windowSettings.get("y").getUpperBound())
		)).drawSwingGraphic(g);
	}

	public HashMap<String, VariableBound> getWindowSettings() {
		return windowSettings;
	}

	public void setWindowSettings(HashMap<String, VariableBound> windowSettings) {
		this.windowSettings = windowSettings;
	}

	public void setCurrentScreen(int width, int height) {
		this.currentScreen = new ScreenConverter(width,height);
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
			double newX = (p.getX() * (width / windowSettings.get("x").getAxisRange())) + (width / 2);
			double newY = (p.getY() * (height / windowSettings.get("y").getAxisRange()) - (height / 2)) * -1;

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
