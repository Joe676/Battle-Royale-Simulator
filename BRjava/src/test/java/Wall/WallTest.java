package Wall;

import static org.junit.Assert.*;

import org.junit.Test;

import Vectors.Vector;

public class WallTest {

	Wall w = new Wall(-1, -1, 2, 2);
	Vector pt = new Vector(2, 0);
	@Test
	public void test() {
		Vector test = w.getClosestPoint(pt);
		assertTrue("Get closest point test", test.getX()==1 && test.getY()==0);
		assertFalse("Is point inside test", w.isPointInside(pt));
		
		pt.setX(0);
		test = w.getClosestPoint(pt);
		assertTrue("Get closest point test 2", test.getX()==0 && test.getY()==0);
		assertTrue("Is point inside test 2", w.isPointInside(pt));
		
	}

}
