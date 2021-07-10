package cn.milai.ib.container.plugin.physics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cn.milai.ib.role.BaseRole;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.base.BaseRotatable;
import cn.milai.ib.role.property.holder.RotatableHolder;

/**
 * {@link BaseCrashCheckPlugin} 测试类
 * @author milai
 * @date 2021.02.10
 */
public class BasePhysicsPluginTest {

	private static class RotatableStub extends BaseRole implements RotatableHolder {

		public RotatableStub(double x, double y, double w, double h, double direction) {
			setRotatable(new BaseRotatable());
			resetBounds(x, y, w, h);
			setDirection(direction);
		}

	}

	private Role r1 = new RotatableStub(0, 0, 10, 15, Math.atan(-3.0 / 11));
	private Role r2 = new RotatableStub(8, 1, 4, 6, 0);
	private Role r3 = new RotatableStub(10, 12, 8, 2, Math.atan(-9.0 / 10));

	private Role r4 = new RotatableStub(8, 0, 2, 20, Math.PI / 4);

	@Test
	public void testIsCrashed() {
		assertTrue(BasePhysicsPlugin.isCollided(r1, r1));
		assertTrue(BasePhysicsPlugin.isCollided(r2, r2));
		assertTrue(BasePhysicsPlugin.isCollided(r3, r3));
		assertTrue(BasePhysicsPlugin.isCollided(r1, r2) && BasePhysicsPlugin.isCollided(r2, r1));
		assertFalse(BasePhysicsPlugin.isCollided(r1, r3) || BasePhysicsPlugin.isCollided(r3, r1));
		assertFalse(BasePhysicsPlugin.isCollided(r2, r3) || BasePhysicsPlugin.isCollided(r2, r3));
	}

	@Test
	public void testNarrowCrashed() {
		assertTrue(BasePhysicsPlugin.isCollided(r4, new RotatableStub(11, 7, 1, 1, 0)));
		assertTrue(BasePhysicsPlugin.isCollided(r4, new RotatableStub(12, 6, 1, 1, 0)));
		assertTrue(BasePhysicsPlugin.isCollided(r4, new RotatableStub(7, 11, 1, 1, 0)));
		assertTrue(BasePhysicsPlugin.isCollided(r4, new RotatableStub(11, 5, 1, 1, 0)));
		assertFalse(BasePhysicsPlugin.isCollided(r4, new RotatableStub(8, 7, 1, 1, 0)));
		assertFalse(BasePhysicsPlugin.isCollided(r4, new RotatableStub(10, 5, 1, 1, 0)));
	}
}
