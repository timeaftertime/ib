package cn.milai.ib.role.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cn.milai.ib.geometry.Point;
import cn.milai.ib.role.BaseRole;
import cn.milai.ib.role.Role;

public class RotatableTest {

	private static final double DELTA = 0.5;

	private static class RotatableStub extends BaseRole {

		public RotatableStub(int x, int y, double w, double h, double direction) {
			setFixedBox(false);
			resetBounds(x, y, w, h);
			setDirection(direction);
		}
	}

	// 绕中心旋转 -15.26 度
	private Role role = new RotatableStub(0, 0, 10, 15, Math.atan(-3.0 / 11));

	@Test
	public void testRotate() {
		Point p1 = new Point(0, 0).rotate(role.centerX(), role.centerY(), role.getDirection());
		assertEquals(-2, p1.getX(), DELTA);
		assertEquals(2, p1.getY(), DELTA);
		Point p2 = new Point(0, 15).rotate(role.centerX(), role.centerY(), role.getDirection());
		assertEquals(2, p2.getX(), DELTA);
		assertEquals(16, p2.getY(), DELTA);
		Point p3 = new Point(10, 15).rotate(role.centerX(), role.centerY(), role.getDirection());
		assertEquals(12, p3.getX(), DELTA);
		assertEquals(13, p3.getY(), DELTA);
		Point p4 = new Point(5, 7).rotate(role.centerX(), role.centerY(), role.getDirection());
		assertEquals(5, p4.getX(), DELTA);
		assertEquals(7, p4.getY(), DELTA);
	}

	@Test
	public void testContainsPoint() {
		assertTrue(role.containsPoint(5, 7));
		assertTrue(role.containsPoint(5, 15));
		assertTrue(role.containsPoint(9, 4));
		assertTrue(role.containsPoint(-1, 2));
		assertFalse(role.containsPoint(0, 0));
		assertFalse(role.containsPoint(0, 15));
		assertFalse(role.containsPoint(10, 0));
		assertFalse(role.containsPoint(10, 15));
		assertFalse(role.containsPoint(3, 0));
		assertFalse(role.containsPoint(0, 10));
		assertFalse(role.containsPoint(1, 13));
		assertFalse(role.containsPoint(4, 16));
		assertFalse(role.containsPoint(-10, -16));
		assertFalse(role.containsPoint(100, -16));
	}

	@Test
	public void testRealBounds() {
		Role r = new RotatableStub(8, 0, 2, 20, Math.PI / 4);
		Point[] points = r.getBoundRect().getPoints();
		assertEquals(15.2, points[0].getX(), DELTA);
		assertEquals(2.2, points[0].getY(), DELTA);
		assertEquals(1.2, points[1].getX(), DELTA);
		assertEquals(16.2, points[1].getY(), DELTA);
		assertEquals(2.8, points[2].getX(), DELTA);
		assertEquals(17.8, points[2].getY(), DELTA);
		assertEquals(16.8, points[3].getX(), DELTA);
		assertEquals(3.8, points[3].getY(), DELTA);
	}

}
