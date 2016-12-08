import java.lang.Math;
import java.awt.event.*;
import java.awt.*;

public class Coord {

    private double x;
    private double y;
	private double theta = 0;

    public Coord(double x, double y)
    {
	this.x = x;
	this.y = y;
    }

    public void setX(double x)
    {
	this.x = x;
    }

    public void setY(double y)
    {
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
	
	public Coord copy()
	{
	return new Coord(x,y);
	}
	
	public static int min(int[] values, int nValues)
	{
	// returns min of array of nValues integers
	int result = values[0];
	for(int i=1; i<nValues; i++)
		{
		if (values[i] < result) result = values[i];
		}
	return result;		
	}
	
	public static int max(int[] values, int nValues)
	{
	// returns min of array of nValues integers
	int result = values[0];
	for(int i=1; i<nValues; i++)
		{
		if (values[i] > result) result = values[i];
		}
	return result;		
	}
	
	public boolean parallel(Coord cOth, Coord seg1, Coord seg2)
	{
	// returns true it line passing through this and cOth is parallel to line passing through seg1 and seg2
	double m = mOf(cOth);
	double m2 = seg1.mOf(seg2);
	return m == m2;
	}	
	
	public Coord intersection(Coord cOth, Coord seg1, Coord seg2)
	{
	// returns intersection point of line passing through this and cOth with line passing through seg1 and seg2
	double m = mOf(cOth);
	double m2 = seg1.mOf(seg2);
	double xI = (m*x - m2*seg1.getX() + seg1.getY() - y)/(m-m2);
	double yI = yOf(cOth,xI);
	/*
	System.out.println(m);
	System.out.println(m2);
	System.out.println(xI);
	System.out.println(yI);
	System.out.println(yI - seg1.yOf(seg2,xI));
	*/
	if(seg1.getX() == seg2.getX())
		{
		//System.out.println("vertical");
		xI = seg1.getX();
		yI = m*(xI-x) + y;
		}
		
	if(x == cOth.getX())
		{
		//System.out.println("vertical");
		xI = x;
		yI = m2*(xI-seg1.getX()) + seg1.getY();
		}
			
	if(seg1.getY() == seg2.getY())
		{
		//System.out.println("horizontal");
		yI = seg1.getY();
		xI = (yI-y)/m + x;
		}
		
	if(y == cOth.getY())
		{
		//System.out.println("horizontal");
		yI = y;
		xI = (yI-seg1.getY())/m2 + seg1.getX();
		}
	
	return new Coord(xI,yI);
	}
	
	public boolean intersectSegment(Coord cOth, Coord seg1, Coord seg2)
	{
	// returns true if segment from current Coord to cOth intersects segment from seg1 to seg2 in point OTHER than one of the vertices
	Coord cI = intersection(cOth,seg1,seg2);
	if(cI.equals(this) ||
	   cI.equals(cOth) ||
	   cI.equals(seg1) ||
	   cI.equals(seg2)) return false;
	
	double lowX = Math.min(x, cOth.getX());
	double highX = Math.max(x, cOth.getX());
	double lowY = Math.min(y, cOth.getY());
	double highY = Math.max(y, cOth.getY());
	
	double lowX2 = Math.min(seg1.getX(), seg2.getX());
	double highX2 = Math.max(seg1.getX(), seg2.getX());
	double lowY2 = Math.min(seg1.getY(), seg2.getY());
	double highY2 = Math.max(seg1.getY(), seg2.getY());
	
	return (cI.contained(lowX,lowY,highX-lowX,highY-lowY) ) && 
		   (cI.contained(lowX2,lowY2,highX2-lowX2,highY2-lowY2) );
	}
	
	
	public boolean contained(double x0, double y0, double w, double h)
	{
	//returns true if this point is contained by the given rectangle
	boolean result = true;
	if (x < x0 || y < y0) result = false;
	if (x > x0 + w || y > y0 + h) result = false;
	return result;
	}
	
	public boolean contained(int x0, int y0, int w, int h)
	{
	//returns true if this point is contained by the given rectangle
	boolean result = true;
	if (x < x0 || y < y0) result = false;
	if (x > x0 + w || y > y0 + h) result = false;
	return result;
	}
	
	
	
	public double mOf(Coord cOth)
	{	
	Vector2D coordsVector = new Vector2D(this,cOth);
	double cSlope = coordsVector.getSlope();
	return cSlope;
	}
	
	
	public double yOf(Coord cOth, double xEval)
	{
	// evaluates y value of line between this and cOth at xEval
	Vector2D coordsVector = new Vector2D(this,cOth);
	double cSlope = coordsVector.getSlope();
	
	return cSlope*(xEval - x) + y;
	}
	
	public double xOf(Coord cOth, double yEval)
	{
	// evaluates x value of line between this and cOth at yEval
	Vector2D coordsVector = new Vector2D(this,cOth);
	double cSlope = coordsVector.getSlope();
	
	if (cSlope != 0) return (1/cSlope)*(yEval - y) + x;
	else 
	{
	return -999.; // slope 0, won't be hitting anything at -999 anyways
	}
	}	
	
	public void rotate(double theta)
	{
	// rotates coord counterclockwise by angle theta
	double newX = +Math.cos(theta)*x + Math.sin(theta)*y;
	double newY = -Math.sin(theta)*x + Math.cos(theta)*y;
	x = newX;
	y = newY;
	this.theta += theta;
	}
	
	public void moveX(double dx)
	{
	x += dx;
	}
	
	public void moveY(double dy)
	{
	y += dy;
	}
	
	public void move(double ds, double angle)
	{
	// angle on the screen counterclockwise from x=0 positive axis
	x += ds * Math.cos(angle);
	y -= ds * Math.sin(angle);
	}

    public double dist(Coord that)
    {
	double diffX = that.getX() - this.getX();
	double diffY = that.getY() - this.getY();
	return Math.sqrt(diffX*diffX + diffY*diffY);
    }
	
	public Coord midpoint(Coord that)
    {
	double sumX = (that.getX() + this.getX())/2;
	double sumY = (that.getY() + this.getY())/2;
	return new Coord(sumX,sumY);
    }
	
	public boolean intersect(Coord that,double radius)
	{
	// post: returns true if this and that are within 2*radius of each other
	return this.dist(that) <= 2*radius;
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

	public void flip(int boardHeight)
	{
	//Post: flips the coordinates such that y = 0 is the bottom of the screen and y = boardHeight is the top (or vice versa)
	y = boardHeight - y;
	}

    public double angle(Coord that)
    {
	//Post: returns the angle in radians of the line going from this to that
	// where 0 corresponds to that being directly to the right of this,
	// Pi/2 corresponds to that directly above this, etc. 
	// (note: negative angle means that is somewhat below this, and then the
	// angle is swept out clockwise instead of the usual counterclockwise)
	// NOTE: this is actually upside down. It's correct in terms of (x,y), but y increases downward. (actually it's fine i think).
	double diffX = that.getX() - this.getX();
	double diffY = that.getY() - this.getY();
	return Math.atan2(diffY, diffX);
    }
	
	public boolean equals(Object o)
	{
	Coord co = (Coord)o;
	return Math.abs(co.getX() - x) < 0.000001 && Math.abs(co.getY() - y) < 0.000001;
	}
	
	public String toString()
	{
	return "(" + x + ", " + y + ")";
	}
}