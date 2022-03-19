package cn.milai.ib.container.plugin;

import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * 只能关联到一个 {@link PluginableContainer} 的 {@link ContainerPlugin} 
 * @author milai
 * @date 2022.03.18
 */
public interface ExclusiveContainerPlugin extends ContainerPlugin {

	/**
	 * 获取所关联的 {@link PluginableContainer}，没有关联时返回 null
	 * @return
	 */
	PluginableContainer container();
}
