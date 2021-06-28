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

	private static class RotatableRoleStub extends BaseRole implements RotatableHolder {

		public RotatableRoleStub(int x, int y, double w, double h, double direction) {
			resetBounds(x, y, w, h);
			setDirection(direction);
			setRotatable(new BaseRotatable());
		}

	}

	private Role r1 = new RotatableRoleStub(0, 0, 10, 15, Math.atan(-3.0 / 11));
	private Role r2 = new RotatableRoleStub(8, 1, 4, 6, 0);
	private Role r3 = new RotatableRoleStub(10, 12, 8, 2, Math.atan(-9.0 / 10));

	@Test
	public void testIsCrashed() {
		assertTrue(BasePhysicsPlugin.isCollided(r1, r1));
		assertTrue(BasePhysicsPlugin.isCollided(r2, r2));
		assertTrue(BasePhysicsPlugin.isCollided(r3, r3));
		assertTrue(BasePhysicsPlugin.isCollided(r1, r2) && BasePhysicsPlugin.isCollided(r2, r1));
		assertFalse(BasePhysicsPlugin.isCollided(r1, r3) || BasePhysicsPlugin.isCollided(r3, r1));
		assertFalse(BasePhysicsPlugin.isCollided(r2, r3) || BasePhysicsPlugin.isCollided(r2, r3));
	}
}
