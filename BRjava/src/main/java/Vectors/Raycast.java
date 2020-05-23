package Vectors;

import java.util.ArrayList;
import java.util.List;

import Agents.Agent;
import Wall.Wall;

public class Raycast {
	private static Vector castOnSegment(Vector from, Vector dir, Vector A, Vector B) {//returns ray's intersection with a segment, null if no intersection is found
		//https://www.youtube.com/watch?v=TOEi6T2mtHo
		dir.add(from);
		
		double denominator = (A.getX() - B.getX()) * (from.getY() - dir.getY()) - (A.getY() - B.getY()) * (from.getX() - dir.getX());
		if(denominator == 0) {
			return null;
		}
		
		double t = ((A.getX() - from.getX())*(from.getY() - dir.getY()) - (A.getY() - from.getY())*(from.getX() - dir.getX())) / denominator;
		double u = -((A.getX() - B.getX())*(A.getY() - from.getY()) - (A.getY() - B.getY())*(A.getX() - from.getX())) / denominator;
		if(t>0 && t<1 && u>0) {
			double x = A.getX() + t*(B.getX()-A.getX());
			double y = A.getY() + t*(B.getY()-A.getY()); 
			
			return new Vector(x, y);
		}
		
		return null;
	}
	
	public static Vector cast(Vector from, Vector dir, Wall wall) {//returns closest intersection with a wall
		//create a list of wall faces - segments
		List<Vector> points = new ArrayList<Vector>();
		points.add(wall.getPos());
		points.add(new Vector(wall.getPos().getX()+wall.getWidth(), wall.getPos().getY()));
		points.add(new Vector(wall.getPos().getX()+wall.getWidth(), wall.getPos().getY()+wall.getHeight()));
		points.add(new Vector(wall.getPos().getX()				  , wall.getPos().getY()+wall.getHeight()));
		
		List<Vector> intersections = new ArrayList<Vector>();
		Vector closestPoint = null;
		double minD = 9999999;
		
		for(int i = 0; i < 4; i++) {	
			intersections.add(castOnSegment(from.copy(), dir.copy(), points.get(i).copy(), points.get((i+1)%4).copy()));
			if(intersections.get(i) != null) {
				Vector temp = intersections.get(i).copy();
				temp.sub(from);
				double d = temp.magnitude();
				if(d < minD) {
					minD = d;
					closestPoint = intersections.get(i);
				}
			}
		}
		
		return closestPoint;
	}
	
	public static boolean cast(Vector from, Vector dir, Agent agent) {//checks whether agent got hit by a raycast (for shooting)
		//https://mathworld.wolfram.com/Circle-LineIntersection.html

		//http://www.algorytm.org/geometria-obliczeniowa/rzutowanie-punktu-prostopadle-na-linie.html
		Vector diff = agent.getCENTER();
		diff.sub(from);
		double u = dir.dotProduct(diff)/dir.dotProduct(dir);
		
		if(u>0) {
			diff = dir.copy();
			diff.mult(u);
			diff.add(from);
			if( pointCircle(diff, agent.getCENTER(), Agent.getR()/2)) {
				//System.out.println("Rzut "+diff.getX()+" "+diff.getY()+ " punktu " + agent.getCENTER().getX()+" "+agent.getCENTER().getY());
				diff.sub(agent.getCENTER());
				//System.out.println("odleg�o�� = "+diff.magnitude());
				return true;
			}
		}
		return false;
		
		//return false;
	}
	
	private static boolean pointCircle(Vector pt, Vector c, double r) {
		Vector diff = pt.copy();
		diff.sub(c);
		return diff.magnitude_squared()<r*r;
	}
}
