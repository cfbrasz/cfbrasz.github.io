import java.lang.Math;
import java.awt.*;

public class Quad3D implements Shape3D {

    private Coord3D[] coords;
	private int npoints;
	private boolean closed = true;
	private Vector3D n;
	private Plane p;
	private Color col;
	private double reflectivity;
	
	public Quad3D(Coord3D[] coords)
	{
	this(coords,Color.RED,0);
	}
	
    public Quad3D(Coord3D[] coords, Color col)
    {	
	this(coords,col,0);
	}
	
    public Quad3D(Coord3D[] coords, Color col, double reflectivity)
    {	
	this.coords = coords;
	this.col = col;
	npoints = coords.length;
	if(npoints != 4) System.out.println("Error, not a quadrilateral");
	//find plane of triangle
	Coord3D a = coords[0];
	Coord3D b = coords[1];
	Coord3D c = coords[2];
	Coord3D d = coords[3];
	Vector3D ab = new Vector3D(a,b);
	Vector3D ac = new Vector3D(a,c);
	n = ab.cross(ac);
	n = n.unit();
	Vector3D ad = new Vector3D(a,d);
	Vector3D ncheck = ab.cross(ad);
	ncheck = ncheck.unit();
	Vector3D ncheck2 = ac.cross(ad);
	ncheck2 = ncheck2.unit();
	if(!n.equals(ncheck) || !ncheck.equals(ncheck2)) System.out.println("Error, doesn't lie in one plane");
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
	if(vertex < npoints) return coords[vertex];
	else 
		{
		System.out.println("Error, only " + npoints + " vertices");
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
	Coord3D d = coords[3];
	Vector3D norm = p.getN();
	Coord a2;
	Coord b2;
	Coord c2;
	Coord d2;
	Coord cInt2;
	if(Math.abs(norm.getX()) > 0.000001)
		{ //flatten in x direction
		//System.out.println("flattening in x dir");
		a2 = new Coord(a.getY(),a.getZ());
		b2 = new Coord(b.getY(),b.getZ());
		c2 = new Coord(c.getY(),c.getZ());
		d2 = new Coord(d.getY(),d.getZ());
		cInt2 = new Coord(cInt.getY(),cInt.getZ());
		}
	else if(Math.abs(norm.getY()) > 0.000001)
		{ //flatten in y direction
		//System.out.println("flattening in y dir");
		a2 = new Coord(a.getX(),a.getZ());
		b2 = new Coord(b.getX(),b.getZ());
		c2 = new Coord(c.getX(),c.getZ());
		d2 = new Coord(d.getX(),d.getZ());
		cInt2 = new Coord(cInt.getX(),cInt.getZ());
		}
	else
		{ //flatten in z direction
		//System.out.println("flattening in z dir");
		if(Math.abs(norm.getZ()) < 0.000001) System.out.println("Error in plane's normal, no nonzero component");
		a2 = new Coord(a.getX(),a.getY());
		b2 = new Coord(b.getX(),b.getY());
		c2 = new Coord(c.getX(),c.getY());
		d2 = new Coord(d.getX(),d.getY());
		cInt2 = new Coord(cInt.getX(),cInt.getY());
		}
	Coord out = new Coord(10000,10005);
	int intersections = 0;
	if(cInt2.intersectSegment(out,a2,b2)) intersections++;
	if(cInt2.intersectSegment(out,b2,c2)) intersections++;
	if(cInt2.intersectSegment(out,c2,d2)) intersections++;
	if(cInt2.intersectSegment(out,d2,a2)) intersections++;
	//System.out.println(intersections);
	//System.out.println(cInt2);
	//System.out.println(a2 + ", " + b2 + ", " + c2 + ", " + d2);
	return intersections%2 == 1;
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
	return "Quadrilateral";
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
	return "Points:\n" + coords[0] + "\n" + coords[1] + "\n" + coords[2] + "\n" + coords[3];
	}
	
	public static void main(String args[])
	{
	Coord3D[] coords = new Coord3D[4];
	coords[0] = new Coord3D(0,0,0);
	coords[1] = new Coord3D(1,0,0);
	coords[2] = new Coord3D(1,0.2,1);
	coords[3] = new Coord3D(0,0.2,1);
	Quad3D qu = new Quad3D(coords);
	
	Vector3D v = new Vector3D(-0.5,-1,0);
	Vector3D v2 = new Vector3D(-0.75,-1,0.6);
	Vector3D v3 = new Vector3D(-1,-1,0.5);
	Coord3D cc = new Coord3D(1,1,0);
	System.out.println(qu.intersectsRay(v,cc));
	System.out.println(qu.intersectsRay(v2,cc));
	System.out.println(qu.intersectsRay(v3,cc));
	Plane p = qu.getPlane();
	
	System.out.println(qu.intersection(v,cc));
	System.out.println(qu.intersection(v2,cc));
	System.out.println(qu.intersection(v3,cc));
		
	coords[0] = new Coord3D(0,0,0);
	coords[1] = new Coord3D(1,0,0);
	coords[2] = new Coord3D(1,1,0);
	coords[3] = new Coord3D(0,1,0);
	qu = new Quad3D(coords);	
	
	v = new Vector3D(-0.5,-0.5,-1);
	cc = new Coord3D(1,1,1);
	System.out.println(qu.intersectsRay(v,cc));
	System.out.println(qu.intersection(v,cc));
	}
}