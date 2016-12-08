import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.awt.image.BufferedImage;

public class SpinBallCanvas extends Canvas implements KeyListener,MouseListener  {

	private Vector<SpinBall> balls;
	private int boardWidth;
	private int boardHeight;
	private int radius = 20;
	private int maxRadius = 100;
	private int ballCount = 0;
	private int pWidth = 200;
	private int pHeight = 30;
	private boolean randomRadius = true;
	private boolean randomOmega = true;
	private double maxOmega = 15*Math.PI;
	private BufferedImage buffer;
	private Graphics bufG;
	private double pressX,pressY,releaseX,releaseY;
	private double mouseX,mouseY;
	private double multiplier;
	private double pixelsPerInch = 67.37; //at my resolution, 1024 X 768, on a 19" monitor
	private double cmPerInch = 2.54;
	//private double gravity = 9.8; //m/s^2
	//private double multiplier = 12.0; // ms / frame
	//private double gravityConst = gravity * 100 / cmPerInch * pixelsPerInch; // = 0.026, gives true gravity for actual distances on screen
	private double gravityConst = 0.001; //0 gives no gravity
	//private double gravityConst = 0.01; //0 gives no gravity
	private double gravity;
	private boolean gravityOn = false;
	private int charge = 0;
	private boolean pausePressed = false;
	private boolean advance = false;
	public boolean advanceMode = false;
	private boolean tracesOn = true;
	private SpinBallApplet japp;
    private double defaultMu = 0.2; //of walls
    private double defaultMuP = 0.85; //of paddle
    private double mu = defaultMu;
    private double muP = defaultMuP;
	private double CORDefault = 0.8; // 1 is perfect bounce (Coefficient of Restitution)
	private double COR = CORDefault;
	private double paddleSpeed;
	private double paddleSlowFactor = 3;
	private Paddle p;
	public boolean moveLeft;
	public boolean moveRight;
	public boolean moveUp;
	public boolean moveDown;
	public boolean slowMode;
	public boolean rotateCCW;
	public boolean rotateCW;

	public SpinBallCanvas(Vector<SpinBall> balls, int boardWidth, int boardHeight, double multiplier, SpinBallApplet japp)
	{
	this.balls = balls;
	this.boardWidth = boardWidth;
	this.boardHeight = boardHeight;
	this.multiplier = multiplier;
	paddleSpeed = 0.7*multiplier;
	this.gravity = multiplier*multiplier*gravityConst;
	buffer = new BufferedImage(boardWidth, boardHeight, 
      BufferedImage.TYPE_INT_RGB);
	bufG = buffer.getGraphics();
	bufG.setColor(Color.BLACK);
	bufG.fillRect(0,0,boardWidth,boardHeight);
	addKeyListener(this);
	addMouseListener( this );
	this.japp = japp;
	addPaddle();
	}

	public void mousePressed(MouseEvent e)
	{
	pressX = e.getX();
	pressY = e.getY();
	e.consume();
	}
	
	public void mouseReleased(MouseEvent e)
	{
	float red = (float)Math.random();
	float green = (float)Math.random();
	float blue = (float)Math.random();
	double omega;
	releaseX = e.getX();
	releaseY = e.getY();
	Coord release = new Coord(releaseX,releaseY);
	Coord press = new Coord(pressX,pressY);
	e.consume();
	requestFocusInWindow();
	int cRadius;
	if(randomRadius) cRadius = 1 + (int)(Math.random()*maxRadius);
	else cRadius = japp.getRadius();
	if(randomOmega) omega = -12*Math.PI + 24*Math.random()*Math.PI;
	else omega = japp.getOmega();
	//omega = -12*Math.PI + 24*Math.random()*Math.PI;
	//omega = release.dist(press)*5 / radius;
	SpinBall bb = new SpinBall(pressX,pressY,cRadius,release.dist(press)*multiplier/200,-press.angle(release),
		0,0,boardWidth,boardHeight,red,green,blue,gravity,gravityOn,charge,0,omega,multiplier,tracesOn,mu,muP,COR,ballCount);
	if (balls.size() > 0) bb.setGravity(balls.get(0).getGravity());
	boolean placeBall = true;
	for (int i=0; i < balls.size(); i++)
	    {		
		if(bb.getCoord().dist(balls.get(i).getCoord()) <= cRadius+balls.get(i).getRadius()) placeBall = false;			
	    }
	if (boardWidth - pressX <= radius || pressX <= cRadius ||
		boardHeight - pressY <= radius || pressY <= cRadius ) placeBall = false;	
	if (placeBall)
		{	
	/*
		for (int i=0; i<balls.size(); i++)
			{
			bb.addForce(balls.get(i));
			balls.get(i).addForce(bb);
			}
			*/
			
		balls.add(bb);
		ballCount++;
		/*
		Vector<Boolean> newRow = new Vector<Boolean>();
		for (int i=0; i <= free.size(); i++) newRow.add(Boolean.TRUE);
		//free.add(newRow);
		//System.out.println(free.toString());
		*/
		}
	}
	
	public void addPaddle()
	{
	double startAngle = 0;//Math.PI/3; // positive clockwise
	int startX = 250;
	int startY = 200;
	p = new Paddle(startX, startY, pWidth, pHeight, startAngle, paddleSpeed, boardWidth, boardHeight, paddleSlowFactor);
    }
	
	public void manyBalls()
	{
	int nX = 10;
	int cR = 6;
	int nY = 20;
	/*
	int nX = 3;
	int nY = 5;	
	int cR = 20;
	*/
	int startX = (boardWidth - 2*cR*nX)/2;
	int startY = (boardHeight - 2*cR*nY)/2;
	double cOmega = 0;
	double startV = 0;
	double startAngle = 0;
	float r = 0;
	float g = 0;
	float b = 1;
	tracesOn = false;
	gravityOn = true;
	//mu = defaultMu;
	
	for(int i=0; i<nX; i++)
		{
		for(int j=0; j<nY; j++)
			{
			//SpinBall bb = new SpinBall(i*2*cR+startX+3*i,j*2*cR+startY+3*j,cR,startV*multiplier/200,startAngle,
			//	0,0,boardWidth,boardHeight,r,g,b,gravity,gravityOn,charge,0,cOmega,multiplier,tracesOn,mu,muP,COR);
			
			SpinBall bb = new SpinBall(i*2*cR+startX+5*i+(int)(2*Math.random())-1,j*2*cR+startY+3*j+i,cR,startV*multiplier/200,startAngle,
				0,0,boardWidth,boardHeight,r,g,b,gravity,gravityOn,charge,0,cOmega,multiplier,tracesOn,mu,muP,COR,i*nY+j);
				
			balls.add(bb);
			}
		}
	}
	
	public void testCollisions(int ds)
	{
	// ds = 1 or 0, changes whether bb1 or bb3 hits bb2 first
	int cR = 20;
	int x1 = 50;
	int y1 = 50;
	int x2 = 200;
	int y2 = 200;
	int x3 = 200+(int)(150*Math.sqrt(2));
	int y3 = 200;
	double cOmega = 0;
	double startV = 20;
	double startAngle = -Math.PI/4;
	float r = 0;
	float g = 0;
	float b = 1;
	tracesOn = true;
	gravityOn = false;
	//ball 1 is red
	SpinBall bb1 = new SpinBall(x1+ds,y1+ds,cR,startV*multiplier/200,startAngle,
		0,0,boardWidth,boardHeight,1,0,0,gravity,gravityOn,charge,0,cOmega,multiplier,tracesOn,mu,muP,COR,0);
	//ball 2 is green
	SpinBall bb2 = new SpinBall(x2,y2,cR,0,startAngle,
		0,0,boardWidth,boardHeight,0,1,0,gravity,gravityOn,charge,0,cOmega,multiplier,tracesOn,mu,muP,COR,1);
	//ball 3 is blue
	SpinBall bb3 = new SpinBall(x3,y3,cR,startV*multiplier/200,Math.PI,
		0,0,boardWidth,boardHeight,0,0,1,gravity,gravityOn,charge,0,cOmega,multiplier,tracesOn,mu,muP,COR,2);
	balls.add(bb1);
	balls.add(bb2);
	balls.add(bb3);
	
	}
	
	public void test1D()
	{
	int cR = 5;
	int x1 = 50;
	int y1 = 200;
	int x2 = 250;
	int y2 = 200;
	int x3 = 450;
	int y3 = 200;
	double cOmega = 0;
	double startV = 200;
	float r = 0;
	float g = 0;
	float b = 1;
	tracesOn = true;
	gravityOn = false;
	//ball 1 is red
	SpinBall bb1 = new SpinBall(x1,y1,cR,startV*multiplier/200,0,
		0,0,boardWidth,boardHeight,1,0,0,gravity,gravityOn,charge,0,cOmega,multiplier,tracesOn,mu,muP,COR,0);
		for(int i=0; i<5; i++)
			{
			//ball 2 is green
			SpinBall bb2 = new SpinBall(x2+(2*cR+1)*i,y2,cR,0,0,
				0,0,boardWidth,boardHeight,0,1,0,gravity,gravityOn,charge,0,cOmega,multiplier,tracesOn,mu,muP,COR,i+1);
			balls.add(bb2);
			}
	//ball 3 is blue
	SpinBall bb3 = new SpinBall(x3,y3,cR,startV*multiplier/200,Math.PI,
		0,0,boardWidth,boardHeight,0,0,1,gravity,gravityOn,charge,0,cOmega,multiplier,tracesOn,mu,muP,COR,6);
	balls.add(bb1);
	//balls.add(bb3);
	
	}
	
	public void test1Dgrav()	
	{
	int nX = 1;
	int cR = 20;
	int nY = 3;
	/*
	int nX = 3;
	int nY = 5;	
	int cR = 20;
	*/
	int startX = (boardWidth - 2*cR*nX)/2;
	int startY = (boardHeight - 2*cR*nY)/2;
	double cOmega = 0;
	double startV = 0;
	double startAngle = 0;
	float r = 0;
	float g = 0;
	float b = 1;
	tracesOn = false;
	gravityOn = true;
	//mu = defaultMu;
	
	for(int i=0; i<nX; i++)
		{
		for(int j=0; j<nY; j++)
			{
			SpinBall bb = new SpinBall(i*2*cR+startX+3*i,j*2*cR+startY+3*j,cR,startV*multiplier/200,startAngle,
				0,0,boardWidth,boardHeight,r,g,(float)(0.5+0.5*j/(nY-1)),gravity,gravityOn,charge,0,cOmega,multiplier,tracesOn,mu,muP,COR,i*nY+j);
			
			//SpinBall bb = new SpinBall(i*2*cR+startX+5*i+(int)(2*Math.random())-1,j*2*cR+startY+3*j+i,cR,startV*multiplier/200,startAngle,
			//	0,0,boardWidth,boardHeight,r,g,b,gravity,gravityOn,charge,0,cOmega,multiplier,tracesOn,mu,muP,COR);
				
			balls.add(bb);
			}
		}
	}
	
	public void selectScenario(String name)
	{
	removeAll();
	if(name.equals("Backspin"))
		{
		//test1D();
		test1Dgrav();
		}
	else if(name.equals("Topspin"))
		{
		testCollisions(1);
		}
	else if(name.equals("Many balls"))
		{
		manyBalls();
		}
	else if(name.equals("Ping pong ball plus basketball"))
		{
		testCollisions(0);
		}
	japp.setTraces(tracesOn);
	japp.setGravity(gravityOn);
	repaint();
	}

	public void movePaddle()
    {
	// User moves the paddle here
		
		if(rotateCCW && !rotateCW)
			{
			p.rotateCCW();
			}
		else if(rotateCW && !rotateCCW)
			{
			p.rotateCW();
			}			
	
		if (moveLeft && !moveRight) 
		    {
			p.moveLeft(slowMode);	
		    }
		else if (moveRight && !moveLeft) 
		    {
			p.moveRight(slowMode);
		    }
		if (moveUp && !moveDown) 
		    {
			p.moveUp(slowMode);	
		    }
		else if (moveDown && !moveUp) 
		    {
			p.moveDown(slowMode);	
		    }
		if(!moveLeft && !moveRight && !moveUp && !moveDown) p.notMoving();
    }	    

	public void checkPaddleCollisions()
	{	
	for (int i=0; i < balls.size(); i++)
	    {
		SpinBall bCur = balls.get(i);
		int testHit = p.justHitSide(bCur);
		if(testHit > -1) 
			{
			if(!bCur.getPushed())
				{
				p.savePosition();
				double timeLeft = p.collisionPoint(bCur); //moves to where they just hit
				bCur.undoGravity(timeLeft);
				//System.out.println("Time left: " + timeLeft);
				int hitSide = p.hitSide(bCur,testHit);
				p.loadPosition();
				//System.out.println("side hit: " + hitSide);
				if(hitSide > -1) 
					{
					//System.out.println(hitSide);
					bCur.update(timeLeft);
					if(p.justHitSide(bCur) > -1)
						{
						//System.out.println("Pushing out");
						p.pushOut(bCur);
						}
					}
				}
			else
				{
				//System.out.println("Pushing out without colliding");
				p.pushOut(bCur);
				}
			}
		}
	}
	
	public boolean checkPause()
	{
	return pausePressed;
	}
	
	public boolean advanceOne()
	{
	if(advance)
		{
		advance = false;
		return true;
		}
	else return false;
	}
	
	public void setGravity(boolean val)
	{	
	gravityOn = val;
	for (int i=0; i < balls.size(); i++)
		{
		balls.get(i).setGravityOn(val);
		}
	}
	
	public void setGravity(int val)
	{	
	gravityOn = true;
	gravity = multiplier*multiplier*gravityConst*val*val/(100*100);
	for (int i=0; i < balls.size(); i++)
		{
		balls.get(i).setGravity(gravity);
		}
	}
	
	public void setWallBallFriction(double val)
	{	
	mu = val;
	for (int i=0; i < balls.size(); i++)
		{
		balls.get(i).setWallBallFriction(val);
		}
	}
	
	public void setWallPaddleFriction(double val)
	{	
	muP = val;
	for (int i=0; i < balls.size(); i++)
		{
		balls.get(i).setWallPaddleFriction(val);
		}
	}
	
	public void setCOR(double val)
	{	
	COR = val;
	for (int i=0; i < balls.size(); i++)
		{
		balls.get(i).setCOR(val);
		}
	}
	
	public void setTraces(boolean val)
	{	
	tracesOn = val;
	for (int i=0; i < balls.size(); i++)
		{
		if(val) balls.get(i).setTrailLength(-1);
		else balls.get(i).setTrailLength(0);
		}
	repaint();
	}
	
	public void setPaused(boolean val)
	{
	pausePressed = val;
	}
	
	public void setRandomRadius(boolean val)
	{
	randomRadius = val;
	}
	
	public void setRandomOmega(boolean val)
	{
	randomOmega = val;
	}
	
	public void setRadius(int val)
	{
	radius = val;
	}
	
	public void removeAll()
	{
		balls.removeAllElements();
		japp.balls.removeAllElements();
		ballCount = 0;
		repaint();
	}
	
	public void mouseClicked(MouseEvent e)
	{
	}
	
	public void mouseEntered(MouseEvent e)
	{
	}
	
	public void mouseExited(MouseEvent e)
	{
	}
	
	/*
	public void mouseMoved( MouseEvent e ) {  // called during motion when no buttons are down
	mouseX = e.getX();
	mouseY = e.getY();
	//repaint();
	e.consume();
    }
	*/

	
	public void keyTyped(KeyEvent e)
    {
	//System.out.println("typed "+e);
	
    }

    public void keyPressed(KeyEvent e)
    {
	//System.out.println("pressed " + e.getKeyCode());
	
	if (e.getKeyCode() == 32) // 32 is space bar
	    {
		pausePressed = !pausePressed;
		}
	
	if (e.getKeyCode() == 17) // 17 is "Ctrl"
	    {				
		slowMode = true;
		}
		
	if (e.getKeyCode() == 87) moveUp = true; // w
	if (e.getKeyCode() == 68) moveRight = true; // d
	
	if (e.getKeyCode() == 65) // 65 is a
	    {
		moveLeft = true;
		}
	
	if (e.getKeyCode() == 83) // 83 is "s"
	    {
		moveDown = true;
		}
	
	if (e.getKeyCode() == 82) // 82 is "r"
	    {
		balls.removeAllElements();
		}
	
	if (e.getKeyCode() == 84) // 84 is "t"
	    {
		advanceMode = false;
		System.out.println("advanceMode turned off");
		}
	
	if (e.getKeyCode() == 90) // "z"
	    {
		advance = true;
		}
	
	if (e.getKeyCode() == 88) // "x"
	    {
		advanceMode = true;
		System.out.println("advanceMode turned on");
		}
		
	if (e.getKeyCode() == 81) // 81 is "q"
	    {
		rotateCCW = true;
		}
		
	if (e.getKeyCode() == 69) // "e"
	    {
		rotateCW = true;
		}
		
	if (e.getKeyCode() == 80) // 81 is "p"
	    {
		charge = 1; //positive charge
		System.out.println("Selected positive charge");
		}
		
	if (e.getKeyCode() == 77) // 77 is "m"
	    {
		charge = -1; //negative charge
		System.out.println("Selected negative charge");
		}
		
	if (e.getKeyCode() == 78) // 77 is "n"
	    {
		charge = 0; //no charge
		System.out.println("Selected no charge");
		}
		
	if (e.getKeyCode() == 57) // 57 is "9" (not num pad)
	{		
		setGravity(true);
		System.out.println("Gravity turned on");
	}
		
	if (e.getKeyCode() == 48) // 48 is "0" (not num pad)
	{
		setGravity(false);
		System.out.println("Gravity turned off");
	}
	
	if (e.getKeyCode() == 61) // = and + key
	    {
		balls.get(0).modifyGravity(1.1);
		// increases gravity's strength		
			for (int i=1; i < balls.size(); i++)
			    {
				SpinBall bCur = balls.get(i);
				bCur.setGravity(balls.get(0).getGravity());				
				//System.out.println(bCur.getGravity());			
			    }			
			System.out.println("Stength of gravity increased to " + balls.get(0).getGravity());
	    }

	if (e.getKeyCode() == 45) // - and _ key
	    {
		balls.get(0).modifyGravity(1/1.1);
		// decreases gravity's strength		
			for (int i=1; i < balls.size(); i++)
			    {
				SpinBall bCur = balls.get(i);
				bCur.setGravity(balls.get(0).getGravity());
				//System.out.println(bCur.getGravity());				
			    }			
			System.out.println("Stength of gravity decreased to " + balls.get(0).getGravity());
	    }
	
	
	}
	
	public void keyReleased(KeyEvent e)
    {
		
	if (e.getKeyCode() == 87) moveUp = false; // w
	if (e.getKeyCode() == 68) moveRight = false; // d
	
	if (e.getKeyCode() == 65) // 65 is a
	    {
		moveLeft = false;
		}
	
	if (e.getKeyCode() == 83) // 83 is "s"
	    {
		moveDown = false;
		}
	
	if (e.getKeyCode() == 17) // 17 is "Ctrl"
	    {				
		slowMode = false;
		}
		
	if (e.getKeyCode() == 81) // 81 is "q"
	    {
		rotateCCW = false;
		}
		
	if (e.getKeyCode() == 69) // "e"
	    {
		rotateCW = false;
		}
	}
	
	
	public void update(Graphics g)
    {
	if (!buffer.equals(null)) {
	bufG = buffer.getGraphics();
	bufG.setColor(Color.BLACK);
	bufG.fillRect(0,0,boardWidth,boardHeight);
	bufG.fillRect(0,0,boardWidth,boardHeight);
	bufG.dispose();
	
	paint(g);	
	g.dispose();
	}
	}
	
	public void paint(Graphics g)
	{
	if (!buffer.equals(null)) {
	bufG = buffer.getGraphics();
	
	bufG.setColor(Color.BLACK);
	bufG.fillRect(0,0,boardWidth,boardHeight);
	
	for (int i=0; i < balls.size(); i++)
	    {
		balls.get(i).paint(bufG);
	    }	
		
	if(p != null) p.paint(bufG);
		
	g.drawImage(buffer, 0, 0, this);
	g.dispose();
	}
		
	}
	
	
}