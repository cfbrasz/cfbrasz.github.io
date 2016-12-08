import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.awt.image.BufferedImage;

public class PolygonCanvas extends Canvas implements MouseListener  {

	private boolean testing = false;
	private int boardWidth;
	private int boardHeight;
	private Vector<Integer> xPointVector = new Vector<Integer>();
	private Vector<Integer> yPointVector = new Vector<Integer>();
	private Vector<Integer> xHoleVector = new Vector<Integer>();
	private Vector<Integer> yHoleVector = new Vector<Integer>();
	private Vector<Integer> xGuardVector = new Vector<Integer>();
	private Vector<Integer> yGuardVector = new Vector<Integer>();
	private int nPoints;
	private Vector<Coord> drawPoints = new Vector<Coord>();
	private Vector<Coord> holePoints = new Vector<Coord>();
	private Vector<Coord> guardPoints = new Vector<Coord>();
	//private Vector<Vector<Coord>> drawPoints = new Vector<Vector<Coord>>();
	private final int OUT = 0;
	private final int HOLE = 1;
	private final int GUARD = 2;
	private int currentPt = 0;
	private Coord[] coords;	
	private boolean firstClick = false;
	private BufferedImage buffer;
	private Graphics bufG;
	private Polygon p1 = null;
	private Vector<Polygon> visibilities = new Vector<Polygon>();
	private boolean hideGuards = false;
	private boolean[][] triangulation;
	private boolean hasTriangulation = false;
	
	public PolygonCanvas(int boardWidth, int boardHeight)
	{
	this.boardWidth = boardWidth;
	this.boardHeight = boardHeight;
	
	addMouseListener( this );
	buffer = new BufferedImage(boardWidth, boardHeight, 
      BufferedImage.TYPE_INT_RGB);
	bufG = buffer.getGraphics();
	bufG.setColor(Color.WHITE);
	bufG.fillRect(0,0,boardWidth,boardHeight);
	
	//Vector<Coord> outPoints = 
	}
	
	public void setOut()
	{
	currentPt = OUT;
	}
	
	public void setHole()
	{
	currentPt = HOLE;
	}
	
	public void setGuard()
	{
	currentPt = GUARD;
	}
	
	public void setHideGuards(boolean value)
	{
	hideGuards = value;
	}
	
	public void mousePressed(MouseEvent e)
	{
	if (p1==null || currentPt == GUARD)
		{
		if (firstClick)
			{
			xPointVector.removeAllElements();
			yPointVector.removeAllElements();
			firstClick = false;
			}
		int xP = e.getX();
		int yP = e.getY();
		Coord c = new Coord(xP,yP);
		switch (currentPt)
			{
			case OUT:
				xPointVector.add(xP);
				yPointVector.add(yP);
				drawPoints.add(c);
				break;
			case HOLE:
				xHoleVector.add(xP);
				yHoleVector.add(yP);
				holePoints.add(c);
				break;
			case GUARD:			
				xGuardVector.add(xP);
				yGuardVector.add(yP);
				guardPoints.add(c);				
				if (!hideGuards)
					{
					addVisibility(guardPoints.size()-1);
					}
				break;
			default:
				System.out.println("error");
				break;			
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
	
	public void triangulate()
	{
	hasTriangulation = true;
	int np = p1.npoints;
	triangulation = new boolean[np][np];
	for(int i=0; i<np; i++)
		{
		for(int j=0; j<np; j++) triangulation[i][j] = false;
		}
	// set to true if we draw a diagonal between i and j
	coords = new Coord[p1.npoints];	
	for(int i=0; i<p1.npoints; i++)
		{
		coords[i] = new Coord(p1.xpoints[i], p1.ypoints[i]);
		}
	for(int i=0; i<np-2; i++)
		{
		for (int j=i+2; j<np; j++)
			{
			//if(!coords[i].intersectPolygon(coords[j],p1)) // connect ALL vertices that don't intersect with polygon
			if(!coords[i].intersectPolygonWithDiagonals(coords[j],p1,triangulation)
			   && coords[i].insidePolygon(coords[j],p1))
				{
				triangulation[i][j]=true;
				triangulation[j][i]=true;
				}
			}
		}
	//System.out.println(triangulation[0][2] +" "+ triangulation[0][3] +" "+ triangulation[0][4] +" "+ triangulation[0][5]);
	repaint();
	}
	
	public void createShapeFromVector()
	{
	nPoints = xPointVector.size();
	//System.out.println(nPoints);
	int[] xpoints = new int[nPoints];
	int[] ypoints = new int[nPoints];
	
	for (int i=0; i<nPoints; i++)
		{		
		xpoints[i] = xPointVector.get(i);
		ypoints[i] = yPointVector.get(i);
		}		
		
	p1 = new Polygon(xpoints,ypoints,nPoints);
	
	repaint();
	}
	
	public void removePolygon()
	{
	//p1.reset();
	p1 = null;
	repaint();
	}
	
	public void removeVisibility()
	{	
	visibilities.removeAllElements();
	repaint();
	}
		
	public void clearGuards()
	{
	xGuardVector.removeAllElements();
	yGuardVector.removeAllElements();
	guardPoints.removeAllElements();
	removeVisibility();
	}
		
	public void clearAll()
	{
	clearGuards();
	xPointVector.removeAllElements();
	yPointVector.removeAllElements();
	drawPoints.removeAllElements();
	xHoleVector.removeAllElements();
	yHoleVector.removeAllElements();	
	holePoints.removeAllElements();
	firstClick = true;
	removePolygon();
	hasTriangulation = false;
	}
	
	public void addVisibility(int i)
	{
	// adds visibility for guardPoint i
	int lastInt = -10;
		Coord cCur = guardPoints.get(i);			
		Vector<Integer> xPoints = new Vector<Integer>();
		Vector<Integer> yPoints = new Vector<Integer>();
		
		//if(cCur.intersectPolygon(p1))
		
		for(int j=0; j<drawPoints.size(); j++)
			{
			boolean seeFirst = false;
			boolean seeSecond = false;
			Coord dCur = drawPoints.get(j);
			Coord dNext = drawPoints.get((j+1)%drawPoints.size());
			if(!cCur.intersectPolygon(dCur,p1)) 
				{
				seeFirst = true;
				}			
			if(!cCur.intersectPolygon(dNext,p1)) 
				{
				seeSecond = true;
				}
			//System.out.println("point " + j + " seeFirst: " + seeFirst + " seeSecond: " + seeSecond);
			if(seeFirst)// && seeSecond)
				{
				xPoints.add(xPointVector.get(j));
				yPoints.add(yPointVector.get(j));
				//lastInt = j;					
				}
				
			if(!seeSecond && seeFirst)
				{
				int dir = 1;
				double slope = dCur.mOf(dNext);
				double dy;
				double dx;
				int iterations;
				if(Math.abs(slope)>1)
					{
					dy=1;
					dx=1/slope;
					iterations = (int)Math.abs(dNext.getY() - dCur.getY());					
					if(dCur.getY() > dNext.getY()) dir = -1;
					}
				else
					{
					dy=slope;
					dx=1;
					iterations = (int)Math.abs(dNext.getX() - dCur.getX());			
					if(dCur.getX() > dNext.getX()) dir = -1;
					}	
				double curX = dCur.getX() + dx*dir;
				double curY = dCur.getY() + dy*dir;
				double endX = -1.;
				double endY = -1.;
				if(!cCur.intersectPolygon(new Coord(curX,curY),p1)) // then we have at least some extra coverage
					{
					//System.out.println("seeFirst, not second: " + j);
					//System.out.println(iterations);
					int kLast = 0;
					for(int k=0; k < iterations; k++)
						{
						if(!cCur.intersectPolygon(new Coord(curX,curY),p1))
							{
								if(k-kLast<=1)
									{
									endX = curX;
									endY = curY;
									//System.out.println(k);
									kLast = k;
									}
							}
						curX += dir*dx;
						curY += dir*dy;
						}					
					if(endX >= 0)
						{
						xPoints.add((int)endX);
						yPoints.add((int)endY);
						//System.out.println(dCur);
						//System.out.println(new Coord(endX,endY));
						}
					}
				}
			else if(!seeFirst && seeSecond)
				{
				int dir = 1;
				double slope = dCur.mOf(dNext);
				double dy;
				double dx;
				int iterations;
				if(Math.abs(slope)>1)
					{
					dy=1;
					dx=1/slope;
					iterations = (int)Math.abs(dNext.getY() - dCur.getY());					
					if(dCur.getY() > dNext.getY()) dir = -1;
					}
				else
					{
					dy=slope;
					dx=1;
					iterations = (int)Math.abs(dNext.getX() - dCur.getX());			
					if(dCur.getX() > dNext.getX()) dir = -1;
					}	
				double curX = dNext.getX() - dx*dir;
				double curY = dNext.getY() - dy*dir;
				double endX = -1.;
				double endY = -1.;
				if(!cCur.intersectPolygon(new Coord(curX,curY),p1)) // then we have at least some extra coverage
					{
					int kLast = 0;
					for(int k=0; k < iterations; k++)
						{
						if(!cCur.intersectPolygon(new Coord(curX,curY),p1))
							{
							//System.out.println(k);
							if(k-kLast<=1) // we get stray points that appear much later that mess up shape otherwise
								{
								endX = curX;
								endY = curY;
								kLast = k;
								//System.out.println(kLast + " = kLast");
								}
							}
						curX -= dir*dx;
						curY -= dir*dy;
						}					
					if(endX >= 0)
						{
						xPoints.add((int)endX);
						yPoints.add((int)endY);
						}
					}
				}
			else if(!seeFirst && !seeSecond) //neither seeFirst nor seeSecond
				{
				int dir = 1;
				double slope = dCur.mOf(dNext);
				double dy;
				double dx;
				int iterations;
				if(Math.abs(slope)>1)
					{
					dy=1;
					dx=1/slope;
					iterations = (int)Math.abs(dNext.getY() - dCur.getY());					
					if(dCur.getY() > dNext.getY()) dir = -1;
					}
				else
					{
					dy=slope;
					dx=1;
					iterations = (int)Math.abs(dNext.getX() - dCur.getX());			
					if(dCur.getX() > dNext.getX()) dir = -1;
					}	
				double curX = dCur.getX() + dx*dir;
				double curY = dCur.getY() + dy*dir;
				double startX = -1.;
				double startY = -1.;
				double endX = -1.;
				double endY = -1.;
				int kLast = 0;
				for(int k=0; k < iterations; k++)
					{
					if(!cCur.intersectPolygon(new Coord(curX,curY),p1))
						{
						//System.out.println(k);
						//System.out.println("kLast = " + kLast);
						if(startX >= 0)
								{
								if (k-kLast <= 1) // else we have a stray point, don't care about it
									{
									endX = curX;
									endY = curY;
									kLast=k;
									}
								}
							else //first point that's visible
								{
								startX = curX;
								startY = curY;
								kLast = k;
								}
						}
					curX += dir*dx;
					curY += dir*dy;
					}					
				if(startX >= 0 && endX >= 0)
					{
					xPoints.add((int)startX);
					yPoints.add((int)startY);
					xPoints.add((int)endX);
					yPoints.add((int)endY);
					}
				
				}
				
					
				
			}
			
		nPoints = xPoints.size();
		//System.out.println(nPoints);
		int[] xpoints = new int[nPoints];
		int[] ypoints = new int[nPoints];
		
		for (int k=0; k<nPoints; k++)
			{		
			xpoints[k] = xPoints.get(k);
			ypoints[k] = yPoints.get(k);
			}		
			
		Polygon p = new Polygon(xpoints,ypoints,nPoints);
		visibilities.add(p);
		
		repaint();
		//visibilities
	}
	
	
	public void showVisibility()
	{
	
	
	for (int i=0; i<guardPoints.size(); i++)
		{
		addVisibility(i);
		}
	//drawPoints.add(guardPoints.get(0).intersection(drawPoints.get(0),drawPoints.get(1),drawPoints.get(2)));
	//System.out.println(guardPoints.get(0).intersectSegment(drawPoints.get(0),drawPoints.get(1),drawPoints.get(2)));
	System.out.println(visibilities.size());
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
	
	if(visibilities.size()>0)
	{
	bufG.setColor(Color.YELLOW);
		for (int i=0; i<visibilities.size(); i++)
			{		
			bufG.fillPolygon(visibilities.get(i));	
			}
	}
	
	bufG.setColor(Color.BLACK);
	if(p1 == null)	
	{
		for (int i=0; i<drawPoints.size(); i++)
			{		
			Coord c = drawPoints.get(i);
			bufG.fillOval((int)c.getX()-4,(int)c.getY()-4,8,8);	
			if(i>0)
				{
				Coord cPrev = drawPoints.get((i-1));	
				bufG.drawLine((int)c.getX(),(int)c.getY(),(int)cPrev.getX(),(int)cPrev.getY());
				}
			}
	} else
	{
		bufG.drawPolygon(p1);
		if (hasTriangulation)
			{
			bufG.setColor(new Color(150,150,150));
			for(int i=0; i<p1.npoints-2; i++)
				{
				for (int j=i+2; j<p1.npoints; j++)
					{
					if(triangulation[i][j])	
						{
						bufG.drawLine((int)coords[i].getX(),(int)coords[i].getY(),(int)coords[j].getX(),(int)coords[j].getY());							
						}
					}
				}
			bufG.setColor(Color.BLACK);
			}
	}
	
		for (int i=0; i<holePoints.size(); i++)
			{		
			bufG.setColor(Color.BLUE);
			Coord c = holePoints.get(i);
			bufG.fillOval((int)c.getX()-4,(int)c.getY()-4,8,8);			
			}
			
		for (int i=0; i<guardPoints.size(); i++)
			{		
			bufG.setColor(Color.RED);
			Coord c = guardPoints.get(i);
			bufG.fillOval((int)c.getX()-4,(int)c.getY()-4,8,8);			
			}
		
		
			
		
		if (testing) //just for gauging where vertices are in terms of coordinates
			{
			bufG.setColor(Color.GREEN);
			bufG.fillOval(100-4,(int)100-4,8,8);	
			bufG.fillOval(200-4,(int)200-4,8,8);	
			bufG.fillOval(300-4,(int)300-4,8,8);	
			bufG.fillOval(400-4,(int)400-4,8,8);	
			}
			
		g.drawImage(buffer, 0, 0, this);
		g.dispose();
		
	}
		
	}
	
}