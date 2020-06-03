package Item;

import java.awt.Color;
import java.awt.Graphics;

import Agents.Agent;
import Vectors.Vector;

/**
 * Class representing a rifle
 * @author Jozef Bossowski
 *
 */
public class Rifle extends Weapon{
	/**
	 * Constructs and initializes rifle owned by an agent
	 * @param owner Agent whose weapon this is
	 */
	public Rifle(Agent owner) {
		super(owner);
		this.setDamage(4);
		
		this.setMagSize(15);
		this.setCurrentBullets(15);
		
		this.setReloadTime(20);
		this.setShootingInterval(1);
	
		int[] spread = {0};
		this.setBulletSpread(spread);
	}

	/**
	 * Constructs and initializes a rifle at a specified point of the map
	 * @param x X coordinate of the point
	 * @param y Y coordinate of the point
	 */
	public Rifle(double x, double y) {
		this(null);
		this.setPos(new Vector(x, y));
	}

	@Override
	public void show(Graphics g) {
		g.setColor(new Color(153, 102, 0));
		g.fillRect((int)this.getPos().getX(), (int)this.getPos().getY(), 10, 3);
		g.fillRect((int)this.getPos().getX(), (int)this.getPos().getY(), 2, 5);
		g.fillRect((int)(this.getPos().getX()+4), (int)this.getPos().getY(), 2, 5);
	}
	
	@Override
	public String toString() {
		return "Rifle";
	}
}
