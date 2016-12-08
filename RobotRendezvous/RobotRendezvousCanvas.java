import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.applet.*;
import java.net.*;
import java.util.Iterator;
import java.util.Collections;

public class RobotRendezvousCanvas extends Canvas implements KeyListener,MouseListener,MouseMotionListener {

	int boardWidth;
	int boardHeight;
	int aHeight = 200;
	private BufferedImage buffer;
	private Graphics bufG;
	Vector<Robot> robots = new Vector<Robot>();
	Vector<Robot> dummyRobots = new Vector<Robot>();
	Vector<Robot> saveRobots = new Vector<Robot>();
	Vector<Polygon> p2 = new Vector<Polygon>(); //ellipses
	Vector<Point2d> centers = new Vector<Point2d>(); //for circumcircles
	Vector<Double> radii = new Vector<Double>(); //for circumcircles
	boolean running = false;
	RobotRendezvousApplet japp;
	Point2d cm;
	static Point2d cmCur;
	static Point2d cmPoly;
	Point2d cmPolyS;
	double perim = 0;
	int cmRad = 2;
	double minV = -0.5;
	double maxV = 0.5;
	private Random generator = new Random();
	double dt = 0.05;
	static boolean testing = false;
	int dragCount;
	int slowFactor = 5;
	int vectorEvery = 5;
	Vector<Integer> areas = new Vector<Integer>();
	double areaScale;
	int stepsPerFrame = 10;
	static Point2d circumcenter = null;
	static double ccRadius = 0;
	static Point2d circumcenter2 = null;
	static double ccRadius2 = 0;
	boolean showVectors = true;
	
	boolean showEdges = true;
	boolean radialToCC = false;
		
	Integrator integrator;
	
	
	public RobotRendezvousCanvas(int boardWidth, int boardHeight, RobotRendezvousApplet japp)
	{
	this.boardWidth = boardWidth;
	this.boardHeight = boardHeight;
	this.japp = japp;
	
	
	addMouseListener( this );         
	addKeyListener( this );         
	addMouseMotionListener( this );         
		  
	buffer = new BufferedImage(boardWidth, boardHeight+aHeight, 
      BufferedImage.TYPE_INT_RGB);
	bufG = buffer.getGraphics();
	bufG.setColor(Color.WHITE);
	bufG.fillRect(0,0,boardWidth,boardHeight);
	}
	
	public boolean start()
	{
	// returns true if it actually starts
	int rSize = robots.size();
	if(rSize > 1)
		{
		cmCur = new Point2d(cm);
		if(japp.linearSchemeRadioButton.isSelected())
			{
			if(japp.implicitRadioButton.isSelected()) integrator = new Integrator_Implicit(robots,dt);
			else if(japp.eulerRadioButton.isSelected()) integrator = new Integrator_Euler(robots);
			else if(japp.rungekuttaRadioButton.isSelected()) integrator = new Integrator_RK4(robots);
			}			
		else if(japp.mmSchemeRadioButton.isSelected())
			{
			if(japp.implicitRadioButton.isSelected()) integrator = new Integrator_ImplicitMM(robots);
			else if(japp.eulerRadioButton.isSelected()) integrator = new Integrator_EulerMM(robots,dummyRobots,japp.mergeRobotsCheckBox.isSelected(),centers,radii);
			else if(japp.rungekuttaRadioButton.isSelected()) integrator = new Integrator_RK4MM(robots,dummyRobots,japp.mergeRobotsCheckBox.isSelected());
			}
		else if(japp.circumcenterSchemeRadioButton.isSelected())
			{
			integrator = new Integrator_EulerCC(robots);
			}
			
		// Start motion	
/*		
		for (int i=0; i<rSize; i++)
			{	
			//Robot rC = new Robot(robots.get(i));
			//saveRobots.add(rC);
			robots.get(i).calculateVelocity(robots.get((i+rSize-1)%rSize),robots.get((i+1)%rSize),rSize);			
			}	
			*/
		running = true;
		return true;
		}
	return false;
	}
	
	public void loadPreset(String preset)
	{
	removeAllRobots();
	cm=new Point2d();
	
	if(preset.equals("Spiral"))
		{
		japp.mmSchemeRadioButton.setSelected(true);
		japp.mergeRobotsCheckBox.setSelected(true);
		japp.mergeRobotsCheckBox.setEnabled(true);
		japp.eulerRadioButton.setSelected(true);
		japp.speedSlider.setValue(15);
		japp.stepsPerFrameSlider.setValue(30);
		int[] xS = {550,50,50,550,550,80,80,520,520,110,110,490,490,140,140,460,460,170,170,430,430,200,200,400,400,230,230,380,380,220,220,410,410,190,190,440,440,160,160,470,470,130,130,500,500,100,100,530,530,70,70,550};
		int[] yS = {450,450,50,50,420,420,80,80,390,390,110,110,360,360,140,140,330,330,170,170,300,300,200,200,270,270,250,250,220,220,280,280,190,190,310,310,160,160,340,340,130,130,370,370,100,100,400,400,70,70,430,430,450};
		
		int oneEvery = 20;
		int l1 = 500;
		int l2 = 400;
		int l3 = 400;
		int n1 = 100;
		int n2 = 100;
		int n3 = 80;
		int xS2 = 50;
		int yS2 = 50;
	
		for(int j=0; j<xS.length; j++) //two cycles of this
		{
		for(int i=0; i<Math.abs(xS[j]-xS[j+1])/oneEvery; i++)
			{
			int sign = +1;
			if(xS[j]>xS[j+1]) sign = -1;
			Point2d c = new Point2d(xS[j]+sign*i*oneEvery,yS[j]);
				
			Robot r = new Robot(c);
			Robot rC = new Robot(r);
			robots.add(r);
			saveRobots.add(rC);	
			cm.add(c);
			}
		j++;
		for(int i=0; i<Math.abs(yS[j]-yS[j+1])/oneEvery; i++)
			{
			int sign = +1;
			if(yS[j]>yS[j+1]) sign = -1;
			Point2d c = new Point2d(xS[j],yS[j]+sign*i*oneEvery);
				
			Robot r = new Robot(c);
			Robot rC = new Robot(r);
			robots.add(r);
			saveRobots.add(rC);	
			cm.add(c);
			}
		}
			
		cm.scale(1./robots.size());
		}
	else if(preset.equals("Area increase"))
		{
		japp.linearSchemeRadioButton.setSelected(true);
		japp.implicitRadioButton.setSelected(true);
		japp.mergeRobotsCheckBox.setEnabled(false);
		japp.mergeRobotsCheckBox.setSelected(false);
		japp.speedSlider.setValue(3);
		japp.stepsPerFrameSlider.setValue(2);
		
		
		//small:
		//int xMin = 27; // for series
		//int xMax = 250;
		//int yDiff = 100;
		
		int xMin = 50; // for series
		int xMax = 590;
		int yMid = 250;
		int yDiff = 240;
		int[] xS = {xMax,10,xMax,xMin,xMax};
		int[] yS = {yMid-yDiff,yMid,yMid+yDiff,yMid,yMid-yDiff};
		//yS=10,250,490,250,10
		int nSeries = 7;
		int[] xSeries = new int[nSeries];
		int[] ySeries = new int[nSeries];
		
		for(int i=0; i<nSeries; i++)
			{
			xSeries[i] = xMin + (xMax-xMin)/((i+1)*(i+1));
			ySeries[i] = yDiff*(xSeries[i]-xMin)/(xMax-xMin); //not including yMid
			}
		for(int i=0; i<3; i++)
			{
			Point2d c = new Point2d(xS[i],yS[i]);
				
			Robot r = new Robot(c);
			Robot rC = new Robot(r);
			robots.add(r);
			saveRobots.add(rC);	
			cm.add(c);
			}
		for(int i=1; i<nSeries; i++)
			{
			Point2d c = new Point2d(xSeries[i],yMid+ySeries[i]);
				
			Robot r = new Robot(c);
			Robot rC = new Robot(r);
			robots.add(r);
			saveRobots.add(rC);	
			cm.add(c);			
			}
			//middle point
			{
			Point2d c = new Point2d(xS[3],yS[3]);			
			Robot r = new Robot(c);
			Robot rC = new Robot(r);
			robots.add(r);
			saveRobots.add(rC);	
			cm.add(c);
			}
		for(int i=nSeries-1; i>0; i--)
			{
			Point2d c = new Point2d(xSeries[i],yMid-ySeries[i]);
				
			Robot r = new Robot(c);
			Robot rC = new Robot(r);
			robots.add(r);
			saveRobots.add(rC);	
			cm.add(c);			
			}
		cm.scale(1./robots.size());
		}
	else if(preset.equals("Snake"))
		{
		japp.mmSchemeRadioButton.setSelected(true);
		japp.mergeRobotsCheckBox.setSelected(true);
		japp.mergeRobotsCheckBox.setEnabled(true);
		japp.eulerRadioButton.setSelected(true);
		japp.speedSlider.setValue(15);
		japp.stepsPerFrameSlider.setValue(30);
		
		int b = 10;
		int oneEvery = 10;
		int w = 40;
		int sp = 20;
		int wPer = 2*(w+sp);
		int h = 40;
		int hT = boardHeight-2*b-h;
		
		int n = (boardWidth-2*b+sp)/wPer;
		int bw = (boardWidth - n*wPer+sp)/2;
		
		//start top right corner of top left of screen
		for(int i=0; i<w/oneEvery; i++)
			{
			Point2d c = new Point2d(bw+w-i*oneEvery,b);
				
			Robot r = new Robot(c);
			Robot rC = new Robot(r);
			robots.add(r);
			saveRobots.add(rC);	
			cm.add(c);
			}
		for(int i=0; i<h/oneEvery; i++)
			{
			Point2d c = new Point2d(bw,b+i*oneEvery);
				
			Robot r = new Robot(c);
			Robot rC = new Robot(r);
			robots.add(r);
			saveRobots.add(rC);	
			cm.add(c);
			}
			
		// bottoms of recursive pieces
		for(int j=0; j<n; j++)
			{
			//left down
			for(int i=0; i<hT/oneEvery; i++)
				{
				Point2d c = new Point2d(bw+j*wPer,b+h+i*oneEvery);
					
				Robot r = new Robot(c);
				Robot rC = new Robot(r);
				robots.add(r);
				saveRobots.add(rC);	
				cm.add(c);
				}
			//bottom right
			for(int i=0; i<(wPer-sp)/oneEvery; i++)
				{
				Point2d c = new Point2d(bw+j*wPer+i*oneEvery,boardHeight-b);
					
				Robot r = new Robot(c);
				Robot rC = new Robot(r);
				robots.add(r);
				saveRobots.add(rC);	
				cm.add(c);
				}
			//right up
			for(int i=0; i<hT/oneEvery; i++)
				{
				Point2d c = new Point2d(bw+j*wPer+wPer-sp,boardHeight-b-i*oneEvery);
					
				Robot r = new Robot(c);
				Robot rC = new Robot(r);
				robots.add(r);
				saveRobots.add(rC);	
				cm.add(c);
				}
			//top right
			if(j!=n-1)
				{
				for(int i=0; i<sp/oneEvery; i++)
					{
					Point2d c = new Point2d(bw+j*wPer+wPer-sp+i*oneEvery,b+h);
						
					Robot r = new Robot(c);
					Robot rC = new Robot(r);
					robots.add(r);
					saveRobots.add(rC);	
					cm.add(c);
					}
				}
			}
		//top right non loop pieces:
		
		for(int i=0; i<h/oneEvery; i++)
			{
			Point2d c = new Point2d(boardWidth-bw,b+h-i*oneEvery);
				
			Robot r = new Robot(c);
			Robot rC = new Robot(r);
			robots.add(r);
			saveRobots.add(rC);	
			cm.add(c);
			}
		for(int i=0; i<w/oneEvery; i++)
			{
			Point2d c = new Point2d(boardWidth-bw-i*oneEvery,b);
				
			Robot r = new Robot(c);
			Robot rC = new Robot(r);
			robots.add(r);
			saveRobots.add(rC);	
			cm.add(c);
			}
			
		// tops of recursive pieces
		for(int j=0; j<n; j++)
			{
			//right down
			for(int i=0; i<hT/oneEvery; i++)
				{
				Point2d c = new Point2d(boardWidth-bw-w-j*wPer,b+i*oneEvery);
					
				Robot r = new Robot(c);
				Robot rC = new Robot(r);
				robots.add(r);
				saveRobots.add(rC);	
				cm.add(c);
				}
			//bottom left
			for(int i=0; i<(sp)/oneEvery; i++)
				{
				Point2d c = new Point2d(boardWidth-bw-w-j*wPer-i*oneEvery,boardHeight-b-h);
					
				Robot r = new Robot(c);
				Robot rC = new Robot(r);
				robots.add(r);
				saveRobots.add(rC);	
				cm.add(c);
				}
			//left up
			for(int i=0; i<hT/oneEvery; i++)
				{
				Point2d c = new Point2d(boardWidth-bw-w-sp-j*wPer,boardHeight-b-h-i*oneEvery);
					
				Robot r = new Robot(c);
				Robot rC = new Robot(r);
				robots.add(r);
				saveRobots.add(rC);	
				cm.add(c);
				}
			//top left
			if(j!=n-1)
				{
				for(int i=0; i<(wPer-sp)/oneEvery; i++)
					{
					Point2d c = new Point2d(boardWidth-bw-w-j*wPer-sp-i*oneEvery,b);
					
					Robot r = new Robot(c);
					Robot rC = new Robot(r);
					robots.add(r);
					saveRobots.add(rC);	
					cm.add(c);
					}
				}
			}
		cm.scale(1./robots.size());
		
		
		}
	
	int rSize = robots.size();
	if(rSize > 1)
		{
		cmPolyS = new Point2d();
		perim = 0;
		
		for(int i=0; i<rSize; i++)
			{
			Point2d c = robots.get(i).x;
			Point2d cN = robots.get((i+1)%rSize).x;
			
			Vector2d vc = new Vector2d();
			vc.sub(cN,c);
			cmPolyS.scaleAdd(vc.length()/2,c,cmPolyS);
			cmPolyS.scaleAdd(vc.length()/2,cN,cmPolyS);
			perim += vc.length();
			}			
		cmPolyS.scale(1./perim);
		cmPoly = new Point2d(cmPolyS);
		}
	if(japp.circumcenterSchemeRadioButton.isSelected()) calculateCircumcenter();
	}
	
	public void updatedt()
	{
	if(integrator != null && japp.implicitRadioButton.isSelected()) integrator.setdt(dt);
	}
	
	public void reset()
	{
	int rSize = robots.size();
	int rSize3 = dummyRobots.size();
	int rSize2 = saveRobots.size();
	centers.removeAllElements();
	radii.removeAllElements();
	if(rSize+rSize3 != rSize2) System.out.println("Error: saveRobots vector has " + rSize2 + " elements, but robots vector has " + rSize + " elements.");
	else
		{
		stopActions();
		dummyRobots.removeAllElements();
		for (int i=0; i<rSize2; i++)
			{	
			Robot r = new Robot(saveRobots.get(i));
			robots.add(r);
			}
		}
	}
	
	public void removeAllRobots()
	{		
	stopActions();
	centers.removeAllElements();
	radii.removeAllElements();
	saveRobots.removeAllElements();		
	dummyRobots.removeAllElements();		
	cm = null;	
	cmCur = null;	
	cmPoly = null;
	cmPolyS = null;
	circumcenter = null;
	ccRadius = 0;
	}
	
	public void calculateCircumcenter()
	{
	if(robots.size() > 1)
		{
		integrator = new Integrator_EulerCC(robots);
		repaint();
		}
	else circumcenter = null;
	}
	
	public void stopActions()
	{
	robots.removeAllElements();
	p2.removeAllElements();
	areas.removeAllElements();
	running = false;
	cmCur = null;
	if(cmPolyS != null) cmPoly.set(cmPolyS);
	repaint();
	}
	
	public void moveRobots()
	{
	for(int i=0; i< stepsPerFrame; i++)	
	{
	integrator.advanceTime(dt);	

	boolean stable = true;
	for(int j=0; j<robots.size(); j++)
		{
		int guess = generator.nextInt(robots.size());
		if(robots.get(j).x.distance(robots.get(guess).x) > 10*boardWidth) 
			{
			japp.pauseToggleButton.setSelected(true);
			stable = false;
			break;
			}			
		}
	if(!stable) break;
	}
	
	
	calculatePolygonArea();
	updatecmCur();
	}
	
	public void calculatePolygonArea()
	{
	double a = 0;
	int rSize = robots.size();
	for(int i=0; i<rSize; i++)
		{
		Point2d x1 = robots.get(i).x;
		Point2d x2 = robots.get((i+1)%rSize).x;
		a += x1.x*x2.y - x1.y*x2.x;
		}
	a = a/2;
	if(areas.isEmpty()) areaScale = a*1.2/aHeight;
	//if(areas.isEmpty()) areaScale = a*7/aHeight; //small
	a = a/areaScale;
	a = Math.abs(a);
	//System.out.println(a);
	a = boardHeight+aHeight-a;
	areas.add((int)a);
	}
	
	public void updatecmCur()
	{
	if(cmCur ==null) cmCur = new Point2d();
	cmCur.set(0,0);
	cmPoly.set(0,0);	
	int rSize = robots.size();
	perim = 0;
	
	for(int i=0; i<rSize; i++)
		{
		Point2d c = robots.get(i).x;
		Point2d cN = robots.get((i+1)%rSize).x;
		cmCur.add(c);
		//cmPoly.add(c);
		//cmPoly.add(cN);
		Vector2d vc = new Vector2d();
		vc.sub(cN,c);
		cmPoly.scaleAdd(vc.length()/2,c,cmPoly);
		cmPoly.scaleAdd(vc.length()/2,cN,cmPoly);
		perim += vc.length();
		}
	cmCur.scale(1./robots.size());
	cmPoly.scale(1./perim);
	}
	
	public void addEllipse()
	{
	Polygon poly = new Polygon();
	int rSize = robots.size();	
	for (int i=0; i<rSize; i++)
		{	
		Point2d xC = robots.get(i).x;
		poly.addPoint((int)xC.x,(int)xC.y);
		}
	p2.add(poly);
	}
	
	public void putInStarForm()
	{
		saveRobots.removeAllElements();
		// now get them in the right order: by angle		
		for(int i=0; i<robots.size(); i++)
			{
			Vector2d v = new Vector2d();
			v.sub(robots.get(i).x,cm);
			robots.get(i).angle = v.angle();
			}
		Collections.sort(robots,new RobotComparator());		
		for(int i=0; i<robots.size(); i++)
			{
			//System.out.println(robots.get(i).angle);
			Robot rC = new Robot(robots.get(i));
			saveRobots.add(rC);	
			}	
		repaint();
	}
	
	public void generateRandomRobots(int num)
	{	
	cm=new Point2d();
	if(japp.randomRadioButton.isSelected())
		{
		for(int i=0; i<num; i++)
			{
			Point2d c = new Point2d(-1,-1);
			int xP=-1;
			int yP=-1;
				xP = generator.nextInt(boardWidth);
				yP = generator.nextInt(boardHeight);
				c = new Point2d(xP,yP);
				
			Robot r = new Robot(c);
			Robot rC = new Robot(r);
			robots.add(r);
			saveRobots.add(rC);	
			cm.add(c);
			}
		cm.scale(1./num);
		}
	else if(japp.starRadioButton.isSelected())
		{
		for(int i=0; i<num; i++)
			{
			Point2d c = new Point2d(-1,-1);
			int xP=-1;
			int yP=-1;
				xP = generator.nextInt(boardWidth);
				yP = generator.nextInt(boardHeight);
				c = new Point2d(xP,yP);
				
			Robot r = new Robot(c);
			robots.add(r);	
			cm.add(c);
			}
		cm.scale(1./num);
		
		putInStarForm();
		}
	else if(japp.convexRadioButton.isSelected())
		{
		boolean done = false;
		while(!done)
			{
			robots.removeAllElements();
			Coord center = new Coord(boardWidth/2,boardHeight/2);
			cm = null;
			done = true;
			int tries=0;
			for(int i=0; i<num; i++)
				{
				tries++;
				if(tries > num*num)
					{
					System.out.println(tries);
					done = false;
					break;
					}
				double th = 2*Math.PI*generator.nextDouble();
				if(cm != null) center = new Coord(cm.x,cm.y);
				double dx = center.x;
				double dy = center.y;
				if(th < Math.PI/2 || th > 3*Math.PI/2) dx = boardWidth - center.x;
				if(th < Math.PI) dy = boardHeight - center.y;
				double maxR = Math.min(Math.abs(dx/Math.cos(th)),Math.abs(dy/Math.sin(th)));
				
				if(i==num-1) 
				{
				/*
				System.out.println("First, maxR = " + maxR);
				System.out.println(center);
				System.out.println(dy);
				System.out.println(dx);
				*/
				}
				double minR = 0;
				
				if(robots.size() > 2)
					{
					for(int j=0; j<robots.size(); j++)
						{
						Vector2d v = new Vector2d();
						v.sub(robots.get(j).x,cm);
						double ang = v.angle()+2*Math.PI;
						if(ang > 2*Math.PI) ang -= 2*Math.PI;
						robots.get(j).angle = ang;
						}
					Collections.sort(robots,new RobotComparator());	
					
					/*
					for(int j=0; j<robots.size(); j++)
						{
						System.out.println("Robot " + robots.get(j).order + ", angle " + robots.get(j).angle);
						}
					*/
					
					int adjp = 0;
					for(int j=0; j<robots.size(); j++)
						{
						if(th < robots.get(j).angle) 
							{
							adjp = j;
							break;
							}
						}
				
					int adjm = (robots.size()+adjp-1) % robots.size();
					int adjpp = (adjp+1) % robots.size();
					int adjmm = (robots.size()+adjp-2) % robots.size();
					
					Coord cp = robots.get(adjp).c;
					Coord cpp = robots.get(adjpp).c;
					Coord cm = robots.get(adjm).c;
					Coord cmm = robots.get(adjmm).c;
					
					Coord c = new Coord(center.x+maxR*Math.cos(th),center.y+maxR*Math.sin(th));
					
					if(center.intersectSegment(c,cm,cp))
						{
						minR = center.intersection(c,cm,cp).dist(center);
						}
						
					Coord mi = center.intersection(c,cmm,cm);
					/*
					System.out.println("cm, cmm, mi");
					System.out.println(robots.get(adjm).order);
					System.out.println(robots.get(adjmm).order);
					System.out.println(mi);
					System.out.println();
					*/
					double mx = mi.dist(center);	
					if(mx < maxR)
						{
						Vector2Do v1 = new Vector2Do(mi,center);
						Vector2Do v2 = new Vector2Do(c,center);
						if(v1.dot(v2) > 0) maxR = mx;
						}
						
					Coord pi = center.intersection(c,cpp,cp);
					/*
					System.out.println("cp, cpp, pi");
					System.out.println(robots.get(adjp).order);
					System.out.println(robots.get(adjpp).order);
					System.out.println(pi);
					System.out.println();
					*/
					double px = pi.dist(center);	
					if(px < maxR)
						{
						Vector2Do v1 = new Vector2Do(pi,center);
						Vector2Do v2 = new Vector2Do(c,center);
						if(v1.dot(v2) > 0) maxR = px;
						}
					}
					
				double rand = generator.nextDouble();
				double r = (maxR-minR)*rand*rand + minR;
				//while(r/minR < 1.2 && (double)(maxR/minR) > 1.2) r = (maxR-minR)*generator.nextDouble() + minR;
				/*
				System.out.println("theta, maxR, minR, r");
				System.out.println(th);
				System.out.println(maxR);
				System.out.println(minR);
				System.out.println(r);
				System.out.println();
				*/
				Point2d c = new Point2d(center.x+r*Math.cos(th),center.y+r*Math.sin(th));
				Coord cs = new Coord(c.x,c.y);	
				boolean tooClose = false;
				for(int j=0; j<robots.size(); j++)
					{
					int minDist = 20;
					if(num>20) minDist = 5;
					if(cs.dist(robots.get(j).c) < minDist)
						{
						i--;
						tooClose = true;
						break;
						}
					}
				if(!tooClose)
					{
					Robot rb = new Robot(c);
					rb.c = cs;
					rb.angle = th;
					robots.add(rb);
					rb.order = i;
					
				
					if(cm == null) cm = new Point2d(c);
					else
						{
						cm.scale(robots.size()-1);
						cm.add(c);
						cm.scale(1./robots.size());
						}
					}
				
				//cm.add(c);
				}
			//cm.scale(1./num);
			
			if(done)
				{
				for(int i=0; i<num; i++)
					{
					Vector2d v = new Vector2d();
					v.sub(robots.get(i).x,cm);
					robots.get(i).angle = v.angle();
					}
				Collections.sort(robots,new RobotComparator());	
				
				for(int i=0; i<num; i++)
					{
					Robot rC = new Robot(robots.get(i));
					saveRobots.add(rC);				
					}
				}
			}
		
		}
		
		
		int rSize = robots.size();
			if(rSize > 1)
				{
				cmPolyS = new Point2d();
				perim = 0;
				
				for(int i=0; i<rSize; i++)
					{
					Point2d c = robots.get(i).x;
					Point2d cN = robots.get((i+1)%rSize).x;
					
					Vector2d vc = new Vector2d();
					vc.sub(cN,c);
					cmPolyS.scaleAdd(vc.length()/2,c,cmPolyS);
					cmPolyS.scaleAdd(vc.length()/2,cN,cmPolyS);
					perim += vc.length();
					}			
				cmPolyS.scale(1./perim);
				cmPoly = new Point2d(cmPolyS);
				}
	if(japp.circumcenterSchemeRadioButton.isSelected()) calculateCircumcenter();
	}
	
	public void mousePressed(MouseEvent e)
	{
	int xP = e.getX();
	int yP = e.getY();
	Point2d x = new Point2d(xP,yP);
	System.out.println(xP + ", " + yP);
	if(!running && yP < boardHeight)
		{
		if(japp.placeRobotRadioButton.isSelected())
			{
			Robot r = new Robot(x);
			if(testing)
				{
				r.order = robots.size();
				}
			robots.add(r);
			saveRobots.add(new Robot(r));
			
			
			if(cm == null) cm = new Point2d(x);
			else
				{
				cm.scale(robots.size()-1);
				cm.add(x);
				cm.scale(1./robots.size());
				}
						
			}
		else if(japp.removeRobotRadioButton.isSelected())
			{
			boolean removed = false;
			for (int i=0; i<robots.size(); i++)
				{	
				if(robots.get(i).x.distance(x) <= 4) 
					{
					removed = true;
					robots.removeElementAt(i);
					saveRobots.removeElementAt(i);
					break;
					}	
				}
				
				if(robots.size()>0)
					{
					if(removed)
						{
						cm = new Point2d(0,0);
						for(int i=0; i<robots.size(); i++)
							{
							cm.add(robots.get(i).x);	
							}
						cm.scale(1./robots.size());	
						}
					}
				else cm=null;			
			
			}
		else if(japp.dragPlaceRobotRadioButton.isSelected())
			{
				Robot r = new Robot(x);
				robots.add(r);
				saveRobots.add(new Robot(r));
				
				if(cm == null) cm = new Point2d(x);
				else
					{
					cm.scale(robots.size()-1);
					cm.add(x);
					cm.scale(1./robots.size());
					}					
			dragCount = 1;
			}
				
			int rSize = robots.size();
			if(rSize > 1)
				{
				cmPolyS = new Point2d();
				perim = 0;
				
				for(int i=0; i<rSize; i++)
					{
					Point2d c = robots.get(i).x;
					Point2d cN = robots.get((i+1)%rSize).x;
					
					Vector2d vc = new Vector2d();
					vc.sub(cN,c);
					cmPolyS.scaleAdd(vc.length()/2,c,cmPolyS);
					cmPolyS.scaleAdd(vc.length()/2,cN,cmPolyS);
					perim += vc.length();
					}			
				cmPolyS.scale(1./perim);
				cmPoly = new Point2d(cmPolyS);
				}
			else 
				{
				cmPolyS = null;
				cmPoly = null;
				}
				
		if(japp.circumcenterSchemeRadioButton.isSelected()) calculateCircumcenter();
		repaint();	
		}
	
	//if(running) integrator.redoPolys();
		
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
	
	public void mouseDragged(MouseEvent e)
	{
	int xP = e.getX();
	int yP = e.getY();
	Point2d x = new Point2d(xP,yP);
	//System.out.println(x.x + " " + x.y);
	
			if(!running && japp.dragPlaceRobotRadioButton.isSelected() && yP < boardHeight && yP > 0 && xP > 0 && xP < boardWidth)
				{
				if(dragCount%slowFactor != 0)
					{
					}
				else
					{
					Robot r = new Robot(x);
					robots.add(r);
					saveRobots.add(new Robot(r));
					
					if(cm == null) cm = new Point2d(x);
					else
						{
						cm.scale(robots.size()-1);
						cm.add(x);
						cm.scale(1./robots.size());
						}
						
					int rSize = robots.size();
					if(rSize > 1)
						{
						cmPolyS = new Point2d();
						perim = 0;
						
						for(int i=0; i<rSize; i++)
							{
							Point2d c = robots.get(i).x;
							Point2d cN = robots.get((i+1)%rSize).x;
							
							Vector2d vc = new Vector2d();
							vc.sub(cN,c);
							cmPolyS.scaleAdd(vc.length()/2,c,cmPolyS);
							cmPolyS.scaleAdd(vc.length()/2,cN,cmPolyS);
							perim += vc.length();
							}			
						cmPolyS.scale(1./perim);
						cmPoly = new Point2d(cmPolyS);
						}	
						
					if(japp.circumcenterSchemeRadioButton.isSelected()) calculateCircumcenter();
					}
				}
				repaint();
	
	dragCount++;
	
	}
	
	public void mouseMoved(MouseEvent e)
	{
	}
	
	public void keyTyped(KeyEvent e)
    {
	//System.out.println("typed "+e);
	
    }

    public void keyPressed(KeyEvent e)
    {
	//System.out.println("pressed " + e.getKeyCode());
	
	if (e.getKeyCode() == 32) // 32 is space bar
	    {
		//japp.pauseToggleButton.setSelected(!japp.pauseToggleButton.isSelected());
		}
	
	if (e.getKeyCode() == 67) // 67 is "c"
	    {
		japp.stopRun();
		removeAllRobots();
		repaint();
		}
	
	if (e.getKeyCode() == 68) // 68 is "d"
	    {
		radialToCC = !radialToCC;
		System.out.println("radialToCC = " + radialToCC);
		repaint();
		}
	
	if (e.getKeyCode() == 69) // 69 is "e"
	    {
		showEdges = !showEdges;
		System.out.println("showEdges = " + showEdges);
		repaint();
		}
		
	
	if (e.getKeyCode() == 61) // = and + key
	    {
		dt += 0.01;
		updatedt();
		System.out.println("dt = " + dt);
		}

	if (e.getKeyCode() == 45) // - and _ key
	    {
		dt-= 0.01;
		updatedt();
		System.out.println("dt = " + dt);
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
	
	public Color colorOf(double value)
	{
	//0 corresponds to blue and +1 to red. If value is not within [0,1] goes to white as value increases to white at maxV, black at minV
	value = 1-value;
	if(value > maxV+1) return Color.BLACK;
	if(value < minV) return Color.WHITE;
	int red = 0;
	int green = 0;
	int blue = 0;
	if(value<0)
		{
		red = 255;
		green = (int)(value*255/minV);		
		blue = (int)(value*255/minV);	
		}
	else if(value<0.25)
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
	else if(value <= 1+maxV)
		{
		blue = 255-(int)((value-1)*255/maxV);
		}
	//System.out.println(red + " " + green + " " + blue);
	//System.out.println(value);
	return new Color(red,green,blue);
	}
	
	public void paint(Graphics g)
	{
	if (!buffer.equals(null)) {
	bufG = buffer.getGraphics();
	
	//bufG.setColor(Color.BLACK);
	bufG.setColor(Color.WHITE);
	bufG.fillRect(0,0,boardWidth,boardHeight+aHeight);
	bufG.setColor(Color.BLACK);	
		
	if(japp.recordShapeCheckBox.isSelected())
		{
		for(int i=0; i<p2.size(); i++)
			{	
			bufG.setColor(new Color(155,155,155));	
			double v = 1 - 2.*(double)i/p2.size();
			//bufG.setColor(colorOf(v));	
			bufG.drawPolygon(p2.get(i));
			}
		}
		
	if(japp.tracePathsCheckBox.isSelected())
		{
		for(int i=0; i<robots.size(); i++)
			{		
			bufG.setColor(new Color(155,155,155));	
			Vector<Integer[]> p = robots.get(i).path;			
			for(int j=1; j<p.size(); j++)
				{
				bufG.drawLine(p.get(j)[0],p.get(j)[1],p.get(j-1)[0],p.get(j-1)[1]);
				}
			}
		for(int i=0; i<dummyRobots.size(); i++)
			{		
			//bufG.setColor(new Color(155,255,255));	
			bufG.setColor(new Color(255,155,155));	
			Vector<Integer[]> p = dummyRobots.get(i).path;			
			for(int j=1; j<p.size(); j++)
				{
				bufG.drawLine(p.get(j)[0],p.get(j)[1],p.get(j-1)[0],p.get(j-1)[1]);
				}
			}
		}		
	
	for(int i=0; i<robots.size(); i++)
		{
		robots.get(i).paint(bufG);
		}
	for(int i=0; i<dummyRobots.size(); i++)
		{
		dummyRobots.get(i).paint(bufG);
		}
	
	if(showEdges)
		{
		for(int i=0; i<robots.size(); i++)
			{
			bufG.setColor(Color.BLACK);	
			Point2d xS = robots.get(i).x;
			Point2d xF = robots.get((i+1)%robots.size()).x;
			bufG.drawLine((int)xS.x,(int)xS.y,(int)xF.x,(int)xF.y);
			}
		}
		
	if(japp.starLinesCheckBox.isSelected())
		{
		bufG.setColor(new Color(155,155,155));
		
		if(circumcenter!=null && radialToCC)
			{
			for(int i=0; i<robots.size(); i++)
				{
				Point2d x = robots.get(i).x;
				bufG.drawLine((int)x.x,(int)x.y,(int)circumcenter.x,(int)circumcenter.y);
				}			
			}
		else
			{			
			if(cmCur!=null)
				{
				for(int i=0; i<robots.size(); i++)
					{
					Point2d x = robots.get(i).x;
					bufG.drawLine((int)x.x,(int)x.y,(int)cmCur.x,(int)cmCur.y);
					}
				}
			else if(cm!=null)
				{
				for(int i=0; i<robots.size(); i++)
					{
					Point2d x = robots.get(i).x;
					bufG.drawLine((int)x.x,(int)x.y,(int)cm.x,(int)cm.y);
					}
				}
			}
		}
		
	if(cm != null && japp.cmCheckBox.isSelected())
		{		
		bufG.setColor(Color.GREEN);
		bufG.fillRect((int)cm.x-cmRad,(int)cm.y-cmRad,2*cmRad,2*cmRad);	
		}
		
	if(cmCur != null && japp.cmCheckBox.isSelected())
		{		
		bufG.setColor(new Color(0,100,0));
		bufG.fillRect((int)cmCur.x-cmRad,(int)cmCur.y-cmRad,2*cmRad,2*cmRad);	
		}
		
	if(cmPoly != null && japp.cmPolyCheckBox.isSelected())
		{		
		bufG.setColor(new Color(0,0,255));
		bufG.fillRect((int)cmPoly.x-cmRad,(int)cmPoly.y-cmRad,2*cmRad,2*cmRad);	
		}
		
	if(circumcenter != null)
		{		
		bufG.setColor(new Color(155,155,155));
		bufG.drawOval((int)(circumcenter.x-ccRadius),(int)(circumcenter.y-ccRadius),(int)(2*ccRadius),(int)(2*ccRadius));	
		bufG.fillOval((int)(circumcenter.x-cmRad),(int)(circumcenter.y-cmRad),(int)(2*cmRad),(int)(2*cmRad));
		}
		
	if(testing && circumcenter2 != null)
		{		
		bufG.setColor(new Color(0,0,155));
		bufG.drawOval((int)(circumcenter2.x-ccRadius2),(int)(circumcenter2.y-ccRadius2),(int)(2*ccRadius2),(int)(2*ccRadius2));	
		}
	
	if(testing)
		{
		bufG.setColor(new Color(155,155,155));
		bufG.drawLine(boardWidth/2,0,boardWidth/2,boardHeight);
		bufG.drawLine(0,boardHeight/2,boardWidth,boardHeight/2);
		}
		
	if(japp.velVectorsCheckBox.isSelected())
		{
		double vAvg=0;
		for(int i=0; i<robots.size(); i++)
			{
			vAvg+=robots.get(i).v.length();
			}
		vAvg=vAvg/robots.size();
		double vMax=0;
		double maxD=0;
		
		for(int i=0; i<robots.size(); i++)
			{
			if(robots.get(i).v.length()>vMax) vMax = robots.get(i).v.length();
			//for(int j=0; j<robots.size(); j++)			
			//	{
			//	if(robots.get(i).x.distance(robots.get(j).x) > maxD) maxD = robots.get(i).x.distance(robots.get(j).x);
			//	}
			}
			
		//if(vAvg>0)
		if(vMax>0)
			{
			int rSize = robots.size();
			for(int i=0; i<robots.size(); i++)
				{
				if(rSize < 20 || i%vectorEvery==0)
					{
					bufG.setColor(Color.BLACK);	
					Point2d x = robots.get(i).x;
					Vector2d v = new Vector2d(robots.get(i).v);	
					//double areaFactor = areas.get(areas.size()-1)*areas.get(areas.size()-1)/(areas.get(0)*areas.get(0));
					//double areaFactor = Math.sqrt(areas.get(areas.size()-1)/areas.get(0));
					v.scale((40/vMax));
					//v.scale((40/vMax)*areaFactor);
					//System.out.println(v.length());
					Point2d xF = new Point2d(x);
					xF.add(v);
					Vector2d vL = new Vector2d(v);
					vL.rotate(Math.PI/10);
					Vector2d vR = new Vector2d(v);
					vR.rotate(-Math.PI/10);
					Point2d xFL = new Point2d();
					xFL.scaleAdd(0.67,vL,x);
					Point2d xFR = new Point2d();
					xFR.scaleAdd(0.67,vR,x);
					/*
					int xD = xCur + (int)(res*arrowLength*vel.getX()*vectorsEvery);
					int yD = yCur + (int)(res*arrowLength*vel.getY()*vectorsEvery);
					int xLD = xCur + (int)(res*arrowLength*vArrowL.getX()*vectorsEvery*2/3);
					int yLD = yCur + (int)(res*arrowLength*vArrowL.getY()*vectorsEvery*2/3);
					int xRD = xCur + (int)(res*arrowLength*vArrowR.getX()*vectorsEvery*2/3);
					int yRD = yCur + (int)(res*arrowLength*vArrowR.getY()*vectorsEvery*2/3);
					*/
					bufG.drawLine((int)x.x,(int)x.y,(int)xF.x,(int)xF.y);
					bufG.drawLine((int)xF.x,(int)xF.y,(int)xFL.x,(int)xFL.y);
					bufG.drawLine((int)xF.x,(int)xF.y,(int)xFR.x,(int)xFR.y);
					}
				}
			}
		}
		
	if(japp.ccCheckBox.isSelected() && centers.size()>0)
		{
		for(int i=0; i<centers.size(); i++)
			{	
			Point2d cc = centers.get(i);
			double rc = radii.get(i);
		bufG.setColor(new Color(155,155,155));
		bufG.drawOval((int)(cc.x-rc),(int)(cc.y-rc),(int)(2*rc),(int)(2*rc));	
		bufG.fillOval((int)(cc.x-cmRad),(int)(cc.y-cmRad),(int)(2*cmRad),(int)(2*cmRad));
				
			}
		}
		
	bufG.setColor(Color.BLACK);
	bufG.drawLine(0,boardHeight,boardWidth,boardHeight);
	
	bufG.setColor(Color.WHITE);
	bufG.fillRect(0,boardHeight+1,boardWidth,aHeight);
	
	bufG.setColor(Color.BLACK);
	bufG.drawString("Area of Polygon vs. Time",boardWidth/2 - 40,boardHeight+15);
	
	int mult = areas.size()/boardWidth+1;
	for(int i=0; i<areas.size()-mult; i++)
		{
		//if(mult == 0) bufG.drawLine(i,(int)areas.get(i),i+1,(int)areas.get(i+1));
		if(i%mult == 0) bufG.drawLine(i/mult,(int)areas.get(i),(i+mult)/mult,(int)areas.get(i+mult));
		}
	}
	
	g.drawImage(buffer, 0, 0, this);
		g.dispose();
	}
	
}