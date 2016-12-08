import java.lang.Math;
import java.awt.event.*;
import java.awt.*;

public class Coord {

    private double x;
    private double y;
	private double theta = 0;

    public Coord(double x, double y)
    {
	this.x = x;
	this.y = y;
    }

    public double getX()
    {
	return x;
    }

    public double getY()
    {
	return y;
    }
	
	public Coord copy()
	{
	return new Coord(x,y);
	}
	
	public static int min(int[] values, int nValues)
	{
	// returns min of array of nValues integers
	int result = values[0];
	for(int i=1; i<nValues; i++)
		{
		if (values[i] < result) result = values[i];
		}
	return result;		
	}
	
	public static int max(int[] values, int nValues)
	{
	// returns min of array of nValues integers
	int result = values[0];
	for(int i=1; i<nValues; i++)
		{
		if (values[i] > result) result = values[i];
		}
	return result;		
	}
	
	public boolean parallel(Coord cOth, Coord seg1, Coord seg2)
	{
	// returns true it line passing through this and cOth is parallel to line passing through seg1 and seg2
	double m = mOf(cOth);
	double m2 = seg1.mOf(seg2);
	return m == m2;
	}	
	
	public Coord intersection(Coord cOth, Coord seg1, Coord seg2)
	{
	// returns intersection point of line passing through this and cOth with line passing through seg1 and seg2
	double m = mOf(cOth);
	double m2 = seg1.mOf(seg2);
	double xI = (m*x - m2*seg1.getX() + seg1.getY() - y)/(m-m2);
	double yI = yOf(cOth,xI);
	/*
	System.out.println(m);
	System.out.println(m2);
	System.out.println(xI);
	System.out.println(yI);
	System.out.println(yI - seg1.yOf(seg2,xI));
	*/
	if(seg1.getX() == seg2.getX())
		{
		//System.out.println("vertical");
		xI = seg1.getX();
		yI = m*(xI-x) + y;
		}
		
	if(x == cOth.getX())
		{
		//System.out.println("vertical");
		xI = x;
		yI = m2*(xI-seg1.getX()) + seg1.getY();
		}
			
	if(seg1.getY() == seg2.getY())
		{
		//System.out.println("horizontal");
		yI = seg1.getY();
		xI = (yI-y)/m + x;
		}
		
	if(y == cOth.getY())
		{
		//System.out.println("horizontal");
		yI = y;
		xI = (yI-seg1.getY())/m2 + seg1.getX();
		}
	
	return new Coord(xI,yI);
	}
	
	public boolean intersectSegment(Coord cOth, Coord seg1, Coord seg2)
	{
	// returns true if segment from current Coord to cOth intersects segment from seg1 to seg2 in point OTHER than one of the vertices
	Coord cI = intersection(cOth,seg1,seg2);
	if(cI.equals(this) ||
	   cI.equals(cOth) ||
	   cI.equals(seg1) ||
	   cI.equals(seg2)) return false;
	
	double lowX = Math.min(x, cOth.getX());
	double highX = Math.max(x, cOth.getX());
	double lowY = Math.min(y, cOth.getY());
	double highY = Math.max(y, cOth.getY());
	
	double lowX2 = Math.min(seg1.getX(), seg2.getX());
	double highX2 = Math.max(seg1.getX(), seg2.getX());
	double lowY2 = Math.min(seg1.getY(), seg2.getY());
	double highY2 = Math.max(seg1.getY(), seg2.getY());
	
	return (cI.contained(lowX,lowY,highX-lowX,highY-lowY) ) && 
		   (cI.contained(lowX2,lowY2,highX2-lowX2,highY2-lowY2) );
	}
	
	
	public boolean intersectPolygon(Coord cOth, Polygon p)
	{
	// returns true if segment from current Coord to cOth intersects outline of polygon p (other than at cOth)
	boolean result = false;
	Coord[] coords = new Coord[p.npoints];	
	for(int i=0; i<p.npoints; i++)
		{
		coords[i] = new Coord(p.xpoints[i], p.ypoints[i]);
		}
	for(int i=0; i<p.npoints; i++)
		{
		if (intersectSegment(cOth,coords[i],coords[(i+1)%p.npoints])
			&& !intersection(cOth,coords[i],coords[(i+1)%p.npoints]).equals(cOth)
			&& !intersection(cOth,coords[i],coords[(i+1)%p.npoints]).equals(this) ) 
			{
			result = true;
			/*
			System.out.println(cOth.getX());
			System.out.println(cOth.getY());
			System.out.println(intersection(cOth,coords[i],coords[(i+1)%p.npoints]).getX());
			System.out.println(intersection(cOth,coords[i],coords[(i+1)%p.npoints]).getY());
			System.out.println(intersection(cOth,coords[i],coords[(i+1)%p.npoints]).equals(cOth));
			*/
			}
		}
	return result;
	}
	
	public boolean intersectPolygonWithDiagonals(Coord cOth, Polygon p, boolean[][] diagonal)
	{
	// returns true if segment from current Coord to cOth intersects outline of polygon p (other than at cOth) or diagonals between indices containing true
	if(intersectPolygon(cOth,p)) return true;
	// otherwise check if it intersects a diagonal
	boolean result = false;
	Coord[] coords = new Coord[p.npoints];	
	for(int i=0; i<p.npoints; i++)
		{
		coords[i] = new Coord(p.xpoints[i], p.ypoints[i]);
		}
	for(int i=0; i<p.npoints; i++)
		{
		for(int j=0; j<p.npoints; j++)
			{
			if(diagonal[i][j])
				{
				if (intersectSegment(cOth,coords[i],coords[j])
					&& !intersection(cOth,coords[i],coords[j]).equals(cOth)
					&& !intersection(cOth,coords[i],coords[j]).equals(this)) 
					{
					result = true;
					}
				}
			}
		}
	return result;
	}
	
	public boolean insidePolygon(Coord cOth, Polygon p)
	{
	//returns true if every point from this to cOth is contained by p
	boolean result = true;
	int dir = 1;
	double slope = mOf(cOth);
	double dy;
	double dx;
	int iterations;
	if(Math.abs(slope)>1)
		{
		dy=1;
		dx=1/slope;
		iterations = (int)Math.abs(cOth.getY() - y)-3;					
		if(y > cOth.getY()) dir = -1;
		}
	else
		{
		dy=slope;
		dx=1;
		iterations = (int)Math.abs(cOth.getX() - x)-3;			
		if(x > cOth.getX()) dir = -1;
		}	
	double curX = x + dx*dir;
	double curY = y + dy*dir;
	double endX = -1.;
	double endY = -1.;
	for(int k=0; k < iterations; k++)
		{
		if(!p.contains((int)curX,(int)curY))
			{
			System.out.println("iteration number " + k + " out of " + iterations);
			result = false;
			break;
			}			
		curX += dir*dx;
		curY += dir*dy;
		}
	return result;
	}
	
	public boolean contained(double x0, double y0, double w, double h)
	{
	//returns true if this point is contained by the given rectangle
	boolean result = true;
	if (x < x0 || y < y0) result = false;
	if (x > x0 + w || y > y0 + h) result = false;
	return result;
	}
	
	public boolean contained(int x0, int y0, int w, int h)
	{
	//returns true if this point is contained by the given rectangle
	boolean result = true;
	if (x < x0 || y < y0) result = false;
	if (x > x0 + w || y > y0 + h) result = false;
	return result;
	}
	
	public Coord[] findTwoEdgePoints(Coord cOth, int boardWidth, int boardHeight)
	{
	int foundPoints = 0;
	Coord[] result = new Coord[2];
	
	double lowX = Math.min(x, cOth.getX());
	double highX = Math.max(x, cOth.getX());
	double lowY = Math.min(y, cOth.getY());
	double highY = Math.max(y, cOth.getY());
	
	double newX = 0;
	double newY = 0;
	
	
	if (yOf(cOth,0) > 0 && yOf(cOth,0) < boardHeight &&
		lowX < 0 && highX > 0) 
		{
		newY = yOf(cOth,0);
		newX = 0;
		if (foundPoints < 2) result[foundPoints] = new Coord(newX,newY);
		else System.out.println("Error, more than 2 points");
		foundPoints++;
		}
	if (yOf(cOth,boardWidth) > 0 && yOf(cOth,boardWidth) < boardHeight &&
		lowX < boardWidth && highX > boardWidth)
		{
		newY = yOf(cOth,boardWidth);
		newX = boardWidth;
		if (foundPoints < 2) result[foundPoints] = new Coord(newX,newY);
		else System.out.println("Error, more than 2 points");
		foundPoints++;
		}
	if (xOf(cOth,0) > 0 && xOf(cOth,0) < boardWidth &&
		lowY < 0 && highY > 0)
		{
		newX = xOf(cOth,0);
		newY = 0;
		if (foundPoints < 2) result[foundPoints] = new Coord(newX,newY);
		else System.out.println("Error, more than 2 points");
		foundPoints++;
		}
	if (xOf(cOth,boardHeight) > 0 && xOf(cOth,boardHeight) < boardWidth &&
		lowY < boardHeight && highY > boardHeight)
		{
		newX = xOf(cOth,boardHeight);
		newY = boardHeight;
		if (foundPoints < 2) result[foundPoints] = new Coord(newX,newY);
		else System.out.println("Error, more than 2 points");
		foundPoints++;
		}
		
	return result;
	}
	
	
	public Coord findEdgePoint(Coord cOth, int boardWidth, int boardHeight)
	{
	double lowX = Math.min(x, cOth.getX());
	double highX = Math.max(x, cOth.getX());
	double lowY = Math.min(y, cOth.getY());
	double highY = Math.max(y, cOth.getY());
	
	double newX = 0;
	double newY = 0;
	
	
	if (yOf(cOth,0) > 0 && yOf(cOth,0) < boardHeight &&
		lowX < 0 && highX > 0) 
		{
		newY = yOf(cOth,0);
		newX = 0;
		}
	else if (yOf(cOth,boardWidth) > 0 && yOf(cOth,boardWidth) < boardHeight &&
		lowX < boardWidth && highX > boardWidth)
		{
		newY = yOf(cOth,boardWidth);
		newX = boardWidth;
		}
	else if (xOf(cOth,0) > 0 && xOf(cOth,0) < boardWidth &&
		lowY < 0 && highY > 0)
		{
		newX = xOf(cOth,0);
		newY = 0;
		}
	else if (xOf(cOth,boardHeight) > 0 && xOf(cOth,boardHeight) < boardWidth &&
		lowY < boardHeight && highY > boardHeight)
		{
		newX = xOf(cOth,boardHeight);
		newY = boardHeight;
		}
		
	return new Coord(newX,newY);
	}
	
	public int numberOfEdgePoints(Coord cOth, int boardWidth, int boardHeight)
	{
	// solve for all intersections with the 4 lines constructing screen, return the number of them that are within the screen
	
	int points = 0;
	double lowX = Math.min(x, cOth.getX());
	double highX = Math.max(x, cOth.getX());
	double lowY = Math.min(y, cOth.getY());
	double highY = Math.max(y, cOth.getY());
	
	if (yOf(cOth,0) > 0 && yOf(cOth,0) < boardHeight &&
		lowX < 0 && highX > 0) points++;
	if (yOf(cOth,boardWidth) > 0 && yOf(cOth,boardWidth) < boardHeight &&
		lowX < boardWidth && highX > boardWidth) points++;
	if (xOf(cOth,0) > 0 && xOf(cOth,0) < boardWidth &&
		lowY < 0 && highY > 0) points++;
	if (xOf(cOth,boardHeight) > 0 && xOf(cOth,boardHeight) < boardWidth &&
		lowY < boardHeight && highY > boardHeight) points++;
	
	return points;
	}
	
	public double mOf(Coord cOth)
	{	
	Vector2D coordsVector = new Vector2D(this,cOth);
	double cSlope = coordsVector.getSlope();
	return cSlope;
	}
	
	
	public double yOf(Coord cOth, double xEval)
	{
	// evaluates y value of line between this and cOth at xEval
	Vector2D coordsVector = new Vector2D(this,cOth);
	double cSlope = coordsVector.getSlope();
	
	return cSlope*(xEval - x) + y;
	}
	
	public double xOf(Coord cOth, double yEval)
	{
	// evaluates x value of line between this and cOth at yEval
	Vector2D coordsVector = new Vector2D(this,cOth);
	double cSlope = coordsVector.getSlope();
	
	if (cSlope != 0) return (1/cSlope)*(yEval - y) + x;
	else 
	{
	return -999.; // slope 0, won't be hitting anything at -999 anyways
	}
	}
	
	public static Coord findEdgeIntersection(Coord cIn, Coord cOut, int boardWidth, int boardHeight)
	{
	// when one coordinate is in and other is out
	int edgeNumber = whichEdgeIntersection(cIn, cOut, boardWidth, boardHeight);
	
	double newX;
	double newY;
	
	Vector2D coordsVector = new Vector2D(cIn,cOut);
	double cSlope = coordsVector.getSlope();
	Coord result;
	
	switch (edgeNumber) {
		case 0: //y = 0
			newY = 0.;
			newX = cIn.getX() + (newY - cIn.getY())/cSlope;
			result = new Coord(newX,newY);
			break;
		case 2: // y = boardHeight
			newY = boardHeight*1.0;
			newX = cIn.getX() + (newY - cIn.getY())/cSlope;
			result = new Coord(newX,newY);
			break;
		case 1: // x = boardWidth
			newX = boardWidth*1.0;
			newY = cIn.getY() + (newX - cIn.getX())*cSlope;
			result = new Coord(newX,newY);
			break;
		case 3: // x = 0
			newX = 0.;
			newY = cIn.getY() + (newX - cIn.getX())*cSlope;
			result = new Coord(newX,newY);
			break;
		default:
            System.out.println("Invalid Coordinates");
			result = new Coord(0.,0.);
            break;
		}
	
	return result;
	}
	
	public static int whichEdgeIntersection(Coord cIn, Coord cOut, int boardWidth, int boardHeight)
	{
	// when one coord is in and other is out
	// 0 = top, 1 = right, 2 = bottom, 3 = left
	int result = -1;
	
	Vector2D coordsVector = new Vector2D(cIn,cOut);
	double cSlope = Math.abs(coordsVector.getSlope());
	
	if (cOut.getX() >= 0 && cOut.getX() <= boardWidth)
	{
		if (cOut.getY() > 0) result = 2;
		else result = 0;
	}
	
	else if (cOut.getY() >= 0 && cOut.getY() <= boardHeight)
	{
		if (cOut.getX() > 0) result = 1;
		else result = 3;
	} 
	
	// Now corner areas:
	else if (cOut.getX() < 0 && cOut.getY() < 0) // top left
	{
		Vector2D cornerVector = new Vector2D(0,0,cOut.getX(),cOut.getY());
		double cornerSlope = Math.abs(cornerVector.getSlope());
		
		if (cSlope > cornerSlope) result = 3;
		else result = 0;
	}
	
	else if (cOut.getX() > boardWidth && cOut.getY() < 0) // top right
	{
		Vector2D cornerVector = new Vector2D(boardWidth,0,cOut.getX(),cOut.getY());
		double cornerSlope = Math.abs(cornerVector.getSlope());
		
		if (cSlope > cornerSlope) result = 1;
		else result = 0;
	}
	
	else if (cOut.getX() > boardWidth && cOut.getY() > boardHeight) // bottom right
	{
		Vector2D cornerVector = new Vector2D(boardWidth,boardHeight,cOut.getX(),cOut.getY());
		double cornerSlope = Math.abs(cornerVector.getSlope());
		
		if (cSlope > cornerSlope) result = 1;
		else result = 2;
	}
	
	else if (cOut.getX() < 0 && cOut.getY() > boardHeight) // bottom left
	{
		Vector2D cornerVector = new Vector2D(0,boardHeight,cOut.getX(),cOut.getY());
		double cornerSlope = Math.abs(cornerVector.getSlope());
		
		if (cSlope > cornerSlope) result = 3;
		else result = 2;
	}
	
	return result;
	}
	
	/*
	public int whichEdgeIntersectionOut(Coord cIn, int boardWidth, int boardHeight)
	{
	Coord cOut = this; //cIn not actually in
	// 0 = top, 1 = right, 2 = bottom, 3 = left
	int result = -1;
	int region = -1;
	
	// 8 possible regions for cOut to be in, 4 corners to check
	
	Vector2D coordsVector = new Vector2D(cIn,cOut);
	double cSlope = Math.abs(coordsVector.getSlope());
	
	Vector2D corners[] = new Vector2D[4];
	Coord coordCorners[] = new Coord[4];
	double cornerSlope[] = new double[4];
	coordCorners[0] = new Coord(0,0);
	coordCorners[1] = new Coord(boardWidth,0);
	coordCorners[2] = new Coord(boardWidth,boardHeight);
	coordCorners[3] = new Coord(0,boardHeight);	
	int TL = 0;
	int TR = 1;
	int BR = 2;
	int BL = 3;
	for (int i=0; i<4; i++)
	{
	corners[i] = new Vector2D(coordCorners[i],cOut); 
	cornerSlope = Math.abs(corners[i].getSlope());
	}
	
	// define region
	
	if (y<0) //top row
		{
		region = 1; //top middle
		if (x<0) region = 0; //top left
		if (x>boardWidth) region = 2; //top right
		} 
	
	else if (y>boardHeight) //bottom row
		{
		region = 5; //bottom middle
		if (x<0) region = 6; //bottom left
		if (x>boardWidth) region = 4; //bottom right
		} 
	
	else //middle row
		{
		if (x<0) region = 7; //mid left
		if (x>boardWidth) region = 3; //mid right
		}
		
	switch (region) {
		case 0: //y = 0
			newY = 0.;
			newX = cIn.getX() + (newY - cIn.getY())/cSlope;
			result = new Coord(newX,newY);
			break;
	
	
	if (cOut.getX() >= 0 && cOut.getX() <= boardWidth)
	{
		if (cOut.getY() > 0) 
		{
		Vector2D cornerVector = new Vector2D(0,0,cOut.getX(),cOut.getY());
		double cornerSlope = Math.abs(cornerVector.getSlope());
		result = 2;
		else result = 0;
	}
	
	else if (cOut.getY() >= 0 && cOut.getY() <= boardHeight)
	{
		if (cOut.getX() > 0) result = 1;
		else result = 3;
	} 
	
	// Now corner areas:
	else if (cOut.getX() < 0 && cOut.getY() < 0) // top left
	{
		Vector2D cornerVector = new Vector2D(0,0,cOut.getX(),cOut.getY());
		double cornerSlope = Math.abs(cornerVector.getSlope());
		
		if (cSlope > cornerSlope) result = 3;
		else result = 0;
	}
	
	else if (cOut.getX() > boardWidth && cOut.getY() < 0) // top right
	{
		Vector2D cornerVector = new Vector2D(boardWidth,0,cOut.getX(),cOut.getY());
		double cornerSlope = Math.abs(cornerVector.getSlope());
		
		if (cSlope > cornerSlope) result = 1;
		else result = 0;
	}
	
	else if (cOut.getX() > boardWidth && cOut.getY() > boardHeight) // bottom right
	{
		Vector2D cornerVector = new Vector2D(boardWidth,boardHeight,cOut.getX(),cOut.getY());
		double cornerSlope = Math.abs(cornerVector.getSlope());
		
		if (cSlope > cornerSlope) result = 1;
		else result = 2;
	}
	
	else if (cOut.getX() < 0 && cOut.getY() > boardHeight) // bottom left
	{
		Vector2D cornerVector = new Vector2D(0,boardHeight,cOut.getX(),cOut.getY());
		double cornerSlope = Math.abs(cornerVector.getSlope());
		
		if (cSlope > cornerSlope) result = 3;
		else result = 2;
	}
	
	return result;
	}
	*/
	
	
	public void rotate(double theta)
	{
	// rotates coord counterclockwise by angle theta
	double newX = +Math.cos(theta)*x + Math.sin(theta)*y;
	double newY = -Math.sin(theta)*x + Math.cos(theta)*y;
	x = newX;
	y = newY;
	this.theta += theta;
	}
	
	public void moveX(double dx)
	{
	x += dx;
	}
	
	public void moveY(double dy)
	{
	y += dy;
	}
	
	public void move(double ds, double angle)
	{
	// angle on the screen counterclockwise from x=0 positive axis
	x += ds * Math.cos(angle);
	y -= ds * Math.sin(angle);
	}

    public double dist(Coord that)
    {
	double diffX = that.getX() - this.getX();
	double diffY = that.getY() - this.getY();
	return Math.sqrt(diffX*diffX + diffY*diffY);
    }
	
	public Coord midpoint(Coord that)
    {
	double sumX = (that.getX() + this.getX())/2;
	double sumY = (that.getY() + this.getY())/2;
	return new Coord(sumX,sumY);
    }
	
	public boolean intersect(Coord that,double radius)
	{
	// post: returns true if this and that are within 2*radius of each other
	return this.dist(that) <= 2*radius;
	}

    public double angle()
    {
	//Post: returns the angle from this to the origin (0,0).
	// (Useful for the paddle ball collision to give us an angle to compare
	// to the perpendicular angle at the surface of the paddle.)
	Coord n = new Coord (0,0);
	//return this.angle(n);
	return n.angle(this);
    }

	public void flip(int boardHeight)
	{
	//Post: flips the coordinates such that y = 0 is the bottom of the screen and y = boardHeight is the top (or vice versa)
	y = boardHeight - y;
	}

    public double angle(Coord that)
    {
	//Post: returns the angle in radians of the line going from this to that
	// where 0 corresponds to that being directly to the right of this,
	// Pi/2 corresponds to that directly above this, etc. 
	// (note: negative angle means that is somewhat below this, and then the
	// angle is swept out clockwise instead of the usual counterclockwise)
	// NOTE: this is actually upside down. It's correct in terms of (x,y), but y increases downward. (actually it's fine i think).
	double diffX = that.getX() - this.getX();
	double diffY = that.getY() - this.getY();
	return Math.atan2(diffY, diffX);
    }
	
	public boolean equals(Object o)
	{
	Coord co = (Coord)o;
	return Math.abs(co.getX() - x) < 0.000001 && Math.abs(co.getY() - y) < 0.000001;
	}
	
	public String toString()
	{
	return "(" + x + ", " + y + ")";
	}
}