import java.lang.Math;
import java.awt.*;

public class Sphere implements Shape3D {

    private Coord3D center;
	private double radius;
	private Color col;
	private double reflectivity;
	
	public Sphere(Coord3D center, double radius)
	{
	this(center,radius,Color.RED,0);
	}    
	
	public Sphere(Coord3D center, double radius, Color col)
	{
	this(center,radius,col,0);
    } 
	
	public Sphere(Coord3D center, double radius, Color col, double reflectivity)
	{
	this.center = center;
	this.col = col;
	this.radius = radius;
	this.reflectivity = reflectivity;
    }
	
	public Color getColor()
	{
	return col;
	}
	
    public Coord3D getCenter()
    {
	return center;
    }
	
	public double getRadius()
	{
	return radius;
	}
	
	public static double[] findRoots(double a, double b, double c)
	{
	//post: finds the roots of the quadratic equation given equation ax^2 + bx + c = 0
	double[] result = new double[2];
	if(b*b-4*a*c < 0) System.out.println("Error: no real roots");
	result[0] = (-b + Math.sqrt(b*b-4*a*c))/(2*a);
	result[1] = (-b - Math.sqrt(b*b-4*a*c))/(2*a);
	return result;
	}
	
	public static boolean hasRealRoots(double a, double b, double c)
	{
	return b*b-4*a*c >= 0;
	}
	
	public boolean intersectsRay(Vector3D v, Coord3D cc)
	{
	// don't actually use, waste of calculation
	double x0 = cc.getX()-center.getX();
	double y0 = cc.getY()-center.getY();
	double z0 = cc.getZ()-center.getZ();
	double vx = v.getX();
	double vy = v.getY();
	double vz = v.getZ();
	double a = vx*vx + vy*vy + vz*vz;
	double b = 2*(vx*x0 + vy*y0 + vz*z0);
	double c = x0*x0 + y0*y0 + z0*z0 - radius*radius;
	return hasRealRoots(a,b,c);
	}
	
	public Coord3D intersection(Vector3D v, Coord3D cc)
	{
	// return null if it doesn't intersect, else return intersection CLOSER to cc
	double x0 = cc.getX()-center.getX();
	double y0 = cc.getY()-center.getY();
	double z0 = cc.getZ()-center.getZ();
	double vx = v.getX();
	double vy = v.getY();
	double vz = v.getZ();
	double a = vx*vx + vy*vy + vz*vz;
	double b = 2*(vx*x0 + vy*y0 + vz*z0);
	double c = x0*x0 + y0*y0 + z0*z0 - radius*radius;
	if(!hasRealRoots(a,b,c)) return null;
	double[] t = findRoots(a,b,c);
	if(t[0] < 0 && t[1] < 0) return null;
	Coord3D[] ints = new Coord3D[2];
	ints[0] = v.parametric(cc,t[0]);
	ints[1] = v.parametric(cc,t[1]);
	if(cc.dist(ints[0]) < cc.dist(ints[1]) && t[0] >= 0) return ints[0];
	else if(t[1] >= 0) return ints[1];
	return null;
	}	
	
	public String toString()
	{
	return "Sphere centered at:" + center + " with radius " + radius;
	}
	
	public String type()
	{
	return "Sphere";
	}
	
	public double reflectivity()
	{
	return reflectivity;
	}
	
	public Vector3D unitNormal(Coord3D intersectionPoint)
	{
	return new Vector3D(getCenter(),intersectionPoint).unit();
	}
	
	public static void main(String args[])
	{
	Coord3D center = new Coord3D(10,0,0);
	double r = 5;
	Shape3D sph = new Sphere(center,r);
	
	Vector3D v = new Vector3D(1,0,0);
	Coord3D cc = new Coord3D(0,0,5);
	System.out.println(sph.intersectsRay(v,cc));
	System.out.println(sph.intersection(v,cc));
	}
}