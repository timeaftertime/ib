package cn.milai.ib.role.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cn.milai.ib.geometry.Point;
import cn.milai.ib.role.BaseRole;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.base.BaseRotatable;
import cn.milai.ib.role.property.holder.RotatableHolder;

public class RotatableTest {

	private static class RotatableStub extends BaseRole implements RotatableHolder {

		public RotatableStub(int x, int y, double w, double h, double direction) {
			resetBounds(x, y, w, h);
			setDirection(direction);
			setRotatable(new BaseRotatable());
		}
	}

	// 绕中心旋转 -15.26 度
	private RotatableHolder role = new RotatableStub(0, 0, 10, 15, Math.atan(-3.0 / 11));
	private Rotatable rotatable = role.getRotatable();

	@Test
	public void testRotate() {
		Role role = rotatable.owner();
		Point p1 = new Point(0, 0).rotate(role.centerX(), role.centerY(), role.getDirection());
		assertEquals(-2, p1.getX());
		assertEquals(2, p1.getY());
		Point p2 = new Point(0, 15).rotate(role.centerX(), role.centerY(), role.getDirection());
		assertEquals(2, p2.getX());
		assertEquals(16, p2.getY());
		Point p3 = new Point(10, 15).rotate(role.centerX(), role.centerY(), role.getDirection());
		assertEquals(12, p3.getX());
		assertEquals(13, p3.getY());
		Point p4 = new Point(5, 7).rotate(role.centerX(), role.centerY(), role.getDirection());
		assertEquals(5, p4.getX());
		assertEquals(7, p4.getY());
	}

	@Test
	public void testContainsPoint() {
		assertTrue(Rotatable.containsPoint(role, 5, 7));
		assertTrue(Rotatable.containsPoint(role, 5, 15));
		assertTrue(Rotatable.containsPoint(role, 9, 4));
		assertTrue(Rotatable.containsPoint(role, -1, 2));
		assertFalse(Rotatable.containsPoint(role, 0, 0));
		assertFalse(Rotatable.containsPoint(role, 0, 15));
		assertFalse(Rotatable.containsPoint(role, 10, 0));
		assertFalse(Rotatable.containsPoint(role, 10, 15));
		assertFalse(Rotatable.containsPoint(role, 3, 0));
		assertFalse(Rotatable.containsPoint(role, 0, 10));
		assertFalse(Rotatable.containsPoint(role, 1, 13));
		assertFalse(Rotatable.containsPoint(role, 4, 16));
		assertFalse(Rotatable.containsPoint(role, -10, -16));
		assertFalse(Rotatable.containsPoint(role, 100, -16));
	}

}
