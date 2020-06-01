package Item;

import java.awt.Color;
import java.awt.Graphics;

import Agents.Agent;
import Vectors.Vector;

public class Armor extends Item{
	private int maxHitPoints = 50;
	private int hitPoints = 50;
	
	public Armor(Agent owner){
		super(owner);
	}
	
	public Armor(double x, double y) {
		this(null);
		this.setPos(new Vector(x, y));
	}
	
	public int getHitPoints() {
		return this.hitPoints;
	}
	
	public void hit(int damage) {
		this.hitPoints -= damage;
		if(this.hitPoints <= 0) {
			this.destroy();
		}
	}
	
	private void destroy() {
		this.getOwner().setArmor(null);
	}

	@Override
	public void show(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int)this.getPos().getX(), (int)this.getPos().getY(), 12, 4);
		g.fillRect((int)(this.getPos().getX()+2), (int)(this.getPos().getY()+2), 8, 8);
	}

	@Override
	public Item pickUp(Agent owner) {
		owner.setArmor(this);
		this.setIsOnMap(false);
		this.setOwner(owner);
		return this;
	}
	
	public String toString() {
		return "Armor with "+this.hitPoints+"/"+this.maxHitPoints+ " hit points left";
	}
	
}
