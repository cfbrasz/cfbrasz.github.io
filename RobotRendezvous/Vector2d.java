//
// Class for 2D vectors
// (c) Fred Brasz 2008

import java.lang.Math;

public class Vector2d extends Tuple2d {

    //double x;
    //double y;
	
	public Vector2d()
    {
	super();
    }
	
	public Vector2d(double x, double y)
    {
	super(x,y);
    }
	
	public Vector2d(Vector2d v)
    {
	super(v);
    }	
	
	public double lengthSquared()
	{
	return x*x + y*y;
	}
	
	public double length()
	{
	return Math.sqrt(lengthSquared());
	}
	
	public void normalize()
	{
	double l = length();
	if(l > 0)
		{
		x = x/l;
		y = y/l;
		}
	//System.out.println(length());
	}
	
	public double dot(Vector2d v1)
	{
	return x*v1.x + y*v1.y;
	}
	
	public double angle()
	{
	// CCW from horizontal
	return Math.atan2(y,x);
	}

	public void rotate(double theta)
	{
	// rotates coord counterclockwise by angle theta
	double newX = +Math.cos(theta)*x + Math.sin(theta)*y;
	double newY = -Math.sin(theta)*x + Math.cos(theta)*y;
	x = newX;
	y = newY;
	}
}