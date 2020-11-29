package cn.milai.ib.container;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cn.milai.ib.character.AbstractIBCharacter;
import cn.milai.ib.character.property.Rotatable;

public class CharacterAwareContainerTest {

	private static class RotatableCharacterStub extends AbstractIBCharacter implements Rotatable {

		private int width;
		private int height;

		public RotatableCharacterStub(int x, int y, int width, int height, double direction, Container container) {
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
			return getY() + height / 2.0;
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

	private Rotatable c1 = new RotatableCharacterStub(0, 0, 10, 15, Math.atan(-3.0 / 11), null);
	private Rotatable c2 = new RotatableCharacterStub(8, 1, 4, 6, 0, null);
	private Rotatable c3 = new RotatableCharacterStub(10, 12, 8, 2, Math.atan(-9.0 / 10), null);

	@Test
	public void testIsCrashed() {
		assertTrue(CharacterAwareContainer.isCrashed(c1, c1));
		assertTrue(CharacterAwareContainer.isCrashed(c2, c2));
		assertTrue(CharacterAwareContainer.isCrashed(c3, c3));
		assertTrue(CharacterAwareContainer.isCrashed(c1, c2) && CharacterAwareContainer.isCrashed(c2, c1));
		assertFalse(CharacterAwareContainer.isCrashed(c1, c3) || CharacterAwareContainer.isCrashed(c3, c1));
		assertFalse(CharacterAwareContainer.isCrashed(c2, c3) || CharacterAwareContainer.isCrashed(c2, c3));
	}
}