import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.awt.image.BufferedImage;
import java.lang.Math;
import javax.swing.JApplet;

public class PolyhedraCanvas extends Canvas implements KeyListener,MouseListener  {

	private int boardWidth;
	private int boardHeight;
	private BufferedImage buffer;
	private Graphics bufG;
	private Vector<Triangle3D> tris = new Vector<Triangle3D>();
	private Vector<Quad3D> quads = new Vector<Quad3D>();
	private Vector<Sphere> spheres = new Vector<Sphere>();
	private Vector<Shape3D> shapes = new Vector<Shape3D>();
	private Sphere lightSphere;
	private double lightSphereRadius = 2;
	private Vector3D direction;
	private Vector3D directionStart;
	private Coord3D origin;
	private Coord3D originStart;
	private Coord3D originSave;
	private int res = 5; // divide boardWidth/Height by res to get number of rays per row/column that we shoot
	private int pixelsWide;
	private int pixelsHigh;
	private double apertureX = Math.PI/3;
	private double apertureY = Math.PI/3;
	private double lightPower = 6; //4000 for dist squared
	private double lightPowerSq = 6000; // for dist squared
	private Coord3D lightSource;
	private Coord3D lightSourceStart;
	private int lightingMode = 1; //0 = no dist dependence, 1 = sqrt, 2 = inverse square
	private int lightColorMode = 1; //0 makes light white and shadow the base color, 1 makes light the base color and shadow dark
	public final int FISHEYE = 0;
	public final int ORTH = 1;
	public final int PINHOLE = 2;
	//private int viewType = FISHEYE;
	private int viewType = PINHOLE;
	private double viewFactor = 30; // for orth view
	private double moveDist = 5;
	private Color lightColor = Color.YELLOW;
	private PolyhedraApplet japp;
	private double pinHoleDist = 1;
	private double testRefl = 0.6;
	private double filmConst = 1.154700538;
	private double filmWidth = filmConst;
	private double filmHeight = filmConst;
	private double minIntensity = 0.15;
	private int maxReflections = 10;
	private int defaultV = 25;
	private boolean testing = true;
	private int selectedRemoveShape = -1;
	private MiniCanvas miniCanvas;

	public PolyhedraCanvas(int boardWidth, int boardHeight, PolyhedraApplet japp, MiniCanvas miniCanvas)
	{
	this.boardWidth = boardWidth;
	this.boardHeight = boardHeight;
	pixelsWide = boardWidth/res;
	pixelsHigh = boardHeight/res;
	buffer = new BufferedImage(boardWidth, boardHeight, 
      BufferedImage.TYPE_INT_RGB);
	bufG = buffer.getGraphics();
	bufG.setColor(Color.WHITE);
	bufG.fillRect(0,0,boardWidth,boardHeight);
	addKeyListener(this);
	//addMouseListener( this );	
	this.japp = japp;
	this.miniCanvas = miniCanvas;
	//if(lightingMode == 2) lightPower = 4000;
	
	//trianglesTest();
	//else roomWithSpheres();
	//japp.updateDirectionFields();
	}	
	
	public String[] shapeNames()
	{
	String[] result = new String[shapes.size()+1];
	result[0] = "None";
	for(int i=1; i<shapes.size()+1; i++)
		{
		result[i] = (i) + ": " + shapes.get(i-1).type();
		}
		//System.out.println(result);
	return result;
	}
	
	public void setSelectedRemoveShape(int i)
	{
	selectedRemoveShape = i;
	}
	
	public void roomWithSpheres()
	{
	setLightSource(7,-20,-15);
	directionStart = new Vector3D(0.927,0.374,0.017); 
	direction = directionStart.copy();
	originStart = new Coord3D(-43,-37,3);
	origin = originStart.copy();
	originSave = origin.copy();
	simpleRoom();
	addSpheres();
	}
	
	public void removeShape(int index)
	{
	shapes.removeElementAt(index);
	repaint();
	}
	
	public void reset()
	{ 
	direction = directionStart.copy(); 
	origin = originStart.copy();
	originSave = origin.copy();
	setLightSource((int)lightSourceStart.getX(),(int)lightSourceStart.getY(),(int)lightSourceStart.getZ());
	repaint();
	}
	
	public void setViewAngleSlider(int value)
	{
	filmWidth = filmConst*(double)value*value/(defaultV*defaultV);
	filmHeight = filmConst*(double)value*value/(defaultV*defaultV);
	repaint();
	}
	
	public void setMaxReflections(int maxReflections)
	{
	this.maxReflections = maxReflections;
	repaint();
	}
	
	public void setBrightness(int value)
	{
	lightPower = (double)value/10;
	lightPowerSq = (double)value*100;
	repaint();
	}
	
	public void loadPreset(String name)
	{
	clearAll();
	if(name.equals("Blank"))
		{
		lightSource = new Coord3D(-10,0,0);
		setLightSource(lightSource);
		directionStart = new Vector3D(1,0,0); 
		direction = directionStart.copy();
		originStart = new Coord3D(0,0,0);
		origin = originStart.copy();
		originSave = origin.copy();
		}
	else if(name.equals("Simple room"))
		{
		lightSource = new Coord3D(20,0,20);
		setLightSource(lightSource);
		simpleRoom();
		directionStart = new Vector3D(1,0,0); 
		direction = directionStart.copy();
		originStart = new Coord3D(-50,0,0);
		origin = originStart.copy();
		originSave = origin.copy();
		}
	else if(name.equals("Room with cylinders, disks"))
		{
		lightSource = new Coord3D(20,0,20);
		setLightSource(lightSource);
		simpleRoom();
		testShapes();
		directionStart = new Vector3D(1,0,0); 
		direction = directionStart.copy();
		originStart = new Coord3D(-50,0,0);
		origin = originStart.copy();
		originSave = origin.copy();
		}
	else if(name.equals("Room with spheres"))
		{
		roomWithSpheres();	
		}
	else if(name.equals("Mirror room"))
		{
		setLightSource(10,-10,0);
		directionStart = new Vector3D(1,0,0); 
		direction = directionStart.copy();
		originStart = new Coord3D(0,0,0);
		origin = originStart.copy();
		originSave = origin.copy();		
		mirrorRoom();
		}
	else if (name.equals("Reflective cylinder and sphere"))
		{
		setLightSource(-20,0,5);
		directionStart = new Vector3D(1,0,-2); 
		direction = directionStart.copy();
		originStart = new Coord3D(-20,0,35);
		origin = originStart.copy();
		originSave = origin.copy();	
		cylinderSphere();
		}
	japp.updateViewpointFields();
	japp.updateViewSliders();
	japp.updateLightSourceFields();
	repaint();
	}	
	
	public void addSpheres()
	{
	Coord3D cent = new Coord3D(30,10,20);
	double r = 20;
	Sphere sph = new Sphere(cent,r,Color.GREEN,testRefl);
	//spheres.add(sph);
	shapes.add(sph);
	
	cent = new Coord3D(70,10,30);
	r = 15;
	sph = new Sphere(cent,r,new Color(100,0,200),testRefl);
	//spheres.add(sph);
	shapes.add(sph);
	
	cent = new Coord3D(70,-30,-30);
	r = 20;
	sph = new Sphere(cent,r,Color.CYAN);
	//spheres.add(sph);
	shapes.add(sph);
	}
	
	public void addSphere(Coord3D cent, double radius, Color col)
	{
	addSphere(cent,radius,col,0);
	}	
	
	public void addSphere(Coord3D cent, double radius, Color col, double reflectivity)
	{
	Sphere sph = new Sphere(cent,radius,col,reflectivity);
	shapes.add(sph);	
	repaint();
	}
	
	public void addCircle(Coord3D cent, double radius, Vector3D n, Color col, double reflectivity)
	{
	Circle cir = new Circle(cent,radius,n,col,reflectivity);
	shapes.add(cir);	
	repaint();
	}
	
	public void addCylinder(Coord3D cent, double radius, double height, Vector3D n, Color col, double reflectivity)
	{
	Cylinder cyl = new Cylinder(cent,radius,height,n,col,reflectivity);
	shapes.add(cyl);	
	repaint();
	}
	
	public void addCappedCylinder(Coord3D cent, double radius, double height, Vector3D n, Color col, double reflectivity)
	{
	n = n.unit();
	Cylinder cyl = new Cylinder(cent,radius,height,n,col,reflectivity);
	shapes.add(cyl);	
	Coord3D top = n.parametric(cent,height/2);
	Coord3D bottom = n.parametric(cent,-height/2);
	Circle cTop = new Circle(top,radius,n,col,reflectivity);
	shapes.add(cTop);	
	Circle cBottom = new Circle(bottom,radius,n,col,reflectivity);
	shapes.add(cBottom);	
	repaint();
	}
	
	public void addPolygon3D(Coord3D[] coords, Color col, double reflectivity)
	{
	//ensure that coords are in a plane beforehand
	Polygon3D poly = new Polygon3D(coords,col,reflectivity);
	shapes.add(poly);
	repaint();
	}
	
	public void cylinderSphere()
	{
	Coord3D cent = new Coord3D(0,0,0);
	Vector3D n = new Vector3D(0,0,1);
	double r = 30;
	double r2 = 15;
	double height = 70;
	addCappedCylinder(cent,r,height,n,Color.ORANGE,0.7);
	
	addSphere(cent,r2,Color.BLUE,0.5);
	}
	
	
	
	public void simpleRoom()
	{	
	Color wallColor = new Color(150,100,150); //purpleish
	Color doorColor = new Color(50,25,0); //darker brown
	Color floorColor = new Color(100,50,0); //brown	
	Coord3D backLeftTop;
	Coord3D leftTop;
	double xWidth;
	double yWidth;
	double zWidth;
	
	//room:
	backLeftTop = new Coord3D(100,50,50);
	xWidth = 200;
	yWidth = 100;
	zWidth = 100;
	addRectPrism(backLeftTop,xWidth,yWidth,zWidth,floorColor,wallColor);		
	
	//door
	leftTop = new Coord3D(99,20,20);
	addYZQuad(leftTop,40,70,doorColor);
	
	//red rectPrism
	backLeftTop = new Coord3D(70,30,-20);
	xWidth = 20;
	yWidth = 20;
	zWidth = 30;
	addRectPrism(backLeftTop,xWidth,yWidth,zWidth,Color.RED);	
	
	Coord3D cn = new Coord3D(20,-35,-35);
	Color col = Color.BLUE;
	addCube(cn,30,col);		
	}
	
	public void testShapes()
	{		
	Coord3D[] coords = new Coord3D[3];
	coords[0] = new Coord3D(100,50,-50);
	coords[1] = new Coord3D(75,50,0);
	coords[2] = new Coord3D(80,25,50);
	Triangle3D tri = new Triangle3D(coords);
	shapes.add(tri);
		
	coords = new Coord3D[5];
	coords[0] = new Coord3D(40,-10,0);
	coords[1] = new Coord3D(40,-40,0);
	coords[2] = new Coord3D(70,-20,25);
	coords[3] = new Coord3D(100,-40,50);
	coords[4] = new Coord3D(100,-10,50);
	addPolygon3D(coords,Color.GREEN,0);		
	
	Coord3D cent = new Coord3D(30,20,-10);
	Vector3D n = new Vector3D(0,0,1);
	double r = 20;
	Circle cir = new Circle(cent,r,n,Color.BLUE,0);
	shapes.add(cir);
	
	cent = new Coord3D(30,20,5);
	n = new Vector3D(1,0.4,0.2);
	r = 15;
	cir = new Circle(cent,r,n,Color.YELLOW,0.5);
	shapes.add(cir);
	
	cent = new Coord3D(30,-30,0);
	n = new Vector3D(-0.4,0.9,2);
	r = 10;
	double height = 20;
	addCylinder(cent,r,height,n,Color.CYAN,0);
		
	cent = new Coord3D(60,-30,35);
	n = new Vector3D(-0.5,1,0);
	r = 5;
	height = 20;
	addCappedCylinder(cent,r,height,n,Color.ORANGE,0);
	}
	
	public void mirrorRoom()
	{
	Color wallColor = new Color(150,100,150); //purpleish
	Color floorColor = new Color(100,50,0); //brown	
	Coord3D backLeftTop;
	Coord3D leftTop;
	int xWidth;
	int yWidth;
	int zWidth;
	double reflect = 0.8;
	
	//room:
	backLeftTop = new Coord3D(100,50,50);
	xWidth = 200;
	yWidth = 100;
	zWidth = 100;
	addRectPrism(backLeftTop,xWidth,yWidth,zWidth,floorColor,wallColor,reflect);	
	
	Coord3D cent = new Coord3D(70,-30,-30);
	double r = 20;
	addSphere(cent,r,Color.CYAN);
	}
	
	public void addXYQuad(Coord3D backLeft, double xWidth, double yWidth, Color col)
	{
	addXYQuad(backLeft, xWidth, yWidth, col, 0);	
	}
	
	public void addXYQuad(Coord3D backLeft, double xWidth, double yWidth, Color col, double reflectivity)
	{
	Coord3D[] coords = new Coord3D[4];
	coords[0] = backLeft;
	coords[1] = backLeft.moveXn(-xWidth);
	coords[2] = coords[1].moveYn(-yWidth);
	coords[3] = backLeft.moveYn(-yWidth);
	Quad3D qu = new Quad3D(coords,col,reflectivity);
	//quads.add(qu);
	shapes.add(qu);
	}
	
	public void addXZQuad(Coord3D backTop, double xWidth, double zWidth, Color col)
	{
	addXZQuad(backTop, xWidth, zWidth, col, 0);	
	}
	
	public void addXZQuad(Coord3D backTop, double xWidth, double zWidth, Color col, double reflectivity)
	{
	Coord3D[] coords = new Coord3D[4];
	coords[0] = backTop;
	coords[1] = backTop.moveXn(-xWidth);
	coords[2] = coords[1].moveZn(-zWidth);
	coords[3] = backTop.moveZn(-zWidth);
	Quad3D qu = new Quad3D(coords,col,reflectivity);
	//quads.add(qu);
	shapes.add(qu);
	}
	
	public void addYZQuad(Coord3D leftTop, double yWidth, double zWidth, Color col)
	{
	addYZQuad(leftTop, yWidth, zWidth, col, 0);
	}
	
	public void addYZQuad(Coord3D leftTop, double yWidth, double zWidth, Color col, double reflectivity)
	{
	Coord3D[] coords = new Coord3D[4];
	coords[0] = leftTop;
	coords[1] = leftTop.moveYn(-yWidth);
	coords[2] = coords[1].moveZn(-zWidth);
	coords[3] = leftTop.moveZn(-zWidth);
	Quad3D qu = new Quad3D(coords,col,reflectivity);
	//quads.add(qu);
	shapes.add(qu);
	}		
	
	public void addRectPrism(Coord3D backLeftTop, double xWidth, double yWidth, double zWidth, Color col)
	{	
	addRectPrism(backLeftTop,xWidth,yWidth,zWidth,col,col);
	}	
	
	public void addRectPrism(Coord3D backLeftTop, double xWidth, double yWidth, double zWidth, Color col, double reflectivity)
	{	
	addRectPrism(backLeftTop,xWidth,yWidth,zWidth,col,col,reflectivity);
	}
	
	public void addRectPrism(Coord3D backLeftTop, double xWidth, double yWidth, double zWidth, Color floorColor, Color wallColor)
	{
	addRectPrism(backLeftTop,xWidth,yWidth,zWidth,floorColor,wallColor,0);
	}
	
	public void addRectPrism(Coord3D backLeftTop, double xWidth, double yWidth, double zWidth, Color floorColor, Color wallColor, double reflectivity)
	{	
	//back
	addYZQuad(backLeftTop,yWidth,zWidth,wallColor,reflectivity);
	
	//left wall
	addXZQuad(backLeftTop,xWidth,zWidth,wallColor,reflectivity);
	
	//ceiling
	addXYQuad(backLeftTop,xWidth,yWidth,wallColor,reflectivity);
	
	//right wall
	Coord3D backRightTop = backLeftTop.moveYn(-yWidth);
	addXZQuad(backRightTop,xWidth,zWidth,wallColor,reflectivity);	
	
	//floor
	Coord3D backLeftBottom = backLeftTop.moveZn(-zWidth);
	addXYQuad(backLeftBottom,xWidth,yWidth,floorColor,reflectivity);		
	
	//front
	Coord3D frontLeftTop = backLeftTop.moveXn(-xWidth);
	addYZQuad(frontLeftTop,yWidth,zWidth,wallColor,reflectivity);
	
	repaint();
	}
	
	public void addCube(Coord3D center, int sideLength, Color col)
	{
	center.moveX(-(double)sideLength/2);
	center.moveY(-(double)sideLength/2);
	center.moveZ(-(double)sideLength/2);
	Coord3D bfr = center;
	Coord3D bbr = bfr.moveXn(sideLength);
	Coord3D tfr = bfr.moveZn(sideLength);
	Coord3D tbr = tfr.moveXn(sideLength);
	Coord3D bfl = bfr.moveYn(sideLength);
	Coord3D bbl = bfl.moveXn(sideLength);
	Coord3D tfl = bfl.moveZn(sideLength);
	Coord3D tbl = tfl.moveXn(sideLength);
	addRectPrism(tbl,tbr,tfl,tfr,bbl,bbr,bfl,bfr,col);
	}		
	
	public void addRectPrism(Coord3D tbl, Coord3D tbr, Coord3D tfl, Coord3D tfr,
						Coord3D bbl, Coord3D bbr, Coord3D bfl, Coord3D bfr, Color col)
	{
	// tbl = top back left, bfr = bottom front right (all relative to 1,0,0 viewpoint, so top has larger z value than bottom, front has lower x value than back, and
	// right has lower y value than left)
	
	//cubef
	Coord3D[] coords = new Coord3D[4];
	coords[0] = tfl;
	coords[1] = tfr;
	coords[2] = bfr;
	coords[3] = bfl;
	Quad3D qu = new Quad3D(coords,col);
	//quads.add(qu);
	shapes.add(qu);
	
	//cubeb
	coords = new Coord3D[4];
	coords[0] = tbl;
	coords[1] = tbr;
	coords[2] = bbr;
	coords[3] = bbl;
	qu = new Quad3D(coords,col);
	//quads.add(qu);
	shapes.add(qu);
	
	//cuber
	coords = new Coord3D[4];
	coords[0] = tfr;
	coords[1] = tbr;
	coords[2] = bbr;
	coords[3] = bfr;
	qu = new Quad3D(coords,col);
	//quads.add(qu);
	shapes.add(qu);
	
	//cubel
	coords = new Coord3D[4];
	coords[0] = tfl;
	coords[1] = tbl;
	coords[2] = bbl;
	coords[3] = bfl;
	qu = new Quad3D(coords,col);
	//quads.add(qu);
	shapes.add(qu);
	
	//cubet
	coords = new Coord3D[4];
	coords[0] = tfl;
	coords[1] = tfr;
	coords[2] = tbr;
	coords[3] = tbl;
	qu = new Quad3D(coords,col);
	//quads.add(qu);
	shapes.add(qu);
	
	//cubebot
	coords = new Coord3D[4];
	coords[0] = bfl;
	coords[1] = bfr;
	coords[2] = bbr;
	coords[3] = bbl;
	qu = new Quad3D(coords,col);
	//quads.add(qu);
	shapes.add(qu);
	
	repaint();
	}
	
	public void clearAll()
	{
	quads.removeAllElements();
	tris.removeAllElements();
	spheres.removeAllElements();
	shapes.removeAllElements();
	}
	
	public Coord3D getOrigin()
	{
	return originSave;
	}
	
	public Coord3D getLightSource()
	{
	return lightSource;
	}
	
	public int getHorizontalSliderValue()
	{
	double theta = direction.getTheta();
	int result = (int)(180*theta/Math.PI)%360;
	if(result > 180) result -= 360;
	return -result;
	}
	
	public int getVerticalSliderValue()
	{
	double theta = direction.angleVert();
	int result = (int)(180*theta/Math.PI);
	return result;
	}
	
	public void setOrthographicView()
	{
	viewType = ORTH;
	origin = originSave.copy();
	miniCanvas.setOrthographicView();
	repaint();
	}
	
	public void setFishEyeView()
	{
	viewType = FISHEYE;
	origin = originSave.copy();
	repaint();
	}
	
	public void setPinHoleView()
	{
	viewType = PINHOLE;
	origin = originSave.copy();
	miniCanvas.setPinHoleView();
	repaint();
	}
	
	public void setWhiteToBaseColor()
	{
	lightColorMode = 0;
	repaint();
	}
	
	public void setBaseColorToBlack()
	{
	lightColorMode = 1;
	
	repaint();
	}
	
	public void setNoDistLighting()
	{
	lightingMode = 0;
	repaint();
	}
	
	public void setSqrtLighting()
	{
	lightingMode = 1;
	//lightPower = 4;
	repaint();
	}
	
	public void setInverseSquareLighting()
	{
	lightingMode = 2;
	//lightPower = 4000;
	repaint();
	}
	
	public void setLowResolution()
	{
	res = 10;
	pixelsWide = boardWidth/res;
	pixelsHigh = boardHeight/res;
	repaint();
	}
	
	public void setMediumResolution()
	{
	res = 5;
	pixelsWide = boardWidth/res;
	pixelsHigh = boardHeight/res;
	repaint();
	}
	
	public void setHighResolution()
	{
	res = 2;
	pixelsWide = boardWidth/res;
	pixelsHigh = boardHeight/res;
	repaint();
	}
	
	public void setHighestResolution()
	{
	res = 1;
	pixelsWide = boardWidth/res;
	pixelsHigh = boardHeight/res;
	repaint();
	}
	
	public void setViewpoint(double x, double y, double z)
	{
	origin = new Coord3D(x,y,z);
	originSave = origin.copy();
	repaint();
	}
	
	public void setDirection(double x, double y, double z)
	{
	direction = new Vector3D(x,y,z);
	direction = direction.unit();
	System.out.println(direction);
	miniCanvas.setDirection(direction);
	repaint();
	}
	
	public Vector3D getDirection()
	{
	return direction;
	}
	
	public void setLightSource(Coord3D ls)
	{
	setLightSource(ls.getX(),ls.getY(),ls.getZ());
	}
	
	public void setLightSource(double x, double y, double z)
	{
	lightSourceStart = new Coord3D(x,y,z);
	lightSource = lightSourceStart.copy();
	lightSphere = new Sphere(lightSource,lightSphereRadius);
		
	repaint();
	}		
	
	public void setHorizontalView(int value)
	{
	double curTheta = direction.getTheta();
	direction.rotateXY(-(double)value*Math.PI/180-curTheta);
	miniCanvas.setDirection(direction);
	repaint();
	}
	
	public void setVerticalView(int value)
	{
	//double vertAng = direction.angleVert();
	//System.out.println(vertAng);
	//System.out.println(direction);
	double curTheta = direction.getTheta();
	direction = new Vector3D(1,0,0);
	direction.rotateVert(-(double)value*Math.PI/180);
	direction.rotateXY(curTheta);
	miniCanvas.setDirection(direction);
	//direction.rotateVert(-(double)value*Math.PI/180-vertAng);
	repaint();	
	}	
	
	public void trianglesTest()
	{
	direction = new Vector3D(1,0,0); // so theta = 0;
	origin = new Coord3D(0,0,0);	
	originSave = origin.copy();	
	//lightSource = new Coord3D(20,20,20);
	lightSource = new Coord3D(50,-25,0);
	
	Coord3D[] coords = new Coord3D[3];
	coords[0] = new Coord3D(100,50,-50);
	coords[1] = new Coord3D(75,50,0);
	coords[2] = new Coord3D(80,-50,25);
	Triangle3D tri = new Triangle3D(coords);
	tris.add(tri);
	
	coords = new Coord3D[3];
	coords[0] = new Coord3D(20,40,-30);
	coords[1] = new Coord3D(35,-20,50);
	coords[2] = new Coord3D(40,0,0);
	tri = new Triangle3D(coords);
	tris.add(tri);
	
	coords = new Coord3D[3];
	coords[0] = new Coord3D(100,50,-50);
	coords[1] = new Coord3D(-100,50,-50);
	coords[2] = new Coord3D(100,-50,-50);
	tri = new Triangle3D(coords);
	tris.add(tri);
	
	coords = new Coord3D[3];
	coords[0] = new Coord3D(100,50,-50);
	coords[1] = new Coord3D(-100,50,-50);
	coords[2] = new Coord3D(100,50,100);
	tri = new Triangle3D(coords);
	tris.add(tri);
	
	coords = new Coord3D[3];
	coords[0] = new Coord3D(100,-50,-50);
	coords[1] = new Coord3D(-100,-50,-50);
	coords[2] = new Coord3D(100,-50,100);
	tri = new Triangle3D(coords);
	tris.add(tri);
	
	coords = new Coord3D[3];
	coords[0] = new Coord3D(100,-50,100);
	coords[1] = new Coord3D(-100,50,100);
	coords[2] = new Coord3D(100,50,100);
	tri = new Triangle3D(coords);
	tris.add(tri);
	
	coords = new Coord3D[3];
	coords[0] = new Coord3D(100,-50,100);
	coords[1] = new Coord3D(100,50,-50);
	coords[2] = new Coord3D(100,50,100);
	tri = new Triangle3D(coords);
	tris.add(tri);
	
	coords = new Coord3D[3];
	coords[0] = new Coord3D(100,-50,-50);
	coords[1] = new Coord3D(100,50,-50);
	coords[2] = new Coord3D(100,-50,100);
	tri = new Triangle3D(coords);
	tris.add(tri);
	}
	
	public void mousePressed(MouseEvent e)
	{
	int pressX = e.getX();
	int pressY = e.getY();
	e.consume();
	}
	
	public void mouseReleased(MouseEvent e)
	{
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
	//System.out.println("pressed " + e.getKeyText(e.getKeyCode()) + ", with keyCode " + e.getKeyCode());
	
	if (e.getKeyCode() == 32) // 32 is space bar
	    {
		}
	
	if (e.getKeyCode() == 81) // 81 is "q"
	    {	
		//rotate left	
		direction.rotateXY(5*Math.PI/180);
		miniCanvas.setDirection(direction);
		japp.updateDirectionFields();				
		japp.updateViewSliders();	
		repaint();
		}
	
	if (e.getKeyCode() == 69) // 69 is "e"
	    {	
		//rotate right
		direction.rotateXY(-5*Math.PI/180);
		miniCanvas.setDirection(direction);
		japp.updateDirectionFields();			
		japp.updateViewSliders();	
		repaint();
		}
	
	if (e.getKeyCode() == 82) // 82 is "r"
	    {	
		//rotate up
		double curTheta = direction.getTheta();
		double curPhi = direction.angleVert();
		direction = new Vector3D(1,0,0);
		direction.rotateVert(-(curPhi+5*Math.PI/180));
		direction.rotateXY(curTheta);
		miniCanvas.setDirection(direction);
		japp.updateDirectionFields();			
		japp.updateViewSliders();	
		repaint();
		}
	
	if (e.getKeyCode() == 70) // 70 is "f"
	    {	
		//rotate down	
		double curTheta = direction.getTheta();
		double curPhi = direction.angleVert();
		direction = new Vector3D(1,0,0);
		direction.rotateVert(-(curPhi-5*Math.PI/180));
		direction.rotateXY(curTheta);
		miniCanvas.setDirection(direction);
		japp.updateDirectionFields();			
		japp.updateViewSliders();	
		repaint();
		}
	
	if (e.getKeyCode() == 69) // 69 is "e"
	    {
		}
		
	
	if (e.getKeyCode() == 61) // = and + key
	    {
		}

	if (e.getKeyCode() == 45) // - and _ key
	    {
	    }
	
	/*
	if (e.getKeyCode() == 37) moveLeft = true; // left arrow key
	if (e.getKeyCode() == 39) moveRight = true; // right arrow key
	if (e.getKeyCode() == 38) moveUp = true; // up arrow key
	if (e.getKeyCode() == 40) moveDown = true; // down arrow key
	*/
	if (e.getKeyCode() == 37 || e.getKeyCode() == 65) // 65 is "a" 
		{
		origin.moveLeft(direction,moveDist); // left arrow key
		originSave.moveLeft(direction,moveDist); // left arrow key
		japp.updateViewpointFields();
		repaint();
		}
	if (e.getKeyCode() == 39 || e.getKeyCode() == 68) // 68 is "d" 
		{
		origin.moveRight(direction,moveDist); // right arrow key
		originSave.moveRight(direction,moveDist); // right arrow key
		japp.updateViewpointFields();
		repaint();
		}
	if (e.getKeyCode() == 38 || e.getKeyCode() == 87) // 87 is "w"  
		{
		origin.moveForward(direction,moveDist); // up arrow key
		originSave.moveForward(direction,moveDist); // up arrow key
		japp.updateViewpointFields();
		repaint();
		}
	if (e.getKeyCode() == 40 || e.getKeyCode() == 83) // 83 is "s"  
		{
		origin.moveBackward(direction,moveDist); // down arrow key
		originSave.moveBackward(direction,moveDist); // down arrow key
		japp.updateViewpointFields();
		repaint();
		}
	if (e.getKeyCode() == 97) 
		{
		origin.moveZ(moveDist); // 1 numpad key
		originSave.moveZ(moveDist); // 1 numpad key
		japp.updateViewpointFields();
		repaint();
		}
	if (e.getKeyCode() == 96) 
		{
		origin.moveZ(-moveDist); // 0 numpad key
		originSave.moveZ(-moveDist); // 0 numpad key
		japp.updateViewpointFields();
		repaint();
		}
    }
	
	

    public void keyReleased(KeyEvent e)
    {
	
	/*
	// used to make paddle movement continuous
	if (e.getKeyCode() == 37) moveLeft = false; // left arrow key
	if (e.getKeyCode() == 39) moveRight = false; // right arrow key
	if (e.getKeyCode() == 38) moveUp = false; // up arrow key
	if (e.getKeyCode() == 40) moveDown = false; // down arrow key
	*/
    }
	
	public void rotateView()
    {
	
	}	
	
	
	public void update(Graphics g)
    {
	if (!buffer.equals(null)) 
		{
		bufG = buffer.getGraphics();
		//bufG.setColor(Color.BLACK);
		bufG.setColor(Color.WHITE);
		bufG.fillRect(0,0,boardWidth,boardHeight);
		bufG.fillRect(0,0,boardWidth,boardHeight);
		bufG.dispose();
		
		paint(g);	
		g.dispose();
		}
	}
	
	
	public void paint(Graphics g)
	{
	if (!buffer.equals(null)) 
		{
			bufG = buffer.getGraphics();
			
			//bufG.setColor(Color.BLACK);
			bufG.setColor(Color.WHITE);
			bufG.fillRect(0,0,boardWidth,boardHeight);
				
			Vector3D vCur;	
			if(direction != null)
				{
				for(int i=0; i<pixelsWide; i++)
					{
					for(int j=0; j<pixelsHigh; j++)
						{					// arctan( sqrt(xi^2 + yi^2))
						int firstHit = -2;
						double xi = Math.tan(apertureX)*(-1 + 2*(double)i/(pixelsWide-1));
						double yi = Math.tan(apertureY)*(-1 + 2*(double)j/(pixelsHigh-1));
						double theta = Math.atan(Math.sqrt(xi*xi + yi*yi));
						//double theta = (Math.sqrt(xi*xi + yi*yi)); // fisheye lensish
						double phi = Math.atan2(yi,xi);
						if(phi<0) phi += 2*Math.PI;
						phi -= Math.PI/2;
						vCur = direction.copy();
						origin = originSave.copy();
						if(viewType == FISHEYE)
							{
							vCur.rotateVert(theta);
							vCur.rotateAbout(direction,phi);
							}
						else if(viewType == ORTH) // orthographic
							{
							origin = originSave.copy();
							Vector3D dS = new Vector3D(0,-xi,-yi);
							dS.scalarMult(viewFactor);
							dS.rotateXZ(-vCur.angleVert());
							dS.rotateXY(vCur.getTheta());
							//System.out.println("xi " + xi + ", " + "yi " + yi);
							//System.out.println(dS);
							origin.plus(dS);
							//System.out.println("origin: " + origin);
							//System.out.println("vCur: " + vCur);
							//System.out.println(vCur.angleVert());
							}
						else if(viewType == PINHOLE)
							{
							Coord3D filmPt = originSave.copy();
							filmPt = direction.unit().parametric(filmPt,-pinHoleDist); // move along direction back to plane of screen
							Vector3D dS = new Vector3D(0,-filmWidth/2+(double)(i*filmWidth/(pixelsWide-1)),
														 -filmHeight/2+(double)(j*filmHeight/(pixelsHigh-1))); 
														 //overall minus sign to undo upside down and mirror image effect
							dS.rotateXZ(-vCur.angleVert());
							dS.rotateXY(vCur.getTheta());
							filmPt.plus(dS);
							vCur = new Vector3D(filmPt,origin);
							}
				
						//System.out.println("i = " + i + "; j = " + j);						
						//System.out.println("xi = " + xi + "; yi = " + yi);
						//System.out.println("vCur = " + vCur);
						//System.out.println("nShapes = " + shapes.size());
						
						boolean hitLight = false;
						Coord3D intersectionPoint;		
						int nReflection = 0;
						boolean hasReflection = true;
						Vector<Double> lightIntensities = new Vector<Double>();
						Vector<Color> colors = new Vector<Color>();
						double currentFraction = 1;
						
						while(hasReflection && nReflection <= maxReflections)
						{
						vCur = vCur.unit();						
						double minDist = -1;
						int minK = -1;
						
						hasReflection = false;
						for(int k=0; k<shapes.size(); k++)
							{							
							intersectionPoint = shapes.get(k).intersection(vCur,origin);
							if(intersectionPoint != null)
								{
								// find which point intersects closest
								double dist = intersectionPoint.dist(origin);
								if(minDist == -1) minDist = dist+1;
								if(dist < minDist)
									{
									minDist = dist;
									minK = k;
									}
								}
							}
						intersectionPoint = lightSphere.intersection(vCur,origin);
						if(intersectionPoint != null)
							{
							// find which point intersects closest
							double dist = intersectionPoint.dist(origin);
							if(minDist == -1) minDist = dist+1;
							if(dist < minDist)
								{
								minDist = dist;
								minK = -1;
								hitLight = true;
								}
							}
						//System.out.println(minK);
							
						if(minK != -1)
							{
							if(firstHit == -2) firstHit = minK;
							//System.out.println(shapes.get(minK));
							intersectionPoint = shapes.get(minK).intersection(vCur,origin);
							//System.out.println(intersectionPoint);
							//if(shapes.get(minK).type().equals("Cylinder")) System.out.println(intersectionPoint);
							Vector3D lightRay = new Vector3D(intersectionPoint,lightSource);
							lightRay = lightRay.unit();		
							if(shapes.get(minK).reflectivity() > 0) hasReflection = true;
							
							boolean inShadow = false;
							Vector3D unitNormal = shapes.get(minK).unitNormal(intersectionPoint);
							double dt = -0.001;
							if(vCur.opposite(unitNormal)) dt = -dt;
							Coord3D nearIntersection = unitNormal.parametric(intersectionPoint,dt);
							for(int k=0; k<shapes.size(); k++)
								{	
								Coord3D shadowInt = shapes.get(k).intersection(lightRay,nearIntersection);
								if(shadowInt != null && intersectionPoint.dist(shadowInt) < intersectionPoint.dist(lightSource))
									{
									inShadow = true;
									break;
									}
								}
							
							/* //Old shadows
							if(shapes.get(minK).type().equals("Sphere") || shapes.get(minK).type().equals("Cylinder"))
								{
								Coord3D sphereInt = shapes.get(minK).intersection(lightRay.reverse(),lightSource);
								if(sphereInt != null && !sphereInt.equals(intersectionPoint)) inShadow = true;
								}
							
							
							for(int k=0; k<shapes.size(); k++)
								{		
								Coord3D intersectionCur = shapes.get(k).intersection(lightRay,intersectionPoint);						
								if(k != minK && intersectionCur != null)
									{
									//check if intersection is closer than intersection point
									if(intersectionCur.dist(lightSource) < intersectionPoint.dist(lightSource)
										&& Math.abs(intersectionCur.dist(lightSource)
										+ intersectionPoint.dist(intersectionCur))
										- intersectionPoint.dist(lightSource) < 0.00001)
										{
										inShadow = true;
										break;
										}
									}								
								}
								*/
							
							unitNormal = shapes.get(minK).unitNormal(intersectionPoint);
							double lightIntensity;
							if(inShadow) lightIntensity = minIntensity;
							else
								{
								double lightRayDist = lightSource.dist(intersectionPoint);
								if(lightingMode == 0) lightIntensity = Math.abs(unitNormal.dot(lightRay)); // no distance dependence
								else if(lightingMode == 1) lightIntensity = lightPower*Math.abs(unitNormal.dot(lightRay))/(Math.sqrt(lightRayDist)); // square root
								else if(lightingMode == 2) lightIntensity = lightPowerSq*Math.abs(unitNormal.dot(lightRay))/(lightRayDist*lightRayDist); // inverse square law (extreme)
								else lightIntensity = 0;
								
								//
								if(lightIntensity > 1) lightIntensity = 1;
								if(lightIntensity < minIntensity) lightIntensity = minIntensity;
								}								
							lightIntensity = lightIntensity*(1-shapes.get(minK).reflectivity())*currentFraction;
							lightIntensities.add(lightIntensity);
							Color col = shapes.get(minK).getColor();
							colors.add(col);
							
							if(hasReflection)
								{
								currentFraction = shapes.get(minK).reflectivity()*currentFraction;
								nReflection++;
								double ds = 0.01;
								if(vCur.angle(unitNormal) < Math.PI/2 || vCur.angle(unitNormal) > 3*Math.PI/2) ds = -ds;
								//System.out.println(unitNormal);
								//System.out.println(vCur.angle(unitNormal));
								origin = unitNormal.parametric(intersectionPoint,ds);
								vCur = vCur.reflect(unitNormal);
								}
							}
						else if(hitLight)
							{
							intersectionPoint = lightSphere.intersection(vCur,origin);
							double th = (new Vector3D(lightSphere.getCenter(),intersectionPoint)).angle(new Vector3D(0,0,1));
							double minLightIntensity = 0.3;
							double lightIntensity = Math.cos(th/2)*(1-minLightIntensity)+minLightIntensity;
							lightIntensity = lightIntensity*currentFraction;	
								
							lightIntensities.add(lightIntensity);
							colors.add(lightColor);
							}
						} //end while loop for bounces			
												
						if(lightIntensities.size() != colors.size()) System.out.println("Error, colors and lightInt don't match up");
						//System.out.println("colors: " + colors.size());			
						if(lightColorMode == 1) 
							{
							double redSum = 0;
							double greenSum = 0;
							double blueSum = 0;
							for(int k=0; k<lightIntensities.size(); k++)
								{
								redSum += lightIntensities.get(k)*colors.get(k).getRed();
								greenSum += lightIntensities.get(k)*colors.get(k).getGreen();
								blueSum += lightIntensities.get(k)*colors.get(k).getBlue();
								/*
								System.out.println("lightIn, red, green, blue: ");
								System.out.println(lightIntensities.get(k));
								System.out.println(colors.get(k).getRed());
								System.out.println(colors.get(k).getGreen());
								System.out.println(colors.get(k).getBlue());
								*/
								}		
							int red = (int)redSum;
							int green = (int)greenSum;
							int blue = (int)blueSum;	
							if(firstHit == selectedRemoveShape)
								{
								red = 255 - red;
								green = 255 - green;
								blue = 255 - blue;
								}
							bufG.setColor(new Color(red,green,blue));
							if(colors.size() == 0) bufG.setColor(Color.WHITE);
							}
						else if(lightColorMode == 0)
							{
							if(colors.size() == 0) bufG.setColor(Color.WHITE);
							else
								{
								int red = colors.get(0).getRed();
								int green = colors.get(0).getGreen();
								int blue = colors.get(0).getBlue();
								
								int antiRed = 255-red;
								int antiGreen = 255-green;
								int antiBlue = 255-blue;
								double lightIntensity = lightIntensities.get(0);
								bufG.setColor(new Color((int)(255-antiRed*(1-lightIntensity)),(int)(255-antiGreen*(1-lightIntensity)),(int)(255-antiBlue*(1-lightIntensity))));
								/*
								for(int k=0; k<lightIntensities.size(); k++)
									{
									antiRedSum -= (1-lightIntensities.get(k))*(255-colors.get(k).getRed());
									antiGreenSum -= (1-lightIntensities.get(k))*(255-colors.get(k).getGreen());
									antiBlueSum -= (1-lightIntensities.get(k))*(255-colors.get(k).getBlue());
									}	
								
								bufG.setColor(new Color(255-(int)antiRedSum,255-(int)antiGreenSum,255-(int)antiBlueSum));
								*/
								}
							}
						else 
							{
							bufG.setColor(new Color(255,0,0));
							System.out.println("Error, no lightColorMode selected");
							}
						
						bufG.fillRect(i*res,j*res,res,res);
						
						}
					}			
								
				}
					
			g.drawImage(buffer, 0, 0, this);
			g.dispose();
				
		}
		
	}
	
	
}