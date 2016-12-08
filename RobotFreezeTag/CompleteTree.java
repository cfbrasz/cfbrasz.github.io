import java.util.Vector;
import java.awt.*;

public class CompleteTree {

	private Vector<Robot> robots;
	private Vector<PathTree> pathTrees;
	private Vector<Robot> copiedRobots;
	private Vector<Robot> copiedPathTrees;
	private Vector<Coord> pointsLeft;
	private Vector<Coord> points;
	private Vector<CompleteTree> children;
	private CompleteTree parent;
	private int size;

	public CompleteTree()
	{
	robots = new Vector<Robot>();
	size = 0;
	children = new Vector<CompleteTree>();
	robots.add(new Robot());	
	parent = null;
	children.add(this);
	}
	
	public CompleteTree(Robot r, PathTree pt, Vector<Coord> pointsLeft)
	{
	size = 1;
	robots = new Vector<Robot>();
	pathTrees = new Vector<PathTree>();
	children = new Vector<CompleteTree>();
	this.pointsLeft = pointsLeft;
	Vector<Coord> points = new Vector<Coord>();
	for (int j = 0; j<pointsLeft.size(); j++)
		{
		Coord cC = pointsLeft.get(j).copy();
		points.add(cC);
		} 
		
	robots.add(r);
	pathTrees.add(pt);
	parent = null;
	children.add(new CompleteTree());
	}
	
	public CompleteTree(Vector<Robot> robots, Vector<PathTree> pathTrees, Vector<Coord> pointsLeft, CompleteTree parent)
	{
	children = new Vector<CompleteTree>();
	this.robots = robots;
	this.pathTrees = pathTrees;
	this.pointsLeft = pointsLeft;
	this.parent = parent;
	Vector<Coord> points = new Vector<Coord>();
	for (int j = 0; j<pointsLeft.size(); j++)
		{
		Coord cC = pointsLeft.get(j).copy();
		points.add(cC);
		} 
	size = robots.size();
	}
	
	public CompleteTree(Robot r, Robot r2, PathTree pt, PathTree pt2, Vector<Coord> pointsLeft, CompleteTree parent)
	{
	size = parent.size() + 2;
	this.parent = parent;
	children = new Vector<CompleteTree>();
	this.pointsLeft = pointsLeft;
		
	copyRobots();
	
	robots.add(r);
	robots.add(r2);
	pathTrees.add(pt);
	pathTrees.add(pt2);
	children.add(new CompleteTree());
	}
	
	public int size()
	{
	return size;
	}
	
	public CompleteTree copy()
	{
	Vector<Robot> newRobots = new Vector<Robot>();
	Vector<PathTree> newPathTrees = new Vector<PathTree>();
	
	
	for(int i=0; i<this.getRobots().size(); i++)
		{
		Robot r = new Robot(this.getRobots().get(i));
		newRobots.add(r);
		}
		
	//PathTree ptStart = new PathTree(robots.get(0));
	//newPathTrees.add(ptStart);
		
	for(int i=0; i<this.getRobots().size(); i++)
		{
		Robot rCur = this.getRobots().get(i);
		int targetIndex = -1;
		for(int j=0; j<this.getRobots().size(); j++)
			{
			if(rCur.getTarget() != null && rCur.getTarget().getC().equals(this.getRobots().get(j).getC())) 
				{
				targetIndex = j;
				break;
				}
			}
		if(targetIndex>-1) newRobots.get(i).setTarget(newRobots.get(targetIndex));
		}
	
	for(int i=0; i<this.getRobots().size(); i++)
		{	
		int parentIndex = -1;
		Robot rCur = this.getRobots().get(i);
		PathTree parentPT = rCur.getTree().parent();
		//System.out.println("rCur tree: " + rCur.getTree());
		//System.out.println("ParentPT: " + parentPT);
		if(parentPT != null)
			{
			for(int j=0; i<this.getRobots().size(); i++)
				{	
				Robot rCur2 = this.getRobots().get(j);
				
				if(parentPT != null && parentPT.getValue().getC().equals(rCur2.getC()))
					{
					parentIndex = j;
					break;
					}
				}				
				Robot rNew = newRobots.get(i);
				Robot rParent = newRobots.get(parentIndex);
				if(rParent.getTree() != null)
					{
					PathTree pt = new PathTree(rCur,rParent.getTree());
					rCur.setTree(pt);
					newPathTrees.add(pt);
					}
				else System.out.println("Parent doesn't have a tree yet");
			}			
		else
			{
			PathTree ptt = new PathTree(rCur);
			newPathTrees.add(ptt);
			newRobots.get(i).setTree(ptt);
			}	
		}
	
	
	Vector<Coord> newPointsLeft = new Vector<Coord>();
	for (int j = 0; j<pointsLeft.size(); j++)
		{
		Coord cC = pointsLeft.get(j).copy();
		newPointsLeft.add(cC);
		} 
	
	//System.out.println(newPathTrees.get(0));
	//System.out.println(pathTrees.get(0));
	
	if(parent != null) return new CompleteTree(newRobots, newPathTrees, newPointsLeft, parent.copy());
	else return new CompleteTree(newRobots, newPathTrees, newPointsLeft, null);
	}
	
	public void copyRobots()
	{
	robots = new Vector<Robot>();
	pathTrees = new Vector<PathTree>();
	
	
	for(int i=0; i<parent.getRobots().size(); i++)
		{
		Robot r = new Robot(parent.getRobots().get(i));
		robots.add(r);
		}
		
	PathTree ptStart = new PathTree(robots.get(0));
	pathTrees.add(ptStart);
		
	for(int i=0; i<parent.getRobots().size(); i++)
		{
		Robot rCur = parent.getRobots().get(i);
		int targetIndex = -1;
		for(int j=0; j<parent.getRobots().size(); j++)
			{
			if(rCur.getTarget() != null && rCur.getTarget().getC().equals(parent.getRobots().get(j).getC())) 
				{
				targetIndex = j;
				break;
				}
			}
		if(targetIndex>-1) robots.get(i).setTarget(robots.get(targetIndex));
		}
	
	for(int i=0; i<parent.getRobots().size(); i++)
		{	
		int parentIndex = -1;
		Robot rCur = parent.getRobots().get(i);
		PathTree parentPT = rCur.getTree().parent();
		if(parentPT != null)
			{
			for(int j=0; i<parent.getRobots().size(); i++)
				{	
				Robot rCur2 = parent.getRobots().get(j);
				
				if(parentPT != null && parentPT.getValue().getC().equals(rCur2.getC()))
					{
					parentIndex = j;
					break;
					}
				}				
				Robot rNew = robots.get(i);
				Robot rParent = robots.get(parentIndex);
				if(rParent.getTree() != null)
					{
					PathTree pt = new PathTree(rCur,rParent.getTree());
					rCur.setTree(pt);
					pathTrees.add(pt);
					}
				else System.out.println("Parent doesn't have a tree yet");
			}
		else
			{
			PathTree ptt = new PathTree(rCur);
			pathTrees.add(ptt);
			}			
		}
	
	}
	
	public Vector<Robot> getRobots()
	{
	return robots;
	}
	
	public Vector<PathTree> getPathTrees()
	{
	return pathTrees;
	}
	
	public CompleteTree parent()
	{
	return parent;
	}
	
	public Vector<Coord> pointsLeft()
	{
	return pointsLeft;
	}
	
	public String toString()
	{
	String result = robots.size() + " robots.\n";
	for(int i=0; i<robots.size(); i++)
		{
		result = result + robots.get(i).getC() + "\n";
		}
	return result;
	}
	
}