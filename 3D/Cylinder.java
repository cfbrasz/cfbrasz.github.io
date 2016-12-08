import java.lang.Math;
import java.awt.*;

public class Cylinder implements Shape3D {

    private Coord3D center;
    private Coord3D centerZhat; //center point in frame in which the normal is (0,0,1)
	private Vector3D n;
	private Plane p;
	private double radius;
	private double height;
	private Color col;
	private double reflectivity;
	private double maxD; //max dist of center to a point on cylinder Sqrt(r^2 + h^2)
	
	public Cylinder(Coord3D center, double radius)
	{
	this(center,radius,10000,new Vector3D(0,0,1),Color.RED,0);
	}
	
	public Cylinder(Coord3D center, double radius, double height, Vector3D n, Color col, double reflectivity)
	{
	this.center = center;
	this.col = col;
	this.n = n;
	this.radius = radius;
	this.height = height;
	p = new Plane(n,center);
	this.reflectivity = reflectivity;
	maxD = Math.sqrt(radius*radius + height*height/4);	
	centerZhat = n.rotateCoordToZHat(center);
    }
	
	public Color getColor()
	{
	return col;
	}
	
	public Coord3D getCenter()
	{
	return center;
	}
	
	public Coord3D getCenterZhat()
	{
	return centerZhat;
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
	// finds min distance from axis
	double x0 = cc.getX()-center.getX();
	double y0 = cc.getY()-center.getY();
	double vx = v.getX();
	double vy = v.getY();
	double t = -(vx*x0 + vy*y0)/(vx*vx + vy*vy);
	//System.out.println(t);
	if(t<0) return false;
	Coord3D minD = v.parametric(cc,t);
	Coord minD2d = minD.xyCoord();
	Coord center2d = center.xyCoord();
	//System.out.println(minD2d.dist(center2d) + " 2D dist");
	return minD2d.dist(center2d) <= radius && minD.dist(center) <= maxD;
	}
	
	public Coord3D intersection(Vector3D v, Coord3D cc)
	{
	cc.plus(center.reverse());
	
	Vector3D vN = n.rotateVectorToZHat(v,cc);
	Coord3D ccN = n.rotateCoordToZHat(cc);
	
	cc.plus(center);
		
	//System.out.println("Vector, Coord:");
	//System.out.println(vN);
	//System.out.println(ccN);
	
	
	// return null if it doesn't intersect, else return intersection CLOSER to cc
	double x0 = ccN.getX(); //-center.getX(); // No longer include center because we translate it out
	double y0 = ccN.getY(); //-center.getY();
	double vx = vN.getX();
	double vy = vN.getY();
	double a = vx*vx + vy*vy;
	double b = 2*(vx*x0 + vy*y0);
	double c = x0*x0 + y0*y0 - radius*radius;
	if(!Sphere.hasRealRoots(a,b,c)) return null;
	double[] t = Sphere.findRoots(a,b,c);
	if(t[0] < 0 && t[1] < 0) return null;
	Coord3D[] ints = new Coord3D[2];
	ints[0] = vN.parametric(ccN,t[0]);
	ints[1] = vN.parametric(ccN,t[1]);
	/*
	System.out.println("int0, int1:");
	System.out.println(ints[0]);
	System.out.println(ints[1]);
	System.out.println("int0, int1: orig ref frame (but not translated back)");
	System.out.println(n.rotateCoordFromZHat(ints[0]));
	System.out.println(n.rotateCoordFromZHat(ints[1]));
	*/
	
	if(ccN.dist(ints[0]) < ccN.dist(ints[1]) && t[0] >= 0 && ints[0].dist(Coord3D.ORIGIN) <= maxD) return n.rotateCoordFromZHat(ints[0]).plusN(center);
	else if(t[1] >= 0 && ints[1].dist(Coord3D.ORIGIN) <= maxD) return n.rotateCoordFromZHat(ints[1]).plusN(center);
	else if(t[0] >= 0 && ints[0].dist(Coord3D.ORIGIN) <= maxD) return n.rotateCoordFromZHat(ints[0]).plusN(center);
	return null;
	}		
	
	public String type()
	{
	return "Cylinder";
	}
	
	public Vector3D unitNormal(Coord3D intersectionPoint)
	{
	Coord3D cc = intersectionPoint.copy();
	cc.plus(center.reverse());	
	Coord3D ccN = n.rotateCoordToZHat(cc);	
	Vector3D norm = new Vector3D(ccN.getX(),ccN.getY(),0);
	Vector3D result = n.rotateVectorFromZHat(norm,ccN);
	return result.unit();
	}
	
	public String toString()
	{
	return "Cylinder with center: " + center + ", radius " + radius + ", height " + height + " and normal vector" + n;
	}
	
	public static void main(String args[])
	{
	Coord3D center = new Coord3D(0,0,0);
	double radius = 1;
	Cylinder ci = new Cylinder(center,radius,10000,new Vector3D(0,0,1),Color.RED,0);
	
	
	Vector3D v = new Vector3D(1,0,0);
	Vector3D v2 = new Vector3D(1,0.05,0);
	Vector3D v3 = new Vector3D(1,0.15,0);
	Coord3D cc = new Coord3D(-10,0,0);
	System.out.println(ci.intersectsRay(v,cc));
	System.out.println(ci.intersectsRay(v2,cc));
	System.out.println(ci.intersectsRay(v3,cc));
	//System.out.println(ci.intersection(v,cc));
	System.out.println(ci.intersection(v,cc));
	System.out.println(ci.intersection(v2,cc));
	System.out.println(ci.intersection(v3,cc));
	
	center = new Coord3D(0,-1,0);
	Coord3D center2 = new Coord3D(4,-1,0);
	radius = 5;
	v = new Vector3D(0,0,1);
	cc = new Coord3D(0,5,0);
	ci = new Cylinder(center,radius,10000,new Vector3D(0,0.1,1),Color.RED,0);
	Cylinder ci2 = new Cylinder(center2,radius,10000,new Vector3D(0,0.1,1),Color.RED,0);
	/*
	System.out.println(ci.getCenter());
	System.out.println(ci.getCenterZhat());	
	System.out.println(ci.getPlane().getN().rotateCoordFromZHat(ci.getCenterZhat()));	
	*/
	System.out.println(ci.intersection(v,cc));	
	System.out.println(ci2.intersection(v,cc));	
	
	}
}