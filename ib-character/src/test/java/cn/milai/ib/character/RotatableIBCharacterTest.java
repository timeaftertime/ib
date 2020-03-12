package cn.milai.ib.character;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Test;

import cn.milai.ib.character.property.Rotatable;
import cn.milai.ib.container.Container;

public class RotatableIBCharacterTest {

	private static class RotatableCharacterStub extends RotatableIBCharacter {

		private int width;
		private int height;
		private int speedX;
		private int speedY;

		public RotatableCharacterStub(int x, int y, int width, int height, int speedX, int speedY,
			Container container) {
			super(x, y, container);
			this.width = width;
			this.height = height;
			this.speedX = speedX;
			this.speedY = speedY;
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
		public int getSpeedX() {
			return speedX;
		}

		@Override
		public int getSpeedY() {
			return speedY;
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
		protected int proratedIntProp(String key) {
			return 0;
		}

		@Override
		protected int intProp(String key) {
			return 1;
		}
	}

	private Rotatable rotatable1 = new RotatableCharacterStub(0, 0, 10, 15, 3, 11, null);
	private Rotatable rotatable2 = new RotatableCharacterStub(8, 1, 4, 6, 0, 0, null);
	private Rotatable rotatable3 = new RotatableCharacterStub(10, 12, 8, 2, 9, 10, null);

	@Test
	public void testRotate() {
		Point p1 = rotatable1.rotate(new Point(0, 0));
		assertEquals(-2, p1.x);
		assertEquals(2, p1.y);
		Point p2 = rotatable1.rotate(new Point(0, 15));
		assertEquals(2, p2.x);
		assertEquals(16, p2.y);
		Point p3 = rotatable1.rotate(new Point(10, 15));
		assertEquals(12, p3.x);
		assertEquals(13, p3.y);
		Point p4 = rotatable1.rotate(new Point(5, 7));
		assertEquals(5, p4.x);
		assertEquals(7, p4.y);
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

	@Test
	public void testIntersects() {
		assertTrue(rotatable1.intersects(rotatable1));
		assertTrue(rotatable2.intersects(rotatable2));
		assertTrue(rotatable3.intersects(rotatable3));
		assertTrue(rotatable1.intersects(rotatable2) && rotatable2.intersects(rotatable1));
		assertFalse(rotatable1.intersects(rotatable3) || rotatable3.intersects(rotatable1));
		assertFalse(rotatable2.intersects(rotatable3) || rotatable3.intersects(rotatable2));
	}

}
