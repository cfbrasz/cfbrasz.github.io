import java.lang.Math;
import java.awt.*;

public class Triangle3D implements Shape3D {

    private Coord3D[] coords;
	private int npoints;
	private boolean closed = true;
	private Vector3D n;
	private Plane p;
	private Color col;
	private double reflectivity;
	
    public Triangle3D(Coord3D[] coords)
    {	
	this(coords,Color.RED,0);
	}
	
	public Triangle3D(Coord3D[] coords, Color col)
	{
	this(coords,col,0);
	}

    public Triangle3D(Coord3D[] coords, Color col, double reflectivity)
    {	
	this.coords = coords;
	this.col = col;
	npoints = coords.length;
	if(npoints != 3) System.out.println("Error, not a triangle");
	//find plane of triangle
	Coord3D a = coords[0];
	Coord3D b = coords[1];
	Coord3D c = coords[2];
	Vector3D ab = new Vector3D(a,b);
	Vector3D ac = new Vector3D(a,c);
	n = ab.cross(ac).unit();
	p = new Plane(n,a);
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
	
    public int getSides()
    {
	return npoints;
    }
	
	public Plane getPlane()
	{
	return p;
	}
	
	public Coord3D getCoord(int vertex)
	{
	if(vertex < 3) return coords[vertex];
	else 
		{
		System.out.println("Error, only 3 vertices");
		return null;
		}
	}
	
	public boolean intersectsRay(Vector3D v, Coord3D cc)
	{
	if(!p.intersects(v,cc)) return false;
	// else it intersects the plane somewhere. See if this is within the triangle
	Coord3D cInt = p.intersection(v,cc);
	Coord3D a = coords[0];
	Coord3D b = coords[1];
	Coord3D c = coords[2];
	Coord a2;
	Coord b2;
	Coord c2;
	Coord cInt2;
	Vector3D norm = p.getN();
	
	if(Math.abs(norm.getX()) > 0.000001)
		{ //flatten in x direction
		//System.out.println("flattening in x dir");
		a2 = new Coord(a.getY(),a.getZ());
		b2 = new Coord(b.getY(),b.getZ());
		c2 = new Coord(c.getY(),c.getZ());
		cInt2 = new Coord(cInt.getY(),cInt.getZ());
		}
	else if(Math.abs(norm.getY()) > 0.000001)
		{ //flatten in y direction
		//System.out.println("flattening in y dir");
		a2 = new Coord(a.getX(),a.getZ());
		b2 = new Coord(b.getX(),b.getZ());
		c2 = new Coord(c.getX(),c.getZ());
		cInt2 = new Coord(cInt.getX(),cInt.getZ());
		}
	else
		{ //flatten in z direction
		//System.out.println("flattening in z dir");
		if(Math.abs(norm.getZ()) < 0.000001) System.out.println("Error in plane's normal, no nonzero component");
		a2 = new Coord(a.getX(),a.getY());
		b2 = new Coord(b.getX(),b.getY());
		c2 = new Coord(c.getX(),c.getY());
		cInt2 = new Coord(cInt.getX(),cInt.getY());
		}
	Coord out = new Coord(10000,10000);
	int intersections = 0;
	if(cInt2.intersectSegment(out,a2,b2)) intersections++;
	if(cInt2.intersectSegment(out,b2,c2)) intersections++;
	if(cInt2.intersectSegment(out,c2,a2)) intersections++;
	//System.out.println(intersections);
	return intersections == 1;
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
	return "Triangle";
	}
	
	public String toString()
	{
	return "Points:\n" + coords[0] + "\n" + coords[1] + "\n" + coords[2];
	}
	
	public Vector3D unitNormal(Coord3D intersectionPoint)
	{
	return unitNormal();
	}
	
	public Vector3D unitNormal()
	{
	return getPlane().getN().unit();
	}
	
	public static void main(String args[])
	{
	Coord3D[] coords = new Coord3D[3];
	coords[0] = new Coord3D(0,0,0);
	coords[1] = new Coord3D(1,0,0);
	coords[2] = new Coord3D(0,0,1);
	Triangle3D tri = new Triangle3D(coords);
	
	Vector3D v = new Vector3D(-0.5,-1,0);
	Vector3D v2 = new Vector3D(-0.5,-1,0.2);
	Vector3D v3 = new Vector3D(-1,-1,0);
	Coord3D cc = new Coord3D(1,1,0);
	System.out.println(tri.intersectsRay(v,cc));
	System.out.println(tri.intersectsRay(v2,cc));
	System.out.println(tri.intersectsRay(v3,cc));
	Plane p = tri.getPlane();
	
	System.out.println(tri.intersection(v,cc));
	System.out.println(tri.intersection(v2,cc));
	System.out.println(tri.intersection(v3,cc));
	
	coords[0] = new Coord3D(0,0,0);
	coords[1] = new Coord3D(1,0,0);
	coords[2] = new Coord3D(0,0.2,1);
	Triangle3D qu = new Triangle3D(coords);
	
	v = new Vector3D(-0.5,-1,0);
	v2 = new Vector3D(-0.75,-1,0.6);
	v3 = new Vector3D(-1,-1,0.5);
	cc = new Coord3D(1,1,0);
	System.out.println(qu.intersectsRay(v,cc));
	System.out.println(qu.intersectsRay(v2,cc));
	System.out.println(qu.intersectsRay(v3,cc));
	
	System.out.println(qu.intersection(v,cc));
	System.out.println(qu.intersection(v2,cc));
	System.out.println(qu.intersection(v3,cc));
	
	}
}