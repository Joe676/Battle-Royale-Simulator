package Item;

import Agents.Agent;

public class Rifle extends Weapon{
	
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

	@Override
	public Item pickUp() {
		// TODO Auto-generated method stub
		return null;
	}

}
