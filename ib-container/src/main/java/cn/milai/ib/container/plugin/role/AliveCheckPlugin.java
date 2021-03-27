package cn.milai.ib.container.plugin.role;

import cn.milai.ib.container.plugin.MonitorPlugin;
import cn.milai.ib.container.plugin.metrics.MetrizablePlugin;
import cn.milai.ib.role.Role;

/**
 * 实现 {@link Role} 存活检测的插件
 * @author milai
 * @date 2021.02.10
 */
public interface AliveCheckPlugin extends MonitorPlugin<Role>, MetrizablePlugin {

	@Override
	default String getCategory() { return "aliveCheck"; }

}
