import java.awt.*;

public class Robot {

	private Coord c;
	private Coord cO;
	double totalD;
	double speed = 4.0;
	double slope;
	double dX = 0;
	double dY = 0;
	double sign;
	Robot target;
	PathTree tree;
	boolean asleep = true;
	
	public Robot(){
		c = null;
	}
	
	public Robot(Robot r){
		c = new Coord(r.getC().getX(),r.getC().getY());
	}
		
	public Robot(Coord c){
		this.c = c;
	}
	
	public Robot(Coord c, Robot target){
		this.c = c;
		cO = new Coord(c.getX(),c.getY());
		this.target = target;
	}
	
	public void wake(){
		asleep = false;
	}
	
	public boolean isAwake(){
		return !asleep;
	}
	
	public Coord getC(){
		return c;
	}
	
	public double getTotalD(){
		return totalD;
	}
	
	public double totalDistance() {
	totalD=0;
	PathTree curTree=tree;
	while (curTree.parent() != null) {
		totalD += curTree.getValue().getC().dist(curTree.parent().getValue().getC());
		curTree = curTree.parent();
		}
	return totalD;
	}
	
	public Robot getTarget(){
		return target;
	}
	
	public void setTree(PathTree tree)
	{
	this.tree = tree;
	}
	
	public void setSpeed(double value)
	{
	double old = speed;
	speed = value;
	dX = dX*value/old;
	dY = dY*value/old;
	}
	
	public PathTree getTree()
	{
	return tree;
	}
	
	public void setTarget(Robot r){
		target = r;
		slope = c.mOf(target.getC());
		dX = speed/Math.sqrt(1.0+slope*slope);
		//System.out.println(slope);
		//System.out.println(dX);
		dY = slope*dX;
		if (c.getX()>target.getC().getX()) sign = -1.0;
		else sign = 1.0;
		dX = sign*dX;
		dY = sign*dY;
	}
	
	public boolean reachedTarget()
	{
	boolean result = false;
	if(target != null) result = c.dist(target.getC()) < speed;///1.9;
	if(result) 
		{
		c.setX(target.getC().getX());
		c.setY(target.getC().getY());
		}
	return result;
	}
	
	public void stop()
	{
	dX = 0;
	dY = 0;
	}
	
	public void update(){
	
		c.moveX(dX);
		//System.out.println(c.getX());
		c.moveY(dY);
	}
	
	public void paint(Graphics g){
	if (asleep)	g.setColor(Color.RED);
	else g.setColor(Color.GREEN);
	g.fillOval((int)c.getX()-4,(int)c.getY()-4,8,8);
	}
	
	
}