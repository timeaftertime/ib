package cn.milai.ib.plugin;

import java.util.Set;

/**
 * 插件
 * @author milai
 * @date 2021.02.08
 */
public interface Plugin {

	/**
	 * 初始化当前 {@link Plugin}。
	 * 多次调用将忽略
	 */
	void init();

	/**
	 * 关联到指定 {@link Pluginable}。
	 * @param p
	 * @param 是否关联成功
	 */
	boolean plug(Pluginable<?> p);

	/**
	 * 取消与指定 {@link Pluginable} 的关联。
	 * @param p
	 * @return 是否取消关联成功
	 */
	boolean unplug(Pluginable<?> p);

	/**
	 * 获取当前已经关联的所有 {@link Pluginable}
	 * @return
	 */
	Set<Pluginable<?>> pluginables();

	/**
	 * 取消与所有 {@link Pluginable} 的关联并销毁当前 {@link Plugin}。
	 * 重复调用将忽略
	 */
	void destroy();

}
