package cn.milai.ib.container.plugin.role;

import cn.milai.ib.container.plugin.ContainerPlugin;
import cn.milai.ib.container.plugin.MonitorPlugin;
import cn.milai.ib.container.plugin.metrics.MetrizablePlugin;
import cn.milai.ib.role.property.Movable;

/**
 * 实现 {@link Movable} 移动的 {@link ContainerPlugin}
 * @author milai
 * @date 2021.02.10
 */
public interface MovablePlugin extends MonitorPlugin<Movable>, MetrizablePlugin {

	@Override
	default String getCategory() { return "movable"; }

}
