package cn.milai.ib.container.plugin.physics;

import cn.milai.ib.container.plugin.ContainerPlugin;
import cn.milai.ib.container.plugin.metrics.MetrizablePlugin;

/**
 * 实现物理效果(移动、加速度、碰撞等)的 {@link ContainerPlugin}
 * @author milai
 * @date 2021.04.07
 */
public interface PhysicsPlugin extends MetrizablePlugin {

	@Override
	default String getCategory() { return "physics"; }
}
