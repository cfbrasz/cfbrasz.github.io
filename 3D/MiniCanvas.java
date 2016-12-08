import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.awt.image.BufferedImage;
import java.lang.Math;
import javax.swing.JApplet;

public class MiniCanvas extends Canvas {

	private int boardWidth;
	private int boardHeight;
	private BufferedImage buffer;
	private Graphics bufG;
	private Vector<Shape3D> shapes = new Vector<Shape3D>();
	private Vector3D direction;
	private Vector3D directionStart;
	private double viewSphereRadius = 20;
	private Coord3D originStart;
	private Coord3D origin;
	private Coord3D originSave;
	private int res = 2; // divide boardWidth/Height by res to get number of rays per row/column that we shoot
	private int pixelsWide;
	private int pixelsHigh;
	private double apertureX = Math.PI/3;
	private double apertureY = Math.PI/3;
	private PolyhedraApplet japp;
	private double pinHoleDist = 1;
	private double filmConst = 1.154700538;
	private double filmWidth = filmConst;
	private double filmHeight = filmConst;
	public final int FISHEYE = 0;
	public final int ORTH = 1;
	public final int PINHOLE = 2;
	//private int viewType = FISHEYE;
	private int viewType = PINHOLE;
	private double minIntensity = 0.1;
	private double viewFactor = 6; // for orth view
	private Sphere sph;

	public MiniCanvas(int boardWidth, int boardHeight, PolyhedraApplet japp)
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
	this.japp = japp;	
	setup();
	}	
	
	
	public void reset()
	{ 
	direction = directionStart.copy(); 
	origin = originStart.copy();
	originSave = origin.copy();
	repaint();
	}
	
	public void setup()
	{
		axes();
		sph = new Sphere(Coord3D.ORIGIN,viewSphereRadius,Color.RED,0);
		directionStart = new Vector3D(1,0,0); 
		direction = directionStart.copy();
		originStart = new Coord3D(-viewSphereRadius,0,0);
		origin = originStart.copy();
		originSave = origin.copy();
		repaint();
	}
	
	
	
	public void axes()
	{	
	Color xColor = Color.RED;
	Color yColor = Color.GREEN;
	Color zColor = Color.BLUE;
	Coord3D backLeftTop;
	double xWidth;
	double yWidth;
	double zWidth;
	double shortW = 1;
	double arrowW = 2;
	double arrowL = 8;
	
	//x axis:	
	backLeftTop = new Coord3D(arrowL,shortW/2,shortW/2);
	xWidth = arrowL;
	yWidth = shortW;	
	zWidth = shortW;
	addRectPrism(backLeftTop, xWidth, yWidth, zWidth, xColor);
	
	Coord3D[] coords = new Coord3D[3];
	coords[0] = new Coord3D(arrowL+2*arrowW,0,0);
	coords[1] = new Coord3D(arrowL,arrowW,0);
	coords[2] = new Coord3D(arrowL,-arrowW,0);
	Triangle3D tri = new Triangle3D(coords,xColor);
	shapes.add(tri);
	coords = new Coord3D[3];
	coords[0] = new Coord3D(arrowL+2*arrowW,0,0);
	coords[1] = new Coord3D(arrowL,0,arrowW);
	coords[2] = new Coord3D(arrowL,0,-arrowW);
	tri = new Triangle3D(coords,xColor);
	shapes.add(tri);
	//addXYQuad(backLeft,xWidth,yWidth,xColor);
	
	//y axis
	backLeftTop = new Coord3D(shortW/2,arrowL,shortW/2);
	xWidth = shortW;
	yWidth = arrowL;
	zWidth = shortW;
	addRectPrism(backLeftTop, xWidth, yWidth, zWidth, yColor);
	
	coords = new Coord3D[3];
	coords[0] = new Coord3D(0,arrowL+2*arrowW,0);
	coords[1] = new Coord3D(0,arrowL,arrowW);
	coords[2] = new Coord3D(0,arrowL,-arrowW);
	tri = new Triangle3D(coords,yColor);
	shapes.add(tri);
	coords = new Coord3D[3];
	coords[0] = new Coord3D(0,arrowL+2*arrowW,0);
	coords[1] = new Coord3D(arrowW,arrowL,0);
	coords[2] = new Coord3D(-arrowW,arrowL,0);
	tri = new Triangle3D(coords,yColor);
	shapes.add(tri);
	//addXYQuad(backLeft,xWidth,yWidth,yColor);
	
	//z axis
	backLeftTop = new Coord3D(shortW/2,shortW/2,arrowL);
	xWidth = shortW;
	yWidth = shortW;
	zWidth = arrowL;
	addRectPrism(backLeftTop, xWidth, yWidth, zWidth, zColor);
	
	coords = new Coord3D[3];
	coords[0] = new Coord3D(0,0,arrowL+2*arrowW);
	coords[1] = new Coord3D(0,arrowW,arrowL);
	coords[2] = new Coord3D(0,-arrowW,arrowL);
	tri = new Triangle3D(coords,zColor);
	shapes.add(tri);	
	coords = new Coord3D[3];
	coords[0] = new Coord3D(0,0,arrowL+2*arrowW);
	coords[1] = new Coord3D(arrowW,0,arrowL);
	coords[2] = new Coord3D(-arrowW,0,arrowL);
	tri = new Triangle3D(coords,zColor);
	shapes.add(tri);
	//addXYQuad(backLeft,xWidth,yWidth,yColor);
	//addYZQuad(leftTop,yWidth,zWidth,zColor);
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
	
	
	public void clearAll()
	{
	shapes.removeAllElements();
	}
	
	public Coord3D getOrigin()
	{
	return originSave;
	}
	
	public void setOrthographicView()
	{
	viewType = ORTH;
	origin = originSave.copy();
	repaint();
	}
	
	public void setPinHoleView()
	{
	viewType = PINHOLE;
	origin = originSave.copy();
	repaint();
	}
	
	public void setViewpoint(double x, double y, double z)
	{
	origin = new Coord3D(x,y,z);
	originSave = origin.copy();
	repaint();
	}
	
	public void setViewpoint(Coord3D origin)
	{
	this.origin = origin;
	originSave = origin.copy();
	repaint();
	}
	
	public void setDirection(Vector3D dir)
	{
	//System.out.println(direction);
	direction = dir;
	//System.out.println(dir);
	Vector3D rev = dir.reverse();
	Coord3D newOrigin = rev.parametric(Coord3D.ORIGIN,viewSphereRadius);
	//System.out.println(originSave);
	//System.out.println(newOrigin);
	setViewpoint(newOrigin);
	repaint();
	}
	
	public Vector3D getDirection()
	{
	return direction;
	}
	
	public void setHorizontalView(int value)
	{
	double curTheta = direction.getTheta();
	direction.rotateXY(-(double)value*Math.PI/180-curTheta);
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
	//direction.rotateVert(-(double)value*Math.PI/180-vertAng);
	repaint();	
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
							
							
						if(minK != -1)
							{
							if(firstHit == -2) firstHit = minK;
							//System.out.println(shapes.get(minK));
							intersectionPoint = shapes.get(minK).intersection(vCur,origin);
							//System.out.println(intersectionPoint);
							//if(shapes.get(minK).type().equals("Cylinder")) System.out.println(intersectionPoint);
							
							boolean inShadow = false;
							double lightIntensity;
							if(inShadow) lightIntensity = minIntensity;
							else lightIntensity = 1;						
							//lightIntensity = lightIntensity*(1-shapes.get(minK).reflectivity())*currentFraction;
							lightIntensities.add(lightIntensity);
							Color col = shapes.get(minK).getColor();
							colors.add(col);							
							
							}		
												
						if(lightIntensities.size() != colors.size()) System.out.println("Error, colors and lightInt don't match up");
						//System.out.println("colors: " + colors.size());		
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
							bufG.setColor(new Color(red,green,blue));
							if(colors.size() == 0) bufG.setColor(Color.WHITE);
						
						bufG.fillRect(i*res,j*res,res,res);
						
						}
					}			
								
				}
					
			g.drawImage(buffer, 0, 0, this);
			g.dispose();
				
		}
		
	}
	
	
}