package cn.milai.ib.container.plugin;

import java.util.Collection;

import cn.milai.ib.container.listener.ItemListener;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * 容器插件
 * @author milai
 * @date 2021.02.08
 */
public interface ContainerPlugin extends LifecycleListener, ItemListener {

	/**
	 * 初始化当前 {@link ContainerPlugin}。多次调用将忽略
	 */
	void init();

	/**
	 * 关联到指定 {@link PluginableContainer}。
	 * @param c
	 * @param 是否关联成功
	 * @throws PluginDestroyedExpcetion 若插件已经销毁
	 */
	boolean plug(PluginableContainer c) throws PluginDestroyedExpcetion;

	/**
	 * 取消与指定 {@link PluginableContainer} 的关联。
	 * @param 是否取消关联成功
	 * @throws PluginDestroyedExpcetion 若插件已经销毁
	 */
	boolean unplug(PluginableContainer c) throws PluginDestroyedExpcetion;

	/**
	 * 获取当前已经关联的所有 {@link PluginableContainer}
	 * @return
	 */
	Collection<PluginableContainer> containers();

	/**
	 * 取消与所有 {@link PluginableContainer} 的关联并销毁当前 {@link ContainerPlugin}。重复调用将忽略
	 */
	void destroy();

}
