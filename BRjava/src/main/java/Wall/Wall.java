package Wall;

import java.awt.Color;
import java.awt.Graphics;

import Vectors.Vector;

public class Wall{
	private Vector pos;//Position of top-left corner
	private double width;
	private double height;
	
	public Wall(double x, double y, int w, int h) {
		this.pos = new Vector(x, y);
		this.width = w;
		this.height = h;
	}
	
	public Vector getPos() {
		return this.pos.copy();
	}
	
	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public void show(Graphics g) {
        final int x = (int)this.pos.getX();
        final int y = (int)this.pos.getY();
        
        g.setColor(Color.BLACK);
        g.fillRect(x, y, (int)this.width, (int)this.height);
	}
	
	public Vector getClosestPoint(Vector other) {
		double closestX = Math.max(this.pos.getX(), Math.min(other.getX(), this.pos.getX()+this.width));
		double closestY = Math.max(this.pos.getY(), Math.min(other.getY(), this.pos.getY()+this.height));
		
		return new Vector(closestX, closestY);
	}
	
	public boolean isPointInside(Vector point) {
		return (this.getClosestPoint(point).getX()==point.getX() && this.getClosestPoint(point).getY() == point.getY());
	}
}
