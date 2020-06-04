package Vectors;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jozef Bossowski
 *
 */
public class VectorTest {

	Vector v = new Vector();
	@Test
	public void test() {
		Assert.assertTrue("Get x test", v.getX()==0);
		Assert.assertTrue("Get Y test", v.getY()==0);
		Assert.assertTrue("Magnitude test", v.magnitude()==0);
		Assert.assertTrue("Point distance test", Vector.pointDistance(v, new Vector(1, 0))==1);
		v.setX(1);
		Assert.assertTrue("Magnitude test 2", v.magnitude()==1);
		Assert.assertTrue("Dot product test", v.dotProduct(new Vector(2, 0))==2);
	}

}
