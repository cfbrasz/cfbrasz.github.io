//
// Class for 2D vectors
// (c) Fred Brasz 2008

import java.lang.Math;

public abstract class Tuple2d {

    double x;
    double y;
	
	public Tuple2d()
    {
	this.x = 0;
	this.y = 0;
    }
	
    public Tuple2d(double x, double y)
    {
	this.x = x;
	this.y = y;
    }
	
    public Tuple2d(Tuple2d v)
    {
	this.x = v.x;
	this.y = v.y;
    }
	
	public void set(double x, double y)
	{
	this.x = x;
	this.y = y;
	}
		
	public void set(Tuple2d v)
	{
	this.x = v.x;
	this.y = v.y;
	}
	
	public void add(Tuple2d v)
	{
	x += v.x;
	y += v.y;
	}
	
	public void add(Tuple2d v,Tuple2d v2)
	{
	x = v.x + v2.x;
	y = v.y + v2.y;
	}
	
	public void scale(double f)
	{
	x = f*x;
	y = f*y;
	}
	
	public void negate()
	{
	x = -x;
	y = -y;
	}
	
	public void scaleAdd(double f, Tuple2d t1)
	{
	x = f*x + t1.x;
	y = f*y + t1.y;
	}
	
	public void scaleAdd(double f, Tuple2d t1, Tuple2d t2)
	{
	x = f*t1.x + t2.x;
	y = f*t1.y + t2.y;
	}
	
	public void sub(Tuple2d p1, Tuple2d p2)
	{
	x = p1.x - p2.x;
	y = p1.y - p2.y;
	}
	
	public static void main(String args[])
	{
	Point2d x = new Point2d(1,2);
	Point2d x2 = new Point2d(4,6);
	Vector2d v = new Vector2d(3,4);
	Vector2d v2 = new Vector2d(-2,-1);
	System.out.println("x and x2 dist:");
	System.out.println(x.distance(x2));
	System.out.println(x.distanceSquared(x2));
	System.out.println(x.x +" "+ x.y);
	x.scaleAdd(1.0,v,x);
	System.out.println(x.x +" "+ x.y);
	System.out.println("v and v length");
	System.out.println(v.x +" "+ v.y);
	System.out.println(v.length());
	System.out.println(v.lengthSquared());
	System.out.println("v2 and v2 length");
	System.out.println(v2.x +" "+ v2.y);
	System.out.println(v2.length());
	System.out.println(v2.lengthSquared());
	System.out.println("v dot v2");
	System.out.println(v.dot(v2));
	}
}