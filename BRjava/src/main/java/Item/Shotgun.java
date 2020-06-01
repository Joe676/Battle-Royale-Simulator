package Item;

import java.awt.Color;
import java.awt.Graphics;

import Agents.Agent;
import Vectors.Vector;

public class Shotgun extends Weapon{
	
	public Shotgun(Agent owner) {
		super(owner);
		this.setDamage(5);
		
		this.setMagSize(3);
		this.setCurrentBullets(3);
		
		this.setReloadTime(15);
		this.setShootingInterval(8);
	
		int[] spread = {-10, -5, 0, 5, 10};
		this.setBulletSpread(spread);
	}
	
	public Shotgun(double x, double y) {
		this(null);
		this.setPos(new Vector(x, y));
	}

	@Override
	public void show(Graphics g) {
		g.setColor(new Color(153, 102, 0));
		g.fillRect((int)this.getPos().getX(), (int)this.getPos().getY(), 9, 3);
		g.fillRect((int)this.getPos().getX(), (int)this.getPos().getY(), 2, 4);
	}
	
	public String toString() {
		return "Shotgun";
	}
}
