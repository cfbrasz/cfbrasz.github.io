
import java.awt.Graphics;
import java.util.Vector;
import java.awt.*;

public class Edge {

	private Coord3D c1;
	private Coord3D c2;
	
	public Edge(Coord3D c1, Coord3D c2)
	{
	this.c1 = c1;
	this.c2 = c2;
	}
	
	public Edge copy()
	{
	return new Edge(c1.copy(), c2.copy());
	}

	public Coord3D getC1()
	{
	return c1;
	}

	public Coord3D getC2()
	{
	return c2;
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
}
	