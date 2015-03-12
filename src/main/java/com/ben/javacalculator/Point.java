package com.ben.javacalculator;

/**
 * Class containing 2 pieces of data.
 * One containing the x, and the other containing
 * the corresponding y coordinate on a cartesian plane.
 * @author Ben Rasmussen
 */
public class Point {
	public double x,y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point() {
		x = 0;
		y = 0;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	@Override
	public String toString() {
		return "(" +
				 x +
				", " + y +
				')';
	}
}
