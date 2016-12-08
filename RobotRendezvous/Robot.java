import java.awt.*;
import java.util.Vector;

public class Robot {

	Point2d x;
	Point2d xT = new Point2d(); //temp, for implicit
	//Point2d[] xRK = new Point2d[3]; //RK
	Vector2d[] k = new Vector2d[3]; //RK
	Vector2d v;
	int rad = 4;
	static double globalSpeedModifier = 1; //modified by slider
	double globalSpeed = 0.05;
	Vector<Integer[]> path = new Vector<Integer[]>();
	double angle;
	Coord c;
	int order;
	boolean dummy = false;
	Robot rp;
	Robot rm;
	
	public Robot(){
		x = new Point2d(0,0);
		v = new Vector2d(0,0);
		addToPath();
	}
	
	public Robot(Robot r){
		x = new Point2d();
		v = new Vector2d();
		x.x = r.x.x;
		x.y = r.x.y;
		v.x = r.v.x;
		v.y = r.v.y;
		order = r.order;
		addToPath();
	}
		
	public Robot(Point2d x)
	{
		this.x = x;
		v = new Vector2d(0,0);
		addToPath();
	}
		
	public Robot(Point2d x, Vector2d v)
	{
		this.x = x;
		this.v = v;
		addToPath();
	}	
	
	public void addToPath()
	{
	Integer[] c = new Integer[2];
	c[0] = (int)x.x;
	c[1] = (int)x.y;
	path.add(c);
	}
	
	public void calculateVelocity(Robot r1, Robot r2, int nRobots)
	{
	//old way
	Point2d xAvg = new Point2d(r1.x);
	xAvg.add(r2.x);
	xAvg.scale(0.5);
	v.sub(xAvg,x);
	
	//v.scale(globalSpeed*globalSpeedModifier);
	//double nRfactor = nRobots*nRobots/100;
	//if(nRobots > 40) nRfactor = 16;
	//v.scale(nRfactor);
	
	//System.out.println(v.x + " " + v.y);
	}
	
	public Vector2d returnVelocity(Robot r1, Robot r2)
	{
	//uses temp xs
	Point2d xAvg = new Point2d(r1.xT);
	xAvg.add(r2.xT);
	xAvg.scale(0.5);
	Vector2d vr = new Vector2d();
	vr.sub(xAvg,xT);
	return vr;
	//v.scale(globalSpeed*globalSpeedModifier);
	//double nRfactor = nRobots*nRobots/100;
	//if(nRobots > 40) nRfactor = 16;
	//v.scale(nRfactor);
	
	//System.out.println(v.x + " " + v.y);
	}
	
	public void updatePath()
	{	
	//x.x += v.x;
	//x.y += v.y;
	
	// draw movement if new 
	if((int)x.x != (int)path.get(path.size()-1)[0] || (int)x.y != (int)path.get(path.size()-1)[1]) addToPath();
	}
	
	public void paint(Graphics g)
	{
	if(RobotRendezvousCanvas.testing)
		{
		g.setColor(Color.BLACK);
		//g.setColor(Color.RED);
		//int f = (int)(255*angle/(2*Math.PI));
		if(order==0) g.setColor(Color.RED);
		if(order==1) g.setColor(Color.YELLOW);
		if(order==2) g.setColor(Color.GREEN);
		if(order==3) g.setColor(Color.BLUE);
		}
	else g.setColor(Color.RED);
	if(dummy) g.setColor(new Color(0,255,255));
	
	g.fillOval((int)x.x-rad,(int)x.y-rad,2*rad,2*rad);	
	}
	
	
}