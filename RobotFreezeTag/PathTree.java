
import java.awt.*;

public class PathTree {

	private Robot c;
	private PathTree left;
	private PathTree right;
	private PathTree parent;
	private boolean hasLeft = false;
	private boolean hasRight = false;

	public PathTree()
	{
	c = new Robot();
	parent = null;
	left = this;
	right = this;
	}
	
	public PathTree(Robot c)
	{
	this.c = c;
	parent = null;
	//left = null;
	//right = null;
	right = new PathTree();
	left = new PathTree();
	setLeft(left);	
	setRight(right);

	}
	
	public PathTree(Robot c, PathTree parent)
	{
	this.c = c;
	this.parent = parent;
	if(parent.left().equals(new PathTree())) 
	//if(parent.left().getValue() != null) 
	 {
	 parent.setLeft(this);
	 }
	else if (parent.right().equals(new PathTree())) parent.setRight(this);
	else System.out.println("Error, already has two children");
	
	right = new PathTree();
	left = new PathTree();
	//setLeft(left);
	//setRight(right);
	}
	
	public Robot getValue()
	{
	return c;
	}
	
	public boolean isEmpty()
	{
	return left.getValue().getC() == null && right.getValue().getC() == null;
	}
	
	public PathTree parent()
	{
	return parent;
	}
	
	public void setLeft(PathTree pt)
	{
	//if(left != null) left.setParent(null);
	this.left = pt;
	left.setParent(this);
	//timer(1000);
	}
	
	public void setRight(PathTree pt)
	{
	//if(left != null) left.setParent(null);
	this.right = pt;
	right.setParent(this);
	}
	
	public PathTree left()
	{
	return left;
	}
	
	public PathTree right()
	{
	return right;
	}
	
	public void setParent(PathTree pt)
	{
	this.parent = pt;
	}
	
	public void paint(Graphics g)
	{	
	g.setColor(new Color(200,200,200));	
	if(left.getValue().getC() != null) g.drawLine((int)c.getC().getX(),(int)c.getC().getY(),(int)left.getValue().getC().getX(),(int)left.getValue().getC().getY());	
	if(right.getValue().getC() != null) g.drawLine((int)c.getC().getX(),(int)c.getC().getY(),(int)right.getValue().getC().getX(),(int)right.getValue().getC().getY());						
	}
	
	public boolean equals(Object o)
	{
	PathTree pt = (PathTree)o;
	if(c.getC()!=null && pt.getValue().getC()!=null) return c.getC().equals(pt.getValue().getC());
	else return c.getC()==null && pt.getValue().getC() == null;
	}
	
	public String toString()
	{
	return "PathTree with Coord at " + c.getC() + " and children at " + left.getValue().getC() + " and " + right.getValue().getC();
	}
	
	public static void main(String args[])
	{
	PathTree p1 = new PathTree();
	//PathTree p2 = new PathTree(new Coord(1,1));
	//PathTree p3 = new PathTree(new Coord(10,10),p2);
	
	System.out.println(p1);
	
	//System.out.println(p2);
	//System.out.println(p3.left() == null);
	//System.out.println(p3);
	//System.out.println(p2.equals(p3));
	//System.out.println(p1.getValue() != null);
	}
	
	
}