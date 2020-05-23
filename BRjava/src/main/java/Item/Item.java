package Item;

import Agents.Agent;
import Vectors.Vector;

public abstract class Item implements Pickupable {
	private Vector pos;
	private boolean isOnMap;
	private Agent owner = null;
	
	public Item(Vector pos) {
		this.pos = pos.copy();
		this.isOnMap = true;
	}
	
	public Item(double x, double y) {
		this.pos = new Vector(x, y);
		this.isOnMap = true;
	}
	
	public Item(Agent owner) {
		this.isOnMap = false;
		this.owner = owner;
	}
	
	public Vector getPos() {
		return this.pos;
	}
	
	public void setPos(Vector pos) {
		this.pos = pos.copy();
	}
	
	public boolean getIsOnMap() {
		return this.isOnMap;
	}
	
	public void setIsOnMap(boolean is) {
		this.isOnMap = is;
	}
	
	public Agent getOwner() {
		return this.owner;
	}
	
}
