import java.lang.Math;

public class Coord {

    private double x;
    private double y;

    public Coord(double x, double y)
    {
	this.x = x;
	this.y = y;
    }
	
	public Coord copy()
	{
	return new Coord(x,y);
	}

    public double getX()
    {
	return x;
    }

    public double getY()
    {
	return y;
    }
	
	public static double minX(Coord[] list)
	{
	double minX = list[0].getX();
	for(int i=1; i<list.length; i++)
		{
		if(list[i].getX() < minX) minX = list[i].getX();
		}
	return minX;
	}
	
	public static double minY(Coord[] list)
	{
	double minY = list[0].getY();
	for(int i=1; i<list.length; i++)
		{
		if(list[i].getY() < minY) minY = list[i].getY();
		}
	return minY;
	}
	
	public static double maxX(Coord[] list)
	{
	double maxX = list[0].getX();
	for(int i=1; i<list.length; i++)
		{
		if(list[i].getX() > maxX) maxX = list[i].getX();
		}
	return maxX;
	}
	
	public static double maxY(Coord[] list)
	{
	double maxY = list[0].getY();
	for(int i=1; i<list.length; i++)
		{
		if(list[i].getY() > maxY) maxY = list[i].getY();
		}
	return maxY;
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
	
	public void rotate(double theta)
	{
	// rotates coord counterclockwise by angle theta
	double newX = +Math.cos(theta)*x + Math.sin(theta)*y;
	double newY = -Math.sin(theta)*x + Math.cos(theta)*y;
	x = newX;
	y = newY;
	}
	
	public void translate(double dx, double dy)
	{
	x += dx;
	y += dy;
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
	
	public Coord lerp(Coord that, double f)
	{
	// post: returns the point f between this and that (f = 0 gives this, f = 1 gives that, etc.)
	double newX = (1-f)*this.getX() + f*that.getX();
	double newY = (1-f)*this.getY() + f*that.getY();
	return new Coord(newX,newY);
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