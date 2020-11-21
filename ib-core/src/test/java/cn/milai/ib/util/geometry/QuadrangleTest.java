package cn.milai.ib.util.geometry;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cn.milai.ib.Point;

public class QuadrangleTest {

	@Test
	public void testIntersects() {
		Quadrangle q1 = new Quadrangle(new Point[] {
			new Point(-4, 6),
			new Point(5, 12),
			new Point(9, 6),
			new Point(0, 0),
		});
		Quadrangle q2 = new Quadrangle(new Point[] {
			new Point(3, 1),
			new Point(6, 3),
			new Point(9, -1),
			new Point(6, -3),
		});
		Quadrangle q3 = new Quadrangle(new Point[] {
			new Point(8, 1),
			new Point(8, 7),
			new Point(12, 7),
			new Point(12, 1),
		});
		Quadrangle q4 = new Quadrangle(new Point[] {
			new Point(-4, -4),
			new Point(-4, 13),
			new Point(13, 13),
			new Point(13, -4),
		});
		assertTrue(q1.intersects(q1));
		assertTrue(q2.intersects(q2));
		assertTrue(q3.intersects(q3));
		assertTrue(q4.intersects(q4));
		assertTrue(q1.intersects(q3) && q3.intersects(q1));
		assertFalse(q1.intersects(q2) || q2.intersects(q1));
		assertFalse(q2.intersects(q3) || q3.intersects(q2));
		assertTrue(q1.intersects(q4) && q4.intersects(q1));
		assertTrue(q2.intersects(q4) || q4.intersects(q2));
		assertTrue(q3.intersects(q4) || q4.intersects(q3));
	}

}
