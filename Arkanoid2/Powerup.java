//
// Class for powerups in Arkanoid
// (c) Fred Brasz 2008

import java.awt.event.*;
import java.awt.*;
import java.lang.Math;

public class Powerup {

    private int x;
    private double y;
    private int blockWidth = Constants.blockWidth;
    private int blockHeight = Constants.blockHeight;  
    private int boardWidth = Constants.boardWidth;
    private int boardHeight = Constants.boardHeight;
    private Color powerupColor;
    private int type;
    private int arcWidth = 20;
    private int arcHeight = 20;
    int colors = 7;
    private double speedY;

    public Powerup(int x, double y, double speedY, int type)
    {
	this.x = x;
	this.y = y;
	this.speedY = speedY;
	this.type = type;
	if (type == Constants.BIG) powerupColor = Color.BLUE;
	else if (type == Constants.STICKY) powerupColor = Color.GREEN;
	else if (type == Constants.SLOW) powerupColor = Color.MAGENTA;
	else if (type == Constants.LASER) powerupColor = Color.RED;
	else if (type == Constants.MULTIPLY) powerupColor = Color.CYAN;
	else if (type == Constants.UNSTOPPABLE) powerupColor = new Color(255,175,0); //gold
	else if (type == Constants.SMALL) powerupColor = new Color(50,50,50);
	else if (type == Constants.GODMODE) powerupColor = new Color(150,0,255);
	else if (type == Constants.SPLIT) powerupColor = new Color(0,0,150);
	else if (type == Constants.BIGBALL) powerupColor = Color.YELLOW;
	else if (type == Constants.GRAVITY) powerupColor = Constants.PINK;
	else if (type == Constants.MISSILE) powerupColor = new Color(0,150,0);
	else if (type == Constants.STRONGLYCURVED) powerupColor = Color.WHITE;
    }

    public void update()
    {
	y += speedY;	
    }

    public boolean checkRemove()
    {
	if (y > boardHeight) return true;
	else return false;
    }

    public double getSpeed()
    {
	return speedY;
    }

    public double getY()
    {
	//Post: returns the y value of the bottom edge
	return y + blockHeight;
    }

    public double getX()
    {
	//Post: returns the x value of the left edge
	return (double)(x);
    }

    public double getWidth()
    {
	return (double)blockWidth;
    }
	
    public double getHeight()
    {
	return (double)blockHeight;
    }
	
    public int getType()
    {
	return type;
    }

    public void paint(Graphics g)
    {
	g.setColor(powerupColor);
	//g.fillRoundRect(x, (int)y, blockWidth, blockHeight, arcWidth, arcHeight);
	
	int r = powerupColor.getRed();
	int gr = powerupColor.getGreen();
	int b = powerupColor.getBlue();
	
	Color darker[] = new Color[colors];
	for(int i=0; i<colors; i++)
		{
		darker[i] = new Color((int)(r*(i+colors/2)/(3*colors/2)),(int)(gr*(i+colors/2)/(3*colors/2)),(int)(b*(i+colors/2)/(3*colors/2)));
		g.setColor(darker[i]);
		g.fillRoundRect(x+i, (int)y+i, blockWidth-2*i, blockHeight-2*i, arcWidth, arcHeight);
		}
	g.setColor(new Color(0,0,0));
    }
}