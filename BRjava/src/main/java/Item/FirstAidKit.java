package Item;

import java.awt.Color;
import java.awt.Graphics;

import Agents.Agent;

/**
 * Class representing first aid kits
 * @author Jozef Bossowski
 *
 */
public class FirstAidKit extends Item{
	
	private static final int HP = 30;
	
	/**
	 * Constructs and initializes first aid kit at a specified point of the map
	 * @param x X coordinate of the point
	 * @param y Y coordinate of the point
	 */
	public FirstAidKit(double x, double y) {
		super(x, y);
	}

	@Override
	public void show(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)(this.getPos().getX()+2), (int)this.getPos().getY(), 2, 6);
		g.fillRect((int)this.getPos().getX(), (int)(this.getPos().getY()+2), 6, 2);
		
	}

	@Override
	public Item pickUp(Agent owner) {
		owner.heal(FirstAidKit.HP);
		return this;
	}

}
