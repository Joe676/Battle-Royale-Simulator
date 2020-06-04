package Vectors;

import org.junit.Assert;
import org.junit.Test;

import Agents.Agent;
import Map.Map;
import Wall.Wall;

public class RaycastTest {
	
	Vector from = new Vector();
	Vector dir = new Vector(1, 0);
	Wall wall = new Wall(5, -5, 10, 10);
	Agent a = new Agent(5, -1, new Map(0, 0, 0, 0, 0), 0);
	@Test
	public void test() {
		Vector test = Raycast.cast(from, dir, wall);
		Assert.assertTrue("Wall cast test", test.getX()==5 && test.getY()==0);
		Assert.assertTrue("Agent cast test", Raycast.cast(from, dir, a));
		dir.setX(-1);
		dir.setY(1);

		test = Raycast.cast(from, dir, wall);
		Assert.assertTrue("Wall cast test 2", test==null);
		
		Assert.assertFalse("Agent cast test 2", Raycast.cast(from, dir, a));
	}

}
