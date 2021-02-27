package cn.milai.ib.container.plugin;

import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * 容器插件
 * @author milai
 * @date 2021.02.08
 */
public interface ContainerPlugin {

	/**
	 * 获取插件最后作用于的容器
	 * @return
	 */
	PluginableContainer getContainer();

	/**
	 * 重置插件
	 */
	default void reset() {}

	/**
	 * 开始关联到指定 {@link PluginableContainer} 工作
	 * @return 是否真正进行了 start 动作
	 */
	boolean start(PluginableContainer container);

	/**
	 * 停止插件工作
	 * @return 是否真正进行 stop 操作
	 */
	boolean stop();

	/**
	 * 插件是否正在工作
	 * @return
	 */
	boolean isRunning();

}
