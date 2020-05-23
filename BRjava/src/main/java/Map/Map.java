package Map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Agents.Agent;
import Item.Item;
import Item.Pistol;
import Item.Rifle;
import Item.Shotgun;
import Vectors.Vector;
import Wall.Wall;

public class Map {
	private int width;
	private int height;
	
	private Vector ringCenter;
	private double ringRadius;
	
	private int playerCount;
	private List<Agent> Agents = new ArrayList<Agent>();
	private List<Item> Items = new ArrayList<Item>();
	private List<Wall> Walls = new ArrayList<Wall>();
	
	private List<Integer> toKill = new ArrayList<Integer>();
	public Agent testSubject;
	
	public Map(int playerCount, int wallCount, int width, int height) {
		this.playerCount = playerCount;
		
		this.width = width;
		this.height = height;
		
		double ringX = Math.random()*(width-60)+30;
		double ringY = Math.random()*(height-60)+30;
		this.ringCenter = new Vector(ringX, ringY);
		
		this.ringRadius = Math.max(width, height);
		
		Walls.add(new Wall(-40, -40, width+80, 42));
		Walls.add(new Wall(-40, -40, 42, height+80));
		Walls.add(new Wall(-40, height-2, width+80, 42));
		Walls.add(new Wall(width-2, -40, 42, height+80));

		//this.testSubject = new Agent(width/2, height/2, this);
		//this.testSubject.setWeapon(new Shotgun(this.testSubject));
		
		for(int i = 0; i<wallCount; i++) {
			Walls.add(new Wall(Math.random()*this.width, Math.random()*this.height, (int)(Math.random()*40+10), (int)(Math.random()*40+10)));
		}

		for(int i = 0; i<playerCount; i++) {
			double rX = Math.random()*(this.width-Agent.getR()*2)+Agent.getR();
			double rY = Math.random()*(this.height-Agent.getR()*2)+Agent.getR();
			Agents.add(new Agent(rX, rY, this));
			
			//while(Agents.get(i).getIntersectedWalls().size()>0) {
				//rX = Math.random()*(this.width-Agent.getR()*2)+Agent.getR();
				//rY = Math.random()*(this.height-Agent.getR()*2)+Agent.getR();
				//Agents.get(i).setPos(rX, rY);
			//}
			
			Agents.get(i).setWeapon(new Pistol(Agents.get(i)));
		}
		
		
	}
	
	public List<Item> getItems() {
		return this.Items;
	}

	public List<Agent> getAgents() {
		return this.Agents;
	}
	
	public List<Wall> getWalls() {
		return this.Walls;
	}
	
	public void update() {
		Collections.sort(this.toKill);
		int h = -1;
		for(int i = this.toKill.size()-1; i>=0; i--) {
			 if(h != this.toKill.get(i).intValue()) {
				 h = this.toKill.get(i).intValue();
				 this.Agents.remove(h);
			 }
		}
		this.toKill.clear();
		
		if(this.Agents.size()==1) {
			System.out.println("We have a winner! "+this.Agents.get(0));
			return;
		}
		if(this.Agents.size()<1) {
			System.out.println("We have no winner! :(");
			return;
		}
		
		if(this.ringRadius>0)this.ringRadius--;
		
		if(this.testSubject!=null) {
			this.testSubject.wander();
			this.testSubject.rotateWander();
			this.testSubject.update();
		}
		
		for(Agent agent: this.Agents) {
			//if(agent.isDead)continue;
			List<Agent> V = agent.getVisibleAgents();
			Agent closestVisible = null;
			double dist = 10000;
			
			for(Agent a: V) {
				if(a!=agent) {
					if(agent.getDistanceToAgent(a)<dist) {
						dist = agent.getDistanceToAgent(a);
						closestVisible = a;
					}
				}
				
			}
			if(closestVisible!=null) {
				agent.rotateSeek(closestVisible.getCENTER());
				agent.seek(closestVisible.getCENTER(), 1);
			}else {
				agent.wander();
			}
			agent.shoot();
			
			if(V.size()==0)agent.rotateWander();
			agent.update();
			if(Vector.pointDistance(agent.getCENTER(), this.ringCenter) > this.ringRadius) {
				agent.hit(5);
			}
		}
	}
	
	public void show(Graphics g) {
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
		
		g.setColor(Color.ORANGE);
		g.drawOval((int)(this.ringCenter.getX()-this.ringRadius), (int)(this.ringCenter.getY()-this.ringRadius), (int)(this.ringRadius*2), (int)(this.ringRadius*2));
		g.drawOval((int)(this.ringCenter.getX()-this.ringRadius-10), (int)(this.ringCenter.getY()-this.ringRadius-10), (int)((this.ringRadius+10)*2), (int)((this.ringRadius+10)*2));
	}

	public void agentDied(Agent corpse) {
		this.toKill.add(this.Agents.indexOf(corpse));
		
	}
}
