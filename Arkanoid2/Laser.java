//
// Class for laser shots in Arkanoid
// (c) Fred Brasz 2008

import java.awt.event.*;
import java.awt.*;
import java.lang.Math;

public class Laser {

    private double x;
    private double y;
    private int laserWidth = Constants.laserWidth;
    private int laserHeight = Constants.laserHeight;  
    private int boardWidth = Constants.boardWidth;
    private int boardHeight = Constants.boardHeight;
    private double speedY = Constants.laserSpeed;
    int colors = 3;


    public Laser(double x, double y)
    {
	this.x = x;
	this.y = y;
    }

    public void update()
    {
	// Laser shot always moves up the screen
	y -= speedY;	
    }

    public boolean checkRemove()
    {
	if (y < 0) return true;
	else return false;
    }

    public double getSpeed()
    {
	return speedY;
    }

    public double getY()
    {
	//Post: returns the y value of the top edge
	return y - laserHeight/2;
    }

    public double getX()
    {
	return x;
    }

    public double getWidth()
    {
	return (double)laserWidth;
    }
	
    public double getHeight()
    {
	return (double)laserHeight;
    }

    public void paint(Graphics g)
    {	
	int xx = (int)Math.round(x);
	int yy = (int)Math.round(y);
	g.setColor(new Color(255,170,225));
	g.fillRect(xx-2, yy-laserHeight/2+1, 5, laserHeight-1);
	g.fillRect(xx-1, yy-laserHeight/2-1, 3,2);
	g.fillRect(xx-2, yy+laserHeight/2, 1,1);
	g.fillRect(xx+2, yy+laserHeight/2, 1,1);
	//g.fillRect(xx, yy-(laserHeight/2-1), 1, 1);
	g.fillRect(xx, yy-(laserHeight/2)-2, 1, 2);
	
	g.setColor(new Color(236,80,170));
	g.fillRect(xx-1, yy-(laserHeight/2-1), 3, laserHeight-2);
	g.fillRect(xx, yy-(laserHeight/2)-1, 1, 2);
	
	g.setColor(new Color(255,0,0));
	g.fillRect(xx, yy-(laserHeight/2-1), 1, laserHeight-3);
    }
}