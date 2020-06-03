package Simulation;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.List;

import Agents.Agent;
import Map.Map;
import Vectors.Vector;

/**
 * Class that encapsulates all simulation code
 * @author Jozef Bossowski
 *
 */
public class Simulation {
	private Map map;
	private int pCount;
	private int speed;
	private File saveFile;
	private String fileName;
	
	/**
	 * Constructs and initializes the simulation
	 * @param playerCount Number of players at the start of simulation
	 * @param wallCount Number of walls inside the arena
	 * @param width Width of the arena
	 * @param height Height of the arena
	 * @param speed Speed of the game (1-10)
	 * @param zoneSpeed Number of ticks between zone shrinking
	 */
	public Simulation(int playerCount, int wallCount, int width, int height, int speed, int zoneSpeed) {
		this.speed = 11 - speed;//speed ranging from 1-10 - update every # ticks
		this.pCount = playerCount;
		map = new Map(playerCount, wallCount, width, height, zoneSpeed);
		this.createFile();
	}
	
	
	/**
	 * Next step of the simulation 
	 * @param tick current tick number
	 */
	public void step(int tick) {//Step of the simulation
		
		if(tick%this.speed == 0) {
			this.print(tick);
			this.map.update(tick);
		}
	}
	
	/**
	 * Shows the game state
	 * @param g Where it is to be drawn
	 */
	public void show(Graphics g) {//Visualising simulation
		this.map.show(g);
	}
	
	/**
	 * 
	 * @return List of the Items on the map
	 */
	public List getItems(){
		return this.map.getItems();
	}
	
	/**
	 * Creates the text file for saving game state
	 */
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
	
	/**
	 * Writes current game state to the save file
	 * @param tick Current tick number
	 */
	private void print(int tick) {
		if(this.saveFile != null) {
			this.map.print(this.fileName, tick/this.speed);
		}
	}
}
