package cn.milai.ib.obj;

import java.util.Map;

import cn.milai.ib.container.Container;
import cn.milai.ib.ex.ReinitializeException;
import cn.milai.ib.geometry.Bounds;
import cn.milai.ib.geometry.Layer;
import cn.milai.ib.obj.property.Property;

/**
 * 所有游戏对象的接口
 * @author milai
 * @date 2020.02.20
 */
public interface IBObject extends Bounds, Layer {

	/**
	 * 默认 {@link #getStatus()} 值
	 */
	String DEFAULT_STATUS = "default";

	/**
	 * 获取当前状态
	 * @return
	 */
	String getStatus();

	/**
	 * 设置当前状态
	 * @param status
	 */
	void setStatus(String status);

	/**
	 * 获取所属 {@link Container}
	 * @return
	 */
	Container container();

	/**
	 * 初始化，多次初始化将抛出异常
	 * @param container
	 * @throws ReinitializeException 如果重复初始化
	 */
	void init(Container container) throws ReinitializeException;

	/**
	 * 设置指定类型属性为 {@link Property} 并调用 {@link Property#init()}
	 * @param <T>
	 * @param clazz
	 * @param p
	 */
	<T extends Property> void putProperty(Class<T> clazz, Property p);

	/**
	 * 获取指定类型 的 {@link Property}，若没有该 {@link Property}，返回 null
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	<T extends Property> T getProperty(Class<T> clazz);

	/**
	 * 获取是否有指定属性
	 * @param clazz
	 * @return
	 */
	default boolean hasProperty(Class<? extends Property> clazz) {
		return getProperty(clazz) != null;
	}

	/**
	 * 获取所有 name -> {@link Property} 的映射
	 * @return
	 */
	Map<String, Property> properties();

}
