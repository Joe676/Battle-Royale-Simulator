package Vectors;

/**
 * Utility class representing vectors and points (radius vectors)
 * @author Jozef Bossowski
 *
 */
public class Vector {
	private double x, y;
	
	/**
	 * Constructs and initializes a vector with coords (x, y)
	 * @param x X coordinate of the vector
	 * @param y Y coordinate of the vector
	 */
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructs and initializes vector (0, 0)
	 */
	public Vector() {
		this(0, 0);
	}
	
	/**
	 * Sets cartesian coordinates based on polar coordinates provided 
	 * @param radius Length of the vector
	 * @param angle Angle between the vector and X-axis
	 */
	public void fromAngle(double radius, double angle) {//angle in radians
	    this.x = Math.cos((angle)) * radius;
	    this.y = Math.sin((angle)) * radius;
	}
	
	/**
	 * Static method calculating distance between two points (represented by radius vectors)
	 * @param a
	 * @param b
	 * @return
	 */
	public static double pointDistance(Vector a, Vector b) {
		a = a.copy();
		b = b.copy();
		a.sub(b);
		return a.magnitude();
	}
	
	/**
	 * 
	 * @return X coordinate
	 */
	public double getX() {
		return this.x;
	}
	 /**
	  * 
	  * @return Y coordinate
	  */
	public double getY() {
		return this.y;
	}
	
	/**
	 * 
	 * @param x New x coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * 
	 * @param y New Y coordinate
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Subtracts given vector from this one (in place)
	 * @param v Vector to subtract
	 */ 
	public void sub(Vector v){
		this.x -= v.getX();
		this.y -= v.getY();
	}
	
	/**
	 * Adds given vector to this one (in place)
	 * @param v Vector to be added
	 */
	public void add(Vector v){
		this.x += v.getX();
		this.y += v.getY();
	}
	
	/**
	 * Multiplies this vector by a number (in place)
	 * @param n Scale
	 */
	public void mult(double n){
		this.x *= n;
		this.y *= n;
	}
	
	/**
	 * Calculates length of the vector
	 * @return Length of the vector
	 */
	public double magnitude() {
		return Math.sqrt(this.x*this.x+this.y*this.y);
	}
	
	/**
	 * Calculates square of the length of the vector, thus omitting rooting which is more optimal
	 * @return Length of the vector squared
	 */
	public double magnitude_squared() {
		return this.x*this.x+this.y*this.y;
	}
	
	/**
	 * Changes the vector to be of length 1, without changing its angle
	 */
	public void normalize() {
		double l = this.magnitude();
		if(l != 0) {
			this.x /= l;
			this.y /= l;
		}
	}
	
	/**
	 * Shortens the vector if it is longer than given value
	 * @param max Maximum length of the vector
	 */
	public void constrain(double max) {
		if(this.magnitude()>max) {
			this.normalize();
			this.mult(max);
		}
	}
	
	/**
	 * Multiplies two vectors together
	 * @param other Second vector for multiplication
	 * @return Dot product of this and other vectors
	 */
	public double dotProduct(Vector other) {
		return this.x*other.getX()+this.y*other.getY();
	}
	
	/**
	 * Calculates angle from this vector to given
	 * @param other Vector to which angle is calculated
	 * @return Angle to vector in radians
	 */
	public double angleToVector(Vector other) {
		double thisAngle = Math.atan2(this.y, this.x);
		double otherAngle = Math.atan2(other.getY(), other.getX());
		return otherAngle-thisAngle;
	}
	
	/**
	 * 
	 * @return Copy of this vector
	 */
	public Vector copy() {
		return new Vector(this.x, this.y);
	}
	
	@Override
	public String toString() {
		return "("+this.x+", "+this.y+")";
	}
}
