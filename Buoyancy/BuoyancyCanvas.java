import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.awt.image.BufferedImage;

public class BuoyancyCanvas extends Canvas implements MouseListener  {

	private double multiplier;
	private boolean testing = false;
	private int boardWidth = 600;
	private int boardHeight = 500;
	private int startWidth = 200;
	private int startHeight = 40;
	private BufferedImage buffer;
	private Graphics bufG;
	private int iter = 0;
	private int waterLevelDefault = 300;// dist from top of screen to water
	private int waterLevel = waterLevelDefault;
	private Rect r;
	private double maxAccel = 0;
	private boolean enclosed = true;
	private int startVertPosition = 200;
	private double startVerticalSpeed = 0;
	private double startAngularSpeed = 0;
	private double startAngle = -35*Math.PI/180;
	private double startDensity = 0.4;
	private boolean damping = true;
	
	public BuoyancyCanvas(double multiplier)
	{	
	addMouseListener( this );
	buffer = new BufferedImage(boardWidth, boardHeight, 
      BufferedImage.TYPE_INT_RGB);
	bufG = buffer.getGraphics();
	bufG.setColor(Color.WHITE);
	bufG.fillRect(0,0,boardWidth,boardHeight);	
	this.multiplier = multiplier;
	
	/*
	r = new Rect(new Coord(250,20),300,20,70.*Math.PI/180.,0.5,new Color(180,100,50),waterLevel, 
		boardWidth, boardHeight, multiplier, -10, 0.2);		
	*/	
	/*	
	r = new Rect(new Coord(250,50),200,100,70.*Math.PI/180.,0.4,new Color(180,100,50),waterLevel, 
		boardWidth, boardHeight, multiplier, 0, 0.05);
	*/	
		
	createBlock();	
		
		// last one violates conservation of energy when we have no damping: bounces higher and higher...
		// also thin blocks don't damp as easily
	}
	
	public void setWidth(int width)
	{
	r.setWidth(width);
	startWidth = width;
	}
	
	public void setHeight(int height)
	{
	r.setHeight(height);
	startHeight = height;
	}
	
	public void setAngle(double angle)
	{
	r.setAngle(angle);
	startAngle = angle;
	}
	
	public void  setStartVertPosition(int startVertPosition)
	{
	this.startVertPosition = startVertPosition;
	}
	
	public void setStartVerticalSpeed(double startVerticalSpeed)
	{
	this.startVerticalSpeed = startVerticalSpeed;
	}
	
	public void setStartAngularSpeed(double startAngularSpeed)
	{
	this.startAngularSpeed = startAngularSpeed;
	}
	
	public void setStartDensity(double startDensity)
	{
	r.setDensity(startDensity);
	this.startDensity = startDensity;
	}
	
	public void switchDamping()
	{
	r.switchDamping();
	damping = !damping;
	}
	
	public void createBlock()
	{
	r = new Rect(new Coord(boardWidth/2,startVertPosition),startWidth,startHeight,startAngle,startDensity,new Color(180,100,50),waterLevel, 
		boardWidth, boardHeight, multiplier, startVerticalSpeed, startAngularSpeed, damping);
	}
	
	public void iterate()
	{
	r.update();
	
	if(enclosed)
		{
		waterLevel = waterLevelDefault-r.getPixelsUnder()/boardWidth;
		r.setWaterLevel(waterLevel);
		}
	
	double cAccel = r.getBuoyantForces()[1];
	if(cAccel<maxAccel) maxAccel = cAccel;
	else
		{
		//if(maxAccel > -900) System.out.println(maxAccel);
		maxAccel = -999.;
		}
	if(cAccel>=0) maxAccel=1;
	//repaint();
	
	//System.out.println(r.getSpeedY());
	}
	
	public void mousePressed(MouseEvent e)
	{
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
	
	public void update(Graphics g)
    {
	if (!buffer.equals(null)) {
	bufG = buffer.getGraphics();
	bufG.setColor(Color.WHITE);
	bufG.fillRect(0,0,boardWidth,boardHeight);
	bufG.dispose();
	
	paint(g);	
	g.dispose();
	}
	}
	
	public void paint(Graphics g)
	{
	if (!buffer.equals(null)) {
	bufG = buffer.getGraphics();
	
	//bufG.setColor(Color.BLACK);
	bufG.setColor(new Color(230,250,255));
	bufG.fillRect(0,0,boardWidth,boardHeight);
		
	bufG.setColor(new Color(30,70,200));
	bufG.fillRect(0,waterLevel,boardWidth,boardHeight);
	
	bufG.setColor(Color.BLACK);	
	bufG.drawRect(0,0,boardWidth-1,boardHeight-1);			
				
	if(r != null) r.paint(bufG);	

	bufG.setColor(Color.BLACK);	
	bufG.drawRect(0,waterLevel,boardWidth-1,boardHeight-1);	
				
	g.drawImage(buffer, 0, 0, this);
	g.dispose();			
	}
		
	}
	
}