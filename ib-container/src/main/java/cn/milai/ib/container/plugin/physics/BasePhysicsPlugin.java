package cn.milai.ib.container.plugin.physics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

import cn.milai.common.base.Collects;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.Listeners;
import cn.milai.ib.container.listener.PropertyMonitor;
import cn.milai.ib.container.plugin.ListenersPlugin;
import cn.milai.ib.role.Camp;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Collider;
import cn.milai.ib.role.property.Movable;
import cn.milai.ib.role.property.Rotatable;

/**
 * {@link PhysicsPlugin} 默认实现
 * @author milai
 * @date 2021.04.07
 */
public class BasePhysicsPlugin extends ListenersPlugin implements PhysicsPlugin {

	private static final int REGION_ROW = 2;
	private static final int REGION_COL = 2;

	private PropertyMonitor<Collider> colliders;
	private PropertyMonitor<Movable> movables;
	private List<Region> regions;
	private Map<RolePair, Boolean> collided;

	@Override
	protected void afterAddListeners() {
		colliders = PropertyMonitor.monitor(getContainer(), Collider.class);
		movables = PropertyMonitor.monitor(getContainer(), Movable.class);
		collided = new HashMap<>();
	}

	@Override
	protected List<ContainerListener> newListeners() {
		return Arrays.asList(Listeners.refreshListener(c -> {
			if (c.isPaused() || c.isPined()) {
				return;
			}
			long start = System.currentTimeMillis();
			run();
			metric(KEY_DELAY, System.currentTimeMillis() - start);
		}));
	}

	public void run() {
		initRegions();
		initCollided();
		Queue<Mover> movers = new PriorityBlockingQueue<Mover>(
			Collects.mapList(movables.getProps(), Mover::new)
		);
		Set<Mover> started = new HashSet<>();
		while (!movers.isEmpty()) {
			Mover now = movers.poll();
			Movable m = now.getMovable();
			if (started.add(now)) {
				m.beforeMove();
			}
			double nextFuelRatio = movers.isEmpty() ? 0 : movers.peek().getFuelRatio();
			if (!doMove(now, nextFuelRatio)) {
				movers.offer(now);
				continue;
			}
			m.afterMove();
		}
	}

	private List<Region> initRegions() {
		regions = new ArrayList<>();
		double regionW = 1.0 * getContainer().getW() / REGION_ROW;
		double regionH = 1.0 * getContainer().getH() / REGION_COL;
		for (int i = 0; i < REGION_ROW; i++) {
			for (int j = 0; j < REGION_COL; j++) {
				regions.add(new Region(i * regionW, j * regionH, regionW, regionH));
			}
		}
		for (Role r : colliders.getAll()) {
			refreshRegionOf(r);
		}
		return regions;
	}

	private void initCollided() {
		collided = new HashMap<>();
		for (Role r1 : colliders.getAll()) {
			for (Role r2 : colliders.getAll()) {
				if (r1 == r2) {
					continue;
				}
				collided.put(new RolePair(r1, r2), isCollided(r1, r2));
			}
		}
	}

	private Set<Role> sameRegion(Role r) {
		Set<Role> roles = new HashSet<>();
		for (Region region : regions) {
			if (region.has(r)) {
				roles.addAll(region.getRoles());
			}
		}
		return roles;
	}

	/**
	 * 移动指定 {@link Mover} ，返回是否移动完毕
	 * @param now
	 * @param nextFuelRatio 下一个待移动 {@link Mover} 的 fuelRatio
	 * @return
	 */
	private boolean doMove(Mover now, double nextFuelRatio) {
		Movable m = now.getMovable();
		Role r = m.getRole();
		if (!r.hasProperty(Collider.class)) {
			r.setX(r.getX() + m.getSpeedX());
			r.setY(r.getY() + m.getSpeedY());
			m.onMove();
			checkCollision(r);
			return true;
		}
		while (now.getFuelRatio() >= nextFuelRatio && now.getFuelRatio() != 0) {
			now.move();
			now.getMovable().onMove();
			checkCollision(r);
		}
		return now.getFuelRatio() == 0;
	}

	private void refreshRegionOf(Role r) {
		for (Region region : regions) {
			region.check(r);
		}
	}

	private void checkCollision(Role r1) {
		refreshRegionOf(r1);
		for (Role r2 : Collects.unfilterList(sameRegion(r1), r -> Camp.sameCamp(r1.getCamp(), r.getCamp()))) {
			if (!r1.isAlive()) {
				return;
			}
			if (!r2.isAlive()) {
				continue;
			}
			checkCollisionBetween(r1, r2);
		}
	}

	private void checkCollisionBetween(Role r1, Role r2) {
		Collider c1 = r1.getProperty(Collider.class);
		Collider c2 = r2.getProperty(Collider.class);
		boolean nowCollided = isCollided(r1, r2);
		RolePair key = new RolePair(r1, r2);
		if (nowCollided) {
			if (!collided.get(key)) {
				c1.onCollided(c2);
				c2.onCollided(c1);
			} else {
				c1.onTouching(c2);
				c2.onTouching(c1);
			}
		} else {
			c1.onLeave(c2);
			c2.onLeave(c1);
		}
		collided.put(key, nowCollided);
	}

	/**
	 * 判断两个角色是否发生碰撞
	 * @param r1
	 * @param r2
	 * @return
	 */
	public static boolean isCollided(Role r1, Role r2) {
		return Rotatable.getBoundRect(r1).intersects(Rotatable.getBoundRect(r2));
	}
}
