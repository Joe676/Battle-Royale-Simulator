package Item;

import java.awt.Graphics;

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
		this.isOnMap = (owner!=null);
		this.owner = owner;
	}
	
	public Vector getPos() {
		return this.pos.copy();
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
	
	public void setOwner(Agent owner) {
		this.owner = owner;
	}
	
	public abstract void show(Graphics g);
	
}
