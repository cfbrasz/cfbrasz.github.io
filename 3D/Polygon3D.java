import java.lang.Math;
import java.awt.*;

public class Polygon3D implements Shape3D {

    private Coord3D[] coords;
	private int npoints;
	private Vector3D n;
	private Plane p;
	private Color col;
	private double reflectivity;
	private double maxV = 100001;

    public Polygon3D(Coord3D[] coords)
    {
	this(coords,Color.RED,0);
	}

    public Polygon3D(Coord3D[] coords, Color col, double reflectivity)
    {
	// assume good coords, all on same plane
	this.coords = coords;
	npoints = coords.length;
	for(int i=0; i<npoints; i++)
		{
		if(Math.abs(coords[i].getX()) > maxV) maxV = Math.abs(coords[i].getX());
		if(Math.abs(coords[i].getY()) > maxV) maxV = Math.abs(coords[i].getY());
		}
	Coord3D a = coords[0];
	Coord3D b = coords[1];
	Coord3D c = coords[2];
	Vector3D ab = new Vector3D(a,b);
	Vector3D ac = new Vector3D(a,c);
	n = ab.cross(ac).unit();
	p = new Plane(n,a);
	this.reflectivity = reflectivity;
	this.col = col;
    }
	
	public Color getColor()
	{
	return col;
	}
	
	public double reflectivity()
	{
	return reflectivity;
	}
	
	public boolean validPolygon()
	{
	//checks that all points are on same plane and no crossings (not actually no crossings yet)
	if(npoints <= 3) return true;
	Coord3D a = coords[0];
	Coord3D b = coords[1];
	Vector3D vPrev = new Vector3D(a,b);
	Vector3D nPrev = n;
	for(int i=3; i<npoints; i++)
		{
		Vector3D vCur = new Vector3D(a,coords[i]);
		Vector3D nCur = vPrev.cross(vCur).unit();
		if(!nCur.equals(Vector3D.ZEROVECTOR) && !nCur.equals(nPrev)) return false;
		nPrev = nCur;
		vPrev = vCur;
		}
	//if you made it here, all are in same plane
	return true;
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
	if(vertex < npoints && vertex >= 0) return coords[vertex];
	System.out.println("Error: polygon does not contain index " + vertex);
	return null;
	}
	
	public boolean intersectsRay(Vector3D v, Coord3D cc)
	{
	if(!p.intersects(v,cc)) return false;
	// else it intersects the plane somewhere. See if this is within the polygon
	Coord3D cInt = p.intersection(v,cc);
	Vector3D norm = p.getN();
	Coord[] coords2D = new Coord[npoints];
	Coord cInt2;
	if(Math.abs(norm.getX()) > 0.000001)
		{ //flatten in x direction
		//System.out.println("flattening in x dir");
		for(int i=0; i<npoints; i++) coords2D[i] = new Coord(coords[i].getY(),coords[i].getZ());
		cInt2 = new Coord(cInt.getY(),cInt.getZ());
		}
	else if(Math.abs(norm.getY()) > 0.000001)
		{ //flatten in y direction
		//System.out.println("flattening in y dir");
		for(int i=0; i<npoints; i++) coords2D[i] = new Coord(coords[i].getX(),coords[i].getZ());
		cInt2 = new Coord(cInt.getX(),cInt.getZ());
		}
	else
		{ //flatten in z direction
		//System.out.println("flattening in z dir");
		if(Math.abs(norm.getZ()) < 0.000001) System.out.println("Error in plane's normal, no nonzero component");
		for(int i=0; i<npoints; i++) coords2D[i] = new Coord(coords[i].getX(),coords[i].getY());
		cInt2 = new Coord(cInt.getX(),cInt.getY());
		}
	Coord out = new Coord(Math.abs(10*maxV),Math.abs(10*maxV)+5);
	int intersections = 0;
	for(int i=0; i<npoints; i++)
		{
		if(cInt2.intersectSegment(out,coords2D[i],coords2D[(i+1)%npoints])) 
			{
			intersections++;
			//System.out.println("Int at ");
			//System.out.println(cInt2.intersection(out,coords2D[i],coords2D[(i+1)%npoints]));
			}
		}
	//System.out.println(intersections);
	//System.out.println(out);
	//System.out.println(cInt2);
	//System.out.println(coords2D[0] + ", " +coords2D[1] + ", " + coords2D[2]);
	return intersections%2 == 1;
	}
	
	public Coord3D intersection(Vector3D v, Coord3D cc)
	{
	if(!p.intersects(v,cc)) return null;
	//use intersectsRay first to make sure it intersects
	if(!intersectsRay(v,cc)) return null;
	// if we made it this far, we have an intersection
	Coord3D cInt = p.intersection(v,cc);
	//System.out.println("intersection: " + cInt);
	return cInt;
	}	
	
	public Vector3D unitNormal(Coord3D intersectionPoint)
	{
	return unitNormal();
	}
	
	public Vector3D unitNormal()
	{
	return getPlane().getN().unit();
	}
	
	public String type()
	{
	return npoints + "-sided polygon";
	}
	
	public String toString()
	{
	String result = "Points:";
	for(int i=0; i<npoints; i++) result = result + "\ni: " + coords[i];
	return result;
	}
	
	public static void main(String args[])
	{
	Coord3D[] coords = new Coord3D[5];
	coords[0] = new Coord3D(0,0,0);
	coords[1] = new Coord3D(1,0,0);
	coords[2] = new Coord3D(0.1,0.1,0.5);
	coords[3] = new Coord3D(1,0.2,1);
	coords[4] = new Coord3D(0,0.2,1);
	Polygon3D qu = new Polygon3D(coords);
	
	System.out.println("Same plane: " + qu.validPolygon());
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
		
	coords = new Coord3D[3];
	coords[0] = new Coord3D(0,0,5);
	coords[1] = new Coord3D(-5,0,0);
	coords[2] = new Coord3D(0,-5,0);
	qu = new Polygon3D(coords);
	
	v = new Vector3D(0,0,-1);
	cc = new Coord3D(-10,-5,0);
	System.out.println(qu.intersectsRay(v,cc));
	System.out.println(qu.intersection(v,cc));
	}
}