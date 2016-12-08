import java.lang.Math;

public class Plane {

	// of Form a(x-xA) + b(y-yA) + c(z-zA) = 0
    private double xA;
    private double yA;
	private double zA;
    private double a;
    private double b;
	private double c;
	private Vector3D n;
	private Coord3D cA;

    public Plane(double xA, double yA, double zA, double a, double b, double c)
    {
	this.xA = xA;
	this.yA = yA;
	this.zA = zA;
	this.a = a;
	this.b = b;
	this.c = c;
	n = new Vector3D(a,b,c);
	n = n.unit();
	cA = new Coord3D(xA,yA,zA);
    }
	
	public Plane(Vector3D n, Coord3D cA)
    {
	// Constructs a vector from point 1 to point 2
	n = n.unit();
	a = n.getX();
	b = n.getY();
	c = n.getZ();
	this.xA = cA.getX();
	this.yA = cA.getY();
	this.zA = cA.getZ();
	this.n = n;
	this.cA = cA;
    }
	
	public boolean intersects(Vector3D v, Coord3D cc)
	{
	// post: plane intersects ray starting at cc in direction v
	if(Math.abs(n.dot(v)) > 0.000001 || contains(cc))
		{
		// need to check if ray is going the right direction
		double dx = cc.getX()-xA;
		double dy = cc.getY()-yA;
		double dz = cc.getZ()-zA;
		double t = -(a*dx+b*dy+c*dz)/(a*v.getX()+b*v.getY()+c*v.getZ());
		return t>0;
		}
	else return false;
	}
	
	public boolean contains(Coord3D cc)
	{
	return Math.abs(a*(cc.getX()-xA) + b*(cc.getY()-yA) + c*(cc.getZ()-zA)) < 0.000001;
	}
	
	public Coord3D intersection(Vector3D v, Coord3D cc)
	{
	if(!intersects(v,cc)) 
		{
		System.out.println("Error, doesn't intersect");
		return null;
		}
	else if(contains(cc))
		{
		//System.out.println("Whole line intersects");
		return cc;
		}
	else
		{
		double dx = cc.getX()-xA;
		double dy = cc.getY()-yA;
		double dz = cc.getZ()-zA;
		double t = -(a*dx+b*dy+c*dz)/(a*v.getX()+b*v.getY()+c*v.getZ());
		return v.parametric(cc,t);
		}
	}

	public double xOf(double y, double z)
	{
	if(a != 0) return (-b*(y-yA)-c*(z-zA))/a + xA;
	else
		{
		System.out.println("Can't calculate x value");
		return -1234567;
		}
	}

	public double yOf(double z, double x)
	{
	if(b != 0) return (-c*(z-zA)-a*(x-xA))/b + yA;
	else
		{
		System.out.println("Can't calculate y value");
		return -1234567;
		}
	}

	public double zOf(double x, double y)
	{
	if(c != 0) return (-a*(x-xA)-b*(y-yA))/c + zA;
	else
		{
		System.out.println("Can't calculate z value");
		return -1234567;
		}
	}
	
    public double getXA()
    {
	return xA;
    }

    public double getYA()
    {
	return yA;
    }
	
    public double getZA()
    {
	return zA;
    }
	
    public Vector3D getN()
    {
	return n;
    }
	
    public Coord3D getC()
    {
	return cA;
	}
	
	public String toString()
	{
	return "Plane centered at (" + xA + ", " + yA + ", " + zA + ") with normal vector (" + a + ", " + b + ", " + c + ")";
	}
	
	public static void main(String args[])
	{
	Vector3D n = new Vector3D(0,1,1);
	Coord3D cA = new Coord3D(0,1,0);
	Plane p = new Plane(n,cA);
	Vector3D v = new Vector3D(1.2,0.3,0.1);
	Coord3D cc = new Coord3D(0,0,0);
	System.out.println(p.intersection(v,cc));
	}
}