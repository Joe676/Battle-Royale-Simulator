package Item;

import Agents.Agent;
import Vectors.Vector;

/**
 * An abstract class representing all weapons
 * @author Jozef Bossowski
 *
 */
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
	
	/**
	 * Constructs and initializes a weapon belonging to an agent
	 * @param owner Agent owning the weapon
	 */
	public Weapon(Agent owner) {
		super(owner);
	}
	
	/**
	 * Constructs and initializes a weapon at a specified point of the map
	 * @param pos The point
	 */
	public Weapon(Vector pos) {
		super(pos);
	}
	
	/**
	 * Constructs and initializes a weapon at a specified point of the map
	 * @param x X coordinate of the point
	 * @param y Y coordinate of the point
	 */
	public Weapon(double x, double y) {
		super(x, y);
	}
	
	/**
	 * 
	 * @param damage Damage that the weapon deals
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	/**
	 * 
	 * @return Damage that the weapon deals
	 */
	public int getDamage() {
		return this.damage;
	}
	
	/**
	 * 
	 * @param newSize New size of the weapon's magazine
	 */
	public void setMagSize(int newSize) {
		this.magSize = newSize;
	}
	
	/**
	 * 
	 * @return Size of the weapon's magazine
	 */
	public int getMagSize() {
		return this.magSize;
	}
	
	/**
	 * 
	 * @param curBull New amount of bullets in the magazine
	 */
	public void setCurrentBullets(int curBull) {
		this.currentBullets = curBull;
	}

	/**
	 * 
	 * @return Amount of bullets in the magazine
	 */
	public int getCurrentBullets() {
		return this.currentBullets;
	}
	
	/**
	 * 
	 * @return Current state of the timer of reloading
	 */
	public int getReloadTimer() {
		return this.reloadTimer;
	}
	
	/**
	 * 
	 * @param t New state of the timer of reloading
	 */
	public void setReloadTimer(int t) {
		this.reloadTimer = t;
	}
	
	/**
	 * 
	 * @return Current state of the timer for breaks between shots
	 */
	public int getShootingIntervalTimer() {
		return this.shootingIntervalTimer;
	}
	
	/**
	 * 
	 * @param t New wstate of the timer for breaks between shots
	 */
	public void setShootingIntervalTimer(int t) {
		this.shootingIntervalTimer = t;
	}
	
	/**
	 * 
	 * @return Maximum time it takes to reload
	 */
	public int getReloadTime() {
		return this.reloadTime;
	}
	
	/**
	 * 
	 * @param t New maximum time it takes to reload
	 */
	public void setReloadTime(int t) {
		this.reloadTime = t;
	}
	
	/**
	 * 
	 * @return Maximum break between shots
	 */
	public int getShootingInterval() {
		return this.shootingInterval;
	}
	
	/**
	 * 
	 * @param t New maximum break between shots
	 */
	public void setShootingInterval(int t) {
		this.shootingInterval = t;
	}
	
	/**
	 * 
	 * @param spread Which direction do bullets when shot
	 */
	public void setBulletSpread(int[] spread) {
		this.bulletSpread = spread;
	}

	/**
	 * Updates weapon's timers
	 */
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
	
	/**
	 * Starts reloading timer
	 */
	public void reload() {
		
		if(!this.isReloading) {
			this.reloadTimer = this.reloadTime;
			this.isReloading = true;
		}
		
	}

	/**
	 * Manages shooting
	 */
	public void shoot() {
		
		if(this.isReloading) {
			//System.out.println("I'm reloading");
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
