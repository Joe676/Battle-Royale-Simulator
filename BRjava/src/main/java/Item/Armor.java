package Item;

import Agents.Agent;

public class Armor extends Item{
	private int hitPoints = 50;
	
	public Armor(Agent owner){
		super(owner);
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
	public Item pickUp() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
