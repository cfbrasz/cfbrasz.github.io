//
// Class for coordinates in Arkanoid
// (c) Fred Brasz 2008

import java.lang.Math;

public class Coord {

    private double x;
    private double y;

    public Coord(double x, double y)
    {
	this.x = x;
	this.y = y;
    }

    public double getX()
    {
	return x;
    }

    public double getY()
    {
	return y;
    }

    public double dist(Coord that)
    {
	double diffX = that.getX() - this.getX();
	double diffY = that.getY() - this.getY();
	return Math.sqrt(diffX*diffX + diffY*diffY);
    }

    public double angle()
    {
	//Post: returns the angle from this to the origin (0,0).
	// (Useful for the paddle ball collision to give us an angle to compare
	// to the perpendicular angle at the surface of the paddle.)
	Coord n = new Coord (0,0);
	//return this.angle(n);
	return n.angle(this);
    }


    public double angle(Coord that)
    {
	//Post: returns the angle in radians of the line going from this to that
	// where 0 corresponds to that being directly to the right of this,
	// Pi/2 corresponds to that directly above this, etc. 
	// (note: negative angle means that is somewhat below this, and then the
	// angle is swept out clockwise instead of the usual counterclockwise)
	double diffX = that.getX() - this.getX();
	double diffY = that.getY() - this.getY();
	return Math.atan2(diffY, diffX);
    }
}