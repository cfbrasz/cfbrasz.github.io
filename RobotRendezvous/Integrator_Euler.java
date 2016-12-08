
import java.util.Vector;

public class Integrator_Euler implements Integrator 
{

	Vector<Robot> robots;

	public Integrator_Euler(Vector<Robot> robots)
	{
	this.robots = robots;
	}
	
	public void setdt(double dt)
	{
	}
	
    /** 
     * Advances one time step
     */

    public void advanceTime(double dt)
	{
	int rSize = robots.size();	
	for (int i=0; i<rSize; i++)
		{	
		Robot r = robots.get(i);	
		r.x.scaleAdd(dt, r.v, r.x);
		r.updatePath(); 
		}	
	for (int i=0; i<rSize; i++)
		{		
		robots.get(i).calculateVelocity(robots.get((i+rSize-1)%rSize),robots.get((i+1)%rSize),rSize);			
		}	
	}
}