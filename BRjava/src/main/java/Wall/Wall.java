package Wall;

import java.awt.Color;
import java.awt.Graphics;

import Vectors.Vector;

/**
 * Class representing an obstacle
 * @author Jozef Bossowski
 */
public class Wall{
	private Vector pos;//Position of top-left corner
	private double width;
	private double height;
	
	/**
	 * Constructs and initializes Wall at specified point with specified dimensions
	 * @param x X coordinate of the top-left corner
	 * @param y Y coordinate of the top-left corner
	 * @param w Width of the wall
	 * @param h Height of the wall
	 */
	public Wall(double x, double y, int w, int h) {
		this.pos = new Vector(x, y);
		this.width = w;
		this.height = h;
	}
	
	/**
	 * 
	 * @return Position of the top-left corner
	 */
	public Vector getPos() {
		return this.pos.copy();
	}
	 /**
	  * 
	  * @return Width of the wall
	  */
	public double getWidth() {
		return this.width;
	}
	
	/**
	 * 
	 * @return Height of the wall
	 */
	public double getHeight() {
		return this.height;
	}
	
	/**
	 * Draws the wall on the canvas 
	 * @param g Where it's to be drawn
	 */
	public void show(Graphics g) {
        final int x = (int)this.pos.getX();
        final int y = (int)this.pos.getY();
        
        g.setColor(Color.BLACK);
        g.fillRect(x, y, (int)this.width, (int)this.height);
	}
	
	/**
	 * Returns a point on the wall that's the closest to the specified point
	 * @param other Outside point 
	 * @return Point closest to the outside point
	 */
	public Vector getClosestPoint(Vector other) {
		double closestX = Math.max(this.pos.getX(), Math.min(other.getX(), this.pos.getX()+this.width));
		double closestY = Math.max(this.pos.getY(), Math.min(other.getY(), this.pos.getY()+this.height));
		
		return new Vector(closestX, closestY);
	}
	
	/**
	 * Checks whether specified point is inside the wall
	 * @param point Point to check
	 * @return true if Point inside wall, false otherwise
	 */
	public boolean isPointInside(Vector point) {
		return (this.getClosestPoint(point).getX()==point.getX() && this.getClosestPoint(point).getY() == point.getY());
	}
}
