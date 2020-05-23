package Agents;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import Item.Armor;
import Item.FirstAidKit;
import Item.Item;
import Item.Weapon;
import Map.Map;
import Vectors.Raycast;
import Vectors.Vector;
import Wall.Wall;

public class Agent{
	private boolean isDead = false;
	
	private Vector pos;
	private double angle;
	
	private Map map;
	private static double R = 10;
	
	//Vision
	private double visionAngle = 100;
	private double visionR = 150;
	
	
	//items
	private Weapon weapon;
	private Armor armor;
	

	//Health variables
	private double HP = 100;
	private double maxHP = 100;
	
	//MOVEMENT
	
	//movement variables
	private Vector vel = new Vector();
	private Vector acc = new Vector();
	
	//movement restrictions
	private double maxSpeed = 2;
	private double maxForce = 15;
	//private int maxASpeed = 1;
	
	//Wander Variables
	private final double CIRCLE_DIST = 2;
	private final double CIRCLE_RADIUS = 2;
	private final double ANGLE_CHANGE = Math.PI/20;
	private double wanderAngle = Math.random()*2*Math.PI;
	private double rotateSpeed = 0;
	private double maxRotateSpeed = 2;
	private final double RSPEED_CHANGE = 0.3; 
	
	//Obstacle Avoidance Variables
	private double boundingCircleR;//radius
	private Vector CENTER;//for detecting intersection
	private Vector boundingCirclePos;//for drawing
	
	
	//constructors
	public Agent(Vector pos, Map map) {
		this.pos = pos.copy();
		this.map = map;
    	this.boundingCircleR = Agent.R;
		this.calcBounds();
		
		this.angle = Math.random()*360 - 180;
	}
	
	public Agent(double x, double y, Map map) {
		this(new Vector(x, y), map);
	}
	
	public void setPos(double x, double y) {
		this.pos.setX(x);
		this.pos.setY(y);
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
    
	public Weapon getWeapon() {
		return this.weapon;
	}
	
	public static double getR() {
		return Agent.R;
	}
	
    private void calcBounds() {
    	this.CENTER = this.pos.copy();
    	this.CENTER.add(new Vector(Agent.R/2, Agent.R/2));
    	
    	this.boundingCirclePos = this.CENTER.copy();
    	this.boundingCirclePos.sub(new Vector(this.boundingCircleR, this.boundingCircleR));
    }
	
	//getters & setters
	public Vector getPos() {
		return this.pos.copy();
	}
	
	public Vector getCENTER() {
		return this.CENTER.copy();
	}
	
	public double getBoundingCircleR() {
		return this.boundingCircleR;
	}

	public double getVisionAngle() {
		return this.visionAngle;
	}

	public double getVisionR() {
		return this.visionR;
	}
	
	public double getMaxHP() {
		return this.maxHP;
	}
	
	public void rotateSeek(Vector target) {
		Vector desire = target.copy();
		desire.sub(this.pos);
		
		int desireAngle = (int) Math.toDegrees(Math.atan2(desire.getY(), desire.getX()));
		int a = (int) (this.angle % 360);
		desireAngle -= a;
		this.angle+=desireAngle;
	}
	
	public void rotateWander() {
		this.angle+=this.rotateSpeed;
		this.rotateSpeed += Math.random()*2*this.RSPEED_CHANGE - this.RSPEED_CHANGE;
		if(this.rotateSpeed>this.maxRotateSpeed)this.rotateSpeed = this.maxRotateSpeed;
		if(this.rotateSpeed<-this.maxRotateSpeed)this.rotateSpeed = -this.maxRotateSpeed;
	}
	
	//steering behaviours
	private void steer(Vector force) {
		force.constrain(this.maxForce);
		this.acc.add(force);
	}
	
	public void seek(Vector target, double weight) {
		Vector desire = new Vector();
		desire.add(target);
		desire.sub(this.pos);
		desire.constrain(this.maxSpeed);
		
		desire.sub(this.vel);
		desire.mult(weight);
		this.steer(desire);
		this.avoidObstacles(10);
	}

	public void flee(Vector target, double weight) {
		this.seek(target, -weight);
	}
	
	public void wander() {
		Vector circleCenter = this.vel.copy();
		circleCenter.normalize();
		circleCenter.mult(this.CIRCLE_DIST);
		
		Vector displacement = new Vector();
		displacement.fromAngle(this.CIRCLE_RADIUS, this.wanderAngle);
		this.wanderAngle += Math.random()*(2*this.ANGLE_CHANGE)-this.ANGLE_CHANGE;
		
		Vector f = circleCenter.copy();
		f.add(displacement);
		this.steer(f);
		this.avoidObstacles(6);
	}
	
	public void avoidObstacles(double weight) {
		List<Wall> intersected = this.getIntersectedWalls();
		Vector force;
		for(Wall wall: intersected) {
			force = wall.getClosestPoint(this.CENTER).copy();
			force.sub(this.CENTER);
			
			force.normalize();
			force.mult(-weight);
			this.steer(force);
			this.wanderAngle = Math.atan2(this.vel.getY(), this.vel.getX());
		}
	}	

	
	public void update() {
		this.vel.add(this.acc);
		this.vel.constrain(this.maxSpeed);
		this.acc.mult(0);
		this.pos.add(this.vel);
		this.calcBounds();

		if(this.weapon!=null) {
			this.weapon.update();
		}
	}
	
	//Items
	public void pickUp(Weapon weapon) {
		
	}
	
	public void pickUp(FirstAidKit fak) {
		
	}
	
	public void pickUp(Armor armor) {
	}
	
    public double getDistanceToWall(Wall wall) {
    	Vector pt = wall.getClosestPoint(this.CENTER);
    	pt.sub(this.CENTER);
    	return pt.magnitude();
    }
    
    public List<Vector> getClosestWallPoints() {
    	List<Vector> V = new ArrayList<Vector>();
		List<Wall> W = this.map.getWalls();
    	for(Wall wall: W) {
    		V.add(wall.getClosestPoint(this.CENTER));
    	}
    	return V;
    }
    
    public boolean checkWallIntersection(Wall wall) {//true - intersects
    	return this.getDistanceToWall(wall) <= this.boundingCircleR;
    }
    
    public List<Wall> getIntersectedWalls(){
		List<Wall> W = this.map.getWalls();
    	List<Wall> out = new ArrayList<Wall>();
    	for(Wall wall: W) {
    		if(this.checkWallIntersection(wall)) {
    			out.add(wall);
    		}
    	}
    	return out;
    }
    
    public List<Agent> getIntersectedAgents(){
		List<Agent> A = this.map.getAgents();
    	List<Agent> out = new ArrayList<Agent>();
    	for(Agent agent: A) {
    		if(this.checkAgentIntersection(agent)) {
    			out.add(agent);
    		}
    	}
    	return out;
    }

	private boolean checkAgentIntersection(Agent agent) {//true - intersects
    	return this.getDistanceToAgent(agent) <= this.boundingCircleR;
	}

	public double getDistanceToAgent(Agent agent) {
		Vector pt = agent.getCENTER();
		pt.sub(this.CENTER);
		return pt.magnitude();
	}

	public List<Agent> getVisibleAgents(){
		List<Agent> A = this.map.getAgents();
		List<Agent> out = new ArrayList<Agent>();
		for(Agent agent: A) {
			if(agent!=this) {
				if(this.checkAgentVisibility(agent)) {
					out.add(agent);
				}
			}
		}
		return out;
	}
	
	public boolean checkAgentVisibility(Agent agent) {
		Vector diff = agent.getCENTER();
		diff.sub(this.getCENTER());
		if(diff.magnitude_squared() < this.visionR*this.visionR) {
			double angle = Math.toDegrees(Math.atan2(diff.getY(), diff.getX()));
			if(Math.abs(angle - this.angle) < this.visionAngle/2) {
				
				List<Wall> W = this.map.getWalls();
				for(Wall wall: W) {
					Vector pt = Raycast.cast(this.CENTER, diff, wall);
					if(pt!=null) {
						pt.sub(this.CENTER);
						if(pt.magnitude_squared()<diff.magnitude_squared()) {
							return false;
						}
					}
				}
				
				return true;
			}
		}
		return false;
	}
	
	public void shoot() {
		this.weapon.shoot();
	}
	
	public void checkShot(double angleOffset, int damage) {
		Vector dir = new Vector(1, 1);
		dir.fromAngle(1, Math.toRadians(this.angle + angleOffset));
		
		List<Wall> W = this.map.getWalls();
		List<Agent> A = this.map.getAgents();
		double d = 50000;
		for(Wall wall: W) {
			Vector pt = Raycast.cast(this.CENTER.copy(), dir, wall);
			if(pt!=null && Vector.pointDistance(pt, this.CENTER)<d) {
				d = Vector.pointDistance(pt, this.CENTER);
			}
		}
		Agent closestAgent = null;
		double d2 = 50000;
		for(Agent agent: A) {
			if(this!=agent && Raycast.cast(this.CENTER.copy(), dir, agent)) {
				if(Vector.pointDistance(agent.getCENTER(), this.CENTER) < d2) {
					d2 = Vector.pointDistance(agent.getCENTER(), this.CENTER); 
					closestAgent = agent;
				}
			}
		}
		
		if(d2<d) {
			closestAgent.hit(damage);
			return;
		}
		
	}
	
	public Armor getArmor() {
		return this.armor;
	}
	
	public void setArmor(Armor armor) {
		this.armor = armor;
	}
	
	public void hit(int damage) {
		if(this.armor!=null) {
			this.armor.hit(damage);
		}else {
			this.HP -= damage;
			if(this.HP<=0) {
				this.die();
			}
		}
	}
	
	public void die() {
		this.isDead = true;
		this.map.agentDied(this);
	}

	//drawing
    public void show(Graphics g){
        final int x = (int)this.pos.getX();
        final int y = (int)this.pos.getY();
    	
    	g.setColor(Color.RED);
        g.fillOval(x, y, (int)Agent.R, (int)Agent.R);

        g.setColor(Color.BLACK);
        g.drawOval(x, y, (int)Agent.R, (int)Agent.R);  
        
        //g.setColor(Color.BLUE);
        //g.drawOval((int)this.boundingCirclePos.getX(), (int)this.boundingCirclePos.getY(), (int)this.boundingCircleR*2, (int)this.boundingCircleR*2);
        Vector center = this.CENTER;
        
        g.setColor(new Color(30, 30, 30, 10));
        g.fillArc((int)(center.getX()-this.visionR), (int)(center.getY()-this.visionR), (int)(2*this.visionR), (int)(2*this.visionR), -(int)(this.angle-this.visionAngle/2), -(int)this.visionAngle);
    }
    
}




















