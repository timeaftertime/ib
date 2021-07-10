package cn.milai.ib.container;

import java.util.List;

import cn.milai.ib.container.listener.ObjectListener;
import cn.milai.ib.item.Item;

/**
 * 游戏对象的容器
 * 2019.11.16
 * @author milai
 */
public interface Container {

	/**
	 * 向容器中添加游戏对象并设置其所属容器为当前容器。
	 * 同一对象不会被重复添加，返回是否实际添加成功
	 * @param obj
	 */
	boolean addObject(Item obj);

	/**
	 * 从容器中移除游戏对象并设置其所属容器为 {@code null}，返回是否实际移除成功
	 * @param obj
	 */
	boolean removeObject(Item obj);

	/**
	 * 获得容器中属于指定类型及其子类的对象列表
	 * @param type
	 * @return
	 */
	<T extends Item> List<T> getAll(Class<T> type);

	/**
	 * 清空容器中所有对象
	 */
	void reset();

	/**
	 * 获取容器实际宽度
	 * @return
	 */
	int getW();

	/**
	 * 获取容器实际长度
	 * @return
	 */
	int getH();

	/**
	 * 重新设置容器的宽度和高度
	 * @param width
	 * @param height
	 */
	void newSize(int width, int height);

	/**
	 * 添加一个容器对象监听器
	 * @param listener
	 */
	void addObjectListener(ObjectListener listener);

	/**
	 * 移除一个容器对象监听器
	 * @param listener
	 */
	void removeObjectListener(ObjectListener listener);
}
