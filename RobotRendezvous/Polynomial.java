/*************************************************************************
 *  Compilation:  javac Polynomial.java
 *  Execution:    java Polynomial
 *
 *  Polynomials with integer coefficients.
 *
 *  % java Polynomial
 *  zero(x) =     0
 *  p(x) =        4x^3 + 3x^2 + 2x + 1
 *  q(x) =        3x^2 + 5
 *  p(x) + q(x) = 4x^3 + 6x^2 + 2x + 6
 *  p(x) * q(x) = 12x^5 + 9x^4 + 26x^3 + 18x^2 + 10x + 5
 *  p(q(x))     = 108x^6 + 567x^4 + 996x^2 + 586
 *  0 - p(x)    = -4x^3 - 3x^2 - 2x - 1
 *  p(3)        = 142
 *  p'(x)       = 12x^2 + 6x + 2
 *  p''(x)      = 24x + 6
 *
 *************************************************************************/

public class Polynomial {
    double[] coef;  // coefficients
    int deg;     // degree of polynomial (0 for the zero polynomial)

    // a * x^b
    public Polynomial(double a, int b) {
        coef = new double[b+1];
        coef[b] = a;
        deg = degree();
    }
	
    public Polynomial(Polynomial p) {
        coef = new double[p.deg+1];
		for(int i=0; i<=p.deg; i++) coef[i] = p.coef[i];
        deg = degree();
    }
	
    public Polynomial(double a, double b) {
        coef = new double[2];
        coef[0] = a;
        coef[1] = b;
        deg = degree();
    }

    // return the degree of this polynomial (0 for the zero polynomial)
    public int degree() {
        int d = 0;
        for (int i = 0; i < coef.length; i++)
            if (coef[i] != 0) d = i;
        return d;
    }

    // return c = a + b
    public Polynomial plus(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(0, Math.max(a.deg, b.deg));
        for (int i = 0; i <= a.deg; i++) c.coef[i] += a.coef[i];
        for (int i = 0; i <= b.deg; i++) c.coef[i] += b.coef[i];
        c.deg = c.degree();
        return c;
    }

    // return (a - b)
    public Polynomial minus(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(0, Math.max(a.deg, b.deg));
        for (int i = 0; i <= a.deg; i++) c.coef[i] += a.coef[i];
        for (int i = 0; i <= b.deg; i++) c.coef[i] -= b.coef[i];
        c.deg = c.degree();
        return c;
    }

    // return (a * b)
    public Polynomial times(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(0, a.deg + b.deg);
        for (int i = 0; i <= a.deg; i++)
            for (int j = 0; j <= b.deg; j++)
                c.coef[i+j] += (a.coef[i] * b.coef[j]);
        c.deg = c.degree();
        return c;
    }
	
	public Polynomial scale(double d) {
        Polynomial a = this;
		Polynomial b = new Polynomial(0,a.deg);
		for (int i = 0; i <= a.deg; i++) b.coef[i] = d*a.coef[i];
		b.deg = b.degree();
		return b;
	}
	
	public Polynomial power(int n) {
		
        Polynomial a = this;
		if(n==1) return a;
		else return a.times(a.power(n-1));
	}

    // return a(b(x))  - compute using Horner's method
    public Polynomial compose(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(0, 0);
        for (int i = a.deg; i >= 0; i--) {
            Polynomial term = new Polynomial(a.coef[i], 0);
            c = term.plus(b.times(c));
        }
        return c;
    }


    // do a and b represent the same polynomial?
    public boolean eq(Polynomial b) {
        Polynomial a = this;
        if (a.deg != b.deg) return false;
        for (int i = a.deg; i >= 0; i--)
            if (a.coef[i] != b.coef[i]) return false;
        return true;
    }


    // use Horner's method to compute and return the polynomial evaluated at x
    public double evaluate(double x) {
        double p = 0;
        for (int i = deg; i >= 0; i--)
            p = coef[i] + (x * p);
        return p;
    }
	
	// evaluates p(x)/x^n for degree n to avoid infinities in division
	public double evaluateInv(double x) {
		double p = 0;
		//System.out.println(p);
        for (int i = 0; i <= deg; i++)
			{
			//System.out.println(p);
            p = coef[i] + (p / x);
			}
        return p;
	}
	
	
	public double safeDivide(Polynomial b, double x) {
		Polynomial a = this;
		int degDiff = b.deg-a.deg;
		//System.out.println(a.evaluateInv(x));
		//System.out.println(b.evaluateInv(x));
		return (a.evaluateInv(x)/b.evaluateInv(x))/Math.pow(x,degDiff);
	}

    // differentiate this polynomial and return it
    public Polynomial differentiate() {
        if (deg == 0) return new Polynomial(0, 0);
        Polynomial deriv = new Polynomial(0, deg - 1);
        deriv.deg = deg - 1;
        for (int i = 0; i < deg; i++)
            deriv.coef[i] = (i + 1) * coef[i + 1];
        return deriv;
    }

    // convert to string representation
    public String toString() {
        if (deg ==  0) return "" + coef[0];
        if (deg ==  1) return coef[1] + "x + " + coef[0];
        String s = coef[deg] + "x^" + deg;
        for (int i = deg-1; i >= 0; i--) {
            if      (coef[i] == 0) continue;
            else if (coef[i]  > 0) s = s + " + " + ( coef[i]);
            else if (coef[i]  < 0) s = s + " - " + (-coef[i]);
            if      (i == 1) s = s + "x";
            else if (i >  1) s = s + "x^" + i;
        }
        return s;
    }

    // test client
    public static void main(String[] args) { 
        Polynomial zero = new Polynomial(0, 0);

        Polynomial p1   = new Polynomial(4, 3);
        Polynomial p2   = new Polynomial(3, 2);
        Polynomial p3   = new Polynomial(1, 0);
        Polynomial p4   = new Polynomial(2, 1);
        Polynomial p    = p1.plus(p2).plus(p3).plus(p4);   // 4x^3 + 3x^2 + 1

        Polynomial q1   = new Polynomial(3, 2);
        Polynomial q2   = new Polynomial(5, 0);
        Polynomial q    = q1.plus(q2);                     // 3x^2 + 5


        Polynomial r    = p.plus(q);
        Polynomial s    = p.times(q);
        Polynomial t    = p.compose(q);

        System.out.println("zero(x) =     " + zero);
        System.out.println("p(x) =        " + p);
        System.out.println("q(x) =        " + q);
        System.out.println("p(x) + q(x) = " + r);
        System.out.println("p(x) * q(x) = " + s);
        System.out.println("p(q(x))     = " + t);
        System.out.println("0 - p(x)    = " + zero.minus(p));
        System.out.println("p(3)        = " + p.evaluate(3));
        System.out.println("p'(x)       = " + p.differentiate());
        System.out.println("p''(x)      = " + p.differentiate().differentiate());
		
		int n = 10;
		Polynomial[] det = new Polynomial[n];
	
		det[0]=new Polynomial(1.,0);
		det[1]=new  Polynomial(1.,1.);
		Polynomial dsq = new  Polynomial(0.25,2);
		
		for(int i=2; i<n; i++)
			{
			det[i] = (det[1].times(det[i-1])).minus(dsq.times(det[i-2]));
			}
		System.out.println(det[0]);
		System.out.println(det[1]);
		System.out.println(det[2]);
		System.out.println(det[3]);
		System.out.println(det[4]);
		
		Polynomial detN = (det[1].times(det[n-1])).minus(dsq.scale(2).times(det[n-2]));
		int n2 = 2;
		for(int i=1; i<n; i++) n2 *= 2;
		//System.out.println(n2);
		Polynomial dn = new Polynomial(1./n2,n);
		detN = detN.minus(dn.scale(2));
		System.out.println(detN);
		//System.out.println(dsq);
		//System.out.println(dsq.scale(2));
		
		Polynomial[] cP = new Polynomial[n];
		cP[0] = new Polynomial(det[n-1]);
		cP[1] = new Polynomial(2./n2,n-1);
		Polynomial d2 = new  Polynomial(0.5,1);
		cP[1] = cP[1].plus(d2.times(det[n-2]));
			
		System.out.println(cP[0]);
		System.out.println(cP[1]);
		//System.out.println(d2.power(3));
			
		for(int i=2; i<=n/2; i++)
			{
			cP[i] = d2.power(i).times(det[n-1-i]).plus(d2.power(n-i).times(det[i-1]));
			}
		System.out.println(cP[2]);
		//ALL good
		
		System.out.println(cP[0].evaluate(1.5)/detN.evaluate(1.5));
   }

}
