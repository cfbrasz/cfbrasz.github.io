import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.awt.image.BufferedImage;
import java.lang.Math;
import javax.swing.JApplet;

public class FluidsCanvas extends Canvas implements KeyListener,MouseListener  {

	private int boardWidth;
	private int boardHeight;
	private BufferedImage buffer;
	private Graphics bufG;
	private int res = 2; // divide boardWidth/Height by res to get number of rays per row/column that we shoot
	private int pixelsWide;
	private int pixelsHigh;
	private double[][] potential;
	private boolean[][] fixed;
	private boolean[][] fixedDeriv;
	private boolean[][] belowFixedDeriv;
	private boolean[][] rightOfFixedDeriv;
	private Vector2D[][] normal; // for locations that have a fixed derivative
	private double[][] streamFunction;
	private Vector2D[][] velocity;
	public final int VELOCITY = 0;
	public final int POTENTIAL = 1;
	public final int STREAM = 2;
	public final int VX = 3;
	public final int VY = 4;
	private int viewType = VELOCITY;
	private boolean testing = true;
	private FluidsApplet japp;
	private int nIters = 3000;
	private int nStreamlines = 24;
	private int vectorsEvery = 10;
	private double arrowLength = 1.;
	public final int NONE = 0;
	public final int STREAMLINES = 1;
	public final int VECTORS = 2;
	private int extraLines = STREAMLINES;
	private int count = 0;
	private int vCount = 0;
	double vMax = 0;
	double vAvg = 0;
	double vXMax = 0;
	double vXMin = 500;
	double vYMax = 0;
	double vYMin = 500;
	private boolean leftToRight = true;

	public FluidsCanvas(int boardWidth, int boardHeight, FluidsApplet japp)
	{
	this.boardWidth = boardWidth;
	this.boardHeight = boardHeight;
	pixelsWide = boardWidth/res;
	pixelsHigh = boardHeight/res;
	buffer = new BufferedImage(boardWidth, boardHeight, 
      BufferedImage.TYPE_INT_RGB);
	bufG = buffer.getGraphics();
	bufG.setColor(Color.WHITE);
	bufG.fillRect(0,0,boardWidth,boardHeight);
	addKeyListener(this);
	addMouseListener( this );	
	this.japp = japp;	
	
	initArrays();
	applyBoundaryConditions();
	// get potential
	for(int i=0; i<nIters; i++)
		{
		iterate();
		}
	calculateVelocities();
	repaint();
	}	
	
	public void initArrays()
	{
	potential = new double[pixelsWide][pixelsHigh];
	
	for(int i=0; i<pixelsWide; i++)
		{
		for(int j=0; j<pixelsHigh; j++) 
			{
			potential[i][j] = 0.5;
			}
		}
	fixed = new boolean[pixelsWide][pixelsHigh];
	fixedDeriv = new boolean[pixelsWide][pixelsHigh];
	belowFixedDeriv = new boolean[pixelsWide][pixelsHigh];
	rightOfFixedDeriv = new boolean[pixelsWide][pixelsHigh];
	streamFunction = new double[pixelsWide][pixelsHigh];
	velocity = new Vector2D[pixelsWide][pixelsHigh];
	normal = new Vector2D[pixelsWide][pixelsHigh];
	
	//System.out.println(fixed[5][5]);
	}
	
	public void applyBoundaryConditions()
	{	
	if(leftToRight) leftToRight();
	else rightToLeft();
	//circle();
	diagonal();
	//roundingCorner();
	//verticalBlock();
	//square();
	}

	public void loadPreset(String name)
	{
	initArrays();
	if(name.equals("None"))
		{	
		}
	else if(name.equals("Vertical Wall"))
		{	
		verticalBlock();
		}
	else if(name.equals("Diagonal Wall"))
		{	
		diagonal();
		}
	else if(name.equals("Square"))
		{	
		square();
		}
	else if(name.equals("Diamond"))
		{	
		diamond();
		}
	else if(name.equals("Circle"))
		{	
		}
	redo();
	}
		
	
	public void setViewType(int type)
	{
	viewType = type;
	repaint();
	}
	
	public void setExtraLinesType(int type)
	{
	extraLines = type;
	repaint();
	}
	
	public void setLeftToRight(boolean val)
	{
	leftToRight = val;
	redo(nIters,res);
	}
	
	public void redo()
	{
	vCount = 0;
	vMax = 0;
	vAvg = 0;
	vXMax = 0;
	vYMax = 0;
	vXMin = 500;
	vYMin = 500;
	applyBoundaryConditions();
	for(int i=0; i<nIters; i++)
		{
		iterate();
		}
	calculateVelocities();
	repaint();	
	}
	
	public void redo(int nIters, int res)
	{
	vCount = 0;
	vMax = 0;
	vAvg = 0;
	vXMax = 0;
	vYMax = 0;
	vXMin = 500;
	vYMin = 500;
	this.res = res;
	this.nIters = nIters;
	pixelsWide = boardWidth/res;
	pixelsHigh = boardHeight/res;	
	initArrays();
	applyBoundaryConditions();
	for(int i=0; i<nIters; i++)
		{
		iterate();
		}
	calculateVelocities();
	repaint();
	}	
	
	public void leftToRight()
	{
	double fac = 0.5;
	for(int j=0; j<pixelsHigh; j++) 
		{
		potential[0][j] = 1;
		fixed[0][j] = true;
		potential[pixelsWide-1][j] = 0;
		fixed[pixelsWide-1][j] = true;
		}
		//fixed[0][pixelsHigh/2] = true;
	}
	
	public void rightToLeft()
	{
	for(int j=0; j<pixelsHigh; j++) 
		{
		potential[0][j] = 0;
		fixed[0][j] = true;
		potential[pixelsWide-1][j] = 1;
		fixed[pixelsWide-1][j] = true;
		}
	}
	
	public void verticalBlock()
	{
	for(int j=0; j<pixelsHigh/4; j++) 
		{
		fixedDeriv[pixelsWide/2][3*pixelsHigh/8 + j] = true;
		rightOfFixedDeriv[pixelsWide/2+1][3*pixelsHigh/8 + j] = true;
		fixed[pixelsWide/2+1][3*pixelsHigh/8 + j] = true;
		normal[pixelsWide/2][3*pixelsHigh/8 + j] = new Vector2D(-1,0);
		}
	}
	
	public void square()
	{
	//left
	for(int j=0; j<pixelsHigh/4; j++) 
		{
		fixedDeriv[3*pixelsWide/8][3*pixelsHigh/8 + j] = true;
		//rightOfFixedDeriv[pixelsWide/2+1][3*pixelsHigh/8 + j] = true;
		fixed[3*pixelsWide/8+1][3*pixelsHigh/8 + j] = true;
		normal[3*pixelsWide/8][3*pixelsHigh/8 + j] = new Vector2D(-1,0);
		}
		
	//right
	for(int j=0; j<pixelsHigh/4; j++) 
		{
		fixedDeriv[5*pixelsWide/8][3*pixelsHigh/8 + j] = true;
		//rightOfFixedDeriv[pixelsWide/2+1][3*pixelsHigh/8 + j] = true;
		fixed[5*pixelsWide/8+1][3*pixelsHigh/8 + j] = true;
		normal[5*pixelsWide/8][3*pixelsHigh/8 + j] = new Vector2D(1,0);
		}
		
	//top
	for(int j=0; j<pixelsWide/4; j++) 
		{
		fixedDeriv[3*pixelsWide/8 + j][5*pixelsHigh/8] = true;
		//rightOfFixedDeriv[pixelsWide/2+1][3*pixelsHigh/8 + j] = true;
		fixed[3*pixelsWide/8 + j][5*pixelsHigh/8 + 1] = true;
		normal[3*pixelsWide/8 + j][5*pixelsHigh/8] = new Vector2D(0,1);
		}
		
	//bottom	
	for(int j=0; j<pixelsWide/4; j++) 
		{
		fixedDeriv[3*pixelsWide/8 + j][3*pixelsHigh/8] = true;
		//rightOfFixedDeriv[pixelsWide/2+1][3*pixelsHigh/8 + j] = true;
		fixed[3*pixelsWide/8 + j][3*pixelsHigh/8 + 1] = true;
		normal[3*pixelsWide/8 + j][3*pixelsHigh/8] = new Vector2D(0,1);
		}
		
	}
	
	public void diagonal()
	{
	for(int j=0; j<pixelsHigh/4; j++) 
		{
		//left diag
		
		Vector2D nLeftUp = new Vector2D(-1,1);
		Vector2D nLeftDown = new Vector2D(-1,-1);
		normal[pixelsWide/2 -pixelsWide/4 + j][pixelsHigh/2 + j] = nLeftUp;
		fixedDeriv[pixelsWide/2 -pixelsWide/4 + j][pixelsHigh/2 + j] = true;
		fixed[pixelsWide/2 -pixelsWide/4 + j + 1][pixelsHigh/2 + j] = true;
		normal[pixelsWide/2 -pixelsWide/4 + j][pixelsHigh/2 - j] = nLeftDown;
		fixedDeriv[pixelsWide/2 -pixelsWide/4 + j][pixelsHigh/2 - j] = true;
		fixed[pixelsWide/2 -pixelsWide/4 + j + 1][pixelsHigh/2 - j] = true;
		
		
		//right diag
		Vector2D nRightUp = new Vector2D(1,1);		
		Vector2D nRightDown = new Vector2D(1,-1);		
		normal[pixelsWide/2 +pixelsWide/4 - j][pixelsHigh/2 + j] = nRightUp;
		fixedDeriv[pixelsWide/2 +pixelsWide/4 - j][pixelsHigh/2 + j] = true;
		fixed[pixelsWide/2 +pixelsWide/4 - j + 1][pixelsHigh/2 + j] = true;
		normal[pixelsWide/2 +pixelsWide/4 - j][pixelsHigh/2 - j] = nRightDown;
		fixedDeriv[pixelsWide/2 +pixelsWide/4 - j][pixelsHigh/2 - j] = true;
		fixed[pixelsWide/2 +pixelsWide/4 - j + 1][pixelsHigh/2 - j] = true;
		
		}
		/* //BAD
		double midPot = 0.95;
	for(int j=0; j<pixelsHigh/4; j++) 
		{
		double midPot = 0.95;
		//left diag
		potential[pixelsWide/2 -pixelsWide/4 + j][pixelsHigh/2 + j] = midPot;
		fixed[pixelsWide/2 -pixelsWide/4 + j][pixelsHigh/2 + j] = true;
		potential[pixelsWide/2 -pixelsWide/4 + j][pixelsHigh/2 + j + 1] = midPot;
		fixed[pixelsWide/2 -pixelsWide/4 + j][pixelsHigh/2 + j + 1] = true;
		potential[pixelsWide/2 -pixelsWide/4 + j][pixelsHigh/2 - j] = midPot;
		fixed[pixelsWide/2 -pixelsWide/4 + j][pixelsHigh/2 - j] = true;
		potential[pixelsWide/2 -pixelsWide/4 + j][pixelsHigh/2 - j - 1] = midPot;
		fixed[pixelsWide/2 -pixelsWide/4 + j][pixelsHigh/2 - j - 1] = true;
		
		//right diag
		
		potential[pixelsWide/2 +pixelsWide/4 - j][pixelsHigh/2 + j] = midPot/2;
		fixed[pixelsWide/2 +pixelsWide/4 - j][pixelsHigh/2 + j] = true;
		potential[pixelsWide/2 +pixelsWide/4 - j][pixelsHigh/2 + j + 1] = midPot/2;
		fixed[pixelsWide/2 +pixelsWide/4 - j][pixelsHigh/2 + j + 1] = true;
		potential[pixelsWide/2 +pixelsWide/4 - j][pixelsHigh/2 - j] = midPot/2;
		fixed[pixelsWide/2 +pixelsWide/4 - j][pixelsHigh/2 - j] = true;
		potential[pixelsWide/2 +pixelsWide/4 - j][pixelsHigh/2 - j - 1] = midPot/2;
		fixed[pixelsWide/2 +pixelsWide/4 - j][pixelsHigh/2 - j - 1] = true;
		
		}
		*/
	}
	
	public void roundingCorner()
	{
	for(int j=0; j<pixelsHigh/2; j++) 
		{
		potential[pixelsWide/2][j] = 0.5;
		fixed[pixelsWide/2][j] = true;
		potential[pixelsWide-1][pixelsHigh/2 + j] = 0;
		fixed[pixelsWide-1][pixelsHigh/2 + j] = true;
		}
	for(int i=0; i<pixelsWide/2; i++) 
		{
		potential[i][0] = 1;
		fixed[i][0] = true;
		potential[pixelsWide/2 + i][pixelsHigh/2] = 0.5;
		fixed[pixelsWide/2 + i][pixelsHigh/2] = true;
		}
	}
		
	
	public void iterate()
	{
	// iterate once
	
	for(int i=0; i<pixelsWide; i++)
		{
		
		/*
	for(int i=pixelsWide-1; i>=0; i--)
		{
		*/
		//int iSave = i;
		//if(count%2 == 0) i = pixelsWide-1-i;
		for(int j=0; j<pixelsHigh; j++) 
			{
			if(!fixed[i][j] && !fixedDeriv[i][j])
				{
				if(i==0)
					{
					if(j==0) potential[i][j] = (potential[i+1][j] + potential[i][j+1])/2;
					else if(j==pixelsHigh-1) potential[i][j] = (potential[i+1][j] + potential[i][j-1])/2;
					else potential[i][j] = (potential[i+1][j] + potential[i][j+1] + potential[i][j-1])/3;
					}
				else if(i==pixelsWide-1)
					{
					if(j==0) potential[i][j] = (potential[i-1][j] + potential[i][j+1])/2;
					else if(j==pixelsHigh-1) potential[i][j] = (potential[i-1][j] + potential[i][j-1])/2;
					else potential[i][j] = (potential[i-1][j] + potential[i][j+1] + potential[i][j-1])/3;					
					}
				else if(j==0) // note that i != 0 or pixelsWide here
					{
					potential[i][j] = (potential[i-1][j] + potential[i][j+1] + potential[i+1][j])/3;					
					}
				else if(j==pixelsHigh-1) // note that i != 0 or pixelsWide here
					{
					potential[i][j] = (potential[i-1][j] + potential[i][j-1] + potential[i+1][j])/3;					
					}
				else // middle pixel
					{
					potential[i][j] = (potential[i-1][j] + potential[i][j-1] + potential[i+1][j] + potential[i][j+1])/4;					
					}
				}
			else if(fixedDeriv[i][j])
				{
				if(normal[i][j].getY() == 0)
					{
					/*
					potential[i][j] = potential[i-1][j];
					potential[i+1][j] = potential[i-1][j];
					*/
					if(i>0) potential[i][j] = potential[i-1][j];
					if(i<pixelsWide-2) potential[i+1][j] = potential[i+2][j];
					/*
					potential[i][j] = (potential[i+1][j] + potential[i-1][j])/2;
					potential[i-1][j] = potential[i][j];
					potential[i+1][j] = potential[i][j];
					*/
					}
				else if(normal[i][j].getX() == 0)
					{
					if(j>0) potential[i][j] = potential[i][j-1];
					if(j<pixelsHigh-2) potential[i][j+1] = potential[i][j+2];					
					}
				else if(normal[i][j].getSlope() > 0) // 1st or 3rd quadrant
					{
					double dPhiy = potential[i][j] - potential[i][j-1];
					double nyOvernx = normal[i][j].getY()/normal[i][j].getX();
					potential[i][j] = potential[i-1][j] - nyOvernx*dPhiy;	
					
					dPhiy = potential[i+1][j+1] - potential[i+1][j];
					potential[i+1][j] = potential[i+2][j] + nyOvernx*dPhiy;	
					/*
					double dPhiy = potential[i][j+1] - potential[i][j-1];
					double nyOvernx = normal[i][j].getY()/normal[i][j].getX();
					potential[i+1][j] = potential[i-1][j] - nyOvernx*dPhiy;		
					double dPhix = potential[i+1][j] - potential[i-1][j];
					potential[i][j+1] = potential[i][j-1] - dPhix/nyOvernx;
					potential[i][j] = (potential[i-1][j] + potential[i][j-1] + potential[i+1][j] + potential[i][j+1])/4;
					*/
					}
				else if(normal[i][j].getSlope() < 0) //2nd or 4th quadrant
					{
					double dPhiy = potential[i][j+1] - potential[i][j];
					double nyOvernx = normal[i][j].getY()/normal[i][j].getX();
					potential[i][j] = potential[i-1][j] - nyOvernx*dPhiy;	
					
					dPhiy = potential[i+1][j] - potential[i+1][j-1];
					potential[i+1][j] = potential[i+2][j] + nyOvernx*dPhiy;						
					}
				}
			else if(rightOfFixedDeriv[i][j])
				{
				//potential[i-1][j] = potential[i][j];
				}
			}
		//i = iSave;
		}
		count++;
	}
	
	public void calculateVelocities()
	{
	// v = gradient(potential)
	for(int i=0; i<pixelsWide; i++)
		{
		for(int j=0; j<pixelsHigh; j++) 
			{
			/*
			if(i==0)
				{
				if(j==0) velocity[i][j] = new Vector2D(potential[i+1][j]-potential[i][j], potential[i][j+1]-potential[i][j]);
				else if(j==pixelsHigh-1) velocity[i][j] = new Vector2D(potential[i+1][j]-potential[i][j], potential[i][j]-potential[i][j-1]);
				else velocity[i][j] = new Vector2D(potential[i+1][j]-potential[i][j], (potential[i][j+1]-potential[i][j-1])/2);
				}
			else if(i==pixelsWide-1)
				{
				if(j==0) velocity[i][j] = new Vector2D(potential[i][j]-potential[i-1][j], potential[i][j+1]-potential[i][j]);
				else if(j==pixelsHigh-1) velocity[i][j] = new Vector2D(potential[i][j]-potential[i-1][j], potential[i][j]-potential[i][j-1]);
				else velocity[i][j] = new Vector2D(potential[i][j]-potential[i-1][j], (potential[i][j+1]-potential[i][j-1])/2);				
				}
			else if(j==0) // note that i != 0 or pixelsWide here
				{
				velocity[i][j] = new Vector2D((potential[i+1][j]-potential[i-1][j])/2, (potential[i][j+1]-potential[i][j]));				
				}
			else if(j==pixelsHigh-1) // note that i != 0 or pixelsWide here
				{
				velocity[i][j] = new Vector2D((potential[i+1][j]-potential[i-1][j])/2, (potential[i][j]-potential[i][j-1]));				
				}
			else // middle pixel
				{
				velocity[i][j] = new Vector2D((potential[i+1][j]-potential[i-1][j])/2, (potential[i][j+1]-potential[i][j-1])/2);					
				}
				*/
			
			
			if(i==pixelsWide-1)
				{
				if(j==pixelsHigh-1) velocity[i][j] = new Vector2D(potential[i][j]-potential[i-1][j], potential[i][j]-potential[i][j-1]);
				else velocity[i][j] = new Vector2D(potential[i][j]-potential[i-1][j], potential[i][j+1]-potential[i][j]);				
				}
			else if(j==pixelsHigh-1) velocity[i][j] = new Vector2D(potential[i+1][j]-potential[i][j], potential[i][j]-potential[i][j-1]);
			else velocity[i][j] = new Vector2D(potential[i+1][j]-potential[i][j], potential[i][j+1]-potential[i][j]);
			velocity[i][j].reverse();
			if(!((fixed[i][j] && (i>0 || i<pixelsWide-1)) || fixedDeriv[i][j]))
				{
				if(velocity[i][j].magnitude() > vMax) vMax = velocity[i][j].magnitude();
				if(velocity[i][j].getX() > vXMax) vXMax = velocity[i][j].getX();
				if(velocity[i][j].getX() < vXMin) vXMin = velocity[i][j].getX();
				if(velocity[i][j].getY() > vYMax) vYMax = velocity[i][j].getY();
				if(velocity[i][j].getY() < vYMin) vYMin = velocity[i][j].getY();
				// to make v go from high to low potential (-gradient).
				//velocity[i][j] = new Vector2D(-velocity[i][j].getX(), velocity[i][j].getY()); // re reverse y because ??
				vAvg += velocity[i][j].magnitude();
				vCount++;				
				}
				
			}
		}
	vAvg = vAvg/vCount;
	}
	
	public void mousePressed(MouseEvent e)
	{
	int pressX = e.getX();
	int pressY = e.getY();
	System.out.println("At the point (" + pressX + ", " + pressY + "), the velocity is " + velocity[pressX/res][pressY/res]);
	System.out.println("The potential is " + potential[pressX/res][pressY/res]);
	e.consume();
	}
	
	public void mouseReleased(MouseEvent e)
	{
	}
	
	
	public void mouseClicked(MouseEvent e)
	{
	}
	
	public void mouseEntered(MouseEvent e)
	{
	}
	
	public void mouseExited(MouseEvent e)
	{
	}
	
	/*
	public void mouseMoved( MouseEvent e ) {  // called during motion when no buttons are down
	mouseX = e.getX();
	mouseY = e.getY();
	//repaint();
	e.consume();
    }
	*/

	
	public void keyTyped(KeyEvent e)
    {
	//System.out.println("typed "+e);
	
    }

    public void keyPressed(KeyEvent e)
    {
	//System.out.println("pressed " + e.getKeyCode());
	
	if (e.getKeyCode() == 32) // 32 is space bar
	    {
		System.out.println(vMax);
		}
	
	if (e.getKeyCode() == 67) // 67 is "c"
	    {		
		}
	
	if (e.getKeyCode() == 68) // 68 is "d"
	    {
		//reset view:
		//direction = new Vector3D(1,0,0);
		}
	
	if (e.getKeyCode() == 69) // 69 is "e"
	    {
		}
		
	
	if (e.getKeyCode() == 61) // = and + key
	    {
		}

	if (e.getKeyCode() == 45) // - and _ key
	    {
	    }
	
	/*
	if (e.getKeyCode() == 37) moveLeft = true; // left arrow key
	if (e.getKeyCode() == 39) moveRight = true; // right arrow key
	if (e.getKeyCode() == 38) moveUp = true; // up arrow key
	if (e.getKeyCode() == 40) moveDown = true; // down arrow key
	*/
	if (e.getKeyCode() == 37) 
		{
		}
	if (e.getKeyCode() == 39) 
		{
		}
	if (e.getKeyCode() == 38) 
		{
		}
	if (e.getKeyCode() == 40) 
		{
		}
	if (e.getKeyCode() == 97) 
		{
		}
	if (e.getKeyCode() == 96) 
		{
		}
    }
	
	

    public void keyReleased(KeyEvent e)
    {
	
	/*
	// used to make paddle movement continuous
	if (e.getKeyCode() == 37) moveLeft = false; // left arrow key
	if (e.getKeyCode() == 39) moveRight = false; // right arrow key
	if (e.getKeyCode() == 38) moveUp = false; // up arrow key
	if (e.getKeyCode() == 40) moveDown = false; // down arrow key
	*/
    }
	
	public void rotateView()
    {
	
	}	
	
	
	public void update(Graphics g)
    {
	if (!buffer.equals(null)) 
		{
		bufG = buffer.getGraphics();
		//bufG.setColor(Color.BLACK);
		bufG.setColor(Color.WHITE);
		bufG.fillRect(0,0,boardWidth,boardHeight);
		bufG.fillRect(0,0,boardWidth,boardHeight);
		bufG.dispose();
		
		paint(g);	
		g.dispose();
		}
	}
	
	public Color colorOf(double value)
	{
	//0 corresponds to blue and +1 to red. If value is not within [0,1] returns black
	if(value > 1 || value < 0) return Color.BLACK;
	value = 1-value;
	int red = 0;
	int green = 0;
	int blue = 0;
	if(value<0.25)
		{
		red = 255;
		green = (int)(value*255/0.25);
		}
	else if(value<0.5)
		{
		red = 2*255 - (int)(value*255/0.25);
		green = 255;
		}
	else if(value<0.75)
		{							
		green = 255;
		blue = -2*255 + (int)(value*255/0.25);
		}
	else if(value <= 1)
		{							
		green = 255*4 - (int)(value*255/0.25);
		blue = 255;
		}
	//System.out.println(red + " " + green + " " + blue);
	//System.out.println(value);
	return new Color(red,green,blue);
	}
	
	public void paint(Graphics g)
	{
	if (!buffer.equals(null)) 
		{
			bufG = buffer.getGraphics();
			
			//bufG.setColor(Color.BLACK);
			bufG.setColor(Color.WHITE);
			bufG.fillRect(0,0,boardWidth,boardHeight);
				
			for(int i=0; i<pixelsWide; i++)
					{
					for(int j=0; j<pixelsHigh; j++)
						{	
						if(fixedDeriv[i][j])
							{
							bufG.setColor(Color.BLACK);
							}
						else
							{
							double v = -1;
							if(viewType == VELOCITY) v = velocity[i][j].magnitude()/vMax;
							if(viewType == VX) 
								{
								v = (velocity[i][j].getX()+vXMin)/(vXMax-vXMin);
								v = 0.5 + v/2;
								}
							if(viewType == VY)
								{
								v = (velocity[i][j].getY()+vYMin)/(vYMax-vYMin);
								v = 0.5 + v/2;
								}
							else if(viewType == POTENTIAL) v = potential[i][j];
							//double v = (double)i/pixelsWide;
							//System.out.println(v);
							bufG.setColor(colorOf(v));
							}
						bufG.fillRect(i*res,boardHeight-j*res,res,res);
						}
					}
			
			bufG.setColor(Color.BLACK);
			if(extraLines == STREAMLINES)
				{
				for(int i=1; i<2*nStreamlines; i=i+2)
					{
					double yS = (double)i*pixelsHigh/(2*nStreamlines);
					double xS = 0;
					if(!leftToRight) xS = pixelsWide-1;
					Vector2D vCur = velocity[(int)xS][(int)yS].unit();
					int cur = 0;
					while(xS < pixelsWide && xS >= 0 && cur < 500)
						{						
						double xN = xS + vCur.getX();
						double yN = yS + vCur.getY();
						//if(cur > 200) System.out.println(vCur);						
						if(xN > -500) bufG.drawLine((int)(xS*res),boardHeight-(int)(yS*res),(int)(xN*res),boardHeight-(int)(yN*res));
						xS = xN;
						yS = yN;
						if(xS < pixelsWide && xS >= 0 && yS < pixelsHigh && yS >= 0) vCur = velocity[(int)xS][(int)yS].unit();
						else xS = pixelsWide;
						cur++;
						//System.out.println(cur);
						}
					}
				} 
			else if(extraLines == VECTORS)
				{				
				for(int i=1; i<boardWidth/vectorsEvery; i=i+2)
						{
						for(int j=1; j<boardHeight/vectorsEvery; j=j+2)
							{	
							int xCur = i*vectorsEvery;
							int yCur = j*vectorsEvery;
							if(!fixedDeriv[i*vectorsEvery/res][j*vectorsEvery/res])
								{
								Vector2D vCur = velocity[i*vectorsEvery/res][j*vectorsEvery/res].copy();
								//System.out.println(vCur.getX());
								
								vCur.scalarMult(1/vAvg);
								Vector2D vArrowL = vCur.copy();
								vArrowL.rotate(Math.PI/8);
								Vector2D vArrowR = vCur.copy();
								vArrowR.rotate(-Math.PI/8);
								int xD = xCur + (int)(arrowLength*vCur.getX()*vectorsEvery);
								int yD = yCur + (int)(arrowLength*vCur.getY()*vectorsEvery);
								int xLD = xCur + (int)(arrowLength*vArrowL.getX()*vectorsEvery*2/3);
								int yLD = yCur + (int)(arrowLength*vArrowL.getY()*vectorsEvery*2/3);
								int xRD = xCur + (int)(arrowLength*vArrowR.getX()*vectorsEvery*2/3);
								int yRD = yCur + (int)(arrowLength*vArrowR.getY()*vectorsEvery*2/3);
								bufG.drawLine(xCur,yCur,xD,yD);
								bufG.drawLine(xD,yD,xLD,yLD);
								bufG.drawLine(xD,yD,xRD,yRD);
								}
							}
						}
				}
						
			g.drawImage(buffer, 0, 0, this);
			g.dispose();
				
		}
		
	}
	
	
}