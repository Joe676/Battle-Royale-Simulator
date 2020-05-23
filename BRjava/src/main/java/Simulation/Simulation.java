package Simulation;

import java.awt.Graphics;
import java.util.List;

import Agents.Agent;
import Map.Map;
import Vectors.Vector;

public class Simulation {
	private Map map;
	private int pCount;
	
	
	public Simulation(int playerCount, int wallCount, int width, int height) {
		this.pCount = playerCount;
		map = new Map(playerCount, wallCount, width, height);
	}
	
	
	
	public void step() {
		this.map.update();
	}
	
	public void show(Graphics g) {
		this.map.show(g);
	}
	
	public List getItems(){
		return this.map.getItems();
	}
	
	public void testShoot() {
		this.map.testSubject.shoot();
	}
}
