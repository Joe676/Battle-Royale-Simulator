package Item;

import java.awt.Color;
import java.awt.Graphics;

import Agents.Agent;
import Vectors.Vector;

/**
 * Class representing a kind of weapon
 * @author Jozef Bossowski
 *
 */
public class Pistol extends Weapon {

	/**
	 * Constructs and initializes a pistol owned by an agent
	 * @param owner Agent whose weapon this is
	 */
	public Pistol(Agent owner) {
		super(owner);
		this.setDamage(6);
		
		this.setMagSize(6);
		this.setCurrentBullets(6);
		
		this.setReloadTime(10);
		this.setShootingInterval(4);
	
		int[] spread = {0};
		this.setBulletSpread(spread);
	}
	
	/**
	 * Constructs and initializes pistol at a specified point on the map
	 * @param x X coordinate of the point
	 * @param y Y coordinate of the point
	 */
	public Pistol(double x, double y) {
		this(null);
		this.setPos(new Vector(x, y));
	}

	@Override
	public void show(Graphics g) {
		g.setColor(new Color(153, 102, 0));
		g.fillRect((int)this.getPos().getX(), (int)this.getPos().getY(), 5, 2);
		g.fillRect((int)this.getPos().getX(), (int)this.getPos().getY(), 2, 4);
	}
	
	@Override
	public String toString() {
		return "Pistol";
	}
}
