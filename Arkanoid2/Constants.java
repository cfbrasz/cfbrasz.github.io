//
// Class for constants in Arkanoid 2.0
// (c) Fred Brasz 2009

import java.awt.*;

public interface Constants {

    public final double multiplier = 10.; // increase to make game run in fewer frames per second (only necessary for slow computers) 
	public static final Integer TOTALLIVES = 5;
	public static final double eps = 0.000001;
	
	//block constants
    public static final int blockWidth = 40;
    public static final int blockHeight = 20;	
    public static final int blockWidthS = 20;
    public static final int blockHeightS = 10;	
	
	//shine constants
	public static final int shineWidth = 30;
	public static final int shineSpeed = 7;
	
	//board constants
	public static final int boardHeight = 530;
    public static final int blocksPerRow = 11;
    public static final int blocksPerRowS = 22;
    public static final int yBlockMarg = 20;
    public static final int boardWidth = blocksPerRow*blockWidth; //11*40 = 440// 168 + 150 + 11*40 = 758
	
	//powerup constants
    public static final int powerupHeight = 18;	
    public static final double powerupSpeed = 0.2*multiplier;
	public static final double powerupProb = 0.15; // probability of producing a powerup when a block is destroyed
	public static final double godModeCurveRate = 0.05;
	
    // constants for powerups
    public static final int BIG = 0;
    public static final int STICKY = 1;
    public static final int SLOW = 2;
    public static final int LASER = 3;
    public static final int MULTIPLY = 4;
    public static final int UNSTOPPABLE = 5;
    public static final int SMALL = 6;
    public static final int GODMODE = 7;
    public static final int SPLIT = 8;
    public static final int BIGBALL = 9;
    public static final int GRAVITY = 10;
    public static final int MISSILE = 11;
    public static final int STRONGLYCURVED = 12;
	
	//paddle constants
    public static final double paddleSpeed = 0.7*multiplier;
	public static final double paddleSlowFactor = 3;
	public static final int paddleRadius = 32;
	
	//laser constants
    public static final double laserSpeed = 1.0*multiplier;
    public static final int laserWidth = 4;
    public static final int laserHeight = 12;
    public static final int laserReloadTime = 100; //ms to wait before firing again

	//ball constants
    public static final double ballSpeed = 0.4*multiplier;
    public static final int ballRadius = 5;
    public static final int bigBallRadius = 10;
    public static final double minSpeedY = 0.1*multiplier; //require that ball's vertical speed is always at least this much so we don't have to wait too long
    public static final double minSpeedX = 0.02*multiplier; //require that ball's vertical speed is always at least this much so we don't have to wait too long
    public static final double speedUpRate = 1.002;//1.004; // 1.02;//increase in speed with bouncing
	public static final double CORDefault = 1;//0.8; // 1 is perfect bounce (Coefficient of Restitution)
    public static final double cI = 2./3.; //for spherical shell (hollow ball); 2/5 for solid sphere
    public static final double defaultMuW = 0.2; //of walls
    public static final double defaultMuB = 0.2; //of blocks
    public static final double defaultMuP = 0.85; //of paddle
    public static final double BALL_MASS     = 1.0;
	public static final double magnusConst = 0.008;//0.00223; ////a little small seeming... 1/2 rho r A l / m, rho = 1.2 kg/m^3, r = 2 cm, A = Pi*(2 cm)^2, l = lift coefficient, 0.2 to 0.6 (we take 0.4), m = 2.7 g
	public static final double strongCurvatureMultiplier = 4;
	public static final double startingSpin = 0.2; //for ball at beginning of a life (positive means CCW on screen) 
	
	//constants for what edge a ball is bouncing off of: (Note that the ball is on the opposite side, so for LEFT, the ball is on the right side of the wall)
	public static final int NONE = 0;
	public static final int LEFT = 1;
	public static final int TOP = 2;
	public static final int RIGHT = 3;
	public static final int BOTTOM = 4;
	public static final int CORNER = 5;
	
	//Colors
	public static final Color PINK = new Color(255,143,248);
	public static final Color SILVER = new Color(190,190,190); 
	public static final Color GOLD = new Color(220,190,50);//new Color(255,175,0);
	public static final double minColorDist = 5;
}