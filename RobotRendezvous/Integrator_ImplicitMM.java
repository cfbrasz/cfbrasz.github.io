
import java.util.Vector;

public class Integrator_ImplicitMM implements Integrator 
{

	Vector<Robot> robots;
	double eps = 1E-9;
	double[] a;
	double[] b;
	double[] c;
	double[] s;
	double[] f;
	int rSize;
	int n;
	double det;
	double[] d; //determinants
	double[] t; //determinants
	double[][] tt; //determinants for corner part
	double[] d2; //determinants for corner part
	double[] t2; //determinants for corner part
	double[][] inv;
	double[][] invC;
	boolean debugging = false; 

	public Integrator_ImplicitMM(Vector<Robot> robots)
	{
	this.robots = robots;
	// initialize arrays
	rSize = robots.size();
	n = rSize; //for convenience
	a = new double[rSize+1]; //tridiagonal matrix elements, only use 1 - n (not 0)
	b = new double[rSize+1]; //tridiagonal matrix elements
	c = new double[rSize+1]; //tridiagonal matrix elements
	s = new double[rSize+1];
	f = new double[rSize+1];
	d = new double[rSize+1];
	t = new double[rSize+1];
	d2 = new double[rSize+1];
	t2 = new double[rSize+1];
	tt = new double[rSize+1][rSize+1];
	inv = new double[n][n];
	invC = new double[n][n]; //corner terms
	}
	
	public void setdt(double dt)
	{
	}
	
    /** 
     * Advances one time step
     */
	 
	public void calculateVelocity(Robot r, Robot rp, Robot rm, int i)
	{
	//i places s and f into the array
	
	//find circumcircle's center and radius
	Coord center;
	Double radius;
	Coord edge2 = new Coord(r.x.x,r.x.y);
	Coord edge3 = new Coord(rp.x.x,rp.x.y);
	Coord edge1 = new Coord(rm.x.x,rm.x.y);
	Vector2Do m12 = new Vector2Do(edge1,edge2);
	Vector2Do m23 = new Vector2Do(edge2,edge3);
	Double ma = edge1.mOf(edge2);
	Double mb = edge2.mOf(edge3);
	if(m12.angle() != m23.angle())
		{
		Coord ap = edge1.midpoint(edge2);
		Coord bp = edge2.midpoint(edge3);
		Coord ap2 = ap.copy();
		Coord bp2 = bp.copy();
		if(!edge1.isVertical(edge2) && ma != 0 && !ma.isNaN())
			{
			ap2.moveX(1);
			ap2.moveY(-1/ma);
			//System.out.println("here ma = " + ma);
			//System.out.println(edge1.x);
			//System.out.println(edge2.x);
			//System.out.println(edge1.x == edge2.x);
			}
		else if(edge1.isVertical(edge2) || ma.isNaN())
			{
			ap2.moveX(1);			
			}
		else if(ma == 0)
			{
			ap2.moveY(1);
			}
		if(!edge2.isVertical(edge3)&& mb != 0 && !mb.isNaN())
			{
			bp2.moveX(1);
			bp2.moveY(-1/mb);	
			//System.out.println("here mb = " + mb);
			}
		else if(edge2.isVertical(edge3) || mb.isNaN())
			{
			bp2.moveX(1);			
			}
		else if(mb == 0)
			{
			bp2.moveY(1);
			}
		center = ap.intersection(ap2,bp,bp2);
		
		radius = center.dist(edge2);
		
		if(radius>10000)
			{
			//System.out.println(radius);
			}
		else 
			{
			//System.out.println(radius);
			//System.out.println(center);
			}
		
		if(!radius.isNaN() && !radius.isInfinite())
			{
			Point2d cent = new Point2d(center.x,center.y);
			r.v.sub(cent,r.x);
			r.v.scale(10/(radius*radius));
			
			//System.out.println("center = " + center);
			//System.out.println("v = ("+r.v.x+","+r.v.y+")");
			
			Coord intersect = edge2.intersection(center,edge1,edge3);
			//System.out.println("intersect = " + intersect);
			Point2d inter = new Point2d(intersect.x,intersect.y);
			double a = edge1.dist(intersect)/edge1.dist(edge3);
			double sc = r.v.length()/edge2.dist(intersect);
			Vector2d edge2int = new Vector2d();
			edge2int.sub(inter,r.x);
			if(edge2int.dot(r.v) < 0) sc = -sc; //intersection on other side of our middle point, so negate direction when scaling with s
			Vector2Do vint = new Vector2Do(edge1,intersect);
			Vector2Do m13 = new Vector2Do(edge1,edge3);
			//if(vint.dot(m13)/(vint.length()*m13.length()) < 1.-eps)
			
			f[i] = a;
			s[i] = sc;
				
				//Reconstruct the center
				
				if(debugging)
				{
				System.out.println("a = " + a);
				System.out.println("s = " + sc);
				System.out.println("i = " + i);
				double xc = a*edge3.x + (1-a)*edge1.x;
				double yc = a*edge3.y + (1-a)*edge1.y;
				//System.out.println("Reconstructed intersect = ("+xc+","+yc+")");
				double vx = sc*(xc-edge2.x);
				double vy = sc*(yc-edge2.y);
				//System.out.println("Reconstructed v = ("+vx+","+vy+")");
				System.out.println();
				}
			}
		else 
			{
			//System.out.println("collinear points, no circle");
			r.v.x = 0;
			r.v.y = 0;
			}
		
		/*
		System.out.println(ma);
		System.out.println(mb);
		System.out.println();
		System.out.println(m12.angle());
		System.out.println(m23.angle());
		System.out.println();
		*/
		//System.out.println(r.v.x);
		//System.out.println(r.v.y);
		}
	else 
		{
		//System.out.println("collinear points, no circle");
		r.v.x = 0;
		r.v.y = 0;
		}
	
	}
	
	public void constructTridiagonalMatrix(double dt)
	{
	for(int i=1; i<=rSize; i++)
		{
		a[i] = 1.+s[i-1]*dt;
		b[i] = -f[i-1]*s[i-1]*dt;
		if(i==1) c[n] = -(1-f[i-1])*s[i-1]*dt;
		else c[i-1] = -(1-f[i-1])*s[i-1]*dt;
		//c[(i+rSize-1)%rSize] = -(1-f[i])*s[i]*dt;
		
				
		if(debugging)
			{
			System.out.println("a("+i+") = " + a[i] +";");
			System.out.println("b("+i+") = " + b[i] +";");
			if(i==1) System.out.println("c("+1+") = " + c[n] +";");
			else System.out.println("c("+(i)+") = " + c[(i-1)] +";");
			
			System.out.println("a["+i+"] = " + a[i] +";");
			System.out.println("b["+i+"] = " + b[i] +";");
			if(i==1) System.out.println("c["+n+"] = " + c[n] +";");
			else System.out.println("c["+(i-1)+"] = " + c[(i-1)] +";");
			}
		}	
	}
	
	public void generateDeterminants()
	{
	d[0] = 1;
	d[1] = a[n];
	t[0] = 1;
	t[1] = a[1];
	for(int i=2; i<=n; i++)
		{
		t[i] = a[i]*t[i-1] - b[i-1]*c[i-1]*t[i-2];
		d[i] = a[n-i+1]*d[i-1] - b[n-i+1]*c[n-i+1]*d[i-2];
		}
	det=d[n];
	
	//corner part:
	d2[0] = 1;
	d2[1] = a[n-1];
	t2[0] = 1;
	t2[1] = a[2];
	for(int i=2; i<n; i++)
		{
		t2[i] = a[i+1]*t[i-1] - b[i]*c[i]*t[i-2];
		d2[i] = a[n-i]*d[i-1] - b[n-i]*c[n-i]*d[i-2];
		}
		
	for(int p=2; p<=n; p++)
		{
		int dp = p-1;
		tt[p][0]=1;
		tt[p][1]=a[p];
		for(int i=2; i<=n-dp; i++)
			{
			tt[p][i]=a[i+dp]*tt[p][i-1] - b[i+dp-1]*c[i+dp-1]*tt[p][i-2];
			}
		}
	}

	public void calculateInverse()
	{
	for(int i=1; i<=n; i++)
		{
		for(int j=1; j<=n; j++)
			{
			if(i<=j)
				{
				double bs = 1;
				for(int k=i; k<j; k++) bs = b[k]*bs;
				inv[i-1][j-1] = t[i-1]*bs*d[n-j];
				if((i+j)%2 == 1) inv[i-1][j-1] = -inv[i-1][j-1];
				}
			else
				{
				double cs = 1;
				for(int k=j; k<i; k++) cs = c[k]*cs;
				inv[i-1][j-1] = t[j-1]*cs*d[n-i];
				if((i+j)%2 == 1) inv[i-1][j-1] = -inv[i-1][j-1];
				}
			}
		}
	}

	public void calculateCornersInverse()
	{
	for(int i=1; i<=n; i++)
		{
		for(int j=1; j<=n; j++)
			{
			double bs = 1;
			double cs = 1;
			double bncn = 0;
			if(i<=j)
				{
				double offdiag = 0;
				for(int k=j; k<=n; k++) cs = c[k]*cs;
				for(int k=1; k<i; k++) cs = c[k]*cs;
				for(int k=i; k<j; k++) bs = b[k]*bs;
				if(i<n && j<n && i>1 && j>1) bncn = b[n]*c[n]*t2[i-2]*bs*d2[n-j-1];
				if(i!=j) 
					{
					offdiag = tt[i+1][j-i-1]*cs;
					if(n%2==1) offdiag = -offdiag;
					}
				invC[i-1][j-1] = offdiag - bncn;
				if((i+j)%2 == 1) invC[i-1][j-1] = -invC[i-1][j-1];
				}
			else
				{
				for(int k=i; k<=n; k++) bs = b[k]*bs;
				for(int k=1; k<j; k++) bs = b[k]*bs;
				for(int k=j; k<i; k++) cs = c[k]*cs;
				if(i<n && j<n && i>1 && j>1) bncn = b[n]*c[n]*t2[j-2]*cs*d2[n-i-1];
				
				double offdiag = tt[j+1][i-j-1]*bs;
				if(n%2==1) offdiag = -offdiag;
				
				invC[i-1][j-1] = offdiag - bncn;
				if((i+j)%2 == 1) invC[i-1][j-1] = -invC[i-1][j-1];
				}
			}
		}	
	}
	
    public void advanceTime(double dt)
	{
	
	for (int i=0; i<rSize; i++)
		{	
		Robot r = robots.get(i);	
		r.x.scaleAdd(dt, r.v, r.x);
		//System.out.println("i = " + i);
		calculateVelocity(robots.get(i),robots.get((i+rSize-1)%rSize),robots.get((i+1)%rSize),i);			
		
		//putInMatrixForm(robots.get(i),robots.get((i+rSize-1)%rSize),robots.get((i+1)%rSize));
		
		}	
	constructTridiagonalMatrix(dt);
	generateDeterminants();
	calculateInverse(); //easy part, excluding corners
	calculateCornersInverse(); //hard part
	if(debugging) System.out.println();
	for (int i=0; i<rSize; i++)
		{	
		Robot r = robots.get(i);
		r.xT = new Point2d(0,0);
				
		for (int j=0; j<rSize; j++)
			{
			Vector2d rd = new Vector2d();
			rd.sub(robots.get(j).x,RobotRendezvousCanvas.cmCur);
			//r.xT.scaleAdd((inv[i][j]+invC[i][j])/det,robots.get(j).x,r.xT);
			r.xT.scaleAdd((inv[i][j]+invC[i][j])/det,rd,r.xT);
			//Double val = (inv[i][j]+invC[i][j]);
			if(debugging)
				{
				Double val = (inv[i][j]);
				String str = val.toString();
				String suffix = "";
				if(str.indexOf("E") > 0) suffix = str.substring(str.indexOf("E"));
				if(str.length() > 9) str = str.substring(0,9);
				System.out.print(str + suffix + " ");
				}	
			if(debugging) System.out.println();
			}
		r.xT.add(RobotRendezvousCanvas.cmCur);
		}
	if(debugging)
		{
		System.out.println("Corner inverse");
		for (int i=0; i<rSize; i++)
			{		
			for (int j=0; j<rSize; j++)
				{
				Double val = (invC[i][j]);
				String str = val.toString();
				String suffix = "";
				if(str.indexOf("E") > 0) suffix = str.substring(str.indexOf("E"));
				if(str.length() > 9) str = str.substring(0,9);
				System.out.print(str + suffix + " ");
				}	
				System.out.println();
			}
		System.out.println("Total inverse");
		for (int i=0; i<rSize; i++)
			{		
			for (int j=0; j<rSize; j++)
				{
				Double val = (invC[i][j]+inv[i][j])/det;
				String str = val.toString();
				String suffix = "";
				if(str.indexOf("E") > 0) suffix = str.substring(str.indexOf("E"));
				if(str.length() > 9) str = str.substring(0,9);
				System.out.print(str + suffix + " ");
				}	
				System.out.println();
			}
		}
	for (int i=0; i<rSize; i++)
		{	
		Robot r = robots.get(i);
		r.x = new Point2d(r.xT);
		r.updatePath(); 
		}
	
	}
	
	public static void main(String args[])
	{
	
	}
}