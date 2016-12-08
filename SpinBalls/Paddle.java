//
// Class for the paddle in Arkanoid
// (c) Fred Brasz 2008

import java.awt.event.*;
import java.awt.*;
import java.lang.Math;

public class Paddle {

    private int x;
    private int y;
	private double prevX;
	private double prevY;
    private double xD;
    private double yD;
    private double xDS; //save
    private double yDS; //save
    private int width;
    private int height;
    private double theta;
    private double dTheta = 0.06;
    private int boardWidth;
    private int boardHeight;
    private int laserWidth = 6;
    private int laserThickness = 2;
    private int laserHeight = 35;
    private double paddleSpeed = 8.4; //for multiplier of 12
	private double paddleSlowFactor;
	private Coord[] corners;
	private Coord[] cornersS = new Coord[4]; //save
	private Coord[] cornersN; // stay in original position
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingDown = false;
    private boolean movingUp = false;
    

    public Paddle(int x, int y, int width, int height, double theta, double paddleSpeed, int boardWidth, int boardHeight, double paddleSlowFactor)
    {
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
	this.theta = theta;
	this.paddleSpeed = paddleSpeed;
	this.boardWidth = boardWidth;
	this.boardHeight = boardHeight;
	this.paddleSlowFactor = paddleSlowFactor;
	corners = new Coord[4];
	corners[0] = new Coord(-width/2,-height/2);
	corners[1] = new Coord(width/2,-height/2);
	corners[2] = new Coord(width/2,height/2);
	corners[3] = new Coord(-width/2,height/2);
	
	cornersN = new Coord[4];
	cornersN[0] = new Coord(-width/2,-height/2);
	cornersN[1] = new Coord(width/2,-height/2);
	cornersN[2] = new Coord(width/2,height/2);
	cornersN[3] = new Coord(-width/2,height/2);
	for(int i=0; i<4; i++)
		{
		corners[i].rotate(theta);		
		corners[i].translate(x,y);		
		}	
	xD = x;
	yD = y;
	prevX = xD;
	prevY = yD;
    }

    public Coord getCoord()
    {
	return new Coord(x, y);
    }

    public int getY()
    {
	return y;
    }

    public int getX()
    {
	return x;
    }

/*
    public double hitAngle(SpinBall b)
    {
	//Post: returns an angle in radians between 0 and Pi corresponding to
	// where the ball hit the paddle if the paddle is hit, and -1 otherwise.
	// 0 corresponds to the right edge, Pi to the left edge.
	Coord pCoord = this.getCoord();
	if (pCoord.dist(b.getCoord()) < radius + b.getRadius())
	    {		
		return pCoord.angle(b.getCoord());
	    } else if (pCoord.dist(b.getCoord()) > radius + b.getRadius() + 10) return -2;
	else return -1;
    }	    
	*/

    public void setX(int x)
    {
	this.x = x;
    }
	
	public void savePosition()
	{
	xDS = xD;
	yDS = yD;
	for(int i=0; i<4; i++)
		{	
		cornersS[i] = corners[i].copy();	
		}	
	}
	
	public void loadPosition()
	{
	xD = xDS;
	yD = yDS;
	for(int i=0; i<4; i++)
		{	
		corners[i] = cornersS[i].copy();	
		}		
	}
	
	public double collisionPoint(SpinBall that)
	{
	// post: returns the coordinate at which the first point of contact occured between ball and paddle
	// actually, returns the fraction of a time step left after the collision, so the ball can start moving
	// moves paddle and ball to where they were just before colliding
		
	int jPar=0;
	double var[] = new double[4];
	double finalVal[] = new double[4];
	double inc[] = new double[4];
	double diff[] = new double[4];
	double parameter;
	double dx = xD-prevX;
	double dy = yD-prevY;
	//Coord result = new Coord(0,0);
	Coord[] cornerSave = new Coord[4];
	double result = 0;
	var[0] = prevX;
	var[1] = prevY;
	var[2] = that.getPrevX();
	var[3] = that.getPrevY();
	finalVal[0] = xD;
	finalVal[1] = yD;
	finalVal[2] = that.getX();
	finalVal[3] = that.getY();
	
	/*
	System.out.println("ball starts at " + var[2] + ", " + var[3]);
	System.out.println("ball is supposed to go to " + finalVal[2] + ", " + finalVal[3]);
	System.out.println("paddle upper corners at " + corners[0] + " and " + corners[1]);
	System.out.println("dx, dy of paddle: " + dx + " " + dy);
	*/
	
	Coord thisPrevCoord = new Coord(var[0],var[1]);
	Coord thatPrevCoord = new Coord(var[2],var[3]);
	
	//move paddle to prev position
	for(int i=0; i<4; i++)
		{	
		cornerSave[i] = corners[i].copy();
		corners[i].translate(-dx,-dy);		
		}	
	xD = prevX;
	yD = prevY;
	
	//move ball to prev position
	that.setPosition(var[2],var[3]);
	
	//System.out.println(thisPrevCoord.intersect(thatPrevCoord,radius));
	if (this.justHitSide(that) > -1)
		{
		System.out.println("paddle already colliding");
		//alreadyCollided = true;
		//that.alreadyCollided = true;
		// one thought: only consider collisions in which they weren't colliding at beginning of step
		// then have to push them out immediately (in direction of intersection)
		//pushOut(that,balls);
		/*
		for(int i=0; i<4; i++)
			{	
			corners[i] = cornerSave[i];	
			}	
		xD = finalVal[0];
		yD = finalVal[1];
		*/
		return result;
		}
	else
	{
	
	parameter = 0;
	for (int i = 0; i<4; i++) 
		{
		diff[i] = finalVal[i] - var[i];
		parameter = Math.max(parameter,Math.abs(diff[i]));
		}
		
	for (int i = 0; i<4; i++) 
		{
		if (parameter == Math.abs(diff[i])) jPar=i;
		}
		
	int iterations = (int)Math.abs(diff[jPar])*2 + 1;
	
	// Shouldn't these be abs. value?
	/*
	parameter = Math.max(Math.abs(speedX),Math.abs(speedY));
	parameter = Math.max(parameter,Math.abs(that.getSpeedX()));
	parameter = Math.max(parameter,Math.abs(that.getSpeedY()));
	if (parameter == speedX) jPar=0;
	if (parameter == speedY) jPar=1;
	if (parameter == that.getSpeedX()) jPar=2;
	if (parameter == that.getSpeedY()) jPar=3;
	*/
	
	//int iterations = (int)Math.abs(diff[jPar]) + 1;
	if (iterations < 1) iterations = 1;
	//System.out.println(iterations);
	for (int i = 0; i<4; i++) inc[i] = diff[i]/iterations;
	
	Coord thisCoord = new Coord(prevX, prevY);
	Coord thatCoord = new Coord(that.getPrevX(), that.getPrevY());
	
	for (int i = 0; i < iterations; i++)
	{
	for(int j=0; j<4; j++)
		{	
		corners[j].translate(inc[0],inc[1]);		
		}	
	xD += inc[0];
	yD += inc[1];
	
	that.moveX(inc[2]);
	that.moveY(inc[3]);
	
	// Also, let's set the balls to be where they were just before they collided		
	if (this.justHitSide(that) > -1)
		{
		//System.out.println("ball finishes at " + that.getCoordActual());
		
		//System.out.println(thisCoord.dist(thatCoord));
		//Moves to just BEFORE colliding
		/*
		for(int j=0; j<4; j++)
			{	
			corners[j].translate(-inc[0],-inc[1]);		
			}	
		xD -= inc[0];
		yD -= inc[1];
		
		that.moveX(-inc[2]);
		that.moveY(-inc[3]);		
		*/
		
		//reset paddle to where it was going *** do this separately now so it doesn't mess with hitSide
		/*
		for(int j=0; j<4; j++)
			{	
			corners[j] = cornerSave[j];	
			}	
		xD = finalVal[0];
		yD = finalVal[1];
		*/
		
		//result.flip(boardHeight); // Makes it have same convention as speed coords, with y = 0 bottom of screen
		return 1.0-(double)i/iterations;
		}
	}
	System.out.println("Error, didn't find collision point");
	}
	//System.out.println("returning null");
	return -1;
	}
	
	public int hitSide(SpinBall b)
	{
	return hitSide(b,-1);
	}
	
	public int hitSide(SpinBall b, int hs)
	{
	// post: -1 if it doesn't hit, 0 if hits 0-1 edge, 1 if 1-2 edge, 2 if 2-3 edge, 3 if 3-4 edge, or 4 if a corner
	// hs is the hitside, used for calculating velocity changes.
	// rotates to frame where paddle is at origin, bounces, rotates back
	int hitSide = -1;
	int r = b.getRadius();
	Coord c = b.getCoordActual().copy();
	//System.out.println("center x,y: " + (int)c.getX() + ", " + (int)c.getY());
	c.translate(-xD,-yD);
	double vx = b.getSpeedX();
	double vy = b.getSpeedY();
	//System.out.println("velocity x,y: " + vx + ", " + vy);
	Coord cV = c.copy();
	cV.translate(vx,vy);
	
	//account for paddle's motion
	Vector2D paddleV = new Vector2D(0,0);
	if(movingUp) paddleV.moveY(-paddleSpeed);
	if(movingDown) paddleV.moveY(paddleSpeed);
	if(movingLeft) paddleV.moveX(-paddleSpeed);
	if(movingRight) paddleV.moveX(paddleSpeed);
	
	// paddle's orientation
	Coord cB = b.getCoordActual();
	Vector2D pNorm;
	if(hs < 4)
		{
		Vector2D surface = new Vector2D(corners[hs],corners[(hs+1)%4]);
		pNorm = surface.perpendicular().unit();		
		}
	else // corner
		{
		int cornerIndex = hs%4;
		pNorm = (new Vector2D(corners[cornerIndex],cB)).unit();
		}
	
	// only normal velocity of paddle adds to speed (-1 bc going to frame where norm vel = 0)
	double pNormVel = paddleV.dot(pNorm);
	pNorm.scalarMult(pNormVel);
	cV = pNorm.parametric(cV,-1);
	
	//Now for spin: need tangent velocity
	
	
	//System.out.println("ball's velocity: " + vx + " " + vy);
	//System.out.println("paddle's normal velocity vector: " + pNorm);
	
	/*
	if(movingUp) cV.translate(0,paddleSpeed);
	if(movingDown) cV.translate(0,-paddleSpeed);
	if(movingLeft) cV.translate(paddleSpeed,0);
	if(movingRight) cV.translate(-paddleSpeed,0);
	*/
	
	//System.out.println("cV x,y: " + cV);
	c.rotate(-theta);	
	int cx = (int)c.getX();
	int cy = (int)c.getY();
	//System.out.println("transformed center x,y: " + cx + ", " + cy);
	boolean innerX = Math.abs(cx) < (double)width/2;
	boolean innerY = Math.abs(cy) < (double)height/2;
	boolean outerX = Math.abs(cx) - r < (double)width/2;
	boolean outerY = Math.abs(cy) - r < (double)height/2;
	
	//System.out.println("inX, inY, outX, outY: " + innerX + ", " + innerY + ", " + outerX + ", " + outerY);
	
	if(!outerX && !outerY)
		{
		System.out.println("Stopping here... Bad!");
		return -1; //doesn't hit
		}
		
	cV.rotate(-theta);	
	//System.out.println("transformed cV x,y: " + cV);		
	Vector2D vel = new Vector2D(c,cV);
	//System.out.println("vel: " + vel);	
	
	if(innerX && innerY)
		{
		System.out.println("Ball is well inside the block...");
		hitSide = 4;
		}
	else if(innerX && outerY)
		{
		if(cy > 0) hitSide = 2; //bottom
		else hitSide = 0;
		vel.reflect(Vector2D.XAXIS);
		}
	else if(innerY && outerX)
		{
		if(cx > 0) hitSide = 1;
		else hitSide = 3;
		vel.reflect(Vector2D.YAXIS);
		}
	else if(outerX && outerY) 
		{
		hitSide = 4;
		Coord cornerC = new Coord(Math.signum(cx)*width/2,Math.signum(cy)*height/2);
		if(cornerC.dist(c) > b.getRadius()) //not actually colliding
			{
			//System.out.println("Not quite hitting a corner");
			return -1;
			}
		Vector2D norm = (new Vector2D(cornerC,c)).unit();
		vel.bounce(norm);
		}
	Coord cVF = vel.parametric(c,1);
	cVF.rotate(theta);
	c.rotate(theta);
	b.setSpeedX(cVF.getX()-c.getX());
	b.setSpeedY(cVF.getY()-c.getY());
	//System.out.println("final vel: " + vel);	
	//b.update();
	
	return hitSide;
	}
	
	public void pushOut(SpinBall b)
	{
	int hs = justHitSide(b);
	if(hs<0) return; //nothing to push out
	Coord cB = b.getCoordActual();
	Vector2D normal;
	if(hs < 4)
		{
		Vector2D surface = new Vector2D(corners[hs],corners[(hs+1)%4]);
		normal = surface.perpendicular().unit();		
		}
	else // corner
		{
		int cornerIndex = hs%4;
		normal = (new Vector2D(corners[cornerIndex],cB)).unit();
		}
		
	boolean ballOnScreen = true;
	while(justHitSide(b)>-1)
		{			
		if(ballOnScreen)
			{
			cB = normal.parametric(cB,1);
			b.setPosition(cB);
			if(b.onEdge() > 0) 
				{
				ballOnScreen = false;
				cB = normal.parametric(cB,-1);
				b.setPosition(cB);
				normal.reverse(); //to move the paddle
				}
			}
		else
			{
			for(int i=0; i<4; i++)
				{	
				corners[i].translate(normal.getX(),normal.getY());		
				}	
				xD+=normal.getX();
				yD+=normal.getY();
				prevX = xD;
				prevY = yD;
			}
		}
	//System.out.println("after pushing, ball at " + b.getCoordActual() + " and paddle top corners are " 
		//+ corners[0] + " and " + corners[1]);
		b.setPushed(true);
	}
	
	public int justHitSide(SpinBall b)
	{
	// pushes ball away from paddle normal to surface until ball isn't in contact with it)
	
	int hitSide = -1;
	int r = b.getRadius();
	Coord c = b.getCoordActual().copy();
	//System.out.println("center x,y: " + (int)c.getX() + ", " + (int)c.getY());
	c.translate(-xD,-yD);
	//System.out.println("velocity x,y: " + vx + ", " + vy);
	
	c.rotate(-theta);	
	int cx = (int)c.getX();
	int cy = (int)c.getY();
	//System.out.println("transformed center x,y: " + cx + ", " + cy);
	boolean innerX = Math.abs(cx) < (double)width/2;
	boolean innerY = Math.abs(cy) < (double)height/2;
	boolean outerX = Math.abs(cx) - r < (double)width/2;
	boolean outerY = Math.abs(cy) - r < (double)height/2;
	if(!outerX && !outerY) return -1; //doesn't hit
	
	if(innerX && innerY)
		{
		System.out.println("Ball is well inside the block...");
		hitSide = 4;
		}
	else if(innerX && outerY)
		{
		if(cy > 0) hitSide = 2; //bottom
		else hitSide = 0;
		}
	else if(innerY && outerX)
		{
		if(cx > 0) hitSide = 1;
		else hitSide = 3;
		}
	else if(outerX && outerY) 
		{
		hitSide = 4;
		Coord cornerC = new Coord(Math.signum(cx)*width/2,Math.signum(cy)*height/2);
		if(cornerC.dist(c) > b.getRadius()) //not actually colliding
			{
			//System.out.println("Not quite hitting a corner");
			//System.out.println(cornerC.dist(c));
			return -1;
			}
		// if it is actually colliding, designate which corner it hits: 4,5,6,7 (0,1,2,3)
		if(cx < 0)
			{
			if(cy < 0) return 4;
			else return 7;
			}
		else
			{
			if(cy < 0) return 5;
			else return 6;
			}			
		}
	return hitSide;
	}
	
	public boolean hitsBall(SpinBall b)
	{
	//kind of unnecessary, roundOff errors
	// not quite: at corners, should be rounded, not a rectangle
	int[] xPoints = new int[4];
	int[] yPoints = new int[4];
	int r = b.getRadius();
	
	Coord[] cornersP = new Coord[4];
	cornersP[0] = new Coord(-width/2-r,-height/2-r);
	cornersP[1] = new Coord(width/2+r,-height/2-r);
	cornersP[2] = new Coord(width/2+r,height/2+r);
	cornersP[3] = new Coord(-width/2-r,height/2+r);
		//System.out.println("Corners: ");
	for(int i=0; i<4; i++)
		{
		cornersP[i].rotate(theta);		
		cornersP[i].translate(xD,yD);	
		xPoints[i] = (int)cornersP[i].getX();
		yPoints[i] = (int)cornersP[i].getY();
		//System.out.println(cornersP[i]);
		}		
	//System.out.println(b.getCoordActual());
		//System.out.println("Dist to corner: ");
	//System.out.println(b.getCoordActual().dist(corners[1]));
	Polygon poly = new Polygon(xPoints,yPoints,4);
	return poly.contains(b.getX(),b.getY());
	}
	
	public void rotateCW()
	{
	for(int i=0; i<4; i++)
		{	
		corners[i].translate(-xD,-yD);	
		corners[i].rotate(-dTheta);	
		corners[i].translate(xD,yD);
		}	
	theta -= dTheta;
	double minX = Coord.minX(corners);	
	double maxX = Coord.maxX(corners);
	double minY = Coord.minY(corners);	
	double maxY = Coord.maxY(corners);
	if(minX < 0 || maxX > boardWidth || minY < 0 || maxY > boardHeight) //then undo
		{
		for(int i=0; i<4; i++)
			{	
			corners[i].translate(-xD,-yD);	
			corners[i].rotate(dTheta);	
			corners[i].translate(xD,yD);
			}	
		theta += dTheta;		
		}
	}
	
	public void rotateCCW()
	{
	for(int i=0; i<4; i++)
		{
		corners[i].translate(-xD,-yD);
		corners[i].rotate(dTheta);		
		corners[i].translate(xD,yD);		
		}	
	theta += dTheta;
	double minX = Coord.minX(corners);	
	double maxX = Coord.maxX(corners);
	double minY = Coord.minY(corners);	
	double maxY = Coord.maxY(corners);
	if(minX < 0 || maxX > boardWidth || minY < 0 || maxY > boardHeight) //then undo
		{
		for(int i=0; i<4; i++)
			{	
			corners[i].translate(-xD,-yD);	
			corners[i].rotate(-dTheta);	
			corners[i].translate(xD,yD);
			}	
		theta -= dTheta;		
		}
	}
	
	public void moveLeft(boolean slowMode)
    {
	double minX = Coord.minX(corners);
	double dx;
	movingLeft = true;
	if (minX > paddleSpeed) 
		{
		if (!slowMode) dx = -paddleSpeed;
		else dx = -paddleSpeed/paddleSlowFactor;
		}
	else if(minX > 0)
		{
		dx = -minX;
		}
	else 
		{
		movingLeft = false;
		return;
		}
	
	for(int i=0; i<4; i++)
		{	
		corners[i].translate(dx,0);		
		}	
		prevX = xD;
		xD += dx;
    }
	
    public void moveRight(boolean slowMode)
    {
	double maxX = Coord.maxX(corners);
	double dx;
	movingRight = true;
	if (maxX + paddleSpeed < boardWidth )
	    {
		if (!slowMode) dx = paddleSpeed;
		else dx = paddleSpeed/paddleSlowFactor;
	    }
	else if(maxX < boardWidth-1)
		{
		dx = boardWidth - maxX -1;
		}
	else
		{
		movingRight = false;
		return;
		}
	
	for(int i=0; i<4; i++)
		{	
		corners[i].translate(dx,0);	
		}	
		prevX = xD;
		xD += dx;	
    }
	
	public void moveUp(boolean slowMode)
    {
	double minY = Coord.minY(corners);
	double dy;
	movingUp = true;
	if (minY > paddleSpeed) 
		{
		if (!slowMode) dy = -paddleSpeed;
		else dy = -paddleSpeed/paddleSlowFactor;
		}
	else if(minY > 0)
		{
		dy = -minY;
		}
	else 
		{
		movingUp = false;
		return;
		}
	
	for(int i=0; i<4; i++)
		{	
		corners[i].translate(0,dy);		
		}	
		prevY = yD;
		yD += dy;
    }
	
	public void moveDown(boolean slowMode)
    {
	double maxY = Coord.maxY(corners);
	double dy;
	movingDown = true;
	if (maxY < boardHeight-1-paddleSpeed) 
		{
		if (!slowMode) dy = paddleSpeed;
		else dy = paddleSpeed/paddleSlowFactor;
		}
	else if(maxY < boardHeight-1)
		{
		dy = boardHeight-1-maxY;
		}
	else 
		{
		movingDown = false;
		return;
		}
	
	for(int i=0; i<4; i++)
		{	
		corners[i].translate(0,dy);	
		}	
		prevY = yD;
		yD += dy;	
    }
	
    public boolean movingLeft()
    {
	return movingLeft;
    }

    public boolean movingRight()
    {
	return movingRight;
    }
	
    public void notMoving()
    {
	movingLeft = false;
	movingRight = false;
	movingUp = false;
	movingDown = false;
	prevX = xD;
	prevY = yD;
    }	

    public void paint(Graphics g) 
    {
	g.setColor (Color.RED);
	
	for(int i=0; i<4; i++)
		{
		g.drawLine ((int)corners[i].getX(), (int)corners[i].getY(), (int)corners[(i+1)%4].getX(), (int)corners[(i+1)%4].getY());
		}
    }

}
	