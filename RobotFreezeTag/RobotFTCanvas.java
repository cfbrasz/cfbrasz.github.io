import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.applet.*;
import java.net.*;
import java.util.Iterator;

public class RobotFTCanvas extends Canvas implements MouseListener {
	
	private int boardWidth;
	private int boardHeight;
	private BufferedImage buffer;
	private Graphics bufG;
	private Vector<Coord> asleepPoints = new Vector<Coord>();
	private Vector<Coord> awakePoints = new Vector<Coord>();
	private Vector<Coord> points = new Vector<Coord>();
	private Vector<PathTree> pathTrees = new Vector<PathTree>();
	private Vector<Robot> robots = new Vector<Robot>();
	private Vector<PathTree> savePathTrees = new Vector<PathTree>();
	private Vector<Robot> saveRobots = new Vector<Robot>();
	private boolean clearScreen = false;	
	private boolean firstClick = true;	
	private boolean switchedMode = false;
	public static final int USER = 0;
	public static final int RANDOM = 1;
	public static final int OPTIMAL = 2;
	public static final int PLACE = 0;
	public static final int REMOVE = 1;
	public static final int DESIGNATE = 2;
	private int pathMode = 0;
	private int mouseMode = 0;
	private int treeSize = 0;
	private double speed = 4.0;
	private boolean traces = true;
	private boolean running = false;
	private boolean waitingForUser = false;
	private Random generator = new Random();
	private AudioClip yawn = null;
	private boolean yawnsOn = false;
	private Image borgImage = null;
	private boolean borg = false;
	private boolean robotic = false;
	private boolean dots = true;
	public static final int REGULAR = 0;
	public static final int GREEDY1 = 1;
	public static final int GREEDY2 = 2;
	private int algtype = REGULAR;
	//private boolean greedy = false;
	//private boolean greedy1 = false;
	 // Your image name;
	 Image borgAwake;
	 Image borgSleep;
     Image robotAwake;
	 Image robotSleep;

 // The applet base URL
     URL codeBase;

 // This object will allow you to control loading
     MediaTracker mt; 
		
	public RobotFTCanvas(int boardWidth, int boardHeight, Image borgAwake, Image borgSleep, Image robotSleep, Image robotAwake)
	{
	this.boardWidth = boardWidth;
	this.boardHeight = boardHeight;
	//this.borg = borg;
	this.borgSleep = borgSleep;
	this.borgAwake = borgAwake;
	this.robotSleep = robotSleep;
	this.robotAwake = robotAwake;
	addMouseListener( this );         
		  
	buffer = new BufferedImage(boardWidth, boardHeight, 
      BufferedImage.TYPE_INT_RGB);
	bufG = buffer.getGraphics();
	bufG.setColor(Color.WHITE);
	bufG.fillRect(0,0,boardWidth,boardHeight);
	}
		
	public void generateOptimalOrderTree()
	{
	algtype = REGULAR;
	
	//iterate through all permutations
	Vector<Coord> fastest = new Vector<Coord>();
	int nPermutations=1;
	int size = points.size();
		//System.out.println(points.size());
	double minTotal = 9999;
	Coord[] pointsArray = new Coord[size-1];
	Coord first = points.get(0);
	for(int i=1; i<size; i++)
		{
		pointsArray[i-1] = points.get(i);
		}
	
	for (Iterator i = new Permute(pointsArray); i.hasNext(); )
		{
		points = new Vector<Coord>();
		points.add(first);
	    final Coord[] curPerm = (Coord[]) i.next();
		   
		 
		
		for(int j=0; j<size-1; j++)
		{
		points.add(curPerm[j]);
		}		
		
		reset();
		
		double maxDist = 0;
		int maxJ = 0;
		double firstDist = robots.get(0).getC().dist(robots.get(1).getC());
		for(int j=1; j<size; j++)
			{		
				
			double curDist = robots.get(j).totalDistance() + firstDist;
			//System.out.println("curDist " + curDist);			
			if(curDist > maxDist)
				{
				maxDist = curDist;
				maxJ = j;
				}
			}
		//System.out.println(maxDist);
		//System.out.println("mintotal " + minTotal);
		if(maxDist< minTotal)
			{
			minTotal = maxDist;
			fastest = points;
			}		
		}
		
	points = fastest;
	// Find iteration minI of permute iterator, this is "optimal"
	reset();
	repaint();
	
	}		
	
	public void setPlaceMode()
	{
	mouseMode = PLACE;
	}
	
	public void setRemoveMode()
	{
	mouseMode = REMOVE;
	}
	
	public void setDesignateMode()
	{
	mouseMode = DESIGNATE;
	}
	
	public void setUserMode()
	{
	pathMode = USER;
	algtype = REGULAR;
	//generateClickOrderTree();
	}
	
	public void setRandomMode()
	{
	pathMode = RANDOM;
	algtype = REGULAR;
	generateRandomOrderTree();
	}
	
	public void setOptimalMode()
	{
	pathMode = OPTIMAL;
	algtype = REGULAR;
	//generateOptimalOrderTree();
	}
	
	public void generateRandomRobots(int num)
	{	
	for(int i=0; i<num; i++)
		{
		boolean pointTaken = true;
		Coord c = new Coord(-1,-1);
		int xP=-1;
		int yP=-1;
		while(pointTaken)
			{
			pointTaken = false;
			xP = generator.nextInt(boardWidth);
			yP = generator.nextInt(boardHeight);
			c = new Coord(xP,yP);
			for(int j=0; j<points.size(); j++) 
				{
				if(c.equals(points.get(j))) 
					{
					pointTaken = true;
					break;
					}
				}
			}
		Robot r = new Robot();
			Coord cSave = new Coord(xP,yP);
			points.add(cSave);
			if(!firstClick) 
				{
				//if(pathMode == USER) 
					//{
					r = new Robot(c);
					if(robots.size() == 1) // 2nd robot has the first pathTree
						{
						PathTree pt = new PathTree(r);
						pathTrees.add(pt);
						r.setTree(pt);
						}
					else
						{			
						PathTree ptt = new PathTree(r,pathTrees.get((treeSize-1)/2)); //or (i-1)/2
						pathTrees.add(ptt);
						pathTrees.get((treeSize-1)/2).getValue().setTarget(r);
						r.setTree(ptt);
						}
					repaint();
					//}	
				
				treeSize++;
				
				
				if (robots.get(0).getTarget()==null) robots.get(0).setTarget(r);
					
				}
			else 
				{
				r = new Robot(c);
				r.wake();
				firstClick = false;		
				}	
			robots.add(r);
		}
	}
	
	public void runGreedyAlgV2()
	{
	if(points.size() > 0)
		{
		algtype = GREEDY2;
		savePathTrees.removeAllElements();
		saveRobots.removeAllElements();
		double minD, closest1, closest2, temp;
		int nextRIndex, closestIndex1, closestIndex2, tempI, targetAdjuster, tempAdjuster;
		Vector<Robot> chosenRobots = new Vector<Robot>();
		Robot r, rSave;
		PathTree pt, ptSave;
		boolean firstFound, free;
		Vector<Robot> chosenRobotsSave = new Vector<Robot>();
		
		
		clearAll();
		Vector<Coord> tempV = new Vector<Coord>();
		int size = points.size();		
		for (int i = 0; i<size; i++)
		{
		if(points.get(i) != null)
			{
			Coord cCur = points.get(i).copy();
			tempV.add(cCur);
			}
		} 
		Coord cStart = tempV.get(0);
		tempV.remove(0);
		// add starting robot
		r = new Robot(cStart);
		r.wake();
		robots.add(r);
		
		rSave = new Robot(cStart.copy());
		saveRobots.add(rSave);
		chosenRobots.add(r);
		chosenRobotsSave.add(rSave);						
		
		//add first sleeping robot
		closest1=chosenRobots.get(0).getC().dist(tempV.get(0))+5;
		closestIndex1=999;
		for(int i=0; i<tempV.size(); i++) 
			{
			if(chosenRobots.get(0).getC().dist(tempV.get(i)) < closest1) 
				{
				closest1 = chosenRobots.get(0).getC().dist(tempV.get(i));
				closestIndex1 = i;
				}
			}
			
		r= new Robot(tempV.get(closestIndex1));		
		pt = new PathTree(r);
		pathTrees.add(pt);
		r.setTree(pt);
		robots.add(r);
		
		rSave = new Robot(tempV.get(closestIndex1).copy());
		tempV.remove(closestIndex1);
		ptSave = new PathTree(rSave);
		savePathTrees.add(ptSave);
		rSave.setTree(ptSave);
		saveRobots.add(rSave);
		
		chosenRobots.remove(0);
		chosenRobotsSave.remove(0);
		chosenRobots.add(r);
		chosenRobotsSave.add(rSave);
		
		while(tempV.size()>0)
			{			
			// find the unclaimed point closest to a robot that can have children
			minD = chosenRobots.get(0).getC().dist(tempV.get(0));
			int closestRobot = 0;
			int closestPoint = 0;
			
			//loop through all robots that can still have children (chosen robots)
			for(int i=0; i<chosenRobots.size(); i++)
				{
				//loop through all unclaimed points
				for(int j=0; j<tempV.size(); j++)
					{
					double curD = chosenRobots.get(i).getC().dist(tempV.get(j)) + chosenRobots.get(i).totalDistance();
					if(curD < minD)
						{
						minD = curD;
						closestRobot = i;
						closestPoint = j;
						}
					}
				}
			r = new Robot(tempV.get(closestPoint));
			pt = new PathTree(r,chosenRobots.get(closestRobot).getTree());
			pathTrees.add(pt);
			r.setTree(pt);
			chosenRobots.get(closestRobot).setTarget(r);
			robots.add(r);
			chosenRobots.add(r);
			
			rSave = new Robot(tempV.get(closestPoint).copy());
			ptSave = new PathTree(rSave,chosenRobotsSave.get(closestRobot).getTree());
			savePathTrees.add(ptSave);
			rSave.setTree(ptSave);
			chosenRobotsSave.get(closestRobot).setTarget(rSave);
			saveRobots.add(rSave);
			chosenRobotsSave.add(rSave);
			
			if(!(chosenRobots.get(closestRobot).getTree().right().equals(new PathTree()) ||
			   chosenRobots.get(closestRobot).getTree().left().equals(new PathTree()))) //no room for more children
				{
				chosenRobots.remove(closestRobot);
				chosenRobotsSave.remove(closestRobot);
				}
			tempV.remove(closestPoint);
			}
		}		
		if(robots.size() > 1) robots.get(0).setTarget(robots.get(1));
		if(saveRobots.size() > 1) saveRobots.get(0).setTarget(saveRobots.get(1));
		repaint();
		firstClick = false;
	}
	
	
	public void copyRobots()
	{
	//saveRobots and savePathTrees always maintain original hierarchies: Copy them over by recreating robots and pathTrees vectors
	robots = new Vector<Robot>();
	pathTrees = new Vector<PathTree>();	
	
	for(int i=0; i<saveRobots.size(); i++)
		{
		Robot r = new Robot(saveRobots.get(i));
		robots.add(r);
		}
		
	robots.get(0).wake();		
		
	PathTree ptStart = new PathTree(robots.get(1));
	pathTrees.add(ptStart);
	robots.get(1).setTree(ptStart);
		
	for(int i=0; i<saveRobots.size(); i++)
		{
		// set all the targets
		Robot rCur = saveRobots.get(i);
		int targetIndex = -1;
		for(int j=0; j<saveRobots.size(); j++)
			{
			if(rCur.getTarget() != null && rCur.getTarget().getC().equals(saveRobots.get(j).getC())) 
				{
				targetIndex = j;
				break;
				}
			}
		if(targetIndex>-1) robots.get(i).setTarget(robots.get(targetIndex));
		}
	
	for(int i=1; i<saveRobots.size(); i++)
		{	
		int parentIndex = -1;
		Robot rCur = saveRobots.get(i);
		//System.out.println("iteration " + i + ", current Robot " + rCur.getC());
		PathTree parentPT = rCur.getTree().parent();
		if(parentPT != null) // need to connect to parent's pathTree
			{
			for(int j=0; j<saveRobots.size(); j++)
				{	
				Robot rCur2 = saveRobots.get(j);
				
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
					PathTree pt = new PathTree(rNew,rParent.getTree());
					rNew.setTree(pt);
					pathTrees.add(pt);
					}
				else 
					{
					System.out.println("Parent doesn't have a tree yet");
					// Should never occur
					}
			}
		else // we must be at the very first pathTree
			{
			Robot rNew = robots.get(i); 
			PathTree ptt = new PathTree(rNew);
			pathTrees.add(ptt);
			rNew.setTree(ptt);
			}			
		}			
		repaint();		
	}
	
	public void runGreedyAlg()
	{	
	if(points.size() > 0)
		{
		algtype=GREEDY1;
		double minD, closest1, closest2, temp;
		int nextRIndex, closestIndex1, closestIndex2, tempI, targetAdjuster, tempAdjuster;
		Vector<Robot> awakenedRobots = new Vector<Robot>();
		Robot r;
		PathTree pt;
		boolean firstFound, free;
	
		clearAll();
		Vector<Coord> tempV = new Vector<Coord>();
		Vector<Coord> saveV = new Vector<Coord>();
		int size = points.size();

		for (int i = 0; i<size; i++)
		{		
		if(points.get(i) != null) 
			{
			Coord cCur = points.get(i).copy();
			tempV.add(cCur);
			cCur = points.get(i).copy();
			saveV.add(cCur);			
			}
		} 
		Coord cStart = tempV.get(0);
		tempV.remove(0);
		r = new Robot(cStart);
		robots.add(r);
		awakenedRobots.add(r);
		targetAdjuster = 0;
		closest1=awakenedRobots.get(0).getC().dist(tempV.get(0))+5;
		closestIndex1=999;
		for(int i=0; i<tempV.size(); i++) 
			{
			if(awakenedRobots.get(0).getC().dist(tempV.get(i)) < closest1) 
				{
				closest1 = awakenedRobots.get(0).getC().dist(tempV.get(i));
				closestIndex1 = i;
				}
			}
		r = null;
		pt = null;
		r= new Robot(tempV.get(closestIndex1));
		tempV.remove(closestIndex1);
		pt = new PathTree(r);
		pathTrees.add(pt);
		r.setTree(pt);
		robots.add(r);
		awakenedRobots.remove(0);
		awakenedRobots.add(r);
		while(tempV.size()>0)
			{
			minD = awakenedRobots.get(0).totalDistance();
			nextRIndex = 0;
			for(int i=1; i<awakenedRobots.size(); i++) 
				{
				if (awakenedRobots.get(i).totalDistance() < minD) 
					{
					minD = awakenedRobots.get(i).totalDistance();
					nextRIndex = i;
					} 
				}
			closest1 = 100000;
			closest2 = awakenedRobots.get(nextRIndex).getC().dist(tempV.get(0))+5;
			closestIndex1 = 999;
			closestIndex2 = 999;
			for(int i=0; i<tempV.size(); i++) 
				{
				if(awakenedRobots.get(nextRIndex).getC().dist(tempV.get(i)) < closest2)
					{
					closest2 = awakenedRobots.get(nextRIndex).getC().dist(tempV.get(i));
					closestIndex2 = i;
					if (closest2 < closest1) 
						{
						temp = closest1;
						closest1 = closest2;
						closest2 = temp;
						tempI = closestIndex1;
						closestIndex1 = closestIndex2;
						closestIndex2 = tempI; 
						}
					}
				}
			free = false;
			tempAdjuster = 0;
			while (!free) {
			if (pathTrees.get((pathTrees.size()-1)/2+nextRIndex-targetAdjuster+tempAdjuster).left().equals(new PathTree())) {
			free = true;
			break;
			}
			tempAdjuster++;
			}
			Coord cCur = tempV.get(closestIndex1); 
			r = null;
			pt = null;
			r = new Robot(cCur);
			pt = new PathTree(r,pathTrees.get((pathTrees.size()-1)/2+nextRIndex-targetAdjuster+tempAdjuster));
			pathTrees.add(pt);
			r.setTree(pt);
			robots.add(r); 
			awakenedRobots.add(r);
			if (closestIndex2 != 999) 
				{
				Coord cCur1 = tempV.get(closestIndex2); 
				r = null;
				pt = null;
				r = new Robot(cCur1);
				pt = new PathTree(r,pathTrees.get((pathTrees.size()-1)/2+nextRIndex-targetAdjuster+tempAdjuster));
				pathTrees.add(pt);
				r.setTree(pt);
				robots.get((pathTrees.size()-1)/2+nextRIndex-targetAdjuster+tempAdjuster).setTarget(r);
				awakenedRobots.add(r);
				robots.add(r);
				}
			if (closestIndex1 > closestIndex2) 
				{
				tempV.remove(closestIndex1);
				if (closestIndex2 != 999) tempV.remove(closestIndex2);
				} 
			else 
				{
				tempV.remove(closestIndex1);
				if (closestIndex2 != 999) tempV.remove(closestIndex2-1);
				}
			targetAdjuster = 0;
			firstFound = false;
			for (int i =0; i<pathTrees.size(); i++) 
				{
				if (pathTrees.get(i).left().equals(new PathTree()) && firstFound == false && i<pathTrees.size()/2) firstFound = true;
				if (firstFound && !(pathTrees.get(i).left().equals(new PathTree()))) targetAdjuster++;
				}
			awakenedRobots.remove(nextRIndex);
			//if (firstFound) System.out.println(firstFound);
			}
		
		if(robots.size() > 1) robots.get(0).setTarget(robots.get(1));
		robots.get(0).wake();
		firstClick = false;
		repaint();
		}
	}
	
	public void generateRandomOrderTree()
	{
	if(points.size() > 0)
		{
		clearAll();
		Vector<Coord> tempV = new Vector<Coord>();
		int size = points.size();
		
		for (int i = 0; i<size; i++)
			{
			if(points.get(i) != null) 
				{
				Coord cCur = points.get(i).copy();
				tempV.add(cCur);
				}
			}		

		size = tempV.size();
		points.removeAllElements();
		//keep first point the same
		points.add(tempV.get(0));
		tempV.removeElementAt(0);
			
		for (int i=1; i<size; i++) 
			{
			int randomIndex = generator.nextInt(size-i);
			Coord temp = tempV.get(randomIndex);
			points.add(temp);
			tempV.removeElementAt(randomIndex);
			}
		reset();
		}
	}
		
	public void generateClickOrderTree()
	{
	Coord cStart = asleepPoints.get(0);
	Robot r2 = new Robot(cStart);
	PathTree pt = new PathTree(r2);
	pathTrees.add(pt);
	for(int i=1; i<asleepPoints.size(); i++)
		{
		Coord cCur = asleepPoints.get(i);	
		Robot r = new Robot(cCur);
		PathTree ptt = new PathTree(r,pathTrees.get((i-1)/2));
		r.setTree(ptt);
		pathTrees.add(ptt);
		//System.out.println("Current coord: " + ptt.getValue());
		//System.out.println("parent coord: " + pathTrees.get((i-1)/2).getValue());
		}
	repaint();
	}
	
	public boolean checkIfAllAwake()
	{
	boolean result = true;
	for (int i=0; i<robots.size(); i++)
			{	
			if(!robots.get(i).isAwake())
				{
				result = false;	
				break;
				}
			}
	return result;
	}
		
	public boolean start()
	{
	// returns true if it actually starts
	if(robots.size() > 1)
		{
		// Start motion		
		for (int i=robots.size()-1; i>=0; i--)
			{	
			robots.get(i).setSpeed(speed);			
			}	
		running = true;
		return true;
		}
	return false;
	}
	
	public void setSpeed(double value)
	{
	speed = value;
	for (int i=robots.size()-1; i>=0; i--)
			{	
			robots.get(i).setSpeed(value);			
			}		
	}
	
	/*
	public void chooseNewSchedule()
	{
	waitingForUser = true;
	if(points.size() > 0)
		{
		clearAll();
		Coord c = points.get(0).copy();
		Robot rr = new Robot(c);
		rr.wake();		
		robots.add(rr);
		}
	}
	*/
	
	
	public void reset()
	{	
	if(points.size() > 0)
		{
		if(algtype == REGULAR)
		{
		clearAll();
		Coord c = points.get(0).copy();
		Robot rr = new Robot(c);
		rr.wake();		
		robots.add(rr);
		
		
		
		for (int i = 1; i<points.size(); i++)
			{
			if(points.get(i) != null)
			{
			Coord cCur = points.get(i).copy();
			Robot r = new Robot(cCur);
			robots.add(r);
			
			if(robots.size() == 2)
					{
					PathTree pt = new PathTree(r);
					pathTrees.add(pt);
					r.setTree(pt);
					}
			else
					{								
					Robot parentRobot = new Robot();
					for(int j=0; j<robots.size(); j++)
						{
						if(robots.get(j).getC().equals(points.get(i/2))) 
							{
							parentRobot = robots.get(j);
							break;
							}
						}		
					
					
					PathTree parentTree = parentRobot.getTree();
					PathTree ptt = new PathTree(r,parentTree); //or (i-1)/2
					pathTrees.add(ptt);
					parentRobot.setTarget(r);
					r.setTree(ptt);
					}		
			}
				treeSize++;	
			}
			
		if(robots.size() > 1) robots.get(0).setTarget(robots.get(1));
		firstClick = false;
		
		repaint();	
		}
		else if(algtype == GREEDY2) //greedy v2
			{
			copyRobots();
			}
		else if(algtype == GREEDY1) // for now just redo algorithm
			{
			runGreedyAlg();
			}
		}
	}
	
	public void moveRobots(){
		for (int i = 0; i<robots.size(); i++){
			Robot rCur = robots.get(i);
			if (rCur.isAwake()) {
				if(rCur.reachedTarget())
					{
					if (yawnsOn)
						{
						//yawn.stop();
						yawn.play();
						}
					Robot rNext = rCur.getTarget();
					rNext.wake();					
					PathTree nextTree = rNext.getTree();					
					if(!nextTree.isEmpty()) rCur.setTarget(nextTree.left().getValue());					
					else rCur.stop();
					if(i>0)
						{
						PathTree curTree = rCur.getTree();
						curTree.setRight(nextTree.left());
						}
					nextTree.setLeft(new PathTree());
					}
				rCur.update();
			}
			
		}
	}
	
	public void clearAll()
	{
	asleepPoints.removeAllElements();	
	awakePoints.removeAllElements();
	pathTrees.removeAllElements();
	robots.removeAllElements();
	firstClick = true;
	switchedMode = false;
	treeSize = 0;
	repaint();
	running = false;
	}
	
	public void clearPoints()
	{ //removes saved points: Only when clear All button is pressed
	points.removeAllElements();
	}
	
	public void mousePressed(MouseEvent e)
	{
	int xP = e.getX();
	int yP = e.getY();
	Coord c = new Coord(xP,yP);
	Robot r = new Robot();
	if(!running)
	{
	if(mouseMode == PLACE)
		{		
		Coord cSave = new Coord(xP,yP);
		points.add(cSave);
		if(!firstClick) 
			{
			//if(pathMode == USER) 
				//{
				r = new Robot(c);
				if(robots.size() == 1) // 2nd robot has the first pathTree
					{
					PathTree pt = new PathTree(r);
					pathTrees.add(pt);
					r.setTree(pt);
					}
				else
					{			
					PathTree ptt = new PathTree(r,pathTrees.get((treeSize-1)/2)); //or (i-1)/2
					pathTrees.add(ptt);
					pathTrees.get((treeSize-1)/2).getValue().setTarget(r);
					r.setTree(ptt);
					}
				repaint();
				//}	
			
			treeSize++;
			
			
			if (robots.get(0).getTarget()==null) robots.get(0).setTarget(r);
				
			}
		else 
			{
			r = new Robot(c);
			r.wake();
			firstClick = false;		
			}	
		robots.add(r);
	}
	else if(mouseMode == REMOVE)
		{
		for (int i=robots.size()-1; i>=0; i--)
			{	
			if(robots.size() == 1) clearAll();
			else if (robots.size() > 1 && robots.get(i).getC().dist(c) <= 4) 
				{
				points.removeElementAt(i);
				if(points.size() > 0) reset();
				break;
				}			
			}	
		}
	else if(mouseMode == DESIGNATE)
		{
		for (int i=robots.size()-1; i>=0; i--)
			{	
			if(robots.get(i).getC().dist(c) <= 4) 
				{
				Coord temp = points.get(0);
				points.set(0,points.get(i));
				points.set(i,temp);
				reset();
				break;
				}
			}					
		}
		
	repaint();
	}
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
	
	public void switchYawn()
	{
	if (yawn == null) 
		{
		try{				
				//try to upload a file 
				//named "powerchords.wav" to be played
				yawn = Applet.newAudioClip(new URL("http://cfb.qqnoobs.com/RobotFreezeTag/YAWN.wav"));

			}

			//if there is a problem with the URL
			//then this is the exception to be used
			catch (MalformedURLException mfe){}
		}
	yawnsOn = !yawnsOn;
	}
	
	public void clearRobotMode()
	{
	borg=false;
	dots=false;
	robotic=false;
	}
	
	public void regularMode()
	{
	clearRobotMode();
	dots=true;
	repaint();
	}
	
	public void robotMode()
	{
	
	clearRobotMode();
	robotic=true;
	repaint();
	}

	public void borgMode()
	{
	clearRobotMode();
	borg = true;
	repaint();
	}
	
	public void update(Graphics g)
    {
	
	if (!buffer.equals(null)) {
	bufG = buffer.getGraphics();
	//bufG.setColor(Color.BLACK);	
	
	bufG.dispose();
	
	paint(g);	
	g.dispose();
	}
	}
	
	public void switchTrace()
	{
	traces = !traces;
	repaint();
	}
	
	public void paint(Graphics g)
	{
	if (!buffer.equals(null)) {
	bufG = buffer.getGraphics();
	
	//bufG.setColor(Color.BLACK);
	bufG.setColor(Color.WHITE);
	bufG.fillRect(0,0,boardWidth,boardHeight);
	bufG.setColor(Color.BLACK);
	
	for (int i=robots.size()-1; i>=0; i--)
			{	
			if(dots) robots.get(i).paint(bufG);	
			else if(borg)
				{
				if(!robots.get(i).isAwake()) bufG.drawImage(borgAwake, 
					(int)(robots.get(i).getC().getX()-borgAwake.getWidth(this)/2), 
					(int)(robots.get(i).getC().getY() - borgAwake.getHeight(this)/2), this);
				else bufG.drawImage(borgSleep, 
					(int)(robots.get(i).getC().getX()-borgSleep.getWidth(this)/2), 
					(int)(robots.get(i).getC().getY() - borgSleep.getHeight(this)/2), this);
				}
			else if(robotic)
				{
				if(robots.get(i).isAwake()) bufG.drawImage(robotAwake, 
					(int)(robots.get(i).getC().getX()-robotAwake.getWidth(this)/2), 
					(int)(robots.get(i).getC().getY() - robotAwake.getHeight(this)/2), this);
				else bufG.drawImage(robotSleep, 
					(int)(robots.get(i).getC().getX()-robotSleep.getWidth(this)/2), 
					(int)(robots.get(i).getC().getY() - robotSleep.getHeight(this)/2), this);
				}
				
			}			
	}
	if(pathTrees.size() > 0 && traces) 
	{
		for(int i=0; i<pathTrees.size(); i++)
			{
			pathTrees.get(i).paint(bufG);
			}
			
		
		Robot r0 = robots.get(0);
		Robot r1 = r0.getTarget();
		bufG.setColor(new Color(200,200,200));	
		if(!r1.isAwake()) bufG.drawLine((int)r0.getC().getX(),(int)r0.getC().getY(),(int)r1.getC().getX(),(int)r1.getC().getY());
	}
	
	g.drawImage(buffer, 0, 0, this);
		g.dispose();
	}
	
}