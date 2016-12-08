import java.lang.Math;

public class Coord3D {

    private double x;
    private double y;
	private double z;
	private double theta=0;
	public static Coord3D ORIGIN = new Coord3D(0,0,0);

    public Coord3D(double x, double y, double z)
    {
	this.x = x;
	this.y = y;
	this.z = z;
    }

    public Coord3D(Vector3D v)
    {
	this.x = v.getX();
	this.y = v.getY();
	this.z = v.getZ();
    }
	
	public Coord3D copy()
	{
	return new Coord3D(x,y,z);
	}
	
	public Coord xyCoord()
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
	
    public double getZ()
    {
	return z;
    }
	
	public void moveX(double dx)
	{
	x += dx;
	}
	
	public void moveY(double dy)
	{
	y += dy;
	}
	
	public void moveZ(double dz)
	{
	z += dz;
	}
	
	public Coord3D moveXn(double dx)
	{
	double nx = x + dx;
	return new Coord3D(nx,y,z);
	}
	
	public Coord3D moveYn(double dy)
	{
	double ny = y + dy;
	return new Coord3D(x,ny,z);
	}
	
	public Coord3D moveZn(double dz)
	{
	double nz = z + dz;
	return new Coord3D(x,y,nz);
	}
	
	public void moveForward(Vector3D direction, double moveDist)
	{
	Vector3D unitDir = direction.unit();
	plus(unitDir.newScalarMult(moveDist));
	}
	
	public void moveLeft(Vector3D direction, double moveDist)
	{
	// strafe left in xy plane relative to current direction
	Vector3D unitDir = direction.xyProjection().unit();
	unitDir.rotateXY(Math.PI/2);
	plus(unitDir.newScalarMult(moveDist));
	}
	
	public void moveRight(Vector3D direction, double moveDist)
	{
	// strafe left in xy plane relative to current direction
	Vector3D unitDir = (direction.xyProjection()).unit();
	unitDir.rotateXY(-Math.PI/2);
	plus(unitDir.newScalarMult(moveDist));
	}
	
	public void moveBackward(Vector3D direction, double moveDist)
	{
	Vector3D unitDir = direction.unit();
	plus(unitDir.newScalarMult(-moveDist));
	}
	
	/*
	public void move(double ds, double angle)
	{
	// angle on the screen counterclockwise from x=0 positive axis
	x += ds * Math.cos(angle);
	y -= ds * Math.sin(angle);
	}
	*/
	
	public void rotateXY(double theta)
	{
	// rotates coord clockwise by angle theta (was counterclockwise but changed it for 3D applet)
	double newX = +Math.cos(theta)*x - Math.sin(theta)*y;
	double newY = +Math.sin(theta)*x + Math.cos(theta)*y;
	x = newX;
	y = newY;
	this.theta += theta;
	}
	
	public void rotateXZ(double phi)
	{		
	// rotates about y axis
	double newX = +Math.cos(phi)*x + Math.sin(phi)*z;
	double newZ = -Math.sin(phi)*x + Math.cos(phi)*z;
	x = newX;
	z = newZ;
	}

    public double dist(Coord3D that)
    {
	double diffX = that.getX() - this.getX();
	double diffY = that.getY() - this.getY();
	double diffZ = that.getZ() - this.getZ();
	return Math.sqrt(diffX*diffX + diffY*diffY + diffZ*diffZ);
    }
	
	public Coord3D midpoint(Coord3D that)
    {
	double sumX = (that.getX() + this.getX())/2;
	double sumY = (that.getY() + this.getY())/2;
	double sumZ = (that.getZ() + this.getZ())/2;
	return new Coord3D(sumX,sumY,sumZ);
    }
	
	public boolean intersect(Coord3D that,double radius)
	{
	// post: returns true if this and that are within 2*radius of each other
	return this.dist(that) <= 2*radius;
	}
	
	public void plus(Vector3D that)
	{
	this.x += that.getX();
	this.y += that.getY();
	this.z += that.getZ();
	}
	
	public void plus(Coord3D that)
	{
	this.x += that.getX();
	this.y += that.getY();
	this.z += that.getZ();
	}
	
	public Coord3D plusN(Coord3D that)
	{
	double xN = this.x + that.getX();
	double yN = this.y + that.getY();
	double zN = this.z + that.getZ();
	return new Coord3D(xN,yN,zN);
	}
	
	public Coord3D reverse()
	{
	return new Coord3D(-x,-y,-z);
	}

	
    public double angleXYOrigin()
    {
	return Math.atan2(y,x);
    }

	public void flip(int boardHeight)
	{
	//Post: flips the coordinates such that y = 0 is the bottom of the screen and y = boardHeight is the top (or vice versa)
	y = boardHeight - y;
	}

	/*
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
	*/
	
	public boolean equals(Object o)
	{
	Coord3D co = (Coord3D)o;
	return Math.abs(co.getX() - x) < 0.000001 && Math.abs(co.getY() - y) < 0.000001 && Math.abs(co.getZ() - z) < 0.000001;
	}
	
	public String toString()
	{
	return "(" + x + ", " + y + ", " + z + ")";
	}
}