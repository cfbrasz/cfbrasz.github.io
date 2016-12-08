import java.awt.Graphics;
import java.util.Vector;
import java.awt.*;

public class Rect {

	private Coord center;
	private Coord[] corners;
	private int width;
	private int height;
	private int boardWidth;
	private int boardHeight;
	private double theta; // in radians
	private double density; // water's density will be 1
	private Color c;
    private double speedAng;
    private double speedX;
    private double speedY;
	private double gravity;
	private int waterLevel;
	private int pixelsUnder = 0;
	private double multiplier;
	private boolean damping;
	
	public Rect(Coord center, int width, int height, double theta, double density, int waterLevel, int boardWidth, int boardHeight, double multiplier)
	{
	this(center, width, height, theta, density, Color.BLACK, waterLevel, boardWidth, boardHeight, multiplier, 0, 0, true);
	}
	
	public Rect(Coord center, int width, int height, double theta, double density, Color c, int waterLevel, int boardWidth, int boardHeight, double multiplier)
	{
	this(center, width, height, theta, density, c, waterLevel, boardWidth, boardHeight, multiplier, 0, 0, true);
	}
	
	public Rect(Coord center, int width, int height, double theta, double density, Color c, int waterLevel, int boardWidth, int boardHeight, double multiplier, double speedY, double speedAng, boolean damping)
	{
	this.center = center;
	this.width = width;
	this.height = height;
	this.theta = theta;
	this.density = density;
	this.c = c;
	this.speedX = 0;
	this.speedY = speedY;
	this.speedAng = speedAng;
	this.waterLevel = waterLevel;
	this.boardWidth = boardWidth;
	this.boardHeight = boardHeight;	
	this.multiplier = multiplier;
	corners = findCorners();
	gravity = 0.05*multiplier;
	this.damping = damping;
	}
	
	public void setWidth(int width)
	{
	this.width = width;
	}
	
	public void setHeight(int height)
	{
	this.height = height;
	}	
	
	public void setAngle(double angle)
	{
	theta = angle;
	}
	
	public void setDensity(double density)
	{
	this.density = density;
	}
	
	public void switchDamping()
	{
	damping = !damping;
	}
	
	public Coord[] findCorners()
	{
	double centerX = center.getX();
	double centerY = center.getY();
	
	Coord[] fcorners = new Coord[4]; // nw, ne, se, sw
	fcorners[0] = new Coord(-(double)width/2,-(double)height/2);
	fcorners[1] = new Coord((double)width/2,-(double)height/2);
	fcorners[2] = new Coord((double)width/2,(double)height/2);
	fcorners[3] = new Coord(-(double)width/2,(double)height/2);
	
	for(int i=0; i<4; i++)
		{
		fcorners[i].rotate(theta);
		fcorners[i].moveX(centerX);
		fcorners[i].moveY(centerY);
		}	
	/*
	corners[0] = new Coord(centerX-(double)width,centerY+(double)height);
	corners[1] = new Coord(centerX+(double)width,centerY+(double)height);
	corners[2] = new Coord(centerX+(double)width,centerY-(double)height);
	corners[3] = new Coord(centerX-(double)width,centerY-(double)height);
	*/
	
	// rotate:
	
	
	return fcorners;
	}
	
	public int[] getXPoints()
	{
	int[] xPoints = new int[4];
	for(int i=0; i<4; i++)
		{
		xPoints[i] = (int)corners[i].getX();
		}
	return xPoints;
	}
	
	public int[] getYPoints()
	{
	int[] yPoints = new int[4];
	for(int i=0; i<4; i++)
		{
		yPoints[i] = (int)corners[i].getY();
		}
	return yPoints;
	}
	
	public double[] getBuoyantForces()
	{
	//post: returns the angular acceleration in slot 0 and the linear accleration in slot 1
	double[] result = new double[2];
	result[0] = 0;
	result[1] = 0;
	double topSlope = Math.tan(theta);
	double xStart = corners[0].getX();
	double yStart = corners[0].getY();
	pixelsUnder = 0;	
	double mass = density*(width+1)*(height+1);
	double momentOfInertia = mass*(width*width+height*height)/12;
	
	Coord cCur = new Coord(xStart,yStart);
	for(int i=0; i<width+1; i++)
		{
		for(int j=0; j<height+1; j++)
			{
			double xLoc = xStart+i*Math.cos(theta)+j*Math.sin(theta);
			double yLoc = yStart-i*Math.sin(theta)+j*Math.cos(theta);
			cCur.setX(xLoc);
			cCur.setY(yLoc);
			
			Vector2D vCur = new Vector2D(cCur,center);
			
			double dAngle = vCur.angle()-Math.PI/2;
			if(dAngle<-Math.PI) dAngle+=2*Math.PI;
			double dTorque = -Math.sin(dAngle)*cCur.dist(center)*gravity/momentOfInertia; // actually this is angular acceleration
			double dLinearAcc = 0;
			
			if(yLoc>waterLevel) // buoyant force
				{
				dTorque = -dTorque*(1-density); // if block density is close to 1, won't be much of a buoyant force at all
				dLinearAcc = -gravity/mass; //density of water is 1, minus sign because upward on screen;
				pixelsUnder++;
				}
			
			result[0] += dTorque;	
			result[1] += dLinearAcc;	
			
			/*
			System.out.println("i = " + i);		
			System.out.println("cCur = " + cCur);	
			System.out.println("dAngle = " + dAngle);		
			System.out.println("dTorque = " + dTorque);	
			System.out.println("***************");		
			*/

			//cCur.moveX(Math.cos(theta));
			//cCur.moveY(Math.sin(theta));
			if(height>150)
				{
				j++;
				}
			}		
		if(width>150)
			{
			i++;
			}
		}
	//System.out.println(center);
	//System.out.println(result[1]);
	//System.out.println(corners[1] + " and " + corners[2]);
	//System.out.println(Math.PI/2);
	if(height>150)
		{
		result[0]=2*result[0];
		result[1]=2*result[1];
		}
	if(width>150)
		{		
		result[0]=2*result[0];
		result[1]=2*result[1];
		}
	return result;
	}
	
			
	/*
	public double getBuoyantForce()
	{
	// Need area under water: use trick for rectangles that it'll always be a trapezoid
	
	int[] yPoints = getYPoints();
	if(Coord.max(yPoints,4) < waterLevel) return 0; //nothing reaches water
		
	boolean[] cornerUnder = new boolean[4];
	int nCornersUnder = 0;
	for(int i=0; i<4; i++)
		{
		if(yPoints[i] < waterLevel) cornerUnder[i] = true;
		nCornersUnder++;
		}
	if (nCornersUnder == 1) //have a triangle
		{
		}
		
	Edge[] edges = new Edge[4]; // north, east, south, west
	boolean[] edgeIntersects = new boolean[4];
	
	Edge water = new Edge(new Coord(0,waterLevel), new Coord(boardWidth,waterLevel));
	for(int i=0; i<4; i++)
		{
		edges[i] = new Edge(corners[i],corners[(i+1)%4]);
		if(edges[i].intersects(water)) edgeIntersects[i] = true;
		}
	
	
	return 0;
	}
	*/
	
	public int getPixelsUnder()
	{
	return pixelsUnder;
	}
	
	public double getSpeedY()
	{
	return speedY;
	}
	
	public void setWaterLevel(int waterLevel)
	{
	this.waterLevel = waterLevel;
	}
	
	public void update()
    {
	corners = findCorners();
	theta += speedAng;
	double[] buoyancies = getBuoyantForces();	
	speedAng += buoyancies[0];
	if(pixelsUnder>0 && damping) speedAng = speedAng*0.9;
	//speedAng = speedAng*Math.pow(0.995,multiplier);
	
	center.moveX(speedX);
	center.moveY(speedY);
	speedY += gravity;
	speedY += buoyancies[1];
	if(pixelsUnder>0 && damping) speedY = speedY*0.9;
	// drag proportional to velocity here. Could also be prop to v^2
	//speedY = speedY*Math.pow(0.99,multiplier);
    }
	
	public void paint(Graphics g)
	{	
	g.setColor(c);	
	g.fillPolygon(getXPoints(),getYPoints(),4);	
	g.setColor(Color.BLACK);
	g.drawPolygon(getXPoints(),getYPoints(),4);
	}
	
}