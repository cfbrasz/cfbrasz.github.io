import java.util.Comparator;

public class RobotComparator implements Comparator<Robot> {

    public RobotComparator() {
	
    }

    public int compare(Robot a, Robot b) 
    {
	double result = a.angle - b.angle;
	if(result < 0) return -1;
	if(result > 0) return 1;
	return 0;
    }
}