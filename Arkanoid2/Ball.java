//
// Class for balls in Arkanoid
// (c) Fred Brasz 2008

import java.awt.event.*;
import java.awt.*;
import java.lang.Math;
import javax.vecmath.*;

public class Ball {
    
    /** Deformed Position. */
    Point2d  x = new Point2d();
    /** Velocity. */
    Vector2d v = new Vector2d();
	// Net force
	Vector2d f = new Vector2d();
    /** Default mass. */
    double   m = Constants.BALL_MASS;
    static int radius = Constants.ballRadius;
	//double pixPerMeter = 100*radius/2; // 1000 for r = 20
	
	/*
	    double x;
	    double y;
		private double prevX;
	    private double prevY;
		
	    double v.x;
	    double v.y;
	*/
    private int boardWidth = Constants.boardWidth;
    private int boardHeight = Constants.boardHeight;
    double speed;
    double angle;
    double theta = 0;
    double omega = 0;//0.1; //rotational velocity, in rad/time step, counterclockwise
    private double speedUpRate = Constants.speedUpRate;
    private boolean alive = true;
    boolean stuck = false;
	private boolean blockHit = false;
	private double COR = Constants.CORDefault;
    double muW = Constants.defaultMuW;
    double muB = Constants.defaultMuB;
    double muP = Constants.defaultMuP;
	double gravity = 0.01;
	//int colors = 4;
    
    public Ball(double xn, double yn, double speed, double angle)
    {
	//post: Constructs a ball at x and y moving with a speed at an angle above the horizontal in the counterclockwise direction
	x.x = xn;
	x.y = yn;
	/*
	this.x = x;
	this.y = y;
	prevX = x;
	prevY = y;
	this.v.x = speed*Math.cos(angle);
	this.v.y = -speed*Math.sin(angle);
	*/
	v.x = speed*Math.cos(angle);
	v.y = -speed*Math.sin(angle);
    }
	
    public int onEdge()
    {
	int value = Constants.NONE;//0; // Means not on edge
	int countEdges = 0;
	if (x.x - radius < 0) //left edge
	    {
		countEdges++;
		value = Constants.LEFT;
	    } 
	if (x.y - radius < 0) //top
	    {
		countEdges++;
		value = Constants.TOP;
	    } 
	if (x.x + radius > boardWidth) //right edge
	    {
		countEdges++;
		value = Constants.RIGHT;
	    }
	if (x.y + radius > boardHeight + 2*radius + 1) // bottom
	    {
		//countEdges++;
		value = Constants.BOTTOM;
	    }
	if (countEdges > 1) value = Constants.CORNER; //a corner
	return value;
    }

    public Coord getCoord()
    {
	return new Coord(x.x, boardHeight - x.y);
    }

    public int getRadius()
    {
	return radius;
    }

	public double getY()
    {
	return x.y;
    }

    public double getX()
    {
	return x.x;
    }
	
    public double getSpeed()
    {
	//Post: returns the magnitude of the velocity of the ball
	//return Math.sqrt(v.x*v.x + v.y*v.y);
	return v.length();
    }
	
    public void slow(double f)
    {
	v.x = v.x/f;
	v.y = v.y/f;			
    }

    public void speedUp(double f)
    {
	v.x = v.x*f;
	v.y = v.y*f;			
    }
	
    public void moveX(double dx)
    {
	x.x += dx;
    }
	
	public void moveY(double dy)
    {
	x.y += dy;
    }

    public void setPosition(Coord c)
    {
	x.x = c.getX();
	x.y = c.getY();	
    }
	
	public double getAngle()
    {
	//Post: returns angle of direction of motion
	return (getDirection() + Math.PI) % 2*Math.PI;
    }

    public double getDirection()
    {
	//Post: returns angle of direction OPPOSITE direction of motion (used for ball bouncing off paddle)
	Coord dir = new Coord(v.x, -v.y);
	return dir.angle();
    }

    public void changeDirection(double angle)
    {
	//Post: Changes the direction of the ball by angle radians in the 
	// counterclockwise direction.
	double oldDir = this.getDirection();
	double speed = this.getSpeed();
	double newDir = oldDir + angle;

	v.x = speed*Math.cos(newDir);
	v.y = -speed*Math.sin(newDir);	
    }      
	
	public void hitBlock(boolean blockHit)
	{
	this.blockHit = blockHit;
	}
	
	public boolean checkIfBlockHit()
	{
	return blockHit;
	}
	
	public void adjustForSpin(int edgeValue, double mu)
	{	
	// updates speed parallel to bouncing surface and rotational velocity after a collision to take spin into account
	// if slip time is shorter than time ball in contact with paddle, it reaches rolling without slipping motion - depends on vN and w0
	int aSign = +1;
	int alphaSign = +1;
	int vToOmegaSign = +1;
	double dSpeed;
	double dOmega;
	double maxDSpeed;
	double maxDOmega;
	double vStart = v.length();
	if(edgeValue == Constants.LEFT)
		{
		vToOmegaSign = -1;
		double vTan = v.y; // tangent speed downward
		double vNet = -vTan - radius*omega; // net speed upward at point of contact
		//System.out.println("vNet upward = " + vNet);
		if(vNet < 0) // then net speed downward, so friction points up: -v.y is tang speed upward, omega*r gives tang speed downward from rot.
			{
			aSign = -1;			
			alphaSign = -1;			
			}	
		dSpeed = aSign*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/Constants.cI); // - vToOmegaSign because its (-+) not (+-)
		dOmega = alphaSign*(1/(Constants.cI*radius))*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/Constants.cI);
		
		maxDSpeed = (1+COR)*Math.abs(v.x)*mu;
		maxDOmega = (1+COR)*Math.abs(v.x)*mu/(Constants.cI*radius);
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
		
		v.y += dSpeed;
		omega += dOmega;
		}
	else if(edgeValue == Constants.TOP)
		{ 
		vToOmegaSign = 1;	
		aSign = -1;	// if net speed is to right
		double vTan = v.x; // tangent speed to right
		double vNet = vTan - radius*omega; // net speed right at point of contact
		if(vNet < 0) // then net speed left, so friction points right
			{
			aSign = +1;			
			alphaSign = -1;			
			}				
		dSpeed = aSign*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/Constants.cI); // - vToOmegaSign because its (-+) not (+-)
		dOmega = alphaSign*(1/(Constants.cI*radius))*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/Constants.cI);
					
		maxDSpeed = (1+COR)*Math.abs(v.y)*mu;
		maxDOmega = (1+COR)*Math.abs(v.y)*mu/(Constants.cI*radius);
		if(Math.abs(dSpeed) > maxDSpeed)
			{
			dSpeed = maxDSpeed*dSpeed/Math.abs(dSpeed);
			dOmega = maxDOmega*dOmega/Math.abs(dOmega);
			}	
			
		v.x += dSpeed;
		omega += dOmega;
		}
	else if(edgeValue == Constants.RIGHT)
		{ 
		vToOmegaSign = 1;	
		alphaSign = -1;	// if net speed is upward
		double vTan = v.y; // tangent speed downward
		double vNet = -vTan + radius*omega; // net speed upward at point of contact
		//System.out.println("vNet upward = " + vNet);
		if(vNet < 0) // then net speed downward, so friction points up: -v.y is tang speed upward, omega*r gives tang speed downward from rot.
			{
			aSign = -1;			
			alphaSign = +1;			
			}	
		dSpeed = aSign*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/Constants.cI); // - vToOmegaSign because its (-+) not (+-)
		dOmega = alphaSign*(1/(Constants.cI*radius))*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/Constants.cI);
					
		maxDSpeed = (1+COR)*Math.abs(v.x)*mu;
		maxDOmega = (1+COR)*Math.abs(v.x)*mu/(Constants.cI*radius);
		if(Math.abs(dSpeed) > maxDSpeed)
			{
			dSpeed = maxDSpeed*dSpeed/Math.abs(dSpeed);
			dOmega = maxDOmega*dOmega/Math.abs(dOmega);
			}
			
		v.y += dSpeed;
		omega += dOmega;
		
		//System.out.println(v.y);
		//System.out.println(radius*omega);
		}
	else if(edgeValue == Constants.BOTTOM)
		{ 
		vToOmegaSign = -1;	
		aSign = -1;	// if net speed is to right	
		alphaSign = -1;	// if net speed is to right	
		double vTan = v.x; // tangent speed to right
		double vNet = vTan + radius*omega; // net speed right at point of contact
		if(vNet < 0) // then net speed left, so friction points right
			{
			aSign = +1;			
			alphaSign = +1;			
			}
		dSpeed = aSign*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/Constants.cI); // - vToOmegaSign because its (-+) not (+-)
		dOmega = alphaSign*(1/(Constants.cI*radius))*(vToOmegaSign*radius*omega - vTan)/(aSign - vToOmegaSign*alphaSign/Constants.cI);
				
		maxDSpeed = (1+COR)*Math.abs(v.y)*mu;
		maxDOmega = (1+COR)*Math.abs(v.y)*mu/(Constants.cI*radius);
		if(Math.abs(dSpeed) > maxDSpeed)
			{
			dSpeed = maxDSpeed*dSpeed/Math.abs(dSpeed);
			dOmega = maxDOmega*dOmega/Math.abs(dOmega);
			}	
			
		v.x += dSpeed;
		omega += dOmega;
		}
	double vFin = v.length();
	v.scale(vStart/vFin);
	//System.out.println("scale ratio: " + vStart/vFin);
	}
	
	public void advance(double dt)
	{
	x.scaleAdd(dt, v, x);
	}
	
	public double advanceToX(double xf)
	{
	// returns the time it takes to advance ball to position with x-value xf (and advances the ball)
	if(v.x == 0) System.out.println("Error, ball with 0 x velocity can't change x position");
	double dt = (xf - x.x)/v.x;
	advance(dt);
	return dt;
	}
	
	public double advanceToY(double yf)
	{
	// returns the time it takes to advance ball to position with y-value yf (and advances the ball)
	if(v.y == 0) System.out.println("Error, ball with 0 y velocity can't change y position");
	double dt = (yf - x.y)/v.y;
	advance(dt);
	return dt;	
	}

	public void addMagnusSpinEffect()
	{
	//magnusConst
		double spinAccMag = Constants.magnusConst*(radius/Constants.ballRadius)*v.length()*Math.abs(omega);//*1000*1000/(pixPerMeter*Constants.multiplier*Constants.multiplier); //m/s^2
		//spinAccMag = spinAccMag*pixPerMeter*Constants.multiplier*Constants.multiplier/(1000*1000);
		if(ArkanoidApplet.stronglyCurved) spinAccMag = spinAccMag*Constants.strongCurvatureMultiplier;
		
		Vector2d lift = new Vector2d(v);
		lift.normalize();
		int sign = +1;
		if(omega<0) sign = -1;
		//System.out.println(sign);
		//lift.rotate(sign*Math.PI/2); //rotates clockwise
		double temp = lift.x;
		lift.x = lift.y;
		lift.y = -temp; //CCW rotation
		lift.scale(sign);
		//System.out.println(lift);
		lift.scale(spinAccMag);
		f.x += lift.x;
		f.y += lift.y;	
	}

    public void update()
    {
	x.x += v.x;
	x.y += v.y;
	v.x += f.x;
	v.y += f.y;
	if(ArkanoidApplet.gravityOn) v.y += gravity;
	
	f.x = 0;
	f.y = 0;
	
	//System.out.println("Prev Y " + prevY);
	//System.out.println(y);
	
	theta -= omega; // minus to be counterclockwise
	addMagnusSpinEffect();
	
	int edgeValue = this.onEdge();
	if (edgeValue > 0 && edgeValue < 5) {
	
		adjustForSpin(edgeValue,muW);
		
	    if (edgeValue % 2 == 1) 
		{
		    v.x = -v.x; 
		    speedUp(speedUpRate);
			if (x.x < radius) x.x = radius;
			if (x.x > boardWidth - radius) x.x = boardWidth - radius;
		}
	    if (edgeValue == 2) 
		{
		    v.y = -v.y;
		    x.y = radius;
			//y += v.y;
		    //x.x+= v.x;
		    speedUp(speedUpRate);
		}

	    if (edgeValue == 4) alive = false;
	}
	
	if (edgeValue == 5) {
	    v.x = -v.x;
	    v.y = -v.y;
		if (x.x < radius) x.x = radius;
		if (x.x > boardWidth - radius) x.x = boardWidth - radius;
	    x.y = radius;
	    speedUp(speedUpRate);
	}
    }

    public void paint(Graphics g) 
    {
	g.setColor (Color.YELLOW);

	//g.fillOval ((int)(x.x - radius), (int)(x.y - radius), 2 * radius, 2 * radius);
	int colors = radius-1;
	//int colors = 4;
	
	Color pC = g.getColor();
	/*
	int r = bluePart.getRed();
	int gr = bluePart.getGreen();
	int b = bluePart.getBlue();
	*/
	int r = 255-pC.getRed();
	int gr = 255-pC.getGreen();
	int b = 255-pC.getBlue();
	
	Color darker[] = new Color[colors];
	Color[] lighter = new Color[colors];
	for(int i=0; i<colors; i++)
		{
		lighter[i] = new Color(255-(int)(r*(colors-i)/(colors)),255-(int)(gr*(colors-i)/colors),255-(int)(b*(colors-i)/colors));
		g.setColor(lighter[i]);
		//darker[i] = new Color((int)(r*(colors-i+colors/2)/(3*colors/2)),(int)(gr*(colors-i+colors/2)/(3*colors/2)),(int)(b*(colors-i+colors/2)/(3*colors/2)));
		g.setColor(lighter[i]);
		g.fillOval ((int)(x.x - radius) + i, (int)(x.y - radius) + i, (int)(2 * radius) - 2*i, (int)(2 * radius) - 2*i);
		}
	
	Color bluePart = new Color(50,50,255);
	/*
	r = bluePart.getRed();
	gr = bluePart.getGreen();
	b = bluePart.getBlue();
	*/
	r = 255-bluePart.getRed();
	gr = 255-bluePart.getGreen();
	b = 255-bluePart.getBlue();
	//g.setColor(bluePart);
	
	darker = new Color[colors];
	lighter = new Color[colors];
	for(int i=0; i<colors; i++)
		{
		//darker[i] = new Color((int)(r*(colors-i+colors/2)/(3*colors/2)),(int)(gr*(colors-i+colors/2)/(3*colors/2)),(int)(b*(colors-i+colors/2)/(3*colors/2)));
		lighter[i] = new Color(255-(int)(r*(colors-i)/(colors)),255-(int)(gr*(colors-i)/colors),255-(int)(b*(colors-i)/colors));
		g.setColor(lighter[i]);
		g.fillArc((int)x.x - radius  + i, (int)x.y - radius  + i, 2*radius - 2*i, 2*radius - 2*i, (int)(-theta*180/Math.PI), 180);
		}
		
	//g.fillArc((int)x.x - radius, (int)x.y - radius, 2*radius, 2*radius, (int)(-theta*180/Math.PI), 180);
	/*
	int xN = (int)(x + radius*Math.cos(theta))-1;
	int yN = (int)(y + radius*Math.sin(theta));
	int xS = (int)(x - radius*Math.cos(theta));
	int yS = (int)(y - radius*Math.sin(theta));
	g.drawLine(xN,yN,xS,yS);
	*/
	
	if(Paddle.hasGold)
		{
		r = Constants.GOLD.getRed();
		gr = Constants.GOLD.getGreen();
		b = Constants.GOLD.getBlue();
		g.setColor(new Color(r,gr,b,100));
		g.fillOval ((int)(x.x - radius), (int)(x.y - radius), (int)(2 * radius), (int)(2 * radius));
		}		
	
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

}