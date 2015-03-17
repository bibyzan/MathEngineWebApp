package com.ben.javacalculator;

import java.awt.*;

/**
 * Class containing 2 pieces of data.
 * One containing the x, and the other containing
 * the corresponding y coordinate on a cartesian plane.
 * @author Ben Rasmussen
 */
public class Point {
	private double x,y,z;
	private boolean threeDimensional;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		this.threeDimensional = false;
	}

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.threeDimensional = true;
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

	public void swingGraphic(Graphics g) {
		g.drawOval((int)x,(int)y,1,1);
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public boolean isThreeDimensional() {
		return threeDimensional;
	}

	public void setThreeDimensional(boolean threeDimensional) {
		this.threeDimensional = threeDimensional;
	}

	@Override
	public String toString() {
		if (threeDimensional)
			return "(" + x + ", " + y + ", " + z + ")";
		else
			return "(" + x + ", " + y +')';
	}
}
