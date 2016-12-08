//
// Class for block in Arkanoid
// (c) Fred Brasz 2008

import java.awt.event.*;
import java.awt.*;
import java.lang.Math;
import javax.vecmath.*;

public class Shine {
    
    int width = Constants.shineWidth;
    double xPoints[] = new double[4];
    int xPointsI[] = new int[4];
    double yPoints[] = new double[4];
    int yPointsI[] = new int[4];
    int nPoints = 4;
    double slope;
    Block b;
	double speed = Constants.shineSpeed;

    public Shine(Block b)
    {
	this.b = b;
	slope = (double)b.blockHeight/b.blockWidth;
    //xPoints[0] = b.x + b.blockWidth - 10;
    xPoints[0] = b.x + b.blockWidth ;
    xPoints[1] = b.x + b.blockWidth;
    xPoints[2] = b.x + b.blockWidth;
   // xPoints[3] = b.x + b.blockWidth - 5;
    xPoints[3] = b.x + b.blockWidth + width;
	
    yPoints[0] = b.y + b.blockHeight;
    //yPoints[1] = b.y + b.blockHeight - 10;
    yPoints[1] = b.y + b.blockHeight ;
    //yPoints[2] = b.y + b.blockHeight - 5;
    yPoints[2] = b.y + b.blockHeight + width*slope;
    yPoints[3] = b.y + b.blockHeight;
	
    xPointsI[1] = b.x + b.blockWidth;
    xPointsI[2] = b.x + b.blockWidth;	
    yPointsI[0] = b.y + b.blockHeight;
    yPointsI[3] = b.y + b.blockHeight;
	
	}
	
	public void update()
	{
	xPoints[0] -= speed;
	xPoints[3] -= speed;
	yPoints[1] -= speed*slope;
	yPoints[2] -= speed*slope;
	
	xPointsI[0] = (int)xPoints[0];
	xPointsI[3] = (int)xPoints[3];
	yPointsI[1] = (int)yPoints[1];
	yPointsI[2] = (int)yPoints[2];
	
	
	}

    public void paint(Graphics g)
    {
	g.setClip(b.x+1,b.y+1,b.blockWidth-1,b.blockHeight-1);
	g.setColor(Color.WHITE);
	g.fillPolygon(xPointsI, yPointsI, nPoints);	
    }

    
}
