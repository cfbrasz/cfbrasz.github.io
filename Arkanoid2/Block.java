//
// Class for block in Arkanoid
// (c) Fred Brasz 2008

import java.awt.event.*;
import java.awt.*;
import java.lang.Math;
//import javax.vecmath.*;

public class Block {
    
    int x;
    int y;
    int blockWidth = Constants.blockWidth;
    int blockHeight = Constants.blockHeight;  
    private int boardWidth = Constants.boardWidth;
    private int boardHeight = Constants.boardHeight;
    Color blockColor;
    int hits;
	private boolean leftNeighbor,rightNeighbor,topNeighbor,bottomNeighbor;
	boolean shiny = false;
	Crack cr = null;

    public Block(int x, int y,Color blockColor, int hits)
    {
	this.x = x;
	this.y = y;
	this.blockColor = blockColor;
	this.hits = hits;
    }

	public void setLeftNeighbor(boolean leftNeighbor)
	{
	this.leftNeighbor = leftNeighbor;
	}
	
	public void setRightNeighbor(boolean rightNeighbor)
	{
	this.rightNeighbor = rightNeighbor;
	}
	
	public void setTopNeighbor(boolean topNeighbor)
	{
	this.topNeighbor = topNeighbor;
	}
	
	public void setBottomNeighbor(boolean bottomNeighbor)
	{
	this.bottomNeighbor = bottomNeighbor;
	}
	
    public Coord getCoord()
    {
	// returns coords of center of block
	return new Coord(x + (double)blockWidth/2, boardHeight - y - (double)blockHeight/2);
    }

    public int getX()
    {
	// returns x value of top left corner of block
	return x;
    }

    public int getY()
    {
	// returns y value of top left corner of block
	return y;
    }
	
	public javax.vecmath.Point2d[] accessibleCorners(Ball bCur)
	{
	// returns all corners that bCur could hit within the next time step
	//for now, just return all 4
	javax.vecmath.Point2d[] corners = new javax.vecmath.Point2d[4];
	corners[0] = new javax.vecmath.Point2d(x,y);
	corners[1] = new javax.vecmath.Point2d(x+blockWidth,y);
	corners[2] = new javax.vecmath.Point2d(x+blockWidth,y+blockHeight);
	corners[3] = new javax.vecmath.Point2d(x,y+blockHeight);
	return corners;
	}
	
	public EdgeO[] accessibleEdges(Ball bCur)
	{
	// returns all edges that bCur could hit within the next time step
	//for now, just return all 4
	javax.vecmath.Point2d[] c = new javax.vecmath.Point2d[4];
	c[0] = new javax.vecmath.Point2d(x,y);
	c[1] = new javax.vecmath.Point2d(x+blockWidth,y);
	c[2] = new javax.vecmath.Point2d(x+blockWidth,y+blockHeight);
	c[3] = new javax.vecmath.Point2d(x,y+blockHeight);
	EdgeO[] edges = new EdgeO[4];
	edges[0] = new EdgeO(y,x,x+blockWidth,false);
	edges[1] = new EdgeO(x,y,y+blockHeight,true);
	edges[2] = new EdgeO(y+blockHeight,x,x+blockWidth,false);
	edges[3] = new EdgeO(x+blockWidth,y,y+blockHeight,true);
	return edges;
	}
	
	public Edge[] accessibleEdgesGeneral(Ball bCur)
	{
	// returns all edges that bCur could hit within the next time step
	//for now, just return all 4
	javax.vecmath.Point2d[] c = new javax.vecmath.Point2d[4];
	c[0] = new javax.vecmath.Point2d(x,y);
	c[1] = new javax.vecmath.Point2d(x+blockWidth,y);
	c[2] = new javax.vecmath.Point2d(x+blockWidth,y+blockHeight);
	c[3] = new javax.vecmath.Point2d(x,y+blockHeight);
	Edge[] edges = new Edge[4];
	for(int i=0; i<4; i++) edges[i] = new Edge(c[i],c[(i+1)%4]);
	return edges;
	}

    public void blockHit()
    {
	// subtracts 1 from hits
	hits = hits - 1;
    }

    public int hitsLeft()
    {
	// returns hits
	return hits;
    }

    public boolean indestructible()
    {
	// returns true if the block is gold
	return ArkanoidApplet.diff(blockColor,Constants.GOLD) < Constants.minColorDist;
    }

    public boolean hitByLaser(Laser l)
    {
	//Post: returns true if block is hit by a laser	
	return (Math.abs(x+blockWidth/2 - l.getX()) < blockWidth/2 + l.getWidth()
		&& l.getY() < y + blockHeight);
    }	


    public void paint(Graphics g)
    {
	g.setColor(blockColor);
	g.fillRect(x, y, blockWidth, blockHeight);
	
	int r = blockColor.getRed();
	int gr = blockColor.getGreen();
	int b = blockColor.getBlue();
	//Color darker = new Color((int)(r*(colors-i+colors/2)/(3*colors/2)),(int)(gr*(colors-i+colors/2)/(3*colors/2)),(int)(b*(colors-i+colors/2)/(3*colors/2)));
	Color darker = new Color((int)(r*0.5),(int)(gr*0.5),(int)(b*0.5));
	g.setColor(darker);
	if(blockHeight > 4 && blockWidth > 4) g.drawRect(x+1, y+1, blockWidth-2, blockHeight-2);	
	if(blockHeight > 12 && blockWidth > 12) g.drawRect(x+2, y+2, blockWidth-4, blockHeight-4);	
	
	
	if(shiny)
		{
		g.setColor(Color.WHITE);
		g.drawLine(x+1,y+1,x+1+blockWidth-2,y+1);
		g.drawLine(x+2,y+2,x+2+blockWidth-4,y+2);
		g.drawLine(x+1,y+1,x+1,y+1+blockHeight-2);
		g.drawLine(x+2,y+2,x+2,y+2+blockHeight-4);
		
		g.setColor(darker);
		//g.drawLine(x+3,y+3+blockHeight-6,x+3+blockWidth-6,y+3+blockHeight-6);
		//g.drawLine(x+4,y+4+blockHeight-8,x+4+blockWidth-8,y+4+blockHeight-8);	
		//g.drawLine(x+3+blockWidth-6,y+3,x+3+blockWidth-6,y+3+blockHeight-6);
		//g.drawLine(x+4+blockWidth-8,y+4,x+4+blockWidth-8,y+4+blockHeight-8);				
		}
	g.setColor(Color.BLACK);
	g.drawRect(x, y, blockWidth, blockHeight);
    }

    
}
