package Item;

import java.awt.Color;
import java.awt.Graphics;

import Agents.Agent;
import Vectors.Vector;

public class FirstAidKit extends Item{
	
	private static final int HP = 30;

	public FirstAidKit(Agent owner) {
		super(owner);
	}
	
	public FirstAidKit(double x, double y) {
		this(null);
		this.setPos(new Vector(x, y));
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
