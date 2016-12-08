//
// Class for 2D vectors
// (c) Fred Brasz 2008

import java.lang.Math;

public class Point2d extends Tuple2d {

    //double x;
   // double y;	
	
	public Point2d()
    {
	super();
    }
	
	public Point2d(double x, double y)
    {
	super(x,y);
    }
	
	public Point2d(Point2d p)
    {
	super(p);
    }
	
	public double distanceSquared(Point2d p1)
	{
	return (p1.x-this.x)*(p1.x-this.x) + (p1.y-this.y)*(p1.y-this.y);
	}
	
	public double distance(Point2d p1)
	{
	return Math.sqrt(distanceSquared(p1));
	}
}