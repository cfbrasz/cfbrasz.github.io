
import java.util.Vector;

public class Integrator_EulerMM implements Integrator 
{

	Vector<Robot> robots;
	Vector<Robot> dummyRobots;
	boolean useDummy;
	Vector<Point2d> centers;
	Vector<Double> radii;
	
	double minD = 4; //when someone has neighbors one pixel apart, they become dummies and use interpolation

	public Integrator_EulerMM(Vector<Robot> robots, Vector<Robot> dummyRobots,boolean useDummy,Vector<Point2d> centers, Vector<Double> radii)
	{
	this.robots = robots;
	this.dummyRobots = dummyRobots;	
	this.useDummy = useDummy;	
	this.centers = centers;
	this.radii = radii;
	}
	
	public void setdt(double dt)
	{
	}
	
    /** 
     * Advances one time step
     */
	 
	public void calculateVelocity(Robot r, Robot rp, Robot rm)
	{
	
	//find circumcircle's center and radius
	Coord center;
	Double radius;
	Coord edge2 = new Coord(r.x.x,r.x.y);
	Coord edge3 = new Coord(rp.x.x,rp.x.y);
	Coord edge1 = new Coord(rm.x.x,rm.x.y);
	Vector2Do m12 = new Vector2Do(edge1,edge2);
	Vector2Do m23 = new Vector2Do(edge2,edge3);
	Double ma = edge1.mOf(edge2);
	Double mb = edge2.mOf(edge3);
	if(m12.angle() != m23.angle())
		{
		Coord ap = edge1.midpoint(edge2);
		Coord bp = edge2.midpoint(edge3);
		Coord ap2 = ap.copy();
		Coord bp2 = bp.copy();
		if(!edge1.isVertical(edge2) && ma != 0 && !ma.isNaN())
			{
			ap2.moveX(1);
			ap2.moveY(-1/ma);
			//System.out.println("here ma = " + ma);
			//System.out.println(edge1.x);
			//System.out.println(edge2.x);
			//System.out.println(edge1.x == edge2.x);
			}
		else if(edge1.isVertical(edge2) || ma.isNaN())
			{
			ap2.moveX(1);			
			}
		else if(ma == 0)
			{
			ap2.moveY(1);
			}
		if(!edge2.isVertical(edge3)&& mb != 0 && !mb.isNaN())
			{
			bp2.moveX(1);
			bp2.moveY(-1/mb);	
			//System.out.println("here mb = " + mb);
			}
		else if(edge2.isVertical(edge3) || mb.isNaN())
			{
			bp2.moveX(1);			
			}
		else if(mb == 0)
			{
			bp2.moveY(1);
			}
		center = ap.intersection(ap2,bp,bp2);
		
		radius = center.dist(edge2);
		
		if(radius>10000)
			{
			//System.out.println(radius);
			}
		else 
			{
			//System.out.println(radius);
			//System.out.println(center);
			}
		
		if(!radius.isNaN() && !radius.isInfinite())
			{
			Point2d cent = new Point2d(center.x,center.y);
			centers.add(cent);
			radii.add(radius);
			r.v.sub(cent,r.x);
			r.v.scale(10/(radius*radius));
			}
		else 
			{
			//System.out.println("collinear points, no circle");
			r.v.x = 0;
			r.v.y = 0;
			}
		
		/*
		System.out.println(ma);
		System.out.println(mb);
		System.out.println();
		System.out.println(m12.angle());
		System.out.println(m23.angle());
		System.out.println();
		*/
		//System.out.println(r.v.x);
		//System.out.println(r.v.y);
		}
	else 
		{
		//System.out.println("collinear points, no circle");
		r.v.x = 0;
		r.v.y = 0;
		}
	
	}

    public void advanceTime(double dt)
	{
	int rSize = robots.size();	
	centers.removeAllElements();
	radii.removeAllElements();
	if(useDummy)
		{
		for (int i=0; i<robots.size(); i++)
			{	
			rSize = robots.size();	
			Robot r = robots.get(i);	
			Robot rm = robots.get((i+rSize-1)%rSize);
			Robot rp = robots.get((i+1)%rSize);
			if(r.x.distance(rp.x) < minD && r.x.distance(rm.x) < minD) //make r a dummy
				{
				//r.dummy = true;
				robots.removeElementAt(i);
				i--;
				r.dummy = true;
				dummyRobots.add(r);
				r.rp = rp;
				r.rm = rm;
				}
			}
		}
	for (int i=0; i<robots.size(); i++)
		{	
		rSize = robots.size();	
		Robot r = robots.get(i);	
		Robot rm = robots.get((i+rSize-1)%rSize);
		Robot rp = robots.get((i+1)%rSize);
		r.x.scaleAdd(dt, r.v, r.x);
		
		calculateVelocity(r,rp,rm);		
		r.updatePath(); 
			
		}	
	if(useDummy)
		{
		for (int i=0; i<dummyRobots.size(); i++)
			{	
			Robot r = dummyRobots.get(i);		
			r.x.add(r.rp.x,r.rm.x);
			r.x.scale(0.5);
			//calculateVelocity(robots.get(i),robots.get((i+rSize-1)%rSize),robots.get((i+1)%rSize));			
			}	
		}
	}
}