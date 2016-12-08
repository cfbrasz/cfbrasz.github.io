import java.awt.event.*;
import java.awt.*;
//import java.awt.geom.Path2D;
import java.util.Vector;
import java.awt.image.BufferedImage;
import java.lang.Math;
import javax.swing.JApplet;
import java.util.Collections;
import java.util.PriorityQueue;

public class CalculateDistance {

	int boardWidth;
	int boardHeight;
	int pixelsWide;
	int pixelsHigh;
	//Vector<Point2d> surface;
	Vector<Int2d> surface;
	Vector<Int2d> destinations;
	int sl; // number of points in surface
	LevelSetApplet japp;
	LevelSetCanvas canvas1;
	int res;
	Polygon poly;
	//Path2D.Double path;
	double maxPhi = 40;
	
	double[][] phi; //signed distance
	double[][] dPhiX;
	double[][] dPhiY;
	double[][] F;
	double Fc; // if fixed F is used
	double[][] phiTemp; //signed distance
	Vector<Int2d> boundary;
	Vector<Int2d> active;
	Vector<Double> boundaryDist;
	//int[][] boundary;
	double phiStart = 1e8;
	boolean useDestinations = false;
	boolean speedConst = true;
	int speedChoice = 0; // 0 : F = 1;    1: F = sin(t);      2: F = -1;      3: F = -kappa;    4: F = kappa
		
	public CalculateDistance(LevelSetApplet japp, LevelSetCanvas canvas1)
	{
	this.canvas1 = canvas1;
	this.japp = japp;	
		//System.out.println("got here");
	initializations();	
		//System.out.println("got here2");
		System.out.println("Finding Boundary");
	findBoundary(useDestinations);
		System.out.println("Building Initial Band");
	buildInitialBand();
		System.out.println("Running Algorithm");
	
	while(active.size()>0)
		{
		runAlgorithmStep();
		}
  
  System.out.println("Distance function calculated");
	
	maxPhi = findMaxPhi();
	//if(useDestinations) maxPhi = 150;
  
  // Save phi in phiTemp
  for(int i=0; i<pixelsWide; i++) {
    for(int j=0; j<pixelsHigh; j++) {
      phiTemp[i][j] = phi[i][j];
    }
  }
	}	
	
	public void initializations()
	{
	boardWidth = japp.boardWidth;
	boardHeight = japp.boardHeight;
	pixelsWide = japp.pixelsWide;
	pixelsHigh = japp.pixelsHigh;
	surface = canvas1.surface;
	destinations = canvas1.destinations;
	sl = surface.size();
	res = japp.res;	
	phi = new double[pixelsWide][pixelsHigh];
	phiTemp = new double[pixelsWide][pixelsHigh];
	boundary = new Vector<Int2d>();
	active = new Vector<Int2d>();
	
	int[] xpoints = new int[sl];
	int[] ypoints = new int[sl];
	for(int i=0; i<sl; i++)
		{
		xpoints[i] = (int)surface.get(i).x;
		ypoints[i] = (int)surface.get(i).y;
		}
	poly = new Polygon(xpoints,ypoints,sl);
		
	
	for(int i=0; i<pixelsWide; i++)
		for(int j=0; j<pixelsHigh; j++)
			{
			phi[i][j] = phiStart;
			}
	}
	
  public void initializeAdvection()
  {
	dPhiX = new double[pixelsWide-1][pixelsHigh];
  dPhiY = new double[pixelsWide][pixelsHigh-1];
  F = new double[pixelsWide][pixelsHigh];
  }
  
  public void loadSavedPhi()
  {
    for(int i=0; i<pixelsWide; i++) {
      for(int j=0; j<pixelsHigh; j++) {
        phi[i][j] = phiTemp[i][j];
      }
    }
  }
  
  public void advectLevelSet(double dt)
  {
  diff(dPhiX,phi,1);
  diff(dPhiY,phi,2);
  
  calculateSpeedFunction(japp.t);
  
  double c = dt/res;
  
  double tW, tE, tS, tN;
  tW = tE = tS = tN = 0;
  
  if(speedConst) {  
    for(int i=0; i<pixelsWide; i++) {
      for(int j=0; j<pixelsHigh; j++) {
        if(Fc > 0) {
          if(i!=pixelsWide-1) tE = Math.min(dPhiX[i][j],0);
          else tE = 0;
          if(i!=0) tW = Math.max(dPhiX[i-1][j],0);
          else tW = 0;
          if(j!=pixelsHigh-1) tN = Math.min(dPhiY[i][j],0);
          else tN = 0;
          if(j!=0) tS = Math.max(dPhiY[i][j-1],0);
          else tS = 0;
        } else if(Fc < 0) {
          if(i!=pixelsWide-1) tE = Math.max(dPhiX[i][j],0);
          else tE = 0;
          if(i!=0) tW = Math.min(dPhiX[i-1][j],0);
          else tW = 0;
          if(j!=pixelsHigh-1) tN = Math.max(dPhiY[i][j],0);
          else tN = 0;
          if(j!=0) tS = Math.min(dPhiY[i][j-1],0);
          else tS = 0;
        }
        
        phi[i][j] -= Fc*c*Math.sqrt( tW*tW + tE*tE + tS*tS + tN*tN );
      }
    }   
      
  }
    
  }
  
  public void diff(double[][] df, double[][] f, int dim)
  {
  // Calculates a difference in f in df in the dimension dim
  // df[i][j] = f[i+1][j] - f[i][j] if dim = 1, f[i][j+1] - f[i][j] otherwise
  
  if(dim == 1) {
    for(int i=0; i<pixelsWide-1; i++) {
      for(int j=0; j<pixelsHigh; j++) {
        df[i][j] = f[i+1][j] - f[i][j];
      }
    }
  } else {
    for(int i=0; i<pixelsWide; i++) {
      for(int j=0; j<pixelsHigh-1; j++) {
        df[i][j] = f[i][j+1] - f[i][j];
      }
    }
  }
  
  }
  
  public void calculateSpeedFunction(double t)
  {
  // Given current level set, calculate the speed function F everywhere
  
  if(speedChoice == 0) // Constant, positive F
  {
    Fc = 1.0;
  } else if (speedChoice == 1) {
    Fc = Math.sin(t/10);
  } else if (speedChoice == 2) {
    Fc = -1.0;  
  } else {
    for(int i=0; i<pixelsWide; i++) {
      for(int j=0; j<pixelsHigh; j++) {
        F[i][j] = 1.0;
      }
    }
  }
  
  }  
  
  public void recalculateDistanceFunction()
  {
  //System.out.println("Finding Boundary using phi");
  findBoundaryGivenPhi();
  
  active.removeAllElements();
	for(int i=0; i<pixelsWide; i++)
		for(int j=0; j<pixelsHigh; j++)
			{
      if(!boundary.contains(new Int2d(i,j))) phi[i][j] = phiStart;
      //else System.out.println("Boundary point at i,j = " + i + ", " + j + " with phi = " + phi[i][j]);
			}
  
  //System.out.println("Building Initial Band");
	buildInitialBand();  
  
  //System.out.println(active.size());
  
  //System.out.println("Running Algorithm");
	while(active.size()>0)
		{
		runAlgorithmStep();
		}
	
  //System.out.println("Distance function calculated");
	//maxPhi = findMaxPhi();
  }
  
  public void findBoundaryGivenPhi() {
    // Finds boundary points on grid using phi. Adjacent points with oppositely signed values of phi are boundary points
    
    boundary.removeAllElements();
    
  	for(int i=0; i<pixelsWide-1; i++)
  		for(int j=0; j<pixelsHigh-1; j++) {
        Int2d pi = new Int2d(i,j);
        
        double sgnIJ = Math.signum( phi[i][j] );
        if( sgnIJ != Math.signum( phi[i+1][j] ) ) {
          if(!boundary.contains(pi)) boundary.add(pi);
          Int2d p2 = new Int2d(i+1,j);
          if(!boundary.contains(p2)) boundary.add(p2);
        }
        if( sgnIJ != Math.signum( phi[i][j+1] ) ) {
          if(!boundary.contains(pi)) boundary.add(pi);
          Int2d p2 = new Int2d(i,j+1);
          if(!boundary.contains(p2)) boundary.add(p2);          
        }
        
      }
    
  
  }
  
	public void findBoundary(boolean useDestinations)
	{
	// Finds boundary points on grid and gives them their signed distances
	double maxSpacing = res/5.0;
	if(maxSpacing<1) maxSpacing = 1;
	
	//First make sure point sampling is dense enough:	
	Vector<Int2d> surfaceT = new Vector<Int2d>();	
	for(int it=0; it<sl; it++)
		{
		Int2d p = surface.get(it);
		surfaceT.add(p);
		Int2d p2 = surface.get((it+1)%sl);
		double pdist = p2.distance(p);
		if(pdist > maxSpacing)
			{
			// populate this line segment with points
			int extraPoints = (int)(pdist/maxSpacing); // - 2 cause we already have 2 points
			
			for(int i=0; i<extraPoints; i++)
				{
				Int2d pN = new Int2d(p);
				Int2d diff = new Int2d();
				diff.sub(p2,p);
				pN.scaleAdd((double)(i+1.)/(extraPoints+1.),diff,pN);
				surfaceT.add(pN);
				}
			}
		}
	//System.out.println("got here2.1");
	surface = surfaceT;
	canvas1.surface = surface;
	sl = surfaceT.size();
		
	//System.out.println(sl);
	
				
	for(int it=0; it<sl; it++)
		{
		Int2d p = surface.get(it);
		int i = (int)(p.x/res);
		int j = (int)(p.y/res);
		boolean extraWide = false;
		boolean extraHigh = false;		
		
		if((p.y % res) == 0)
			{
			//System.out.println("Extra high at i,j = " + i + ", " + j);
			extraHigh = true;
			}
		if((p.x % res) == 0)
			{
			extraWide = true;
			}
			
		int len = 4;
		if(extraWide && extraHigh) len = 9;
		else if(extraWide || extraHigh) len = 6;
		
		Int2d[] pi = new Int2d[len];
		int[] ic = new int[len];
		int[] jc = new int[len];
		
		generateStencil(i,j,extraWide,extraHigh,ic,jc,pi,len);
		
		for(int c=0; c<len; c++)
			{
			if(withinBounds(ic[c],jc[c]))
				{
				if(!useDestinations)
					{
					if(!boundary.contains(pi[c])) boundary.add(pi[c]);
					Int2d piX = new Int2d(res*pi[c].x,res*pi[c].y);
					double dCur = piX.distance(p);
					int sign = 1;
					if(poly.contains(res*pi[c].x,res*pi[c].y)) sign = -1;
					//if(ic[c] == 10 && jc[c] == 10) System.out.println("sign = " + sign);
					if(dCur<Math.abs(phi[ic[c]][jc[c]]))
						{
						phi[ic[c]][jc[c]] = sign*dCur;
						//System.out.println(sign*dCur);
						}
					}
				//else phi[ic[c]][jc[c]] = -phiStart;
				// Way suggested in paper, but this looks bad with interpolation
				}
			}
		}
		//System.out.println("got here2.2");
		
	if(useDestinations) //Use destinations
		{		
		// Sets destination point distances accordingly
		
		int dl = destinations.size();
		for(int it=0; it<dl; it++)
			{
			Int2d p = destinations.get(it);
			int i = (int)(p.x/res);
			int j = (int)(p.y/res);
			boolean extraWide = false;
			boolean extraHigh = false;		
			
			if((p.y % res) == 0)
				{
				//System.out.println("Extra high at i,j = " + i + ", " + j);
				extraHigh = true;
				}
			if((p.x % res) == 0)
				{
				extraWide = true;
				}
				
			int len = 4;
			if(extraWide && extraHigh) len = 9;
			else if(extraWide || extraHigh) len = 6;
			
			Int2d[] pi = new Int2d[len];
			int[] ic = new int[len];
			int[] jc = new int[len];
			
			generateStencil(i,j,extraWide,extraHigh,ic,jc,pi,len);
			
			for(int c=0; c<len; c++)
				{
				if(withinBounds(ic[c],jc[c]))
					{
					if(!boundary.contains(pi[c])) boundary.add(pi[c]);
					Int2d piX = new Int2d(res*pi[c].x,res*pi[c].y);
					double dCur = piX.distance(p);
					int sign = 1;
					if(poly.contains(res*pi[c].x,res*pi[c].y)) sign = -1;
					//if(ic[c] == 10 && jc[c] == 10) System.out.println("sign = " + sign);
					if(dCur<Math.abs(phi[ic[c]][jc[c]]))
						{
						phi[ic[c]][jc[c]] = sign*dCur;
						//System.out.println(sign*dCur);
						}
					}
				}
			}		
		}
		
				
	int bl = boundary.size();
	/*
	for(int k=0; k<bl; k++)
		{
		System.out.println(boundary.get(k).x + ", " + boundary.get(k).y + ": element " + k);
		}
		*/
			
	for(int i=0; i<bl; i++)
		{
		Int2d pi = boundary.get(i);
		pi.phi = phi[pi.x][pi.y];
		}
	}
		
	public void generateStencil(int i,int j,boolean extraWide,boolean extraHigh,int[] ic, int[] jc, Int2d[] pi, int len)
	{
	ic[0] = i;
	ic[1] = i+1;
	ic[2] = i+1;
	ic[3] = i;
	jc[0] = j;
	jc[1] = j;
	jc[2] = j+1;
	jc[3] = j+1;
	
	if(extraWide)
		{
		ic[4] = i-1;
		jc[4] = j+1;
		ic[5] = i-1;
		jc[5] = j;
		}
	if(extraWide && extraHigh)
		{
		ic[6] = i-1;
		jc[6] = j-1;
		ic[7] = i;
		jc[7] = j-1;
		ic[8] = i+1;
		jc[8] = j-1;
		}
	else if(extraHigh)
		{
		ic[4] = i;
		jc[4] = j-1;
		ic[5] = i+1;
		jc[5] = j-1;
		}
		
	for(int it=0; it<len; it++) pi[it] = new Int2d(ic[it],jc[it]);		
	}
	
	public double phiInterpolate(double x, double y)
	{
	// interpolates bilinearly between the 4 nearest u values
	
	// first get 4 points it's between
	/*
	// Points at cell centers
	double fractionX = (x-0.5*res)%(double)res/res;
	int iStart = (int)((x-0.5*res)/res);
	double fractionY = (y-0.5*res)%(double)res/res;
	int jStart = (int)((y-0.5*res)/res);
	*/
	
	// Points at corners
	double fractionX = (x)%(double)res/res;
	int iStart = (int)((x)/res);
	double fractionY = (y)%(double)res/res;
	int jStart = (int)((y)/res);
	
	if(iStart > -1 && jStart > -1 && iStart+1 < pixelsWide && jStart+1 < pixelsHigh)
		{
		double uBelow = phi[iStart][jStart]*(1-fractionX) + phi[iStart+1][jStart]*fractionX;
		double uAbove = phi[iStart][jStart+1]*(1-fractionX) + phi[iStart+1][jStart+1]*fractionX;
		//System.out.println(phi[iStart][jStart]);
		return uBelow*(1-fractionY) + uAbove*(fractionY);	
		}
	//System.out.println("Error, not on grid");
	return -1;
	}
	
	public double phiInterpolateCR(double x, double y)
	{
	// interpolates using Catmull-Rom cubic spline
	
	// first get 4 points it's between
	double xb = (x)%(double)res/res;
	int iStart = (int)((x)/res);
	double yb = (y)%(double)res/res;
	int jStart = (int)((y)/res);
	
	if(iStart > 0 && jStart > 0 && iStart+2 < pixelsWide && jStart+2 < pixelsHigh)
		{
		double phim1 = phi[iStart-1][jStart-1]*(-0.5*xb+xb*xb-0.5*xb*xb*xb) + 
			phi[iStart][jStart-1]*(1-2.5*xb*xb+1.5*xb*xb*xb) + 
			phi[iStart+1][jStart-1]*(0.5*xb+2*xb*xb-1.5*xb*xb*xb) +
			phi[iStart+2][jStart-1]*(-0.5*xb*xb+0.5*xb*xb*xb);
		double phi0 = phi[iStart-1][jStart]*(-0.5*xb+xb*xb-0.5*xb*xb*xb) + 
			phi[iStart][jStart]*(1-2.5*xb*xb+1.5*xb*xb*xb) + 
			phi[iStart+1][jStart]*(0.5*xb+2*xb*xb-1.5*xb*xb*xb) +
			phi[iStart+2][jStart]*(-0.5*xb*xb+0.5*xb*xb*xb);
		double phi1 = phi[iStart-1][jStart+1]*(-0.5*xb+xb*xb-0.5*xb*xb*xb) + 
			phi[iStart][jStart+1]*(1-2.5*xb*xb+1.5*xb*xb*xb) + 
			phi[iStart+1][jStart+1]*(0.5*xb+2*xb*xb-1.5*xb*xb*xb) +
			phi[iStart+2][jStart+1]*(-0.5*xb*xb+0.5*xb*xb*xb);
		double phi2 = phi[iStart-1][jStart+2]*(-0.5*xb+xb*xb-0.5*xb*xb*xb) + 
			phi[iStart][jStart+2]*(1-2.5*xb*xb+1.5*xb*xb*xb) + 
			phi[iStart+1][jStart+2]*(0.5*xb+2*xb*xb-1.5*xb*xb*xb) +
			phi[iStart+2][jStart+2]*(-0.5*xb*xb+0.5*xb*xb*xb);
		//System.out.println(phi[iStart][jStart]);
		double phiCR = phim1*(-0.5*yb+yb*yb-0.5*yb*yb*yb) + 
			phi0*(1-2.5*yb*yb+1.5*yb*yb*yb) + 
			phi1*(0.5*yb+2*yb*yb-1.5*yb*yb*yb) +
			phi2*(-0.5*yb*yb+0.5*yb*yb*yb);
		return phiCR;	
		}
	else return phiInterpolate(x,y);
	//System.out.println("Error, not on grid");
	//return 1;
	}

	public double computeDistance(int i, int j)
	{
	// Returns 1st order approximation to distance at grid point (i,j) using neighboring values of distance (phi) that are < phiStart/2 (large number)
	
	boolean inside = false;
	if(useDestinations) inside = poly.contains(res*i,res*j);
	
	double[] phiNew = new double[4];
	int[] ic = new int[4];
	int[] jc = new int[4];
	ic[0] = i+1;
	ic[1] = i;
	ic[2] = i-1;
	ic[3] = i;
	jc[0] = j;
	jc[1] = j+1;
	jc[2] = j;
	jc[3] = j-1;
	for(int m=0; m<4; m++)
		{	
		phiNew[m] = phiStart/3;
		if(!useDestinations || (poly.contains(res*ic[m],res*jc[m]) == inside))
			{
			if(withinBounds(ic[m],jc[m]) && withinBounds(ic[(m+1)%4],jc[(m+1)%4]))
				{
				double phi1 = phi[ic[m]][jc[m]];
				double phi2 = phi[ic[(m+1)%4]][jc[(m+1)%4]];
				double phiDiff = phi1-phi2;
				int sign = 1;
				if(phi1 < 0 || phi2<0) sign = -1;
				if(useDestinations)
					{
					sign = -1;
					if(!inside) sign = 1;
					}
				if(Math.abs(phi1) < phiStart/2 && Math.abs(phi2) < phiStart/2 )
					{
					if(Math.abs(phiDiff) <= res)
						{
						// Then this quadrant has frozen distances and theta's will lie in [0,1] (from geometry)
						phiNew[m] = 0.5*((phi1+phi2) + sign*Math.sqrt(2*res*res-phiDiff*phiDiff));
						}
					}
				else if(Math.abs(phi1) < phiStart/2) phiNew[m] = phi1 + sign*res;
				else if(Math.abs(phi2) < phiStart/2) phiNew[m] = phi2 + sign*res;
				
				}
			}
		}
	
	double result = phiNew[0];
	for(int m=1; m<4; m++) if(Math.abs(phiNew[m])<Math.abs(result)) result = phiNew[m];
	/*
	if(result > phiStart/4) 
		{
		System.out.println(i + ", " + j);
		for(int m=0; m<4; m++)
			{	
			System.out.println(phi[ic[m]][jc[m]]);
			}	
		System.out.println("result: " + result);
		}
		*/
	return result;
	}
	
	public void buildInitialBand()
	{
	//PriorityQueue<Int2d> heap = new PriorityQueue<Int2d>();
	// should do heap here, but tricky to implement:
	// how do i find an element in the heap to compare to a current try for distance? Need an ordering by position or something..
	
	int bl = boundary.size();
	for(int i=0; i<bl; i++) 
		{
		Int2d bCur = boundary.get(i);
		//System.out.println(bCur.x + ", " + bCur.y + ": size = " + heap.size());
		
		testPointsAround(bCur.x,bCur.y);
		}
	
	}
	
	public void runAlgorithmStep()
	{
	int minIndex = 0;
	double phiMin = Math.abs(active.get(0).phi);
	for(int i=1; i<active.size(); i++)
		{
		if(Math.abs(active.get(i).phi) < phiMin)
			{
			minIndex = i;
			phiMin = Math.abs(active.get(i).phi);
			}
		}
		
	Int2d pMin = active.get(minIndex);
	phi[pMin.x][pMin.y] = pMin.phi;
	active.removeElementAt(minIndex);
			
	testPointsAround(pMin.x,pMin.y);		
	}
	
	public void testPointsAround(int i, int j)
	{
	boolean inside = false;
	if(useDestinations) inside = poly.contains(res*i,res*j);
	Int2d[] pi = new Int2d[4];
	int[] ic = new int[4];
	int[] jc = new int[4];
	ic[0] = i+1;
	ic[1] = i;
	ic[2] = i-1;
	ic[3] = i;
	jc[0] = j;
	jc[1] = j+1;
	jc[2] = j;
	jc[3] = j-1;
	for(int m=0; m<4; m++)
			{
			if(!useDestinations || (poly.contains(res*ic[m],res*jc[m]) == inside))
				{
				if(withinBounds(ic[m],jc[m]))
					{
					if(phi[ic[m]][jc[m]] > phiStart/2)
						{
						//System.out.println("i,j = " + i + "," + j + "; ic,jc = " + ic[m] + "," + jc[m]);
						//if(useDestinations && (poly.contains(res*ic[m],res*jc[m]) != inside)) break;
						pi[m] = new Int2d(ic[m],jc[m]);		
						double phiC = computeDistance(ic[m],jc[m]);
						
						int ind = active.indexOf(pi[m]);
				//		if(active.size() > 0) ind = active.indexOf(pi[m]);
					//	else ind=-1;
						//if(!heap.contains(pi[m]))
						if(ind<0)
							{
							pi[m].phi = phiC;
							active.add(pi[m]);
							//System.out.println("added ic,jc with phi = " + phiC);
							}					
						else if(Math.abs(phiC) < Math.abs(active.get(ind).phi))
							{
							active.get(ind).phi = phiC;
							//System.out.println("replaced phi of ic,jc with phi = " + phiC);
							//phi[ic[m]][jc[m]] = phiC;
							}
						}
					}
				}
			}
	
	}
	
	public double findMaxPhi()
	{
	double result = 0;
	for(int i=0; i<pixelsWide; i++)
		for(int j=0; j<pixelsHigh; j++)
			{
			if(Math.abs(phi[i][j]) > result) result = Math.abs(phi[i][j]);
			}
	return result;
	}
	
	public boolean withinBounds(int i, int j)
	{
	return i>-1 && i<pixelsWide && j>-1 && j<pixelsHigh;
	}
}
