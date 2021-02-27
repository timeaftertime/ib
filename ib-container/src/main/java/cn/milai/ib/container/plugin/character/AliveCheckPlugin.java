package cn.milai.ib.container.plugin.character;

import cn.milai.ib.character.IBCharacter;
import cn.milai.ib.container.plugin.MonitorPlugin;
import cn.milai.ib.container.plugin.metrics.MetrizablePlugin;

/**
 * 实现 {@link IBCharacter} 存活检测的插件
 * @author milai
 * @date 2021.02.10
 */
public interface AliveCheckPlugin extends MonitorPlugin<IBCharacter>, MetrizablePlugin {

	@Override
	default String getCategory() { return "aliveCheck"; }

}
