
import java.util.Vector;

public class Integrator_EulerCC implements Integrator 
{

	Vector<Robot> robots;
	double eps = 1E-9;
	int rSize;
	Vector<Point2d> centers = new Vector<Point2d>();
	Point2d center;
	//Point2d center;
	double radius;
	double minRadius = 10000;
	double maxDist = 0;
	double maxD = 0;
	double vMag = 1;
	int maxDi;
	int outerI;
	int maxDj;
	int bestCenteri;
	Point2d A = new Point2d();
	Point2d B = new Point2d();
	Point2d C = null;
	
	Vector<Integer> outerPoints = new Vector<Integer>();
	Point2d outerPoint = new Point2d();
	Vector<Double> radii = new Vector<Double>();

	public Integrator_EulerCC(Vector<Robot> robots)
	{
	this.robots = robots;
	rSize = robots.size();
	calculateCircumcircle();
	RobotRendezvousCanvas.circumcenter = center;
	RobotRendezvousCanvas.ccRadius = radius;
	setVelocities();
	}
	
	public void setdt(double dt)
	{
	}
	
	public Point2d ccCenter(Robot r, Robot rp, Robot rm)
	{
	return ccCenter(r.x,rp.x,rm.x);
	}
	
	public Point2d ccCenter(Point2d r, Point2d rp, Point2d rm)
	{
	//returns circumcircle's center, or null if it dne
	Coord center;
	Double radius;
	Coord edge2 = new Coord(r.x,r.y);
	Coord edge3 = new Coord(rp.x,rp.y);
	Coord edge1 = new Coord(rm.x,rm.y);
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
			return cent;
			}
		else 
			{
			//System.out.println("collinear points, no circle");
			return null;
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
		return null;
		}
	
	}
	
	public void calculateCircumcircle()
	{
	minRadius = 10000;
	bestCenteri = -1;
	for(int i=0; i<rSize-1; i++)
		{
		for(int j=i; j<rSize; j++)
			{
			double dc = robots.get(i).x.distance(robots.get(j).x);
			if(dc > maxD)
				{
				maxD = dc;
				maxDi = i;
				maxDj = j;
				}
			}
		}
	center = new Point2d();
	center.add(robots.get(maxDi).x);
	center.add(robots.get(maxDj).x);
	center.scale(0.5);
	radius = maxD/2;
	A.set(robots.get(maxDi).x);
	B.set(robots.get(maxDj).x);
	/*
	System.out.println("A = robot " + maxDi);
	System.out.println("B = robot " + maxDj);
	System.out.println("radius " + radius);
	System.out.println("center " + center.x + ", " + center.y);
	*/
	
	boolean allIn = false;
	
	while(!allIn)
	{
	/*
	System.out.println("pt A = ("  + A.x + ", " + A.y+")");
	System.out.println("pt B = ("  + B.x + ", " + B.y+")");
	if(C!=null)System.out.println("pt C = ("  + C.x + ", " + C.y +")");
	System.out.println("center (" + center.x + ", " + center.y+")");
	System.out.println("radius " + radius);
	*/
	allIn = true;
	maxDist = 0;
	outerI = -1;
	//check if it includes all points
	for(int i=0; i<rSize; i++)
		{
		Robot r = robots.get(i);
		double dist = r.x.distance(center);
		if(dist > radius+eps && dist > maxDist) //need to include current point in circumcircle
			{
			outerPoint.set(r.x);
			maxDist = dist;
			allIn = false;
			outerI=i;
			//Point2d cCur = ccCenter(r,robots.get(maxDi),robots.get(maxDj));
			//centers.add(cCur);
			//radii.add(cCur.distance(r.x));
			}
		//System.out.println("Outerpoint = robot " + outerI);
		//System.out.println("Outerpoint coords = " + outerPoint.x + ", " + outerPoint.y);
		}
	
	if(!allIn)
		{
		if(C==null) 
			{
			C = new Point2d();
			C.set(outerPoint);
			}
		else
			{ //throw away point on same halfplane as outerpoint, where half plane line drawn as cont. of diameter of furthest point from outer point
			double ao = outerPoint.distance(A);
			double bo = outerPoint.distance(B);
			double co = outerPoint.distance(C);
		//	System.out.println("ao = " + ao);
		//	System.out.println("bo = " + bo);
		//	System.out.println("co = " + co);
			Coord Ac = new Coord(A.x,A.y);
			Coord cent = new Coord(center.x,center.y);
			Coord Dc = new Coord(outerPoint.x,outerPoint.y);
			Coord Bc = new Coord(B.x,B.y);
			Coord Cc = new Coord(C.x,C.y);
			if(ao > bo && ao > co)
				{
				if(Ac.lineIntersectSegment(cent,Bc,Dc))//then throw away C
					{
					C.set(outerPoint);
					//System.out.println("Throwing away C");
					}	
				else if(Ac.lineIntersectSegment(cent,Cc,Dc))//then throw away B
					{
					B.set(outerPoint);
					//System.out.println("Throwing away B");
					}	
				else System.out.println("Error, B and C on same side of A's diameter");
				}
			else if(bo > co)
				{
				if(Bc.lineIntersectSegment(cent,Ac,Dc))//then throw away C
					{
					C.set(outerPoint);
					}	
				else if(Bc.lineIntersectSegment(cent,Cc,Dc))//then throw away A
					{
					A.set(outerPoint);
					}	
				else System.out.println("Error, A and C on same side of B's diameter");
				}
			else //co greatest
				{
				if(Cc.lineIntersectSegment(cent,Ac,Dc))//then throw away B
					{
					B.set(outerPoint);
					}	
				else if(Cc.lineIntersectSegment(cent,Bc,Dc))//then throw away A
					{
					A.set(outerPoint);
					}	
				else System.out.println("Error, A and B on same side of C's diameter");
				}						
			}
		center = ccCenter(A,B,C);
		radius = A.distance(center);		
		}
	//if(allIn) System.out.println("All in!");
	//System.out.println();
	}
		
		/*
	System.out.println("Points outside: " + centers.size());
	if(centers.size()>0)
		{
		//find which new circle contains all points and has smallest radius
		boolean allIn;
		for(int j=0; j<centers.size(); j++)
			{		
			System.out.println("Outer point number " + j);
			System.out.println("radius " + radii.get(j));
			allIn = true;
			for(int i=0; i<rSize; i++)
				{
				Robot r = robots.get(i);
				System.out.println(r.x.distance(centers.get(j)));
				if(r.x.distance(centers.get(j)) > radii.get(j)+eps) 
					{
					allIn = false;
					break;
					}
				}
			if(allIn && radii.get(j) < minRadius)
				{
				//System.out.println(radii.get(j));
				System.out.println("All in with outer point # " + j);
				System.out.println();
				minRadius = radii.get(j);
				bestCenteri=j;
				}
			}
		if(bestCenteri>-1)
			{
			bestCenter = centers.get(bestCenteri);
			radius = radii.get(bestCenteri);
			RobotRendezvousCanvas.circumcenter2 = null;
			}
		else
			{
			//other permutations of outer and furthest
			bestCenter = centers.get(0);
			radius = radii.get(0);
			if(centers.size()>1)
				{
				RobotRendezvousCanvas.circumcenter2 = centers.get(1);
				RobotRendezvousCanvas.ccRadius2 = radii.get(1);
				}
			
			}
		}
		*/
	}
	
	public void calculateCircumcircleOld()
	{
	
	//initial guess: 3 spread out points.
	//Note: we want SMALLEST circumcircle containing all points
	int g1 = rSize/3;
	int g2 = 2*rSize/3;
	center = ccCenter(robots.get(0),robots.get(g1),robots.get(g2));
	radius = robots.get(0).x.distance(center);
	for(int i=1; i<rSize; i++)
		{
		Robot r = robots.get(i);
		if(r.x.distance(center) > radius) //need to include current point in circumcircle
			{
			boolean allIn = false;
			for(int j=0; j<i-1; j++)
				{
				for(int k=j+1; k<i; k++)
					{
					//if(j==i) j=g1;
					//if(k==i) k=g1;
					//if(k==i+1) k=g2;
					center = ccCenter(r,robots.get(j),robots.get(k));
					radius = r.x.distance(center);
					//check if this center is good
					allIn = true;
					System.out.println("here?");
					for(int l=0; l<i; l++)
						{
						System.out.println("or here?");
						//if(l==i) l=g1;
						//if(l==i+1) l=g2;
						if(robots.get(l).x.distance(center) > radius)
							{
							allIn = false;
							break;
							}
						}
					if(allIn) break;
					}
				if(allIn) break;
				}
			}
		}
	}
	
	
	public void setVelocities()
	{
	for (int i=0; i<rSize; i++)
		{	
		Robot r = robots.get(i);	
		r.v.sub(center,r.x);
		r.v.normalize();
		r.v.scale(vMag);
		}	
	}
	
    /** 
     * Advances one time step
     */

    public void advanceTime(double dt)
	{
	for (int i=0; i<rSize; i++)
		{	
		Robot r = robots.get(i);	
		if(r.x.distance(center) < vMag*dt) r.x = center;
		else r.x.scaleAdd(dt, r.v, r.x);
		r.updatePath(); 		
		}	
	}
}