import java.lang.Math;

public class Force {

    private double x;
    private double y;
	private SpinBall b1;
	private SpinBall b2;
	private double F;
	private double direction;
	private double scale = 1000;
	private boolean attractive;

    public Force(SpinBall b1, SpinBall b2, boolean attractive)
    {
	this.b1 = b1;
	this.b2 = b2;
	this.attractive = attractive;
    }

	public double getDirection()
	{
	// post: 0 if b2 is to the right of b1, up to PI if b2 is to the left (positive for b2 above b1, negative otherwise)
	Coord b1c = b1.getCoord();
	Coord b2c = b2.getCoord();
	direction = b1c.angle(b2c);
	return direction;
	}
	
	public double getMagnitude()
	{
	// electrostatic force: k q1 q2 / r^2
	Coord b1c = b1.getCoord();
	Coord b2c = b2.getCoord();
	double r = b1c.dist(b2c);
	F = scale/(r*r);
	return F;
	}
	
	public double getForceX()
	{
	getDirection();
	getMagnitude();
	if (attractive) return F*Math.cos(direction);
	else return -F*Math.cos(direction); //minus for repulsion
	}
	
	public double getForceY()
	{
	getDirection();
	getMagnitude();
	if (attractive) return F*Math.sin(direction);
	else return -F*Math.sin(direction); //minus for repulsion
	}	
	
    public double getX()
    {
	return x;
    }

    public double getY()
    {
	return y;
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