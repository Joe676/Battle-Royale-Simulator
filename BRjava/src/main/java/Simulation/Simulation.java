package Simulation;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.List;

import Agents.Agent;
import Map.Map;
import Vectors.Vector;

public class Simulation {
	private Map map;
	private int pCount;
	private int speed;
	private File saveFile;
	private String fileName;
	
	
	public Simulation(int playerCount, int wallCount, int width, int height, int speed, int zoneSpeed) {
		this.speed = 11 - speed;//speed ranging from 1-10 - update every # ticks
		this.pCount = playerCount;
		map = new Map(playerCount, wallCount, width, height, zoneSpeed);
		this.createFile();
	}
	
	
	
	public void step(int tick) {//Step of the simulation
		
		if(tick%this.speed == 0) {
			this.print(tick);
			this.map.update(tick);
		}
	}
	
	public void show(Graphics g) {//Visualising simulation
		this.map.show(g);
	}
	
	public List getItems(){
		return this.map.getItems();
	}
	
	public void testShoot() {//testing shooting
		this.map.testSubject.shoot();
	}
	
	private void createFile() {
		try {
			String time = java.time.LocalTime.now().toString().replace(':', '_');
			this.saveFile = new File("simulationSave"+time+".txt");
			if(this.saveFile.createNewFile()) {
				this.fileName = this.saveFile.getName();
				System.out.println("File created: "+ this.saveFile.getName());
			}else {
				System.out.println("File already exists");
			}
		}
		catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
	    }
	}
	
	private void print(int tick) {
		if(this.saveFile != null) {
			this.map.print(this.fileName, tick/this.speed);
		}
	}
}
