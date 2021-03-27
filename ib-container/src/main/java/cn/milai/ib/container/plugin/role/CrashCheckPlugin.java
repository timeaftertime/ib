package cn.milai.ib.container.plugin.role;

import cn.milai.ib.container.plugin.ContainerPlugin;
import cn.milai.ib.container.plugin.MonitorPlugin;
import cn.milai.ib.container.plugin.metrics.MetrizablePlugin;
import cn.milai.ib.role.property.CanCrash;

/**
 * 实现碰撞检测的 {@link ContainerPlugin}
 * @author milai
 * @date 2021.02.10
 */
public interface CrashCheckPlugin extends MonitorPlugin<CanCrash>, MetrizablePlugin {

	@Override
	default String getCategory() { return "crashCheck"; }

}
