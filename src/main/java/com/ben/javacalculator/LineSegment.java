package com.ben.javacalculator;

/**
 * Class consisting of 2 points,
 * a beginning point, and an end point,
 * that are connected by a line on a cartesian plane
 * @author Ben Rasmussen
 */
public class LineSegment {
	//beginning point, end point
	Point bp, ep;

	public LineSegment(Point bp, Point ep) {
		this.bp = bp;
		this.ep = ep;
	}

	public LineSegment() {
		bp = new Point();
		ep = new Point();
	}

	public LineSegment (LineSegment l) {
		bp = l.getBp();
		ep = l.getEp();
	}

	public Point getBp() {
		return bp;
	}

	public void setBp(Point bp) {
		this.bp = bp;
	}

	public Point getEp() {
		return ep;
	}

	public void setEp(Point ep) {
		this.ep = ep;
	}

	public double findYint() {
		return bp.getY()-(findSlope()*bp.getX());
	}

	public double findDist(){
		//a^2 + b^2 = c^2
		return Math.pow(Math.pow(ep.getX()-bp.getX(),2)+Math.pow(ep.getY()-bp.getY(),2),.5);
	}

	public  double findSlope() {
		return ((double)ep.getY()-(double)bp.getY())/((double)ep.getX()-(double)bp.getX());
	}

	public static Point findIntersection(LineSegment a, LineSegment b) {
		//intersect variables
		double xi,yi;
		//equation variables
		xi = (b.findYint()-a.findYint())/(a.findSlope()-b.findSlope());
		yi = (a.findSlope()*xi)+a.findYint();

		return new Point((int)xi,(int)yi);
	}

	@Override
	public String toString() {
		return "LineSeg{" +
				  bp +
				" - " + ep +
				'}';
	}

}
