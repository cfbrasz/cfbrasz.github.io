
import java.util.Vector;

public class Integrator_RK4 implements Integrator 
{

	Vector<Robot> robots;

	public Integrator_RK4(Vector<Robot> robots)
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
	// v is k1, k[0,1,2] are k2,k3,k4
	for (int i=0; i<rSize; i++)
		{	
		Robot r = robots.get(i);
		r.xT.set(r.x);
		}
	for (int i=0; i<rSize; i++)
		{	
		Robot r = robots.get(i);
		r.v = r.returnVelocity(robots.get((i+rSize-1)%rSize),robots.get((i+1)%rSize));	
		}
	for (int i=0; i<rSize; i++)
		{	
		Robot r = robots.get(i);
		r.xT.scaleAdd(dt/2, r.v, r.x);		
		}	
	for (int i=0; i<rSize; i++)
		{		
		Robot r = robots.get(i);	
		r.k[0]=r.returnVelocity(robots.get((i+rSize-1)%rSize),robots.get((i+1)%rSize));			
		}
	for (int i=0; i<rSize; i++)
		{	
		Robot r = robots.get(i);	
		//r.xRK[0].set(r.x); unneccessary
		r.xT.scaleAdd(dt/2,r.k[0],r.x);
		}
	for (int i=0; i<rSize; i++)
		{		
		Robot r = robots.get(i);	
		r.k[1]=r.returnVelocity(robots.get((i+rSize-1)%rSize),robots.get((i+1)%rSize));			
		}
	for (int i=0; i<rSize; i++)
		{	
		Robot r = robots.get(i);	
		//r.xRK[0].set(r.x); unneccessary
		r.xT.scaleAdd(dt,r.k[1],r.x);
		}
	for (int i=0; i<rSize; i++)
		{		
		Robot r = robots.get(i);	
		r.k[2]=r.returnVelocity(robots.get((i+rSize-1)%rSize),robots.get((i+1)%rSize));			
		}
		//finally move!
	for (int i=0; i<rSize; i++)
		{	
		Robot r = robots.get(i);	
		//r.xRK[0].set(r.x); unneccessary
		Vector2d vf = new Vector2d();
		vf.add(r.k[0],r.k[1]);
		vf.scaleAdd(2,r.v);
		vf.add(r.k[2]);
		r.x.scaleAdd(dt/6,vf,r.x);
		r.updatePath(); 
		}
		
	}
}