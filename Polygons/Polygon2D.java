import java.lang.Math;
import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.awt.image.BufferedImage;

public class Polygon2D {

    private Coord[] coords;
	private int npoints;
	private boolean closed = true;

    public Polygon2D(Coord[] coords, int npoints)
    {
	this.coords = coords;
	this.npoints = npoints;
    }

	public Polygon2D(Coord[] coords, int npoints, boolean closed)
    {
	this.coords = coords;
	this.npoints = npoints;
	this.closed = closed;
    }
	
    public int getSides()
    {
	return npoints;
    }
	
	public void paint(Graphics g, int boardWidth, int boardHeight)
	{		
		for (int i=0; i<npoints; i++)
		{
			
			Coord cCur = coords[i];
			Coord cNext = coords[(i+1) % npoints];
			if (!closed && i==npoints-1) break;
			g.setColor(Color.BLACK);
			if (cCur.contained(0,0,boardWidth,boardHeight) && cNext.contained(0,0,boardWidth,boardHeight))
			{
			g.drawLine((int)cCur.getX(),(int)cCur.getY(),(int)cNext.getX(),(int)cNext.getY());
			}
			else
			{
			int edgePoints = cCur.numberOfEdgePoints(cNext, boardWidth, boardHeight);
			if (edgePoints == 1) 
				{
				Coord cTemp = cCur.findEdgePoint(cNext,boardWidth,boardHeight);
				if (cCur.contained(0,0,boardWidth,boardHeight)) //so cNext isn't contained				
					{							
					g.drawLine((int)cCur.getX(),(int)cCur.getY(),(int)cTemp.getX(),(int)cTemp.getY());
					}
				else if (cNext.contained(0,0,boardWidth,boardHeight)) //so cCur isn't contained
					{				
					g.drawLine((int)cTemp.getX(),(int)cTemp.getY(),(int)cNext.getX(),(int)cNext.getY());
					}
				}					
			else if (edgePoints == 2) 
				{
				Coord cTemp1 = cCur.findTwoEdgePoints(cNext,boardWidth,boardHeight)[0];
				Coord cTemp2 = cCur.findTwoEdgePoints(cNext,boardWidth,boardHeight)[1];
				g.drawLine((int)cTemp1.getX(),(int)cTemp1.getY(),(int)cTemp2.getX(),(int)cTemp2.getY());
				}
			else if (edgePoints > 0) System.out.println("Error, edgepoints = " + edgePoints);
			}
			/*
			else if (cCur.contained(0,0,boardWidth,boardHeight)) //so cNext isn't contained
			{
			Coord cTemp = Coord.findEdgeIntersection(cCur,cNext,boardWidth,boardHeight);
			g.drawLine((int)cCur.getX(),(int)cCur.getY(),(int)cTemp.getX(),(int)cTemp.getY());
			} else if (cNext.contained(0,0,boardWidth,boardHeight)) //so cCur isn't contained
			{
			Coord cTemp = Coord.findEdgeIntersection(cNext,cCur,boardWidth,boardHeight);
			g.drawLine((int)cTemp.getX(),(int)cTemp.getY(),(int)cNext.getX(),(int)cNext.getY());
			} 		
			else //neither contained
			{
			Coord cCurTemp = 
			*/
		}
	}
}