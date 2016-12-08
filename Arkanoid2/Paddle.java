//
// Class for the paddle in Arkanoid
// (c) Fred Brasz 2008

import java.awt.event.*;
import java.awt.*;
import java.lang.Math;
import javax.vecmath.*;

public class Paddle {

    //double x;
    //double y;
    int radius = Constants.paddleRadius;
    int effRadius = radius;
    private int boardWidth = Constants.boardWidth;
    private int boardHeight = Constants.boardHeight;
    private int laserWidth = 6;
    private int laserThickness = 2;
    private int laserHeight = 35;
    private double paddleSpeed = Constants.paddleSpeed;
    private boolean poweredBig = false;
    private boolean poweredSmall = false;
    private boolean poweredUp = false;
    boolean movingLeft = false;
    boolean movingRight = false;
    private boolean hasLaser = false;
    private boolean hasSticky = false;
    private boolean hasGod = false;
    static boolean hasGold = false;
	private double paddleSlowFactor = Constants.paddleSlowFactor;
	int colors = 20;
	/**  Position. */
    Point2d  x = new Point2d();
    /** Velocity. */
    Vector2d v = new Vector2d();
    

    public Paddle(double xs, double ys)
    {
	//this.x = x;
	//this.y = y;
	x.x = xs;
	x.y = ys;
	v.x=0;
	v.y=0;
    }

    public Coord getCoord()
    {
	return new Coord(x.x, boardHeight - x.y);
    }

    public int getY()
    {
	// Post: returns the y value at the bottom of the paddle
	return (int) x.y;
    }

    public double getX()
    {
	return x.x;
    }


    public double hitAngle(Ball b)
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

    public boolean hitPowerup(Powerup pow)
    {
	//Post: returns true if the paddle is hitting the powerup, false if not.
	//Note: Assume powerups are rectangular, and we could correct for rounded edges, but that's a minor effect.
	double theta;
	double maxX;
	double minX;
	if (x.y - pow.getY() < (double)radius) { // powerup even with top of paddle or lower
	    if (pow.getX() > x.x) { // to right of paddle
			if (pow.getY() < x.y) { 
			    //powerup bottom above paddle bottom, so use bottom left corner of powerup to check for overlap				
				theta = Math.asin((x.y - pow.getY())/radius);
				// angle of where lower left corner would have to be (or further left)
				maxX = radius*Math.cos(theta); 				
				return x.x + maxX > pow.getX(); 
				//powerup must be closer to paddle than maxX
			} else { 
			    // powerup bottom below paddle bottom, so use top left corner of powerup
				theta = Math.asin((x.y - pow.getY() + pow.getHeight())/radius);
				// angle of where top left corner would have to be (or further left)
				maxX = radius*Math.cos(theta); 	
				return x.x + maxX > pow.getX();
				//powerup must be closer to paddle than maxX
			}
	    } else if (pow.getX() + pow.getWidth() < x.x) { // to left of paddle
			if (pow.getY() < x.y) { 
			    //powerup bottom above paddle bottom, so use bottom right corner of powerup to check for overlap				
				theta = Math.asin((x.y - pow.getY())/radius); 
				// angle of where lower right corner would have to be (or further right)
				minX = radius*Math.cos(theta); 				
				return x.x - minX < pow.getX() + pow.getWidth(); 
				//powerup must be closer to paddle than maxX
			} else { 
			    // powerup bottom below paddle bottom, so use top left corner of powerup
				theta = Math.asin((x.y - pow.getY() + pow.getHeight())/radius); 
				// angle of where top right corner would have to be (or further right)
				minX = radius*Math.cos(theta); 	
				return x.x - minX < pow.getX() + pow.getWidth(); 
				//powerup must be closer to paddle than maxX
			}
	    } else return true;
	} else return false;
    }
	    

    public void setX(double xs)
    {
	x.x = xs;
    }
	
    public int getRadius()
    {
	return radius;
    }
	
    public void setRadius(int radius)
    {
	this.radius = radius;
    }
	
    public void big()
    {
	if (!poweredBig) 
	    {
		radius = 2*radius;
		x.y = boardHeight + radius/2;
		effRadius = (int)(radius*Math.sqrt(3)/2); // from Sqrt(r^2 - (y-y0)^2), y-y0 = r/2
	    }
	poweredBig = true;
    }
	
    public void undoBig()
    {
	if (poweredBig) 
	    {
		radius = radius/2;
		effRadius = radius;
		x.y = boardHeight;
	    }
	poweredBig = false;
    }
    
    public void small()
    {
	if (!poweredSmall) 
	    {
		radius = radius/2;
		effRadius = radius;
	    }
	poweredSmall = true;
    }
	
    public void undoSmall()
    {
	if (poweredSmall) 
	    {
		radius = radius*2;
		effRadius = radius;
	    }
	poweredSmall = false;
    }

    public void moveLeft(boolean slowMode)
    {
	double speedC = paddleSpeed;
	if(slowMode) speedC = paddleSpeed/paddleSlowFactor;
	
		if (x.x - effRadius >= speedC)
			{
			x.x -= speedC;
			v.x = -speedC;
			movingLeft = true;
			}
		else if(x.x - effRadius > 0)
			{
			double dx = x.x-effRadius;
			x.x -= dx;
			v.x = -dx;
			movingLeft = true;
			}
		else 
			{
			movingLeft = false;
			v.x=0;
			}
    }

    public void moveLeftL(boolean slowMode)
    {
	//moves Left LATER: just sets velocity (advance used later)
	double speedC = paddleSpeed;
	if(slowMode) speedC = paddleSpeed/paddleSlowFactor;
	
		if (x.x - effRadius >= speedC)
			{
			//x.x -= speedC;
			v.x = -speedC;
			movingLeft = true;
			}
		else if(x.x - effRadius > 0)
			{
			double dx = x.x-effRadius;
			//x.x -= dx;
			v.x = -dx;
			movingLeft = true;
			}
		else 
			{
			movingLeft = false;
			v.x=0;
			}
    }
	
    public void notMoving()
    {
	movingLeft = false;
	movingRight = false;
	v.x = 0;
    }
	
    public void addLaser()
    {
	hasLaser = true;
    }
	
    public void removeLaser()
    {
	hasLaser = false;
    }
	
    public boolean hasLaser()
    {
	return hasLaser;
    }
	
	public void addSticky()
	{
	hasSticky = true;
	}
	
	public void removeSticky()
	{
	hasSticky = false;
	}
	
	public void addGold()
	{
	hasGold = true;
	}
	
	public void removeGold()
	{
	hasGold = false;
	}	
	
	public void addGod()
	{
	hasGod = true;
	}
	
	public void removeGod()
	{
	hasGod = false;
	}
	
    public void moveRight(boolean slowMode)
    {
	double speedC = paddleSpeed;
	if(slowMode) speedC = paddleSpeed/paddleSlowFactor;
	
		if (x.x + effRadius <= boardWidth - speedC)
			{
			x.x += speedC;
			v.x = speedC;
			movingRight = true;
			}
		else if(x.x + effRadius < boardWidth)
			{
			double dx = boardWidth-x.x-effRadius;
			x.x+= dx;
			v.x = dx;
			movingRight = true;
			}
		else 
			{
			movingRight = false;
			v.x=0;
			}
    }
	
    public void moveRightL(boolean slowMode)
    {
	//moves Right LATER: just sets velocity (advance used later)
	double speedC = paddleSpeed;
	if(slowMode) speedC = paddleSpeed/paddleSlowFactor;
	
		if (x.x + effRadius <= boardWidth - speedC)
			{
			//x.x += speedC;
			v.x = speedC;
			movingRight = true;
			}
		else if(x.x + effRadius < boardWidth)
			{
			double dx = boardWidth-x.x-effRadius;
			//x.x+= dx;
			v.x = dx;
			movingRight = true;
			}
		else 
			{
			movingRight = false;
			v.x=0;
			}
    }
	
	public void advance(double dt)
	{
	x.scaleAdd(dt, v, x);
	}

    public void paint(Graphics g) 
    {
	g.setColor (Color.BLUE);

	if(hasSticky) g.setColor (Color.GREEN);
	if(hasGold) g.setColor (Constants.GOLD); //Gold
	if(hasGod) g.setColor (new Color(150,0,255)); //Purple
	if(ArkanoidApplet.gravityOn) g.setColor(Constants.PINK);
	if(ArkanoidApplet.stronglyCurved) g.setColor(Color.WHITE);
	Color pC = g.getColor();
	int r = pC.getRed();
	int gr = pC.getGreen();
	int b = pC.getBlue();
	
	Color darker[] = new Color[colors];
	for(int i=0; i<colors; i++)
		{
		darker[i] = new Color((int)(r*(colors-i+colors/2)/(3*colors/2)),(int)(gr*(colors-i+colors/2)/(3*colors/2)),(int)(b*(colors-i+colors/2)/(3*colors/2)));
		g.setColor(darker[i]);
		g.fillOval ((int)(x.x - radius) + i, (int)(x.y - radius) + i, (int)(2 * radius) - 2*i, (int)(2 * radius) - 2*i);
		}		
	
	g.setColor (Color.BLACK);
	g.fillRect((int)(x.x - radius), (int)(x.y), 2 * radius, radius);
	
	if (hasLaser) 
	    {
		g.fillRect((int)(x.x - 3), (int)x.y - radius, 6, boardHeight-((int)x.y - radius));
		g.setColor (new Color(100,0,0));
		g.fillRect((int)(x.x - 2), (int)x.y - radius, 5, boardHeight-((int)x.y - radius));
		g.setColor (new Color(175,0,0));
		g.fillRect((int)(x.x - 1), (int)x.y - radius, 3, boardHeight-((int)x.y - radius));		
		g.setColor (Color.RED);
		g.fillRect((int)(x.x), (int)x.y - radius, 1, boardHeight-((int)x.y - radius));	
		//g.fillRect((int)(x.x - laserWidth/2 + laserThickness), (int)(x.y - laserHeight + laserThickness), laserWidth - 2*laserThickness, laserHeight);
	    }		
		
    }

}
	