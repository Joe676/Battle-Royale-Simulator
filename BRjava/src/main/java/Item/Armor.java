package Item;

import java.awt.Color;
import java.awt.Graphics;

import Agents.Agent;
import Vectors.Vector;

/**
 * Class representing an armor worn by agents
 * @author Jozef Bossowski
 *
 */
public class Armor extends Item{
	private int maxHitPoints = 50;
	private int hitPoints = 50;
	
	/**
	 * Constructs and initializes the armor on an Agent
	 * @param owner Agent on which armor is to be generated
	 */
	public Armor(Agent owner){
		super(owner);
	}
	
	/**
	 * Constructs and initializes the armor on a map
	 * @param x	X coordinate of the point at which armor is to be placed
	 * @param y y coordinate of the point at which armor is to be placed
	 */
	public Armor(double x, double y) {
		this(null);
		this.setPos(new Vector(x, y));
	}
	
	/**
	 * 
	 * @return Current hit points left in this armor
	 */
	public int getHitPoints() {
		return this.hitPoints;
	}
	
	/**
	 * Damage this armor with a specified value
	 * @param damage Value to be subtracted from hit points
	 */
	public void hit(int damage) {
		this.hitPoints -= damage;
		if(this.hitPoints <= 0) {
			this.destroy();
		}
	}
	
	/**
	 * Gets rid of an armor when it is destroyed
	 */
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
	
	@Override
	public String toString() {
		return "Armor with "+this.hitPoints+"/"+this.maxHitPoints+ " hit points left";
	}
	
}
