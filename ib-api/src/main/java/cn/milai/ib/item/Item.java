package cn.milai.ib.item;

import java.util.Map;

import cn.milai.ib.container.Container;
import cn.milai.ib.ex.ReinitializeException;
import cn.milai.ib.geometry.Bounds;
import cn.milai.ib.geometry.Layer;
import cn.milai.ib.item.property.Property;

/**
 * 容器项
 * @author milai
 * @date 2021.07.09
 */
public interface Item extends Bounds, Layer {

	/**
	 * 默认 {@link #getStatus()} 值
	 */
	String STATUS_DEFAULT = "default";

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
	 * 初始化，多次初始化时，若使用同一 {@link Container} 将忽略，否则 将抛出异常
	 * @param container
	 * @throws ReinitializeException 多次使用不同 {@link Container} 初始化
	 */
	void init(Container container) throws ReinitializeException;

	/**
	 * 设置指定类型属性为 {@link Property}
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
