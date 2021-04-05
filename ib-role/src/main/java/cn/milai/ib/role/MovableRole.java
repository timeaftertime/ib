package cn.milai.ib.role;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.role.property.Movable;
import cn.milai.ib.role.property.base.BaseMovable;
import cn.milai.ib.role.property.holder.MovableHolder;

/**
 * 可移动的游戏对象
 * @author milai
 */
public abstract class MovableRole extends AbstractRole implements MovableHolder {

	public MovableRole(double x, double y, LifecycleContainer container) {
		super(x, y, container);
	}

	@Override
	protected void initProperties() {
		super.initProperties();
		setMovable(new BaseMovable(this) {
			@Override
			protected void beforeRefreshSpeeds() {
				MovableRole.this.beforeRefreshSpeeds(this);
			}

			@Override
			protected void afterRefreshSpeeds() {
				MovableRole.this.afterRefreshSpeeds(this);
			}

			@Override
			public void onMove() {
				MovableRole.this.onMove(this);
			}

			@Override
			public void afterMove() {
				MovableRole.this.afterMove(this);
			}
		});
	}

	/**
	 * 移动后调用
	 * @param m
	 */
	protected void afterMove(Movable m) {}

	/**
	 * 开始计算移动速度前调用
	 * @param m
	 */
	protected void beforeRefreshSpeeds(Movable m) {}

	/**
	 * 完成移动速度计算后调用
	 * @param m
	 */
	protected void afterRefreshSpeeds(Movable m) {}

	/**
	 * 每次进行一次移动后调用
	 * @param m
	 */
	protected void onMove(Movable m) {}

}
