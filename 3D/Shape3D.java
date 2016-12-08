import java.awt.*;

public interface Shape3D {
	
	public boolean intersectsRay(Vector3D v, Coord3D cc);
	public Coord3D intersection(Vector3D v, Coord3D cc); //returns null if no intersection exists
	public Color getColor();
	public String type();	
	public Vector3D unitNormal(Coord3D intersectionPoint);
	public double reflectivity();
	}