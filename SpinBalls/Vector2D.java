import java.lang.Math;

public class Vector2D {

    private double x;
    private double y;
	private double theta = 0;
	public static final int XAXIS = 0;
	public static final int YAXIS = 1;

	public Vector2D(double x, double y)
    {
	this.x = x;
	this.y = y;
    }
	
    public Vector2D(double x1, double y1, double x2, double y2)
    {
	// Constructs a vector from point 1 to point 2
	this.x = x2-x1;
	this.y = y2-y1;
    }
	
	public Vector2D copy()
	{
	return new Vector2D(x,y);
	}

    public double getX()
    {
	return x;
    }
	
	public Vector2D(Coord c1, Coord c2)
    {
	// Constructs a vector from point 1 to point 2
	this.x = c2.getX()-c1.getX();
	this.y = c2.getY()-c1.getY();
    }

    public double getY()
    {
	return y;
    }
	
	public double getSlope()
    {
	if (x != 0) return y/x;
	else return y*99999;
    }
	
	public Vector2D perpendicular()
	{
	//rotates 90 deg CW in normal coords, CCW in screen coords
	return new Vector2D(y,-x);
	}
	
	public void plus(Vector2D that)
	{
	x += that.getX();
	y += that.getY();
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

    public double magnitude()
    {
	return Math.sqrt(x*x + y*y);
    }
	
	public Coord midpoint(Coord that)
    {
	double sumX = (that.getX() + this.getX())/2;
	double sumY = (that.getY() + this.getY())/2;
	return new Coord(sumX,sumY);
    }
	
	public Coord parametric(Coord cc, double t)
	{
	//returns the coord at t*(this vector) + cc
	double newX = cc.getX() + t*x;
	double newY = cc.getY() + t*y;
	return new Coord(newX,newY);
	}
	
	public boolean intersect(Coord that,double radius)
	{
	// post: returns true if this and that are within 2*radius of each other
	return this.dist(that) <= 2*radius;
	}

	public void rotate(double theta)
	{
	// rotates coord counterclockwise by angle theta (actually clockwise)
	double newX = +Math.cos(theta)*x + Math.sin(theta)*y;
	double newY = -Math.sin(theta)*x + Math.cos(theta)*y;
	x = newX;
	y = newY;
	this.theta += theta;
	}
	
    public double angle()
    {
	return Math.atan2(y,x);
	//return theta;
    }

	public double getTheta()
    {
	//return Math.atan2(y,x);
	return theta;
    }
	
	public void reverse()
	{
	x=-x;
	y=-y;
	}
	
	public void scalarMult(double factor)
	{
	x = factor*x;
	y = factor*y;
	}
	
	public void reflect(int axis)
	{
	//Post: reflects over x axis if 0, y axis if 1
	if(axis == XAXIS) y = -y;
	if(axis == YAXIS) x = -x;
	}
	
	public void bounce(Vector2D normal)
	{
	// reflects current vector across surface with given unit normal
	double s = -2*this.dot(normal);
	normal.scalarMult(s);
	this.plus(normal);
	}
	
	public Vector2D unit()
	{	
	Vector2D newUnitV = new Vector2D(x/magnitude(),y/magnitude());
	return newUnitV;
	}
	
	public double dot(Vector2D that)
	{
	return x*that.getX() + y*that.getY();
	}
	
	public void flip(int boardHeight)
	{
	//Post: flips the coordinates such that y = 0 is the bottom of the screen and y = boardHeight is the top (or vice versa)
	y = boardHeight - y;
	}
	
	public String toString()
	{
	return "(" + x + ", " + y + ")";
	}
}