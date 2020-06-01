package Item;

import Agents.Agent;
import Vectors.Vector;

public abstract class Weapon extends Item implements Shootable{
	private int magSize;
	private int currentBullets;
	
	private int damage;
	private int[] bulletSpread;
	
	private int reloadTimer = 0;
	private int shootingIntervalTimer = 0;
	
	private int reloadTime;
	private int shootingInterval;
	
	private boolean isReloading = false;
	
	public Weapon(Agent owner) {
		super(owner);
	}
	
	public Weapon(Vector pos) {
		super(pos);
	}
	
	public Weapon(double x, double y) {
		super(x, y);
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public void setMagSize(int newSize) {
		this.magSize = newSize;
	}
	
	public int getMagSize() {
		return this.magSize;
	}
	
	public void setCurrentBullets(int curBull) {
		this.currentBullets = curBull;
	}

	public int getCurrentBullets() {
		return this.currentBullets;
	}
	
	public int getReloadTimer() {
		return this.reloadTimer;
	}
	
	public void setReloadTimer(int t) {
		this.reloadTimer = t;
	}
	
	public int getShootingIntervalTimer() {
		return this.shootingIntervalTimer;
	}
	
	public void setShootingIntervalTimer(int t) {
		this.shootingIntervalTimer = t;
	}
	
	public int getReloadTime() {
		return this.reloadTime;
	}
	
	public void setReloadTime(int t) {
		this.reloadTime = t;
	}
	
	public int getShootingInterval() {
		return this.shootingInterval;
	}
	
	public void setShootingInterval(int t) {
		this.shootingInterval = t;
	}
	
	public void setBulletSpread(int[] spread) {
		this.bulletSpread = spread;
	}

	public void update() {
		if(this.isReloading) {
			if(this.getReloadTimer()>0) {
				this.setReloadTimer(this.getReloadTimer()-1);
			}else {
				this.setCurrentBullets(this.magSize);
				this.isReloading = false;
			}
		}
		if(this.getShootingIntervalTimer()>0) {
			this.setShootingIntervalTimer(this.getShootingIntervalTimer()-1);
		}
	}
	
	public void reload() {
		
		if(!this.isReloading) {
			this.reloadTimer = this.reloadTime;
			this.isReloading = true;
		}
		
	}

	public void shoot() {
		
		if(this.isReloading) {
			//System.out.println("I'm reloding");
			return;
		}
		if(this.getCurrentBullets()==0) {
			//System.out.println("Empty magazine - unable to shoot");
			this.reload();
			return;
		}
		if(this.shootingIntervalTimer>0) {
			//System.out.println("Too fast Cowboy!");
			return;
		}
		
		this.setCurrentBullets(this.getCurrentBullets()-1);
		//System.out.println(""+this.getCurrentBullets()+"/"+this.getMagSize()+" bullets left");
		
		double randomOffset = Math.random()*4-2;// +/- 2 degrees random recoil
		
		for(int offset: this.bulletSpread) {
			this.getOwner().checkShot(offset + randomOffset, this.damage);
		}
		
		this.setShootingIntervalTimer(this.shootingInterval);
		
		return;
	}
	
	@Override
	public Item pickUp(Agent owner) {
		owner.setWeapon(this);
		this.setIsOnMap(false);
		this.setOwner(owner);
		return this;
	}
}
