package Item;

import Agents.Agent;

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


	@Override
	public Item pickUp() {
		// TODO Auto-generated method stub
		return null;
	}

}
