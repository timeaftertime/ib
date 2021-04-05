package cn.milai.ib.container.plugin.character;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.plugin.role.BaseCrashCheckPlugin;
import cn.milai.ib.role.AbstractRole;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Rotatable;
import cn.milai.ib.role.property.holder.RotatableHolder;

/**
 * {@link BaseCrashCheckPlugin} 测试类
 * @author milai
 * @date 2021.02.10
 */
public class BaseCrashCheckPluginTest {

	private static class RotatableRoleStub extends AbstractRole implements RotatableHolder {

		private double width;
		private double height;

		public RotatableRoleStub(int x, int y, int w, int h, double direction, LifecycleContainer container) {
			super(x, y, container);
			this.width = w;
			this.height = h;
			setDirection(direction);
			setRotatable(new Rotatable() {
				@Override
				public Role getRole() { return RotatableRoleStub.this; }
			});
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
		public int intProp(String key) {
			return 1;
		}

		@Override
		public double doubleProp(String key) {
			return 1;
		}
	}

	private Role r1 = new RotatableRoleStub(0, 0, 10, 15, Math.atan(-3.0 / 11), null);
	private Role r2 = new RotatableRoleStub(8, 1, 4, 6, 0, null);
	private Role r3 = new RotatableRoleStub(10, 12, 8, 2, Math.atan(-9.0 / 10), null);

	@Test
	public void testIsCrashed() {
		assertTrue(BaseCrashCheckPlugin.isCrashed(r1, r1));
		assertTrue(BaseCrashCheckPlugin.isCrashed(r2, r2));
		assertTrue(BaseCrashCheckPlugin.isCrashed(r3, r3));
		assertTrue(BaseCrashCheckPlugin.isCrashed(r1, r2) && BaseCrashCheckPlugin.isCrashed(r2, r1));
		assertFalse(BaseCrashCheckPlugin.isCrashed(r1, r3) || BaseCrashCheckPlugin.isCrashed(r3, r1));
		assertFalse(BaseCrashCheckPlugin.isCrashed(r2, r3) || BaseCrashCheckPlugin.isCrashed(r2, r3));
	}
}
