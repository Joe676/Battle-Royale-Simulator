package Item;

import Agents.Agent;

public class Pistol extends Weapon {

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

	@Override
	public Item pickUp() {
		// TODO Auto-generated method stub
		return null;
	}

}
