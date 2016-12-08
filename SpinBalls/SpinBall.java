import java.awt.event.*;
import java.awt.*;
import java.lang.Math;
import java.util.Vector;

public class SpinBall {
    
	private double gravity;
	private double CORDefault = 0.8; // 1 is perfect bounce (Coefficient of Restitution)
	private double COR = CORDefault;
	private double dragConst = 0.131; //1/2 rho Cd A / m, rho = density of air, 1.2 kg/m^3, Cd = drag coefficient, 0.47 for a sphere, A = cross sect area, taking diameter = 4 cm (ping pong ball), m = 2.7 g ( ping pong ball)
	private double magnusConst = 0.01;//0.00223; //a little small seeming... 1/2 rho r A l / m, rho = 1.2 kg/m^3, r = 2 cm, A = Pi*(2 cm)^2, l = lift coefficient, 0.2 to 0.6 (we take 0.4), m = 2.7 g
	private double pixPerMeter;
	private int originalTrailLength = 1000;
	private int trailLength = originalTrailLength;
	private int px[] = new int[trailLength];
	private int py[] = new int[trailLength];	
    private double x;
    private double y;
	private double prevX;
    private double prevY;
    private double theta;
    private double omega; //rotational velocity, in rad/time step, counterclockwise
    private int radius;
	private int frame = 0;
	private int prevHit = 0;
	private int frameDiff = 20;
	private int prevFrameDiff = 0;
	private int frameCount = 0;
    private double speedX;
    private double speedY;
    private int boardWidth;
    private int boardHeight;
    private int xMarg;
    private int yMarg;
    private double speed;
    private double angle;
    private double cI = 2./3.; //for spherical shell (hollow ball); 2/5 for solid sphere
    private double defaultMu = 0.2; //of walls
    private double defaultMuP = 0.85; //of paddle
    private double mu = defaultMu;
    private double muP = defaultMuP;
    private boolean alive = true;
	private boolean stuck = false;
	private boolean justBounced = false;
	private boolean actualBounce = true;
	private Color ballColor;
	//private double acceleration = 0;
	//private double accelerationAngle = 0;
	private boolean gravityOn;
	private Vector<Force> forces = new Vector<Force>();
	private double netForceX = 0;
	private double netForceY = 0;
    private double accelerationX = 0;
	private double accelerationY = 0;
	private int charge = 0;
	private double multiplier;
	public boolean alreadyCollided = false;
	public boolean pushed = false;
	private int pushCount = -1;
	public final int LEFT = 1;
	public final int TOP = 2;
	public final int RIGHT = 3;
	public final int BOTTOM = 4;
	public final int CORNER = 5;
	public int ballID;
		
    public SpinBall(double x, double y, int radius, double speed, double angle, 
	int xMarg, int yMarg, int boardWidth, int boardHeight, float red, float green,
	float blue, double gravity, boolean gravityOn, int charge)
    {
	this(x,y,radius,speed,angle,xMarg,yMarg,boardWidth,boardHeight,red,green,blue,
	gravity,gravityOn,charge,0,0,12,true,0.2,0.85,0.8,1);
	}
	
    public SpinBall(double x, double y, int radius, double speed, double angle, 
	int xMarg, int yMarg, int boardWidth, int boardHeight, float red, float green,
	float blue, double gravity, boolean gravityOn, int charge, double theta, double omega,
	double multiplier, boolean trailsOn, double mu, double muP, double COR, int ballID)
    {
	//post: Constructs a ball at x and y moving with a speed at an angle above the horizontal in the counterclockwise direction
	this.x = x;
	this.y = y;
	prevX = x;
	prevY = y;
	this.radius = radius;
	this.speedX = speed*Math.cos(angle);
	this.speedY = -speed*Math.sin(angle);
	this.xMarg = xMarg;
	this.yMarg = yMarg;
	this.boardWidth = boardWidth;
	this.boardHeight = boardHeight;	
	ballColor = new Color(red,green,blue);
	this.gravity = gravity;
	this.gravityOn = gravityOn;
	this.charge = charge;
	this.theta = theta;
	this.omega = omega*multiplier/1000.;  // 1000/multiplier time steps per second
	this.multiplier = multiplier;
	this.mu = mu;
	this.muP = muP;
	this.COR = COR;
	this.ballID = ballID;
	pixPerMeter = 100*radius/2; // 1000 for r = 20
	
	for (int i=0; i<trailLength; i++)
		{
		px[i] = (int)x;
		py[i] = (int)y;
		}
	if(!trailsOn) setTrailLength(0);
    }
	
    public int onEdge()
    {
	int value = 0; // Means not on edge
	int countEdges = 0;
	if (x - radius < xMarg) //left edge
	    {
		countEdges++;
		value = 1;
	    } 
	if (y - radius < yMarg) //top
	    {
		countEdges++;
		value = 2;
	    } 
	if (x + radius > boardWidth + xMarg) //right edge
	    {
		countEdges++;
		value = 3;
	    }
	if (y + radius > boardHeight + yMarg) // bottom
	    {
		countEdges++;
		value = 4;
	    }
	if (countEdges > 1) value = 5; //a corner
	return value;
    }
	
	public void setTrailLength(int val)
	{
	trailLength = val;
	if(val < 0) trailLength = originalTrailLength;
	px = new int[trailLength];
	py = new int[trailLength];	
	
	for (int i=0; i<trailLength; i++)
		{
		px[i] = (int)x;
		py[i] = (int)y;
		}
	}
	
	public boolean collide(SpinBall that)
	{
	return this.getCoord().dist(that.getCoord()) <= radius + that.getRadius();
	}
	
	public void pushOutGroup(Vector<SpinBall> group, Coord CM)
	{
	// post: pushes this apart from the group to try and keep the center of mass preserved (unless boundary interferes)
	Coord cThis = this.getCoordActual();			
	for (int i=0; i < group.size(); i++)
		{
		SpinBall bCur = group.get(i);
		if(this.collide(bCur))
			{
			Coord cThat = bCur.getCoordActual();
			Vector2D v = (new Vector2D(CM,cThat)).unit();
			
			}
		}
		
	}	
	
	public void pushOut(SpinBall that, Vector<SpinBall> balls)
	{
	//post: pushes this and that apart at rates proportional to their radii until they're just separated
	//if a wall is nearby, stops a ball at the wall
	Coord cThis = this.getCoordActual();
	Coord cThat = that.getCoordActual();
	//System.out.println("Before: " + cThis.dist(cThat));
	Vector2D v = (new Vector2D(cThis,cThat)).unit();
	double dist = radius + that.getRadius() - cThis.dist(cThat) + 0.000001;//left to cover
	cThat = v.parametric(cThat,dist*that.getRadius()/(radius+that.getRadius()));
	//cThat.flip(boardHeight); //only needed with old getCoord method
	v.reverse();
	cThis = v.parametric(cThis,dist*radius/(radius+that.getRadius()));
	//cThis.flip(boardHeight);
	setPosition(cThis);
	that.setPosition(cThat);
	//System.out.println("After: " + cThis.dist(cThat));
	int thisEdge = onEdge();
	int thatEdge = that.onEdge();
	double dx;
	double dy;
	if(thisEdge != 0 || thatEdge != 0)
		{
		if(thisEdge == 0)
			{
			if(thatEdge == LEFT)
				{
				dx = Math.abs(that.getRadius() - that.getX());
				dy = v.getSlope()*dx;
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thatEdge == RIGHT)
				{
				dx = -Math.abs(that.getRadius() + that.getX() - boardWidth);
				dy = v.getSlope()*dx;
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thatEdge == TOP)
				{
				dy = Math.abs(that.getRadius() - that.getY());
				dx = dy/v.getSlope();
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thatEdge == BOTTOM)
				{
				dy = -Math.abs(that.getRadius() + that.getY() - boardHeight);
				dx = dy/v.getSlope();
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thatEdge == CORNER)
				{
				if(that.getX()<boardWidth/2 && that.getY()<boardHeight/2) //top left
					{
					dx = that.getRadius()-that.getX();
					dy = that.getRadius()-that.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				else if(that.getX()<boardWidth/2 && that.getY()>boardHeight/2) //bottom left
					{
					dx = that.getRadius()-that.getX();
					dy = (boardHeight - that.getRadius()) -that.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				else if(that.getX()>boardWidth/2 && that.getY()>boardHeight/2) //bottom right
					{
					dx = (boardWidth - that.getRadius()) -that.getX();
					dy = (boardHeight - that.getRadius()) -that.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				else if(that.getX()>boardWidth/2 && that.getY()<boardHeight/2) //top right
					{
					dx = (boardWidth - that.getRadius()) -that.getX();
					dy = that.getRadius()-that.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				}
			}
		else if(thatEdge == 0)
			{
			v.reverse();
			if(thisEdge == LEFT)
				{
				dx = Math.abs(this.getRadius() - this.getX());
				dy = v.getSlope()*dx;
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thisEdge == RIGHT)
				{
				dx = -Math.abs(this.getRadius() + this.getX() - boardWidth);
				dy = v.getSlope()*dx;
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thisEdge == TOP)
				{
				dy = Math.abs(this.getRadius() - this.getY());
				dx = dy/v.getSlope();
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thisEdge == BOTTOM)
				{
				dy = -Math.abs(this.getRadius() + this.getY() - boardHeight);
				dx = dy/v.getSlope();
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thisEdge == CORNER)
				{
				if(this.getX()<boardWidth/2 && this.getY()<boardHeight/2) //top left
					{
					dx = this.getRadius()-this.getX();
					dy = this.getRadius()-this.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				else if(this.getX()<boardWidth/2 && this.getY()>boardHeight/2) //bottom left
					{
					dx = this.getRadius()-this.getX();
					dy = (boardHeight - this.getRadius()) -this.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				else if(this.getX()>boardWidth/2 && this.getY()>boardHeight/2) //bottom right
					{
					dx = (boardWidth - this.getRadius()) -this.getX();
					dy = (boardHeight - this.getRadius()) -this.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				else if(this.getX()>boardWidth/2 && this.getY()<boardHeight/2) //top right
					{
					dx = (boardWidth - this.getRadius()) -this.getX();
					dy = this.getRadius()-this.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				}
			}
		else
			{
			System.out.println("Both off");
			}
		}
		
			
			// Push out all balls recursively
			/*
			for (int i=0; i < 1; i++)//balls.size(); i++)
			{
				SpinBall bCur = balls.get(i);
				if (!bCur.equals(this) && bCur.collide(this)) 
					{
					this.pushOutThat(bCur,balls);
					//System.out.println("this: i = " + i);
					//System.out.println("this ID: " + ballID + " colliding with " + bCur.ballID);
					}
				if (!bCur.equals(that) && bCur.collide(that)) 
					{
					that.pushOutThat(bCur,balls);
					//that.pushOutThat(bCur);
					//System.out.println("that: i = " + i);
					}
			}
			*/
					
	}
	
	public void pushOutThat(SpinBall that, Vector<SpinBall> balls)
	{
	//post: pushes that apart from this
	//if a wall is nearby, stops a ball at the wall
	Coord cThis = this.getCoordActual();
	Coord cThat = that.getCoordActual();
	//System.out.println("Before: " + cThis.dist(cThat));
	Vector2D v = (new Vector2D(cThis,cThat)).unit();
	double dist = radius + that.getRadius() - cThis.dist(cThat) + 0.000001;//left to cover
	cThat = v.parametric(cThat,dist);
	//cThat = v.parametric(cThat,dist*that.getRadius()/(radius+that.getRadius())); // even
	v.reverse();
	//cThis = v.parametric(cThis,dist*radius/(radius+that.getRadius()));
	//setPosition(cThis);
	that.setPosition(cThat);
	//System.out.println("After: " + cThis.dist(cThat));
	int thisEdge = onEdge();
	int thatEdge = that.onEdge();
	double dx;
	double dy;
	if(thisEdge != 0 || thatEdge != 0)
		{
		if(thisEdge == 0)
			{
			if(thatEdge == LEFT)
				{
				dx = Math.abs(that.getRadius() - that.getX());
				dy = v.getSlope()*dx;
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thatEdge == RIGHT)
				{
				dx = -Math.abs(that.getRadius() + that.getX() - boardWidth);
				dy = v.getSlope()*dx;
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thatEdge == TOP)
				{
				dy = Math.abs(that.getRadius() - that.getY());
				dx = dy/v.getSlope();
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thatEdge == BOTTOM)
				{
				dy = -Math.abs(that.getRadius() + that.getY() - boardHeight);
				dx = dy/v.getSlope();
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thatEdge == CORNER)
				{
				if(that.getX()<boardWidth/2 && that.getY()<boardHeight/2) //top left
					{
					dx = that.getRadius()-that.getX();
					dy = that.getRadius()-that.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				else if(that.getX()<boardWidth/2 && that.getY()>boardHeight/2) //bottom left
					{
					dx = that.getRadius()-that.getX();
					dy = (boardHeight - that.getRadius()) -that.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				else if(that.getX()>boardWidth/2 && that.getY()>boardHeight/2) //bottom right
					{
					dx = (boardWidth - that.getRadius()) -that.getX();
					dy = (boardHeight - that.getRadius()) -that.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				else if(that.getX()>boardWidth/2 && that.getY()<boardHeight/2) //top right
					{
					dx = (boardWidth - that.getRadius()) -that.getX();
					dy = that.getRadius()-that.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				}
			}
		else if(thatEdge == 0)
			{
			v.reverse();
			if(thisEdge == LEFT)
				{
				dx = Math.abs(this.getRadius() - this.getX());
				dy = v.getSlope()*dx;
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thisEdge == RIGHT)
				{
				dx = -Math.abs(this.getRadius() + this.getX() - boardWidth);
				dy = v.getSlope()*dx;
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thisEdge == TOP)
				{
				dy = Math.abs(this.getRadius() - this.getY());
				dx = dy/v.getSlope();
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thisEdge == BOTTOM)
				{
				dy = -Math.abs(this.getRadius() + this.getY() - boardHeight);
				dx = dy/v.getSlope();
				this.moveX(dx);
				that.moveX(dx);
				this.moveY(dy);
				that.moveY(dy);
				}
			else if(thisEdge == CORNER)
				{
				if(this.getX()<boardWidth/2 && this.getY()<boardHeight/2) //top left
					{
					dx = this.getRadius()-this.getX();
					dy = this.getRadius()-this.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				else if(this.getX()<boardWidth/2 && this.getY()>boardHeight/2) //bottom left
					{
					dx = this.getRadius()-this.getX();
					dy = (boardHeight - this.getRadius()) -this.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				else if(this.getX()>boardWidth/2 && this.getY()>boardHeight/2) //bottom right
					{
					dx = (boardWidth - this.getRadius()) -this.getX();
					dy = (boardHeight - this.getRadius()) -this.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				else if(this.getX()>boardWidth/2 && this.getY()<boardHeight/2) //top right
					{
					dx = (boardWidth - this.getRadius()) -this.getX();
					dy = this.getRadius()-this.getY();
					this.moveX(dx);
					that.moveX(dx);
					this.moveY(dy);
					that.moveY(dy);
					}
				}
			}
		else
			{
			System.out.println("Both off");
			}
		}
		
			// Push out all balls recursively
			/*
			for (int i=0; i < 1; i++)//balls.size(); i++)
			{
				SpinBall bCur = balls.get(i);
				if (!bCur.equals(this) && bCur.collide(this)) 
					{
					this.pushOutThat(bCur,balls);
					//System.out.println("this: i = " + i);
					//System.out.println("this ID: " + ballID + " colliding with " + bCur.ballID);
					}
				if (!bCur.equals(that) && bCur.collide(that)) 
					{
					that.pushOutThat(bCur,balls);
					//that.pushOutThat(bCur);
					//System.out.println("that: i = " + i);
					}
			}
			*/
	}
	
	public void setPushed(boolean pushed)
	{
	this.pushed = pushed;
	if(pushed) pushCount = 5;
	else pushCount = -1;
	}
	
	public boolean getPushed()
	{
	return pushed;
	}

	public Coord collisionPoint(SpinBall that, Vector<SpinBall> balls)
	{
	// post: returns the coordinate at which the first point of contact occured between two colliding balls
	// Can probably get away with the just taking the midpoint when they collide but this should be perfect.
		
	int jPar=0;
	double var[] = new double[4];
	double finalVal[] = new double[4];
	double inc[] = new double[4];
	double diff[] = new double[4];
	double parameter;
	Coord result;
	var[0] = prevX;
	var[1] = prevY;
	var[2] = that.getPrevX();
	var[3] = that.getPrevY();
	finalVal[0] = x;
	finalVal[1] = y;
	finalVal[2] = that.getX();
	finalVal[3] = that.getY();
	
	Coord thisPrevCoord = new Coord(var[0],var[1]);
	Coord thatPrevCoord = new Coord(var[2],var[3]);
	
	//System.out.println(thisPrevCoord.intersect(thatPrevCoord,radius));
	if (thisPrevCoord.intersect(thatPrevCoord,(radius+that.getRadius())/2)) 
		{
		//System.out.println("already colliding");
		//alreadyCollided = true;
		//that.alreadyCollided = true;
		// one thought: only consider collisions in which they weren't colliding at beginning of step
		// then have to push them out immediately (in direction of intersection)
		pushOut(that,balls);
		}
	else
	{
	
	for (int i = 0; i<4; i++) diff[i] = finalVal[i] - var[i];
	
	// Shouldn't these be abs. value?
	parameter = Math.max(Math.abs(speedX),Math.abs(speedY));
	parameter = Math.max(parameter,Math.abs(that.getSpeedX()));
	parameter = Math.max(parameter,Math.abs(that.getSpeedY()));
	if (parameter == Math.abs(speedX)) jPar=0;
	if (parameter == Math.abs(speedY)) jPar=1;
	if (parameter == Math.abs(that.getSpeedX())) jPar=2;
	if (parameter == Math.abs(that.getSpeedY())) jPar=3;
	
	//int iterations = (int)Math.abs(diff[jPar]) + 1;
	int iterations = (int)Math.abs(diff[jPar])*2 + 1;
	if (iterations < 1) iterations = 1;
	//System.out.println(iterations);
	for (int i = 0; i<4; i++) inc[i] = diff[i]/iterations;
	
	Coord thisCoord = new Coord(prevX, prevY);
	Coord thatCoord = new Coord(that.getPrevX(), that.getPrevY());
	
	for (int i = 0; i < iterations; i++)
	{
	thisCoord.moveX(inc[0]);
	thisCoord.moveY(inc[1]);
	thatCoord.moveX(inc[2]);
	thatCoord.moveY(inc[3]);
	
	// Also, let's set the balls to be where they were just before they collided		
	if (thisCoord.intersect(thatCoord,(radius+that.getRadius())/2))
		{
		result = thisCoord.lerp(thatCoord,radius/(radius+that.getRadius()));
		
		//System.out.println(thisCoord.dist(thatCoord));
		
		thisCoord.moveX(-inc[0]);
		thisCoord.moveY(-inc[1]);
		thatCoord.moveX(-inc[2]);
		thatCoord.moveY(-inc[3]);
		
		/* Messes with other balls
		double forceAngle = thisCoord.angle(thatCoord);
		while (thisCoord.intersect(thatCoord,radius)) // if they were colliding before, force them apart
		{	
		thatCoord.move(1,forceAngle);
		thisCoord.move(1,forceAngle + Math.PI);
		}
		*/
		
		/* //Need a force class, keep track of forces between two balls
		if (thisCoord.intersect(thatCoord,radius)) // if they were colliding before, force them apart
		{	
		double forceAngle = thisCoord.angle(thatCoord);
		double acc = 1/thisCoord.dist(thatCoord);
		this.addToAccelerationX(acc*Math.cos(forceAngle + Math.PI));
		this.addToAccelerationY(acc*Math.sin(forceAngle + Math.PI));
		that.addToAccelerationX(acc*Math.cos(forceAngle));
		that.addToAccelerationY(acc*Math.sin(forceAngle));
		}
		*/
		
		setPosition(thisCoord);
		that.setPosition(thatCoord);
		
		result.flip(boardHeight); // Makes it have same convention as speed coords, with y = 0 bottom of screen
		return result;
		}
	}
	System.out.println("Error, didn't find collision point");
	}
	//System.out.println("returning null");
	return null;
	}
	
	public void setPosition(Coord c)
	{
	x = c.getX();
	y = c.getY();
	}
	
	public void setPosition(double x, double y)
	{
	this.x = x;
	this.y = y;
	}
	
    public Coord getCoord()
    {
	return new Coord(x, boardHeight + yMarg - y);
    }
	
    public Coord getCoordActual()
    {
	return new Coord(x, y);
    }

    public int getRadius()
    {
	return radius;
    }

	public double getY()
    {
	return y;
    }

    public double getX()
    {
	return x;
    }
	
	public double getPrevY()
    {
	return prevY;
    }

    public double getPrevX()
    {
	return prevX;
    }
	
    public double getSpeed()
    {
	//Post: returns the magnitude of the velocity of the ball
	return Math.sqrt(speedX*speedX + speedY*speedY);
    }

    public double getSpeedX()
    {
	return speedX;
    }

    public double getSpeedY()
    {
	return speedY;
    }

    public void setSpeedX(double speedX)
    {
	this.speedX = speedX;
    }

    public void setSpeedY(double speedY)
    {
	this.speedY = speedY;
    }

	public void slow(double f)
	{
		speedX = speedX/f;
		speedY = speedY/f;			
	}
	
    public void moveX(double dx)
    {
	x += dx;
    }
	
	public void moveY(double dy)
    {
	y += dy;
    }
	
	public double getAngle()
    {
	//Post: returns angle of direction of motion
	return (getDirection() + Math.PI) % 2*Math.PI;
    }

    public double getDirection()
    {
	//Post: returns angle of direction OPPOSITE direction of motion (used for ball bouncing off paddle)
	Coord dir = new Coord(speedX, -speedY);
	return dir.angle();
    }

    public void changeDirection(double angle)
    {
	//Post: Changes the direction of the ball by angle radians in the 
	// counterclockwise direction.
	double oldDir = this.getDirection();
	double speed = this.getSpeed();
	double newDir = oldDir + angle;

	/*
	System.out.println("old speed X = " + speedX);
	System.out.println("old speed Y = " + speedY);
	System.out.println("old speed dir = " + oldDir);
	*/

	speedX = speed*Math.cos(newDir);
	speedY = -speed*Math.sin(newDir);
	
	/*
	System.out.println("new speed X = " + speedX);
	System.out.println("new speed Y = " + speedY);
	System.out.println("new speed dir = " + this.getDirection());
	*/
    }

    //private double edgeBounce(double speedDirectionn)
    //{
    // }
     
		/*
	public void setAcceleration(double acceleration)
	{
	this.acceleration = acceleration;
	}
	
	public void setAccelerationAngle(double accelerationAngle)
	{
	this.accelerationAngle = accelerationAngle;
	}
	*/
	
	public void addToAccelerationX(double dx)
	{
	accelerationX += dx;
	}
	
	public void addToAccelerationY(double dy)
	{
	accelerationY += dy;
	}
	
	public void setAccelerationX(double accelerationX)
	{
	this.accelerationX = accelerationX;
	}
	
	public void setAccelerationY(double accelerationY)
	{
	this.accelerationY = accelerationY;
	}
	
	public void setGravityOn(boolean value)
	{
	this.gravityOn = value;
	}
	
	public double getGravity()
	{
	return gravity;
	}
	
	public void setGravity(double gravity)
	{
	this.gravity = gravity;
	}
	
	public void modifyGravity(double factor)
	{
	this.gravity = gravity * factor;
	}
	
	public int getCharge()
	{
	return charge; // +1, -1, or 0
	}
	
	public void addForce(SpinBall that)
	{
	if (Math.abs(charge + that.getCharge()) == 0 && charge != 0) //attractive
		{
		Force Fnew = new Force(this,that,true);
		forces.add(Fnew);
		}
	else if (Math.abs(charge + that.getCharge()) == 2) //replusive
		{
		Force Fnew = new Force(this,that,false);
		forces.add(Fnew);
		}
	// Else add no force because neutral object won't interact.
	}
	
	public void calculateNetForce()
	{
	netForceX = 0;
	netForceY = 0;
	for (int i = 0; i < forces.size(); i++)
		{
		Force Fcur = forces.get(i);
		netForceX += Fcur.getForceX();
		netForceY += Fcur.getForceY();
		}
		
	// add drag and spin effects
	Vector2D speed = new Vector2D(speedX,speedY);
	if(speed.magnitude() > 0)
		{
		double accMag = dragConst*speed.magnitude()*speed.magnitude()*1000*1000/
		(pixPerMeter*pixPerMeter*multiplier*multiplier); // m/s^2, need in terms of time steps
		
		accMag = accMag*pixPerMeter*multiplier*multiplier/(1000*1000);
		//System.out.println(accMag);
		
		Vector2D drag = speed.unit();
		drag.reverse();
		drag.scalarMult(accMag);
		netForceX += drag.getX();
		netForceY -= drag.getY();
		
		//magnusConst
		double spinAccMag = magnusConst*speed.magnitude()*Math.abs(omega)*1000*1000/(pixPerMeter*multiplier*multiplier); //m/s^2
		spinAccMag = spinAccMag*pixPerMeter*multiplier*multiplier/(1000*1000);
		
		Vector2D lift = speed.unit();
		int sign = +1;
		if(omega<0) sign = -1;
		//System.out.println(sign);
		lift.rotate(sign*Math.PI/2); //rotates clockwise
		//System.out.println(lift);
		lift.scalarMult(spinAccMag);
		netForceX += lift.getX();
		netForceY -= lift.getY();	
		}	
	}

	public void adjustForSpin(int edgeValue)
	{
	
	// updates speed parallel to bouncing surface and rotational velocity after a collision to take spin into account
	// assumes slip time is shorter than time ball in contact with paddle, so it reaches rolling without slipping motion - depends on vN and w0
	int aSign = +1;
	int alphaSign = +1;
	int vToOmegaSign = +1;
	double dSpeed;
	double dOmega;
	double maxDSpeed;
	double maxDOmega;
	if(edgeValue == 1)
		{
		vToOmegaSign = -1;
		double vTan = speedY; // tangent speed downward
		double vNet = -vTan - radius*omega; // net speed upward at point of contact
		//System.out.println("vNet upward = " + vNet);
		if(vNet < 0) // then net speed downward, so friction points up: -speedY is tang speed upward, omega*r gives tang speed downward from rot.
			{
			aSign = -1;			
			alphaSign = -1;			
			}	
		dSpeed = aSign*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/cI); // - vToOmegaSign because its (-+) not (+-)
		dOmega = alphaSign*(1/(cI*radius))*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/cI);
		
		maxDSpeed = (1+COR)*Math.abs(speedX)*mu;
		maxDOmega = (1+COR)*Math.abs(speedX)*mu/(cI*radius);
		if(Math.abs(dSpeed) > maxDSpeed)
			{
			/*
			System.out.println("dSpeed: " + Math.abs(dSpeed));
			System.out.println("maxDSpeed: " + Math.abs(maxDSpeed));
			System.out.println("dOmega: " + Math.abs(dOmega));
			System.out.println("maxDOmega: " + Math.abs(maxDOmega));
			*/
			
			dSpeed = maxDSpeed*dSpeed/Math.abs(dSpeed);
			dOmega = maxDOmega*dOmega/Math.abs(dOmega);
			}
		
		speedY += dSpeed;
		omega += dOmega;
		}
	else if(edgeValue == 2)
		{ 
		vToOmegaSign = 1;	
		aSign = -1;	// if net speed is to right
		double vTan = speedX; // tangent speed to right
		double vNet = vTan - radius*omega; // net speed right at point of contact
		if(vNet < 0) // then net speed left, so friction points right
			{
			aSign = +1;			
			alphaSign = -1;			
			}				
		dSpeed = aSign*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/cI); // - vToOmegaSign because its (-+) not (+-)
		dOmega = alphaSign*(1/(cI*radius))*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/cI);
					
		maxDSpeed = (1+COR)*Math.abs(speedY)*mu;
		maxDOmega = (1+COR)*Math.abs(speedY)*mu/(cI*radius);
		if(Math.abs(dSpeed) > maxDSpeed)
			{
			dSpeed = maxDSpeed*dSpeed/Math.abs(dSpeed);
			dOmega = maxDOmega*dOmega/Math.abs(dOmega);
			}	
			
		speedX += dSpeed;
		omega += dOmega;
		}
	else if(edgeValue == 3)
		{ 
		vToOmegaSign = 1;	
		alphaSign = -1;	// if net speed is upward
		double vTan = speedY; // tangent speed downward
		double vNet = -vTan + radius*omega; // net speed upward at point of contact
		//System.out.println("vNet upward = " + vNet);
		if(vNet < 0) // then net speed downward, so friction points up: -speedY is tang speed upward, omega*r gives tang speed downward from rot.
			{
			aSign = -1;			
			alphaSign = +1;			
			}	
		dSpeed = aSign*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/cI); // - vToOmegaSign because its (-+) not (+-)
		dOmega = alphaSign*(1/(cI*radius))*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/cI);
					
		maxDSpeed = (1+COR)*Math.abs(speedX)*mu;
		maxDOmega = (1+COR)*Math.abs(speedX)*mu/(cI*radius);
		if(Math.abs(dSpeed) > maxDSpeed)
			{
			dSpeed = maxDSpeed*dSpeed/Math.abs(dSpeed);
			dOmega = maxDOmega*dOmega/Math.abs(dOmega);
			}
			
		speedY += dSpeed;
		omega += dOmega;
		
		//System.out.println(speedY);
		//System.out.println(radius*omega);
		}
	else if(edgeValue == 4)
		{ 
		vToOmegaSign = -1;	
		aSign = -1;	// if net speed is to right	
		alphaSign = -1;	// if net speed is to right	
		double vTan = speedX; // tangent speed to right
		double vNet = vTan + radius*omega; // net speed right at point of contact
		if(vNet < 0) // then net speed left, so friction points right
			{
			aSign = +1;			
			alphaSign = +1;			
			}
		dSpeed = aSign*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/cI); // - vToOmegaSign because its (-+) not (+-)
		dOmega = alphaSign*(1/(cI*radius))*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/cI);
				
		maxDSpeed = (1+COR)*Math.abs(speedY)*mu;
		maxDOmega = (1+COR)*Math.abs(speedY)*mu/(cI*radius);
		if(Math.abs(dSpeed) > maxDSpeed)
			{
			dSpeed = maxDSpeed*dSpeed/Math.abs(dSpeed);
			dOmega = maxDOmega*dOmega/Math.abs(dOmega);
			}	
			
		speedX += dSpeed;
		omega += dOmega;
		}
	}
	
	public void undoGravity(double t)
	{
	speedY -= gravity*t;
	}
	
	public void update()
	{
	update(1);
	if(pushCount > 0) pushCount--;
	if(pushCount <= 0) pushed = false;	
	}
	
    public void update(double dt)
    {
	// dt timesteps. note that updated for a whole step before, which was dt too much (for gravity's effect esp)
	frame++;
	
	if(dt == 1) theta -= omega; // minus to be counterclockwise
	
	/*
	if (justBounced)
		{
		if (frameDiff < 8) //2 frames to collide
			{			
			//actualBounce = false;
			System.out.println("gotcha");
			}
		justBounced = false;
		}
		*/
		
	//if(frameCount > 2) actualBounce = false;
	
	
	prevX = x;
	prevY = y;
	x += dt*speedX;
	y += dt*speedY;
	
	
	if(trailLength>0)
	{
		px[0] = (int)prevX;
		py[0] = (int)prevY;
		for (int i=trailLength-1; i>0; i--)
		{
		px[i] = px[i-1];
		py[i] = py[i-1];
		}
	}
	
	if ((boardHeight - y - radius > 1 || speedY < 0) && gravityOn && actualBounce) speedY += gravity;	// otherwise balls always bounce a little bit.
	if (!actualBounce)
		{
		speedY = 0;
		y = boardHeight-radius-1;
		actualBounce = true;
		frameCount = 0;
		}
	//speedY += gravity;
	//if(boardHeight - y - radius - speedY < 0) speedY = boardHeight - y - radius + 1;
	calculateNetForce();
	
	speedX += dt*netForceX;
	speedY -= dt*netForceY;
	
	
	
	int edgeValue = this.onEdge();
	if (edgeValue > 0 && edgeValue < 5 && dt == 1) {
	
		adjustForSpin(edgeValue);
		
	    if (edgeValue % 2 == 1) 
		{
		//System.out.println("side bounce");
		
		speedX = -speedX*COR; 
		if (this.onEdge() == 1) x = radius;
		if (this.onEdge() == 3) x = boardWidth - radius;
		}
	    //if (this.onEdge() == 2) speedY = -speedY; y += speedY; x+= speedX;
	    if (edgeValue % 2 == 0) 
		{
		//System.out.println("top or bottom bounce");
		speedY = -speedY*COR;//y += speedY; x+= speedX;
		justBounced = true;
		//System.out.println("Frame " + frame);
		//System.out.println("Frame difference " + (frame - prevHit));
		frameDiff = frame - prevHit;
		prevHit = frame;
		if (justBounced)
			{
			if (frameDiff < 100 && frameDiff == prevFrameDiff) //2 frames to collide
				{			
				frameCount++;
				}
			justBounced = false;
			}
		prevFrameDiff = frameDiff;
		
	    if (this.onEdge() == 2) y = radius;
		if (this.onEdge() == 4) 
			{
			double ds = (y-(boardHeight-radius))/Math.abs(speedY); //fraction of timestep past intersection
			//System.out.println(ds);
			y = boardHeight - radius;
			speedY += ds*gravity;
			}
		}
		// In the game we'll have the ball disappear if it falls off edge 4
	    // (the bottom)
	    // if (this.onEdge() == 2) speedY = -speedY;
	    //if (this.onEdge() == 4) alive = false;
	}
	
	if (edgeValue == 5 && dt == 1) {
		//System.out.println("Corner bounce");
	    speedX = -speedX*COR;
	    speedY = -speedY*COR;
	    x+= speedX;
	    y += speedY;
	}
	
	//System.out.println("x, y: " + x + ", " + y);
	//System.out.println("speedY " + speedY);
    }

	public boolean stuck()
	{
		return stuck;
	}
	
	public void stick()
	{
		stuck = true;
	}

	public void unstick()
	{
		stuck = false;
	}
	
    public boolean checkAlive()
    {
	return alive;
    }
	
    public void setAlive()
    {
	alive = true;
    }
	
	public void setWallBallFriction(double val)
	{	
	mu = val;
	}
	
	public void setWallPaddleFriction(double val)
	{	
	muP = val;
	}
	
	public void setCOR(double val)
	{	
	COR = val;
	}

	public boolean equals(SpinBall other)
	{
	return this.getCoord().equals(other.getCoord());
	}
	
	public void paint(Graphics g) 
    {
	g.setColor (ballColor);
	if(alreadyCollided) g.setColor(Color.WHITE);
	if (charge == 1) g.setColor(Color.YELLOW);
	if (charge == -1) g.setColor(new Color(150,0,255)); //Purple;
		
	for (int i=1; i<trailLength; i++)
	{
	g.drawLine (px[i],py[i],px[i-1],py[i-1]);
	}
	
	//g.fillOval (x_pos,y_pos,50,50);
	g.fillOval ((int)(x - radius), (int)(y - radius), 2 * radius, 2 * radius);
	
	//g.setColor(new Color(255-ballColor.getRed(),255-ballColor.getGreen(),255-ballColor.getBlue()));
	/*
	int redN = (ballColor.getRed()+127) % 255;
	int greenN = (ballColor.getGreen()+127) % 255;
	int blueN = (ballColor.getBlue()+127) % 255;
	g.setColor(new Color(redN,greenN,blueN));
	*/
	g.setColor(Color.BLACK);
	int xN = (int)(x + radius*Math.cos(theta))-1;
	int yN = (int)(y + radius*Math.sin(theta));
	int xS = (int)(x - radius*Math.cos(theta));
	int yS = (int)(y - radius*Math.sin(theta));
	g.drawLine(xN,yN,xS,yS);
    }
	
}