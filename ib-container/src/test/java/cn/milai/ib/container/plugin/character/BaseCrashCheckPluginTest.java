package cn.milai.ib.container.plugin.character;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.plugin.role.BaseCrashCheckPlugin;
import cn.milai.ib.role.AbstractRole;
import cn.milai.ib.role.property.Rotatable;

/**
 * {@link BaseCrashCheckPlugin} 测试类
 * @author milai
 * @date 2021.02.10
 */
public class BaseCrashCheckPluginTest {

	private static class RotatableCharacterStub extends AbstractRole implements Rotatable {

		private double width;
		private double height;

		public RotatableCharacterStub(int x, int y, int width, int height, double direction,
			LifecycleContainer container) {
			super(x, y, container);
			this.width = width;
			this.height = height;
			setDirection(direction);
		}

		@Override
		public double getW() { return width; }

		@Override
		public double getH() { return height; }

		@Override
		public double centerX() {
			return getIntX() + width / 2.0;
		}

		@Override
		public double centerY() {
			return getIntY() + height / 2.0;
		}

		@Override
		protected int intProp(String key) {
			return 1;
		}

		@Override
		protected double doubleProp(String key) {
			return 1;
		}
	}

	private Rotatable c1 = new RotatableCharacterStub(0, 0, 10, 15, Math.atan(-3.0 / 11), null);
	private Rotatable c2 = new RotatableCharacterStub(8, 1, 4, 6, 0, null);
	private Rotatable c3 = new RotatableCharacterStub(10, 12, 8, 2, Math.atan(-9.0 / 10), null);

	@Test
	public void testIsCrashed() {
		assertTrue(BaseCrashCheckPlugin.isCrashed(c1, c1));
		assertTrue(BaseCrashCheckPlugin.isCrashed(c2, c2));
		assertTrue(BaseCrashCheckPlugin.isCrashed(c3, c3));
		assertTrue(BaseCrashCheckPlugin.isCrashed(c1, c2) && BaseCrashCheckPlugin.isCrashed(c2, c1));
		assertFalse(BaseCrashCheckPlugin.isCrashed(c1, c3) || BaseCrashCheckPlugin.isCrashed(c3, c1));
		assertFalse(BaseCrashCheckPlugin.isCrashed(c2, c3) || BaseCrashCheckPlugin.isCrashed(c2, c3));
	}
}
