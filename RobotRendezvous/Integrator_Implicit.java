
import java.util.Vector;


public class Integrator_Implicit implements Integrator 
{

	Vector<Robot> robots;
	double[] c;
	Polynomial[] cP;
	Polynomial detN;
	Polynomial[] det;
	int n;
	double dt;

	public Integrator_Implicit(Vector<Robot> robots, double dt)
	{
	this.robots = robots;
	this.dt = dt;
	n = robots.size();
	c = new double[1+n/2];
	preparePolys();
	setC();
	}
	
	public void setdt(double dt)
	{
	this.dt = dt;
	setC();
	}
	
	public void redoPolys()
	{
	n = robots.size();
	c = new double[1+n/2];
	preparePolys();
	setC();
	}
	
	public void preparePolys()
	{
	cP = new Polynomial[1+n/2];	
	det = new Polynomial[n];
	
	det[0]=new Polynomial(1.,0);
	det[1]=new  Polynomial(1.,1.);
	Polynomial dsq = new  Polynomial(0.25,2);
	
	for(int i=2; i<n; i++)
		{
		det[i] = (det[1].times(det[i-1])).minus(dsq.times(det[i-2]));
		}
	
	detN = (det[1].times(det[n-1])).minus(dsq.scale(2).times(det[n-2]));	
	
	Polynomial d2 = new  Polynomial(0.5,1);	
	
	detN = detN.minus(d2.power(n).scale(2));
	//System.out.println(detN);
	cP[0] = new Polynomial(det[n-1]);
	
	cP[1] = d2.power(n-1).plus(d2.times(det[n-2]));
	
	for(int i=2; i<=n/2; i++)
		{
		cP[i] = d2.power(i).times(det[n-1-i]).plus(d2.power(n-i).times(det[i-1]));
		}
	}
	
	public void setC()
	{
	//System.out.println(detN);
	//System.out.println(det[n-1]);
		//c[0] = cP[0].safeDivide(detN,dt);
	for(int i=0; i<=n/2; i++)
		{
		//c[i] = detN.evaluate(dt);
		if(dt>1) c[i] = cP[i].safeDivide(detN,dt);
		else c[i] = cP[i].evaluate(dt)/detN.evaluate(dt);
		//System.out.println(cP[0]);
		//System.out.println(detN);
		}	
		/*
		System.out.println("cP, detN, c [0]");
		System.out.println(cP[0].evaluate(dt));
		System.out.println(detN.evaluate(dt));
		System.out.println(c[0]);
		*/
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
			if(j>rSize/2) k=rSize-j;
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