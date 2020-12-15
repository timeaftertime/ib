package cn.milai.ib.character.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cn.milai.ib.character.AbstractIBCharacter;
import cn.milai.ib.container.ui.UIContainer;
import cn.milai.ib.geometry.Point;

public class RotatableTest {

	private static class RotatableStub extends AbstractIBCharacter implements Rotatable {

		private int width;
		private int height;

		public RotatableStub(int x, int y, int width, int height, double direction, UIContainer container) {
			super(x, y, container);
			this.width = width;
			this.height = height;
			setDirection(direction);
		}

		@Override
		public int getWidth() {
			return width;
		}

		@Override
		public int getHeight() {
			return height;
		}

		@Override
		public double getCenterX() {
			return getX() + width / 2.0;
		}

		@Override
		public double getCenterY() {
			return super.getY() + height / 2.0;
		}

		@Override
		protected int intProp(String key) {
			return 1;
		}
	}

	private Rotatable rotatable1 = new RotatableStub(0, 0, 10, 15, Math.atan(-3.0 / 11), null);

	@Test
	public void testRotate() {
		Point p1 = new Point(0, 0).rotate(rotatable1.getCenterX(), rotatable1.getCenterY(), rotatable1.getDirection());
		assertEquals(-2, p1.getX());
		assertEquals(2, p1.getY());
		Point p2 = new Point(0, 15).rotate(rotatable1.getCenterX(), rotatable1.getCenterY(), rotatable1.getDirection());
		assertEquals(2, p2.getX());
		assertEquals(16, p2.getY());
		Point p3 = new Point(10, 15).rotate(rotatable1.getCenterX(), rotatable1.getCenterY(), rotatable1
			.getDirection());
		assertEquals(12, p3.getX());
		assertEquals(13, p3.getY());
		Point p4 = new Point(5, 7).rotate(rotatable1.getCenterX(), rotatable1.getCenterY(), rotatable1.getDirection());
		assertEquals(5, p4.getX());
		assertEquals(7, p4.getY());
	}

	@Test
	public void testContainsPoint() {
		assertTrue(rotatable1.containsPoint(5, 7));
		assertTrue(rotatable1.containsPoint(5, 15));
		assertTrue(rotatable1.containsPoint(9, 4));
		assertTrue(rotatable1.containsPoint(-1, 2));
		assertFalse(rotatable1.containsPoint(0, 0));
		assertFalse(rotatable1.containsPoint(0, 15));
		assertFalse(rotatable1.containsPoint(10, 0));
		assertFalse(rotatable1.containsPoint(10, 15));
		assertFalse(rotatable1.containsPoint(3, 0));
		assertFalse(rotatable1.containsPoint(0, 10));
		assertFalse(rotatable1.containsPoint(1, 13));
		assertFalse(rotatable1.containsPoint(4, 16));
		assertFalse(rotatable1.containsPoint(-10, -16));
		assertFalse(rotatable1.containsPoint(100, -16));
	}

}
