//
// Class for 2D vectors of integers
// (c) Fred Brasz 2008

import java.lang.Math;

public class Int2d implements Comparable {

    int x;
    int y;
	
	double phi = 0;
	
	public Int2d()
    {
	this.x = 0;
	this.y = 0;
    }
	
	public Int2d(int x, int y)
    {
	this.x = x;
	this.y = y;
    }
	
	public Int2d(Int2d p)
    {
	this.x = p.x;
	this.y = p.y;
    }
	
	public void set(int x, int y)
	{
	this.x = x;
	this.y = y;
	}
	
	public int distanceSquared(Int2d p1)
	{
	return (p1.x-this.x)*(p1.x-this.x) + (p1.y-this.y)*(p1.y-this.y);
	}
	
	public double distance(Int2d p1)
	{
	return Math.sqrt(distanceSquared(p1));
	}
	
	public double distanceSquared(Point2d p1)
	{
	return (p1.x-this.x)*(p1.x-this.x) + (p1.y-this.y)*(p1.y-this.y);
	}
	
	public double distance(Point2d p1)
	{
	return Math.sqrt(distanceSquared(p1));
	}
	
	public void sub(Int2d p1, Int2d p2)
	{
	x = p1.x - p2.x;
	y = p1.y - p2.y;
	}
	
	public void scaleAdd(double f, Int2d t1, Int2d t2)
	{
	x = (int)Math.round(f*t1.x) + t2.x;
	y = (int)Math.round(f*t1.y) + t2.y;
	}
	
	public int compareTo(Object o)
	{
	Int2d other = (Int2d)o;
	int result;
	if(Math.abs(this.phi) < Math.abs(other.phi)) result = -1;
	else if(Math.abs(this.phi) > Math.abs(other.phi)) result = 1;
	else result = 0;
	return result;
	}
	
	public boolean equals(Object o)
	{
	Int2d other = (Int2d)o;
	return this.x == other.x && this.y == other.y;
	}
}