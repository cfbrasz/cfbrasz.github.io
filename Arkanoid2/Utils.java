
import javax.vecmath.*;

/** 
 * Catch-all utilities (feel free to add on). 
 * 
 * @author Doug James, January 2007
 */
public class Utils
{
    /**
     * sum += scale*v
     */
    public static final void acc(Tuple2d sum, double scale, Tuple2d v)
    {
	sum.x += scale * v.x;
	sum.y += scale * v.y;
    }


    /**
     * 
     * @param pad Pre-padding char.
     */
    public static String getPaddedNumber(int number, int length, String pad)  {
	return getPaddedString(""+number, length, pad, true);
    }

    /**
     * @param prePad Pre-pads if true, else post pads.
     */
    public static String getPaddedString(String s, int length, String pad, boolean prePad) 
    {
	if(pad.length() != 1) throw new IllegalArgumentException("pad must be a single character.");
	String result = s;
	result.trim();
	if(result.length() > length) 
	    throw new RuntimeException
		("input string "+s+" is already more than length="+length);

	int nPad = (length - result.length());
	for(int k=0; k<nPad; k++) {
	    //System.out.println("nPad="+nPad+", result="+result+", result.length()="+result.length());
	    if(prePad) 
		result = pad + result;
	    else
		result = result + pad;
	}

	return result;
    }
	
	public static boolean hasRoot(double a, double b, double c)
	{
	// returns true if quadratic equation has at least one real root
	return (b*b-4*a*c) >= 0 && (a != 0 || b != 0);
	}
	
    public static double root(double a, double b, double c)
    {
	// returns minimum positive root greater than 0 of quadratic equation ax^2 + bx + c = 0
	// if neither is positive, will just return x2
	/*
	if(Math.abs(a) > 1E-10)
		{
		double x1 = (-b + Math.sqrt(b*b-4*a*c))/(2*a);
		double x2 = (-b - Math.sqrt(b*b-4*a*c))/(2*a);
		double min = Math.min(x1,x2);
		double max = Math.max(x1,x2);
		if(min < 0) return max;
		else return min;
		}
	else // if a = 0, just linear (and if b = 0, no solution)
		{
		return -c/b;
		}
		*/
	if((b*b-4*a*c) < 0 || (a == 0 && b == 0)) return -1; //we ignore negative numbers anyways
	if(a == 0) return -c/b;
	if(b == 0) return Math.sqrt(-c/a);		
	
	double q = -0.5*(b+Math.signum(b)*Math.sqrt(b*b-4*a*c));
	double x1 = q/a;
	double x2 = c/q;
	double min = Math.min(x1,x2);
	double max = Math.max(x1,x2);
	if(min < 0) return max;
	else return min;
	
    }
	
	/*
	public static Vector2d normal(Particle p, Particle q, Particle r)
	{
	// returns a unit normal to the edge pq in the direction or r 
	Vector2d n = new Vector2d();
	n.sub(q.x,p.x);
	double xT = n.x;
	double yT = n.y;
	n.set(-yT,xT);
	n.normalize();
	Vector2d v = new Vector2d();
	v.sub(r.x,p.x);
	if(n.dot(v) < 0) n.negate();
	return n;
	}
	
	
	public static Vector2d normal(Particle p, Particle q, Particle r, double t)
	{
	// returns a unit normal to the edge pq in the direction of r at time of intersection t
	p.advance(t);
	q.advance(t);
	r.advance(t);
	
	Vector2d n = new Vector2d();
	n.sub(q.x,p.x);
	double xT = n.x;
	double yT = n.y;
	n.set(-yT,xT);
	n.normalize();
	
	//System.out.println("Normal currently = " + n);
	
	p.advance(-t);
	Vector2d v = new Vector2d();
	v.sub(r.x,p.x);
	if(n.dot(v) < 0) n.negate();
	
	q.advance(-t);
	r.advance(-t);
	
	return n;		
	}
	*/
	
	public static Vector2d normal(Ball b1, Ball b2, double t)
	{
	// returns a unit normal from b2 to b1 at time t
	b1.advance(t);
	b2.advance(t);
	
	Vector2d n = new Vector2d();
	n.sub(b1.x,b2.x);
	n.normalize();
	
	b1.advance(-t);
	b2.advance(-t);
	
	return n;		
	}
	
	public static Vector2d normal(Ball b1, Paddle b2, double t)
	{
	// returns a unit normal from b2(paddle) to b1 at time t
	b1.advance(t);
	b2.advance(t);
	
	Vector2d n = new Vector2d();
	n.sub(b1.x,b2.x);
	n.normalize();
	
	b1.advance(-t);
	b2.advance(-t);
	
	return n;		
	}
	
	public static Vector2d rotateCW(Vector2d v)
	{
	//CW with y axis pointing downward (CCW normally)
	return new Vector2d(-v.y,v.x);
	}
	
	
	public static Vector2d normal(Point2d p, Point2d q, Point2d r)
	{
	// returns a unit normal to the edge pq in the direction of r
	
	Vector2d n = new Vector2d();
	n.sub(q,p);
	double xT = n.x;
	double yT = n.y;
	n.set(-yT,xT);
	n.normalize();
	
	//System.out.println("Normal currently = " + n);
	Vector2d v = new Vector2d();
	v.sub(r,p);
	if(n.dot(v) < 0) n.negate();
	
	return n;		
	}
	
	public static void reflect(Vector2d v, Vector2d n)
	{
	//bounces v off surface with normal n
	n.normalize();
	//System.out.println(v.length());
	acc(v,-2*v.dot(n),n);	
	//System.out.println("After: " + v.length());
	}

	public static Point2d lerp(Point2d a, Point2d b, double alpha)
	{
	// alpha between 0 and 1, alpha of the way from a to b
	Point2d result = new Point2d(a);
	Vector2d ab = new Vector2d();
	ab.sub(b,a);
	acc(result,alpha,ab);
	return result;
	}

	public static Vector2d lerp(Vector2d a, Vector2d b, double alpha)
	{
	// alpha between 0 and 1, alpha of the way from a to b
	Vector2d result = new Vector2d(a);
	Vector2d ab = new Vector2d();
	ab.sub(b,a);
	acc(result,alpha,ab);
	return result;
	}
	
	public static boolean withinRange(double x, double b1, double b2)
	{
	//returns true if x is between b1 and b2 (where b2 might be min)
	if(x< Math.min(b1,b2)) return false;
	if(x> Math.max(b1,b2)) return false;
	return true;
	}
}
