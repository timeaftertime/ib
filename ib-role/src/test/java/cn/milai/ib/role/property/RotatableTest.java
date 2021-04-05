package cn.milai.ib.role.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.geometry.Point;
import cn.milai.ib.role.AbstractRole;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.holder.RotatableHolder;

public class RotatableTest {

	private static class RotatableStub extends AbstractRole implements RotatableHolder {

		private int width;
		private int height;

		public RotatableStub(int x, int y, int width, int height, double direction, LifecycleContainer container) {
			super(x, y, container);
			this.width = width;
			this.height = height;
			setDirection(direction);
			setRotatable(new Rotatable() {
				@Override
				public Role getRole() { return RotatableStub.this; }
			});
		}

		@Override
		public int getIntW() { return width; }

		@Override
		public int getIntH() { return height; }

		@Override
		public double centerX() {
			return getIntX() + width / 2.0;
		}

		@Override
		public double centerY() {
			return super.getIntY() + height / 2.0;
		}

		@Override
		public double doubleProp(String key) {
			return 1;
		}

		@Override
		public int intProp(String key) {
			return 1;
		}

	}

	private Rotatable rotatable = new RotatableStub(0, 0, 10, 15, Math.atan(-3.0 / 11), null).rotatable();

	@Test
	public void testRotate() {
		Role role = rotatable.getRole();
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
		assertTrue(rotatable.containsPoint(5, 7));
		assertTrue(rotatable.containsPoint(5, 15));
		assertTrue(rotatable.containsPoint(9, 4));
		assertTrue(rotatable.containsPoint(-1, 2));
		assertFalse(rotatable.containsPoint(0, 0));
		assertFalse(rotatable.containsPoint(0, 15));
		assertFalse(rotatable.containsPoint(10, 0));
		assertFalse(rotatable.containsPoint(10, 15));
		assertFalse(rotatable.containsPoint(3, 0));
		assertFalse(rotatable.containsPoint(0, 10));
		assertFalse(rotatable.containsPoint(1, 13));
		assertFalse(rotatable.containsPoint(4, 16));
		assertFalse(rotatable.containsPoint(-10, -16));
		assertFalse(rotatable.containsPoint(100, -16));
	}

}
