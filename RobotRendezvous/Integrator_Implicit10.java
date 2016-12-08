
import java.util.Vector;
//test version, only for 10 robots, and dt = 1.5

public class Integrator_Implicit10 implements Integrator 
{

	Vector<Robot> robots;
	double[] c = new double[6];

	public Integrator_Implicit10(Vector<Robot> robots)
	{
	this.robots = robots;
	//dt = 1.5
	c[0]=0.500016935374611;
	c[1]=0.166694892291018;
	c[2]=0.0556327055954478;
	c[3]=0.0187474596938084;
	c[4]=0.00685882671724699;
	c[5]=0.00411529603034819;
	
	
	//dt = 0.05
	/*
	c[0]=0.953462589245592;
	c[1]=0.0227143741574410;
	c[2]=0.000541125366928828;
	c[3]=1.28912535697871E-05;
	c[4]=3.07283002231021E-07;
	c[5]=1.46325239157629E-08;
*/
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
		r.xT = new Point2d(0,0);
		for(int j=0; j<rSize; j++)
			{
			int k = j;
			if(j>5) k=10-j;
			r.xT.scaleAdd(c[k],robots.get((i+j)%rSize).x,r.xT);
			//if(j==0) System.out.println(r.xT.x + " " + r.xT.y);
			}
		}
	
	for (int i=0; i<rSize; i++)
		{	
		Robot r = robots.get(i);
		r.x = new Point2d(r.xT);
		r.updatePath(); 
		}		
	}
}