//
// Class for block in Arkanoid
// (c) Fred Brasz 2008

import java.awt.event.*;
import java.awt.*;
import java.lang.Math;
import javax.vecmath.*;

public class Crack {
    
    int width = Constants.shineWidth;
	int xS;
	int yS;
	double ds = 3;
	double rX = Math.random()/2 + 0.25; //between 1/4 and 3/4
	double rY = Math.random()/2 + 0.25; //between 1/4 and 3/4
    int nPoints = 16;
	int directions = 9;
    double xPoints[][] = new double[directions][nPoints];
    int xPointsI[][] = new int[directions][nPoints];
    double yPoints[][] = new double[directions][nPoints];
    int yPointsI[][] = new int[directions][nPoints];
    double thetaStart = Math.random();
    Block b;
	    
	int circlesEvery = 3; //every 3
	int nCircles = nPoints/circlesEvery;
    //double xPoints[][] = new double[directions][nPoints];
    int xPointsIC[][] = new int[nCircles][directions];
    //double yPoints[][] = new double[directions][nPoints];
    int yPointsIC[][] = new int[nCircles][directions];

    public Crack(Block b)
    {
	this.b = b;
	xS = (int)(rX*b.blockWidth + b.x);
	yS = (int)(rY*b.blockHeight + b.y);
	
	for(int i=0; i<directions; i++)
		{
		double theta = thetaStart + i*2*Math.PI/directions;
		getCrackLine(i,theta);
		}
	}
	
	public void getCrackLine(int j,double theta)
	{	
	//theta 0 - 2Pi
	xPoints[j][0] = xS;
	yPoints[j][0] = yS;
	xPointsI[j][0] = xS;
	yPointsI[j][0] = yS;
	for(int i=1; i<nPoints; i++)
		{
		// Random walk (not jagged enough)
		//xPoints[j][i] = xPoints[j][i-1] + ds*Math.cos(theta) + i/2*ds*Math.sin(theta)*Math.random();
		//yPoints[j][i] = yPoints[j][i-1] - ds*Math.sin(theta) + i/2*ds*Math.cos(theta)*Math.random();
		
		//Stays centered around radial lines
		xPoints[j][i] = xS + (i+1.5*(Math.random()-0.5))*ds*Math.cos(theta) + i*ds*Math.sin(theta)*Math.random()/2;
		yPoints[j][i] = yS - (i+1.5*(Math.random()-0.5))*ds*Math.sin(theta) + i*ds*Math.cos(theta)*Math.random()/2;
		
		xPointsI[j][i] = (int)xPoints[j][i];
		yPointsI[j][i] = (int)yPoints[j][i];
		
		if(i%circlesEvery == circlesEvery-1)
			{
			int jitter = 0;
			if(Math.random()<0.5) jitter = -1;
			xPointsIC[i/circlesEvery][j] = xPointsI[j][i + jitter];
			yPointsIC[i/circlesEvery][j] = yPointsI[j][i + jitter];
			}
		}
	
	}

    public void paint(Graphics g)
    {
	
	g.setClip(b.x+1,b.y+1,b.blockWidth-1,b.blockHeight-1);
	g.setColor(Color.BLACK);
	for(int i=0; i<directions; i++)
		{
		g.drawPolyline(xPointsI[i], yPointsI[i], nPoints);	
		}
		
	for(int i=0; i<nCircles; i++)
		{
		g.drawPolygon(xPointsIC[i], yPointsIC[i], directions);	
		}
	
    }

    
}
