//
// Class for Resolving collisions in Arkanoid v 2
// (c) Fred Brasz 2009


import javax.vecmath.*;
import java.lang.Math;

public class CollisionProcessor{
	
	static boolean testing = false;
	
	static class BallPaddleCollision
	{	
	Vector2d normal;
	double iMB; //inverse mass of particle 1
	double iMP; //inverse mass of paddle
	Ball b;
	Paddle p;
	double dt;
	double t;
	boolean trueCollision = true;
	//double epsilon = 0.000001;
	//double epsilon = 0;
	//double epsilon = 0.0001;
	
	public BallPaddleCollision(Ball b, Paddle p, double t, double dt)
		{
		this.b = b;
		this.p = p;
		this.t = t; 		if(t <= 0) throw new IllegalArgumentException("Can't have t="+t);
		this.dt = dt;
		
		iMB = 1/b.m;
		iMP = 0; //paddle infinite mass
		normal = Utils.normal(b,p,t); // creates a unit normal vector pointing from b2 to b1 at time t
		
		resolveCollision();		
		}
		
	void resolveCollision()
		{
		/// COMPUTE/APPLY IMPULSE HERE...
		//Find v_rel
		double vnb = -b.v.dot(normal); // positive if toward p
		double vnp = p.v.dot(normal); // positive if toward b
		double vRel = vnb + vnp;
		double vStart = b.v.length();
		
		if(vRel < 0)
			{
			trueCollision = false;
			return;
			}
		
		if(testing) System.out.println("paddle v = " + p.v);		
		if(testing) System.out.println("vnb = " + vnb);		
		if(testing) System.out.println("vnp = " + vnp);		
		if(testing) System.out.println("vRel = " + vRel);		
		
		double j;
		
		j = -(1 + Constants.CORDefault)*vRel/(iMB + iMP);
		//apply impulse 
		double dv1 = -j*iMB;
		double dv2 = j*iMP; // = 0
		if(testing) System.out.println("dv1 " + dv1);
		if(testing) System.out.println("dv2 " + dv2);
		if(testing) System.out.println("normal " + normal);
		
		adjustForSpin(b, vRel);
		
		Utils.acc(b.v,dv1,normal);
		//Utils.acc(p.v,dv2,normal);		Always 0
		
		double vFin = b.v.length();
		b.v.scale(vStart/vFin);
		System.out.println("scale ratio linear: " + vStart/vFin);
		
		
		b.speedUp(Constants.speedUpRate);
		}
		
		void adjustForSpin(Ball b, double vRel)
		{		
		// updates speed parallel to bouncing surface and rotational velocity after a collision to take spin into account
		// if slip time is shorter than time ball in contact with paddle, it reaches rolling without slipping motion - depends on vN and w0
		
		double dSpeed;
		double dOmega;
		double maxDSpeed;
		double maxDOmega;
		double vStart = b.v.length();
		int vToOmegaSign = -1;	
		int aSign = -1;	// if net speed is to right	
		int alphaSign = -1;	// if net speed is to right	
		
		Vector2d tang = Utils.rotateCW(normal); //CW means pointing RIGHT of normal
		if(testing) System.out.println("tang " + tang);
		double vTanB = b.v.dot(tang);
		double vTanP = p.v.dot(tang);
		double vTan = vTanB - vTanP;
		//net velocity of ball surface relative to paddle surface in direction of tang vector
		double vNet = vTan + p.radius*b.omega;
		if(vNet < 0) //then net speed of ball opposite dir of tangent vector at surface (ie left), so friction points right
			{
			aSign = +1;			
			alphaSign = +1;			
			}
		dSpeed = aSign*(vToOmegaSign*Ball.radius*b.omega - vTan)/(aSign - vToOmegaSign*alphaSign/Constants.cI); // - vToOmegaSign because its (-+) not (+-)
		dOmega = alphaSign*(1/(Constants.cI*Ball.radius))*(vToOmegaSign*Ball.radius*b.omega - vTan)/(aSign - vToOmegaSign*alphaSign/Constants.cI);
				
		maxDSpeed = (1+Constants.CORDefault)*Math.abs(vRel)*Constants.defaultMuP;
		maxDOmega = (1+Constants.CORDefault)*Math.abs(vRel)*Constants.defaultMuP/(Constants.cI*Ball.radius);
		if(Math.abs(dSpeed) > maxDSpeed)
			{
			dSpeed = maxDSpeed*dSpeed/Math.abs(dSpeed);
			dOmega = maxDOmega*dOmega/Math.abs(dOmega);
			}	
			
		b.v.x += dSpeed*tang.x;
		b.v.y += dSpeed*tang.y;
		b.omega += dOmega;
		
		double vFin = b.v.length();
		b.v.scale(vStart/vFin);
		System.out.println("scale ratio: " + vStart/vFin);
		}
		
	}
	
	
	static class BallBallCollision
	{	
	Vector2d normal;
	double iM1; //inverse mass of particle 1
	double iM2; //inverse mass of particle 2
	Ball b1,b2;
	double dt;
	double t;
	boolean trueCollision = true;
	//double epsilon = 0.000001;
	//double epsilon = 0;
	//double epsilon = 0.0001;
	
	public BallBallCollision(Ball b1, Ball b2, double t, double dt)
		{
		this.b1 = b1;
		this.b2 = b2;
		this.t = t; 		if(t <= 0) throw new IllegalArgumentException("Can't have t="+t);
		this.dt = dt;
		
		iM1 = 1/b1.m;
		iM2 = 1/b2.m;
		normal = Utils.normal(b1,b2,t); // creates a unit normal vector pointing from b2 to b1 at time t
		
		resolveCollision();		
		}
		
	void resolveCollision()
		{
		/// COMPUTE/APPLY IMPULSE HERE...
		//Find v_rel
		double vn1 = -b1.v.dot(normal); // positive if toward b2
		double vn2 = b2.v.dot(normal); // positive if toward b1
		double vRel = vn1 + vn2;
		
		if(vRel < 0)
			{
			trueCollision = false;
			return;
			}
		
		if(testing) System.out.println("vRel = " + vRel);		
		
		double j;
		
		j = -(1 + Constants.CORDefault)*vRel/(iM1 + iM2);
		//apply impulse 
		double dv1 = -j*iM1;
		double dv2 = j*iM2;
		if(testing) System.out.println("dv1 " + dv1);
		if(testing) System.out.println("dv2 " + dv2);
		if(testing) System.out.println("normal " + normal);
		Utils.acc(b1.v,dv1,normal);
		Utils.acc(b2.v,dv2,normal);		
		}
	}
	
	public static void collideBallBlock(Ball bCur, Block bl, double dt)
	{
	// We know these collide, so take appropriate action to bounce here.
	// for now, place ball eps away from block it just hit
	//Test for collisions between ball and all relevant edges/corners of block
	Point2d[] corners = bl.accessibleCorners(bCur);
	int nC = corners.length;
	
	EdgeO[] edges = bl.accessibleEdges(bCur);
	double tMin = 20;
	int nE = edges.length;
	
	int closestEdge = -1;
	int closestCorner = -1;
	int edgeSide = -1;
	
	double tCur = -1;
	// find edge that is hit first (if an edge is hit)
	for(int i=0; i<nE; i++)
		{
		tCur = testBallEdgeCollision(bCur, edges[i], dt);
		if(tCur > 0 && tCur < tMin)
			{
			tMin = tCur;
			closestEdge = i;
			edgeSide = edges[i].edgeSide(bCur);
			}
		}
		
	// find corner that is hit first (if a corner is hit)
	for(int i=0; i<nC; i++)
		{
		tCur = testBallPointCollision(bCur, corners[i], dt);
		if(tCur > 0 && tCur < tMin)
			{
			tMin = tCur;
			closestCorner = i;
			closestEdge = -1;
			edgeSide=-1;
			}
		}
		
	bCur.advance(tMin);
	if(edgeSide>0) bCur.adjustForSpin(edgeSide,bCur.muB);
	if(edgeSide == Constants.LEFT || edgeSide == Constants.RIGHT) bCur.v.x = -bCur.v.x;
	else if(edgeSide == Constants.TOP || edgeSide == Constants.BOTTOM) bCur.v.y = -bCur.v.y;
	else
		{
		// corner hit
		Vector2d vNorm = new Vector2d();
		vNorm.sub(bCur.x,corners[closestCorner]);
		Utils.reflect(bCur.v,vNorm); //as if ball bounces off a flat surface oriented at vNorm
		}
	bCur.advance(Constants.eps);
		
	}

	public static double testBallBlockCollision(Ball bCur, Block bl, double dt)
	{
	//Test for collisions between ball and all relevant edges/corners of block
	Point2d[] corners = bl.accessibleCorners(bCur);
	int nC = corners.length;
	
	EdgeO[] edges = bl.accessibleEdges(bCur);
	double tMin = 20;
	int nE = edges.length;
	
	int closestEdge = -1;
	int closestCorner = -1;
	
	double tCur = -1;
	// find edge that is hit first (if an edge is hit)
	for(int i=0; i<nE; i++)
		{
		tCur = testBallEdgeCollision(bCur, edges[i], dt);
		if(tCur > 0 && tCur < tMin)
			{
			tMin = tCur;
			closestEdge = i;
			}
		}
		
	// find corner that is hit first (if a corner is hit)
	for(int i=0; i<nC; i++)
		{
		tCur = testBallPointCollision(bCur, corners[i], dt);
		if(tCur > 0 && tCur < tMin)
			{
			tMin = tCur;
			closestCorner = i;
			closestEdge = -1;
			}
		}
		
	return tMin; //will be 20 if no collision
	}
	
	public static double testBallEdgeCollision(Ball b, EdgeO e, double dt)
	{
	//returns -1 if no intersection
	double result = -1;
	Point2d bxStart = new Point2d(b.x);
	if(e.vert)
		{
		//vertical edge
		double xStart = b.x.x;
		double xFin = b.x.x + b.v.x*dt;
		double ds = b.radius;
		if(xStart < e.s) ds = -ds; //account for which side it's on
			// on left side of edge: for an edge collision, check ball position at distance (b_radius) to left of edge
		if(!Utils.withinRange((double)e.s + ds,xStart,xFin)) return -1; //no collision
		else
			{ //could have collision				
			result = b.advanceToX(e.s + ds); //returns time to get there
			if(!Utils.withinRange(b.x.y,e.min,e.max)) result = -1;
			b.x = bxStart;
			return result;							
			}
		}
	else
		{
		//horizontal edge
		double yStart = b.x.y;
		double yFin = b.x.y + b.v.y*dt;
		double ds = b.radius;
		if(yStart < e.s) ds = -ds; //account for which side it's on
			// above edge: for an edge collision, check ball position at distance (b_radius) to above edge
		if(!Utils.withinRange((double)e.s + ds,yStart,yFin)) return -1; //no collision
		else
			{ //could have collision				
			result = b.advanceToY(e.s + ds); //returns time to get there
			if(!Utils.withinRange(b.x.x,e.min,e.max)) result = -1;
			b.x = bxStart;			
			return result;							
			}
		}
	}
	
	public static double testBallPointCollision(Ball b, Point2d p, double dt)
	{
	//return time at which ball b is b.radius away from p
	Vector2d d = new Vector2d();
	d.sub(p,b.x); // b to p
	if(d.dot(b.v) < 0) return -1; //ball moving away from point
	double a0 = b.v.lengthSquared();
	double b0 = -2*(d.x*b.v.x + d.y*b.v.y);
	double c0 = d.lengthSquared() - b.radius*b.radius;
	double tSolve = Utils.root(a0,b0,c0);
	if(tSolve > dt) tSolve = -1;
	return tSolve;
	}	
	
	public static boolean testBallBallCollision(Ball b1, Ball b2, double dt)
	{
	if(b1 == b2) return false;
	
	double dx = b2.x.x - b1.x.x;
	double dy = b2.x.y - b1.x.y;
	double dvx = b2.v.x - b1.v.x;
	double dvy = b2.v.y - b1.v.y;
	double rSum = b1.radius+b2.radius;
	
	//quadratic equation in tSolve: a*t^2 + b*t + c = 0, where a,b,c = ...
	double a = dvx*dvx + dvy*dvy;
	double b = 2*(dx*dvx + dy*dvy);
	double c = dx*dx + dy*dy - rSum*rSum;
	
	// Get minimum positive root
	double tSolve = Utils.root(a,b,c);
	
	if(tSolve <= 0 || tSolve > dt) return false; // doesn't collide in this time step
	else 
		{			
		//if(testing) System.out.println("Colliding at collision Time " + tSolve + ", dt " + dt);
		BallBallCollision bbc = new BallBallCollision(b1,b2,tSolve,dt);
		return bbc.trueCollision;
		}
	}	
	
	public static boolean testBallPaddleCollision(Ball b1, Paddle b2, double dt)
	{
	
	double dx = b2.x.x - b1.x.x;
	double dy = b2.x.y - b1.x.y;
	double dvx = b2.v.x - b1.v.x;
	double dvy = b2.v.y - b1.v.y;
	double rSum = b1.radius+b2.radius;
	
	//quadratic equation in tSolve: a*t^2 + b*t + c = 0, where a,b,c = ...
	double a = dvx*dvx + dvy*dvy;
	double b = 2*(dx*dvx + dy*dvy);
	double c = dx*dx + dy*dy - rSum*rSum;
	
	// Get minimum positive root
	double tSolve = Utils.root(a,b,c);
	
	if(tSolve <= 0 || tSolve > dt) return false; // doesn't collide in this time step
	else 
		{			
		//if(testing) System.out.println("Colliding at collision Time " + tSolve + ", dt " + dt);
		BallPaddleCollision bbc = new BallPaddleCollision(b1,b2,tSolve,dt);
		return bbc.trueCollision;
		}
	}

}