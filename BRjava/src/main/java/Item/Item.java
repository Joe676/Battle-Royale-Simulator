package Item;

import java.awt.Graphics;

import Agents.Agent;
import Vectors.Vector;

/**
 * Abstract class representing items
 * @author Jozef Bossowski
 *
 */
public abstract class Item implements Pickupable {
	private Vector pos;
	private boolean isOnMap;
	private Agent owner = null;
	
	/**
	 * Constructs and initializes an item at a given vector position
	 * @param pos Position of the item
	 */
	public Item(Vector pos) {
		this.pos = pos.copy();
		this.isOnMap = true;
	}

	/**
	 * Constructs and initializes an item at a position given by coordinates
	 * @param x X coordinate of the item 
	 * @param y Y coordinate of the item
	 */
	public Item(double x, double y) {
		this.pos = new Vector(x, y);
		this.isOnMap = true;
	}
	
	/**
	 * Constructs and initializes an item as owned by agent
	 * @param owner Agent owning the item
	 */
	public Item(Agent owner) {
		this.isOnMap = (owner!=null);
		this.owner = owner;
	}
	
	/**
	 * 
	 * @return Current position of the top left corner
	 */
	public Vector getPos() {
		return this.pos.copy();
	}
	
	/**
	 * 
	 * @param pos New position of the top left corner
	 */
	public void setPos(Vector pos) {
		this.pos = pos.copy();
	}
	
	/**
	 * 
	 * @return Whether the item is on map
	 */
	public boolean getIsOnMap() {
		return this.isOnMap;
	}
	
	public void setIsOnMap(boolean is) {
		this.isOnMap = is;
	}
	
	/**
	 * 
	 * @return Owner of this item
	 */
	public Agent getOwner() {
		return this.owner;
	}
	
	/**
	 * 
	 * @param owner New owner of this item
	 */
	public void setOwner(Agent owner) {
		this.owner = owner;
	}
	
	/**
	 * Method for drawing specific item on the map
	 */
	public abstract void show(Graphics g);
	
}
