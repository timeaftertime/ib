package cn.milai.ib.container.pluginable;

import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.plugin.ContainerPlugin;

/**
 * {@link PluginableContainer} 插件相关事件的监听器
 * @author milai
 * @date 2021.02.27
 */
public interface PluginListener extends ContainerListener {

	/**
	 * 插件被添加到容器中后调用
	 * @param container 被添加到的容器
	 * @param plugin 被添加的插件
	 */
	default void onPluginAdded(PluginableContainer container, ContainerPlugin plugin) {}

	/**
	 * 插件从容器中移除后调用
	 * @param container 被从移除的容器
	 * @param plugin 被添加的插件
	 */
	default void onPluginRemoved(PluginableContainer container, ContainerPlugin plugin) {}

}
