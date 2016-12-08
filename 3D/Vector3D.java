import java.lang.Math;

public class Vector3D {

    private double x;
    private double y;
	private double z;
	private double theta = 0;
	public static Vector3D ZEROVECTOR = new Vector3D(0,0,0);
	public static Vector3D ZHAT = new Vector3D(0,0,1);

    public Vector3D(double x1, double y1, double z1, double x2, double y2, double z2)
    {
	// Constructs a vector from point 1 to point 2
	this.x = x2-x1;
	this.y = y2-y1;
	this.z = z2-z1;
	theta = angleHoriz();
    }
	
	public Vector3D(double x, double y, double z)
    {
	// Constructs a vector from point 1 to point 2
	this.x = x;
	this.y = y;
	this.z = z;
	theta = angleHoriz();
    }
	
	public Vector3D(Coord3D c)
	{
	this.x = c.getX();
	this.y = c.getY();
	this.z = c.getZ();
	theta = angleHoriz();
	}
	
	public Vector3D(Coord3D c1, Coord3D c2)
	{
	this.x = c2.getX()-c1.getX();
	this.y = c2.getY()-c1.getY();
	this.z = c2.getZ()-c1.getZ();
	theta = angleHoriz();
	}
	
	public Vector3D copy()
	{
	return new Vector3D(x,y,z);
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
	
    public double getTheta()
    {
	return theta;
    }
	
	public void setTheta(double theta)
	{
	this.theta = theta;
	}
	
	public double length()
	{
	return Math.sqrt(x*x + y*y + z*z);
	}
	
	public boolean opposite(Vector3D that)
	{
	// returns true if the angle between this and that is > Pi/2
	Vector3D plus = this.copy();
	plus.plus(that);
	Vector3D minus = this.copy();
	minus.plus(that.reverse());
	return plus.length() < minus.length();
	}
	
	public void scalarMult(double factor)
	{
	x = factor*x;
	y = factor*y;
	z = factor*z;
	}
	
	public Vector3D newScalarMult(double factor)
	{
	double newX = factor*x;
	double newY = factor*y;
	double newZ = factor*z;
	return new Vector3D(newX,newY,newZ);
	}
	
	public Vector3D xyProjection()
	{
	return new Vector3D(x,y,0);
	}
	
	public Coord3D planeIntersection(Coord3D p1, Coord3D p2)
	{
	//post: finds where the line between the two points intersects the plane defined by the current 3D vector (centered at (0,0,0))
	Vector3D line = new Vector3D(p1,p2);
	// define plane of form ax + by + cz = 0
	double a = x;
	double b = y;
	double c = z;
	double x0 = p1.getX();
	double y0 = p1.getY();
	double z0 = p1.getZ();
	double kx = line.getX();
	double ky = line.getY();
	double kz = line.getZ();
	double i = -(a*x0+b*y0+c*z0)/(a*kx+b*ky+c*kz);
	double newX = x0 + i*kx;
	double newY = y0 + i*ky;
	double newZ = z0 + i*kz;
	/*
	System.out.println(a);
	System.out.println(b);
	System.out.println(c);
	System.out.println(kx);
	System.out.println(ky);
	System.out.println(kz);
	System.out.println(x0);
	System.out.println(y0);
	System.out.println(z0);
	System.out.println(i);
	*/
	//Check if it works:
	if (Math.abs(a*newX+b*newY+c*newZ) < 0.000001) 
	{
	//System.out.println(a*newX+b*newY+c*newZ);
	return new Coord3D(newX,newY,newZ);
	}
	else
		{
		System.out.println("Trying other point");
		System.out.println(a*newX+b*newY+c*newZ);
		x0 = p2.getX();
		y0 = p2.getY();
		z0 = p2.getZ();
		kx= -kx;
		ky= -ky;
		kz= -kz;
		i = -(x0+y0+z0)/(a*kx+b*ky+c*kz);
		newX = x0 + i*kx;
		newY = y0 + i*ky;
		newZ = z0 + i*kz;
		if (Math.abs(a*newX+b*newY+c*newZ) > 0.000001) System.out.println("other point failed");		
		//System.out.println(a*newX+b*newY+c*newZ);
		return new Coord3D(newX,newY,newZ);	
		}
	}
	
	
	public Vector3D unit()
	{	
	Vector3D newUnitV = new Vector3D(x/length(),y/length(),z/length());
	return newUnitV;
	}
	
	public void plus(Vector3D that)
	{
	this.x += that.getX();
	this.y += that.getY();
	this.z += that.getZ();
	theta = angleHoriz();	
	}
	
	public void plus(Coord3D that)
	{
	this.x += that.getX();
	this.y += that.getY();
	this.z += that.getZ();
	theta = angleHoriz();	
	}
	
	public Vector3D plusN(Coord3D that)
	{
	double xN = this.x + that.getX();
	double yN = this.y + that.getY();
	double zN = this.z + that.getZ();
	return new Vector3D(xN,yN,zN);
	}
	
	public Vector3D orthogonalProjection(Vector3D that)
	{
	Vector3D newV = new Vector3D(x,y,z);
	Vector3D thatUnit = that.unit();
	thatUnit.scalarMult(-length()*Math.cos(angle(that)));
	newV.plus(thatUnit);
	return newV;
	}
	
	public double dot(Vector3D that)
	{
	return this.getX()*that.getX() + this.getY()*that.getY() + this.getZ()*that.getZ();
	}
	
	public Vector3D cross(Vector3D that)
	{
	double xComp = y*that.getZ() - z*that.getY();
	double yComp = z*that.getX() - x*that.getZ();
	double zComp = x*that.getY() - y*that.getX();
	return new Vector3D(xComp,yComp,zComp);
	}		
	
	public double angle(Vector3D that)
	{
	return Math.acos(this.dot(that)/(this.length()*that.length()));
	}
	
	public double angleHoriz()
    {
	return Math.atan2(y,x);
	//return theta;
    }
	
	public double angleVert()
    {
	return Math.atan2(z,Math.sqrt(x*x+y*y));
	//return theta;
    }
	
	
	public void rotateVert(double phi)
	{	
	// first rotate to x=1, y=0
	double thetaTemp = theta;
	rotateXY(-thetaTemp);
	//System.out.println(toString());
	
	// now rotate about y axis
	double newX = +Math.cos(phi)*x + Math.sin(phi)*z;
	double newZ = -Math.sin(phi)*x + Math.cos(phi)*z;
	x = newX;
	z = newZ;
	//System.out.println(toString());
		
	rotateXY(thetaTemp);
	}
	
	public void rotateXZ(double phi)
	{		
	// rotates about y axis
	double newX = +Math.cos(phi)*x + Math.sin(phi)*z;
	double newZ = -Math.sin(phi)*x + Math.cos(phi)*z;
	x = newX;
	z = newZ;
	}
	
	public void moveX(double dx)
	{
	x += dx;
	}
	
	public void moveY(double dy)
	{
	y += dy;
	}
	
	public Vector3D reverse()
	{
	return new Vector3D(-x,-y,-z);
	}
	
	public Coord3D parametric(Coord3D cc, double t)
	{
	//returns the coord at t*(this vector) + cc
	double newX = cc.getX() + t*x;
	double newY = cc.getY() + t*y;
	double newZ = cc.getZ() + t*z;
	return new Coord3D(newX,newY,newZ);
	}
	
	public void rotateAbout(Vector3D dir, double phi)
	{
	// rotates by phi counterclockwise about dir: make dir into (0,0,1) then rotateXY by phi then rotate back.	
	double vertAng = Math.PI/2-dir.angleVert();
	double dirTheta = dir.getTheta();
		
	rotateXY(-dirTheta);
	//dir.rotateXY(-dirTheta);
		
	rotateXZ(-vertAng);
	//dir.rotateXZ(-vertAng);
	
	rotateXY(phi);
	
	rotateXZ(vertAng);
	//dir.rotateXZ(vertAng);
		
	rotateXY(dirTheta);	
	//dir.rotateXY(dirTheta);	
	}
	
	public Coord3D rotateCoordToZHat(Coord3D c)
	{
	double vertAng = Math.PI/2-angleVert();
	Vector3D cN = new Vector3D(c);
	cN.rotateXY(-theta);
	cN.rotateXZ(-vertAng);
	return new Coord3D(cN);
	}
	
	public Coord3D rotateCoordFromZHat(Coord3D c)
	{
	double vertAng = Math.PI/2-angleVert();
	Vector3D cN = new Vector3D(c);
	cN.rotateXZ(vertAng);
	cN.rotateXY(theta);
	return new Coord3D(cN);
	}
	
	public Vector3D rotateVectorToZHat(Vector3D dir, Coord3D c)
	{
	// returns new vector with direction given by rotating dir with origin c about axes to make this 0,0,1 vector
	double vertAng = Math.PI/2-angleVert();
	Vector3D dirN = dir.copy();
	dirN.plus(new Vector3D(c));
	//System.out.println("first v = " + dirN);
	dirN.rotateXY(-theta);
	//System.out.println("second v = " + dirN);
	dirN.rotateXZ(-vertAng);
	//System.out.println("third v = " + dirN);
	Vector3D cN = new Vector3D(c);
	cN.rotateXY(-theta);
	cN.rotateXZ(-vertAng);
	dirN.plus(cN.reverse());
	//System.out.println("fourth v = " + dirN);
	return dirN;
	}
	
	public Vector3D rotateVectorFromZHat(Vector3D dir, Coord3D c)
	{
	// returns new vector with direction given by rotating dir with origin c about axes back from 0,0,1 vector to this
	double vertAng = Math.PI/2-angleVert();
	Vector3D dirN = dir.copy();
	dirN.plus(new Vector3D(c));
	dirN.rotateXZ(vertAng);
	//System.out.println("first v = " + dirN);
	dirN.rotateXY(theta);
	//System.out.println("second v = " + dirN);
	//System.out.println("third v = " + dirN);
	Vector3D cN = new Vector3D(c);
	cN.rotateXZ(vertAng);
	cN.rotateXY(theta);
	dirN.plus(cN.reverse());
	//System.out.println("fourth v = " + dirN);
	return dirN;
	}
	
	public Vector3D reflect(Vector3D normal)
	{
	Vector3D newV = this.copy();
	newV.rotateAbout(normal, Math.PI);
	return newV.reverse();
	}
	
	
	public void rotateXY(double theta)
	{
	// rotates coord clockwise by angle theta
	double newX = +Math.cos(theta)*x - Math.sin(theta)*y;
	double newY = +Math.sin(theta)*x + Math.cos(theta)*y;
	x = newX;
	y = newY;
	this.theta += theta;
	}
	
	public void move(double ds, double angle)
	{
	// angle on the screen counterclockwise from x=0 positive axis
	x += ds * Math.cos(angle);
	y -= ds * Math.sin(angle);
	theta = angleHoriz();
	}
	
	public String toString()
	{
	return "(" + x + ", " + y + ", " + z + ")";
	}
	
	public boolean equals(Object o)
	{
	Vector3D co = (Vector3D)o;
	return Math.abs(co.getX() - x) < 0.000001 && Math.abs(co.getY() - y) < 0.000001 && Math.abs(co.getZ() - z) < 0.000001;
	}
	
	public boolean parallelTo(Vector3D other)
	{
	return this.cross(other).equals(ZEROVECTOR);
	}
	
	public static void main(String args[])
	{
	
	Vector3D v = new Vector3D(0,1,0);
	System.out.println(v.getTheta());
	//Vector3D v3 = new Vector3D(-1,-1,0);
	double theta = Math.PI/3;
	v.rotateVert(theta);
	System.out.println(v);
	
	Vector3D v2 = new Vector3D(1,1,0);
	Vector3D v3 = new Vector3D(0,1,0.2);
	System.out.println(v2);
	v2.rotateAbout(v3,Math.PI/3);
	System.out.println(v2);
	/*
	v2.rotateXY(Math.PI/2);
	System.out.println(v2);
	v2.rotateXY(Math.PI/2);
	System.out.println(v2);
	*/
	v = new Vector3D(0,1,0);
	System.out.println(v.parallelTo(new Vector3D(0,13.2,0)));
	
	
	
	}
}