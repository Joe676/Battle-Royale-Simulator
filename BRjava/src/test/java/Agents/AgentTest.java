package Agents;

import static org.junit.Assert.*;

import org.junit.Test;

import Map.Map;
import Wall.Wall;

public class AgentTest {

	Agent a = new Agent(0, 0, new Map(0, 0, 0, 0, 0), 0);
	@Test
	public void test() {
		assertTrue("HP test", a.getHP() == a.getMaxHP());
		a.hit(50);
		assertTrue("Hit test", a.getHP() == a.getMaxHP()-50);
		a.heal(25);
		assertTrue("Heal test", a.getHP() == a.getMaxHP()-25);
		
		assertTrue("Wall intersection test", a.checkWallIntersection(new Wall(0, 0, 10, 10)));
		
		
		assertTrue("Distance to wall test", a.getDistanceToWall(new Wall(-10, -10, 20, 20))==0);
	}

}
