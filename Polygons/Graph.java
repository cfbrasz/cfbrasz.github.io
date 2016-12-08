
import java.awt.Graphics;
import java.util.Vector;
import java.awt.*;

public class Graph {

	private Vector<Edge> edges;
	private int n; // number of sites
	
	public Graph(int n)
	{
	edges = new Vector<Edge>();
	this.n = n;
	}
	
	public Graph(Graph t)
	{
	edges = t.copyEdges();
	n = t.getN();
	}
	
	public Graph(Vector<Edge> edges, int n)
	{
	this.edges = edges;
	this.n = n;
	}
	
	public void addEdge(Edge e)
	{
	edges.add(e);
	}
	
	public boolean removeEdge(Edge e)
	{
	// returns false if no edge was removed
	return edges.remove(e);
	}
	
	public Vector<Edge> getEdges()
	{
	return edges;
	}
	
	public Vector<Edge> copyEdges()
	{
	// creates new objects, used make a new graph
	Vector<Edge> result = new Vector<Edge>();
	for (int i=0; i<edges.size(); i++)
		{
		result.add(edges.get(i).copy());
		}
	return result;
	}
	
	public int getN()
	{
	return n;
	}
	
	public int getNEdges()
	{
	return edges.size();
	}
	
	public Vector<Edge[]> intersections()
	{
	//returns a vector of all pairs of intersecting edges in this graph
	Vector<Edge[]> result = new Vector<Edge[]>();
	for (int i=0; i<edges.size(); i++)
		{
		Edge ei = edges.get(i);
		for (int j=i+1; j<edges.size(); j++)
			{
			Edge ej = edges.get(j);		
			if(ei.intersects(ej))
				{
				Edge[] pair = new Edge[2];
				pair[0] = ei;
				pair[1] = ej;
				result.add(pair);
				}
			}
		}
	return result;
	}	
	
	public boolean equals(Object o)
	{
	Graph t2 = (Graph)o;
	Vector<Edge> oEdges = t2.getEdges();
	boolean result = true;
	for (int i=0; i<oEdges.size(); i++)
		{
		if(!edges.contains(oEdges.get(i)))
			{
			result = false;
			break;
			}
		}
	return result;
	}
	
	public void paint(Graphics g)
	{
	g.setColor(Color.BLACK);
	for (int i=0; i<edges.size(); i++)
			{		
			edges.get(i).paint(g);		
			}
	}
}