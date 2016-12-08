
import java.awt.Graphics;
import java.util.Vector;
import java.awt.*;

public class Edge {

	private Coord c1;
	private Coord c2;
	private double slope;
	
	public Edge(Coord c1, Coord c2)
	{
	this.c1 = c1;
	this.c2 = c2;
	slope = c1.mOf(c2);
	}
	
	public Edge copy()
	{
	return new Edge(c1.copy(), c2.copy());
	}

	public Coord getC1()
	{
	return c1;
	}

	public Coord getC2()
	{
	return c2;
	}	
	
	public boolean intersects(Edge e)
	{
	return c1.intersectSegment(c2,e.getC1(),e.getC2());
	}
	
	public String toString()
	{
	return "Edge from " + c1.toString() + " to " + c2.toString();
	}
	
	public boolean equals(Object o)
	{
	Edge e = (Edge)o;
	return (c1.equals(e.getC1()) && c2.equals(e.getC2())) ||
		   (c1.equals(e.getC2()) && c2.equals(e.getC1()));
	}
	
	public void paint(Graphics g)
	{	
	g.setColor(Color.BLACK);
	g.drawLine((int)c1.getX(),(int)c1.getY(),(int)c2.getX(),(int)c2.getY());							
	}
}
	