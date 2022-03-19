package cn.milai.ib.container.plugin.physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

import cn.milai.beginning.collection.Filter;
import cn.milai.beginning.collection.Mapping;
import cn.milai.ib.container.listener.ContainerListeners;
import cn.milai.ib.container.listener.RolePropertyMonitor;
import cn.milai.ib.container.plugin.BaseListenersPlugin;
import cn.milai.ib.container.pluginable.PluginableContainer;
import cn.milai.ib.geometry.BaseBounds;
import cn.milai.ib.geometry.Bounds;
import cn.milai.ib.geometry.Point;
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
public class BasePhysicsPlugin extends BaseListenersPlugin implements PhysicsPlugin {

	private static final int REGION_ROW = 2;
	private static final int REGION_COL = 2;

	private RolePropertyMonitor<Collider> colliders;
	private RolePropertyMonitor<Movable> movables;
	private List<Region> regions;
	private Map<RolePair, Boolean> collided;

	@Override
	protected void beforeAddListeners(PluginableContainer container) {
		addListener(ContainerListeners.refreshListener(c -> {
			if (c.isPaused() || c.isPined()) {
				return;
			}
			long start = System.currentTimeMillis();
			run();
			metric(KEY_DELAY, System.currentTimeMillis() - start);
		}));
	}

	@Override
	protected void afterAddListeners(PluginableContainer container) {
		colliders = RolePropertyMonitor.monitorRoles(container, Collider.class);
		movables = RolePropertyMonitor.monitorRoles(container, Movable.class);
		collided = new HashMap<>();
	}

	public void run() {
		initRegions();
		initCollided();
		Queue<Mover> movers = new PriorityBlockingQueue<Mover>(
			Mapping.list(movables.getProps(), Mover::new)
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
		Bounds bounds = buildNowBounds(colliders.getAll());
		double regionW = bounds.getW() / REGION_ROW;
		double regionH = bounds.getH() / REGION_COL;
		for (int i = 0; i < REGION_ROW; i++) {
			for (int j = 0; j < REGION_COL; j++) {
				regions.add(new Region(bounds.getX() + i * regionW, bounds.getY() + j * regionH, regionW, regionH));
			}
		}
		for (Role r : colliders.getAll()) {
			refreshRegionOf(r);
		}
		return regions;
	}

	private Bounds buildNowBounds(List<Role> roles) {
		if (roles.isEmpty()) {
			return new BaseBounds();
		}
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;
		for (Role r : roles) {
			for (Point p : Rotatable.getBoundRect(r).getPoints()) {
				if (p.getX() < minX) {
					minX = p.getX();
				}
				if (p.getY() < minY) {
					minY = p.getY();
				}
				if (p.getX() > maxX) {
					maxX = p.getX();
				}
				if (p.getY() > maxY) {
					maxY = p.getY();
				}
			}
		}
		return new BaseBounds(minX, minY, maxX - minX, maxY - minY);
	}

	private void initCollided() {
		Map<RolePair, Boolean> pre = collided;
		collided = new HashMap<>();
		for (Role r1 : colliders.getAll()) {
			for (Role r2 : colliders.getAll()) {
				if (r1 == r2) {
					continue;
				}
				RolePair key = new RolePair(r1, r2);
				collided.put(key, pre.getOrDefault(key, false));
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
		while (now.getFuelRatio() >= nextFuelRatio && now.getFuelRatio() != 0) {
			now.move();
			m.onMove();
			checkCollision(m.owner());
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
		for (Role r2 : Filter.nlist(sameRegion(r1), r -> Camp.sameCamp(r1.getCamp(), r.getCamp()))) {
			if (r1 == r2) {
				continue;
			}
			if (!r1.getHealth().isAlive()) {
				return;
			}
			if (!r2.getHealth().isAlive()) {
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
			if (!collided.getOrDefault(key, false)) {
				c1.onCollided(c2);
				c2.onCollided(c1);
			} else {
				c1.onTouching(c2);
				c2.onTouching(c1);
			}
		} else if (collided.getOrDefault(key, false)) {
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
