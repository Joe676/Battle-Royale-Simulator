package Vectors;

public class Vector {
	private double x, y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector() {
		this(0, 0);
	}
	
	public void fromAngle(double radius, double angle) {//angle in radians
	    this.x = Math.cos((angle)) * radius;
	    this.y = Math.sin((angle)) * radius;
	}
	
	public static double pointDistance(Vector a, Vector b) {
		a = a.copy();
		b = b.copy();
		a.sub(b);
		return a.magnitude();
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void sub(Vector v){
		this.x -= v.getX();
		this.y -= v.getY();
	}
	
	public void add(Vector v){
		this.x += v.getX();
		this.y += v.getY();
	}
	
	public void mult(double n){
		this.x *= n;
		this.y *= n;
	}
	
	public double magnitude() {
		return Math.sqrt(this.x*this.x+this.y*this.y);
	}
	
	public double magnitude_squared() {
		return this.x*this.x+this.y*this.y;
	}
	
	public void normalize() {
		double l = this.magnitude();
		if(l != 0) {
			this.x /= l;
			this.y /= l;
		}
	}
	
	public void constrain(double max) {
		if(this.magnitude()>max) {
			this.normalize();
			this.mult(max);
		}
	}
	
	public double dotProduct(Vector other) {
		return this.x*other.getX()+this.y*other.getY();
	}
	
	public double angleToVector(Vector other) {
		double thisAngle = Math.atan2(this.y, this.x);
		double otherAngle = Math.atan2(other.getY(), other.getX());
		return otherAngle-thisAngle;
	}
	
	public Vector copy() {
		return new Vector(this.x, this.y);
	}
}
