//
// Class for edges of blocks in Arkanoid v2
// O for Oriented horizontally or vertically
// (c) Fred Brasz 2009

import java.lang.Math;

public class EdgeO {

	int s; //coordinate for position
	int min; //extent along orientation
	int max; //extent along orientation
	boolean vert;
	
	public EdgeO(int s, int min, int max, boolean vert)
	{
	this.s = s;
	this.min = min;
	this.max = max;
	this.vert = vert;
	}
	
	public int edgeSide(Ball b)
	{
	if(vert)
		{
		if(b.x.x < s) return Constants.RIGHT; //ball to left, edge to right
		else return Constants.LEFT;
		}
	else
		{
		if(b.x.y < s) return Constants.BOTTOM;
		else return Constants.TOP;
		}
	}	
}