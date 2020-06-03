package Map;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Agents.Agent;
import Item.Armor;
import Item.FirstAidKit;
import Item.Item;
import Item.Pistol;
import Item.Rifle;
import Item.Shotgun;
import Item.Weapon;
import Vectors.Vector;
import Wall.Wall;

/**
 * Class representing map, storing walls, agents and items and managing ring
 * @author Jozef Bossowski
 *
 */
public class Map {
	private int width;//width of the arena
	private int height;//height of the arena
	
	private Vector ringCenter;//center of the Zone Ring
	private double ringRadius;//Current Radius of the Zone Ring
	private int ringSpeed;
	
	private int playerCount;//Starting # of players
	private List<Agent> Agents = new ArrayList<Agent>();//Collection of Agents currently on the map
	private List<Item> Items = new ArrayList<Item>();//Collection of Items currently on the map 
	private List<Wall> Walls = new ArrayList<Wall>();//Collection of Walls on the map
	
	private List<Integer> toKill = new ArrayList<Integer>();//list of Agents that died this frame
	public Agent testSubject;//Agent for testing different behaviours
	
	/**
	 * Constructs and initializes map object
	 * @param playerCount Number of players on the map in the beginning 
	 * @param wallCount	Number of inside walls on the map
	 * @param width Width of the arena
	 * @param height Height of the arena
	 * @param zoneSpeed How many ticks between zone shrinking
	 */
	public Map(int playerCount, int wallCount, int width, int height, int zoneSpeed) {
		this.playerCount = playerCount;
		
		this.width = width;
		this.height = height;
		
		//Choosing random location for Zone
		double ringX = Math.random()*(width-60)+30;
		double ringY = Math.random()*(height-60)+30;
		this.ringCenter = new Vector(ringX, ringY);
		
		this.ringRadius = Math.max(width, height);
		this.ringSpeed = zoneSpeed;
		//Generating outer walls
		Walls.add(new Wall(-40, -40, width+80, 42));
		Walls.add(new Wall(-40, -40, 42, height+80));
		Walls.add(new Wall(-40, height-2, width+80, 42));
		Walls.add(new Wall(width-2, -40, 42, height+80));

		//this.testSubject = new Agent(width/2, height/2, this);
		//this.testSubject.setWeapon(new Shotgun(this.testSubject));
		
		//Generating all the inside walls
		for(int i = 0; i<wallCount; i++) {
			Walls.add(new Wall(Math.random()*this.width, Math.random()*this.height, (int)(Math.random()*40+10), (int)(Math.random()*40+10)));
		}
		
		//Generating Weapons
		for(int i = 0; i<playerCount; i++) {
			double kind = Math.random()*3;
			Vector p = this.randomItemPoint();
			
			//1/3 probability of getting each kind of gun
			if(kind>2) {
				this.Items.add(new Shotgun(p.getX(), p.getY()));
			}else if(kind>1) {
				this.Items.add(new Pistol(p.getX(), p.getY()));
			}else {
				this.Items.add(new Rifle(p.getX(), p.getY()));
			}
		}
		
		//Genarating First Aid Kits
		for(int i = 0; i<playerCount; i++) {
			Vector p = this.randomItemPoint();
			
			this.Items.add(new FirstAidKit(p.getX(), p.getY()));
		}
		
		//Generating armors
		for(int i = 0; i<playerCount/3; i++) {
			Vector p = this.randomItemPoint();
			
			this.Items.add(new Armor(p.getX(), p.getY()));
		}
		
		//Generating all the agents
		for(int i = 0; i<playerCount; i++) {
			double rX = Math.random()*(this.width-Agent.getR()*2)+Agent.getR();
			double rY = Math.random()*(this.height-Agent.getR()*2)+Agent.getR();
			
			for(Wall wall : this.Walls) {
				while(wall.isPointInside(new Vector(rX, rY))) {
					rX = Math.random()*(this.width-Agent.getR()*2)+Agent.getR();
					rY = Math.random()*(this.height-Agent.getR()*2)+Agent.getR();
				}	
			}
			
			Agents.add(new Agent(rX, rY, this, i));
			
		}
	}
	
	/**
	 * Generating a random point on the map for placing an item, out of the walls
	 * @return A point at which an item is not inside any wall
	 */
	private Vector randomItemPoint() {
		double x = Math.random() *(this.width-10)+5;
		double y = Math.random() *(this.height-10)+5;
		
		for(Wall wall: this.Walls) {
			if(wall.isPointInside(new Vector(x, y))) {
				return this.randomItemPoint();
			}
		}
		return new Vector(x, y);
	}
	
	/**
	 * 
	 * @return List of items on the map
	 */
	public List<Item> getItems() {//returns Items currently on the map
		return this.Items;
	}

	/**
	 * 
	 * @return List of agents on the map
	 */
	public List<Agent> getAgents() {//returns Agents currently on the map
		return this.Agents;
	}
	
	/**
	 *  
	 * @return List of walls on the map
	 */
	public List<Wall> getWalls() {//returns Walls on the map
		return this.Walls;
	}
	
	/**
	 * Updates game state saved on the map
	 * @param current tick number
	 */
	public void update(int tick) {//Step and updating the game state
		//Removing Agents who died last frame
		Collections.sort(this.toKill);
		int h = -1;
		for(int i = this.toKill.size()-1; i>=0; i--) {
			 if(h != this.toKill.get(i).intValue()) {
				 h = this.toKill.get(i).intValue();
				 this.Agents.remove(h);
			 }
		}
		this.toKill.clear();
		
		//checking end conditions
		if(this.Agents.size()==1) {
			System.out.println("We have a winner! "+this.Agents.get(0));
			return;
		}
		if(this.Agents.size()<1) {
			System.out.println("We have no winner! :(");
			return;
		}
		
		//Shrinking Zone
		if(tick%this.ringSpeed == 0 && this.ringRadius>0)this.ringRadius--;
		
		//Testing
		if(this.testSubject!=null) {
			this.testSubject.wander();
			this.testSubject.rotateWander();
			this.testSubject.update();
		}
		
		//Evaluating each agent's turn
		for(Agent agent: this.Agents) {
			agent.decide();
			agent.update();
			if(Vector.pointDistance(agent.getCENTER(), this.ringCenter) > this.ringRadius) {
				agent.hit(5);
				agent.seek(this.ringCenter, 5);
			}
		}
	}
	
	/**
	 * Drawing current gamestate
	 * @param g Where the graphics are going to be drawn
	 */
	public void show(Graphics g) {//Visualising game state
		if(this.testSubject != null) {//testing environment
			this.testSubject.show(g);
			g.setColor(Color.GREEN);
			g.fillOval((int)this.testSubject.getPos().getX(), (int)this.testSubject.getPos().getY(), (int)Agent.getR(), (int)Agent.getR());
			
			List<Wall> W = this.testSubject.getIntersectedWalls();
			for(Wall wall: W) {
				g.setColor(Color.RED);
				g.drawRect((int)wall.getPos().getX()-1, (int)wall.getPos().getY()-1, (int)wall.getWidth()+1, (int)wall.getHeight()+1);
			}
			
			List<Agent> A = this.testSubject.getVisibleAgents();
			for(Agent agent: A) {
				g.setColor(Color.RED);
				g.drawLine((int)this.testSubject.getCENTER().getX(), (int)this.testSubject.getCENTER().getY(), (int)agent.getCENTER().getX(), (int)agent.getCENTER().getY());
			}
			
		}
		
		for(Wall wall: this.Walls) {
			wall.show(g);
		}
		
		for(Agent agent: this.Agents) {
			agent.show(g);
		}
		
		for(Item item: this.Items) {
			item.show(g);
		}
		
		g.setColor(Color.ORANGE);
		g.drawOval((int)(this.ringCenter.getX()-this.ringRadius), (int)(this.ringCenter.getY()-this.ringRadius), (int)(this.ringRadius*2), (int)(this.ringRadius*2));
		g.drawOval((int)(this.ringCenter.getX()-this.ringRadius-10), (int)(this.ringCenter.getY()-this.ringRadius-10), (int)((this.ringRadius+10)*2), (int)((this.ringRadius+10)*2));
	}

	/**
	 * Managing an agent's death
	 * @param corpse The dead agent
	 */
	public void agentDied(Agent corpse) {
		this.toKill.add(this.Agents.indexOf(corpse));
	}

	/**
	 * Adding an item to the map
	 * @param item Item to be added
	 */
	public void addItem(Item item) {
		this.Items.add(item);
	}

	/**
	 * Removing an item from the map
	 * @param item Item to be removed
	 */
	public void removeItem(Item item) {
		this.Items.remove(item);
	}
	
	/**
	 * Writing current game state to a text file
	 * @param fileName Name of the save file
	 * @param tick Number of the current tick
	 */
	public void print(String fileName, int tick) {
		try{
			FileWriter writer = new FileWriter(fileName, true);
			writer.append("Tick #" + tick+"\n");
			writer.append("Last tick "+this.toKill.size()+" agents died\n");
			writer.append(""+this.Agents.size()+"/"+this.playerCount + " players still alive:\n");
			for(Agent agent: this.Agents) {
				writer.append("" + agent+" at " + agent.getPos() + ", "+agent.getHP()+"/"+agent.getMaxHP()+" HP left, Weapon: "+(agent.getWeapon()==null? "none":agent.getWeapon())+", Armor: "+ (agent.getArmor()==null ? "none": agent.getArmor())+"\n");
			}
			writer.close();
		}catch(IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
}
