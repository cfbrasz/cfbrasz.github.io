import java.lang.Math;
import java.awt.*;

public class Circle implements Shape3D {

    private Coord3D center;
	private Vector3D n;
	private Plane p;
	private double radius;
	private Color col;
	private double reflectivity;
	
	public Circle(Coord3D center, double radius)
	{
	this(center,radius,new Vector3D(0,0,1),Color.RED,0);
	}
	
	public Circle(Coord3D center, double radius, Vector3D n, Color col, double reflectivity)
	{
	this.center = center;
	this.col = col;
	this.n = n;
	this.radius = radius;
	p = new Plane(n,center);
	this.reflectivity = reflectivity;
    }
	
	public Color getColor()
	{
	return col;
	}
	
	public double reflectivity()
	{
	return reflectivity;
	}
	
	public Plane getPlane()
	{
	return p;
	}
	
	public boolean intersectsRay(Vector3D v, Coord3D cc)
	{
	if(!p.intersects(v,cc)) return false;
	// else it intersects the plane somewhere. See if this is within the circle
	Coord3D cInt = p.intersection(v,cc);
	return cInt.dist(center) <= radius;
	}
	
	public Coord3D intersection(Vector3D v, Coord3D cc)
	{
	if(!p.intersects(v,cc)) return null;
	//use intersectsRay first to make sure it intersects
	if(!intersectsRay(v,cc)) return null;
	// else it intersects the plane somewhere. See if this is within the triangle
	Coord3D cInt = p.intersection(v,cc);
	//System.out.println("intersection: " + cInt);
	return cInt;
	}	
	
	public String type()
	{
	return "Circle";
	}
	
	public Vector3D unitNormal(Coord3D intersectionPoint)
	{
	return unitNormal();
	}
	
	public Vector3D unitNormal()
	{
	return getPlane().getN().unit();
	}
	
	public String toString()
	{
	return "Circle with center: " + center + " and radius " + radius + " and normal vector" + n;
	}
	
	public static void main(String args[])
	{
	Coord3D center = new Coord3D(0,0,0);
	double radius = 1;
	Circle ci = new Circle(center,radius);
	
	Vector3D v = new Vector3D(0,0,-1);
	Vector3D v2 = new Vector3D(0.1,0,-1);
	Coord3D cc1 = new Coord3D(0.9,0.9,1);
	Coord3D cc2 = new Coord3D(1,0,1);
	Coord3D cc3 = new Coord3D(0,0,1);
	System.out.println(ci.intersectsRay(v,cc1));
	System.out.println(ci.intersectsRay(v,cc2));
	System.out.println(ci.intersectsRay(v2,cc2));
	System.out.println(ci.intersectsRay(v2,cc3));
	System.out.println(ci.intersection(v,cc2));
	System.out.println(ci.intersection(v2,cc3));
	}
}