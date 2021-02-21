package cn.milai.ib.container;

import java.util.List;

import cn.milai.ib.IBObject;

/**
 * 可关闭的 {@link Container}
 * @author milai
 * @date 2021.02.04
 */
public interface CloseableContainer extends Container {

	/**
	 * 关闭容器
	 * @throws ContainerClosedException
	 */
	void close() throws ContainerClosedException;

	/**
	 * 容器是否已经关闭
	 * @return
	 */
	boolean isClosed();

	/**
	 * 向容器中添加游戏对象
	 * @param obj
	 * @return 是否实际添加对象
	 * @throws ContainerClosedException 若容器已被关闭
	 */
	@Override
	boolean addObject(IBObject obj) throws ContainerClosedException;

	/**
	 * 从容器中国移除游戏对象
	 * @param obj
	 * @return 是否实际移除对象
	 * @throws ContainerClosedException 若容器已被关闭
	 */
	@Override
	boolean removeObject(IBObject obj) throws ContainerClosedException;

	/**
	 * 获取容器中指定类型和其子类的所有实例
	 * @throws ContainerClosedException 若容器已被关闭
	 */
	@Override
	<C extends IBObject> List<C> getAll(Class<C> type) throws ContainerClosedException;

	/**
	 * 清空容器中所有对象。若容器已经关闭，将抛出 ContainerClosedException
	 * @throws ContainerCl@Override
	osedException
	 */
	@Override
	void reset() throws ContainerClosedException;

	/**
	 * 检查当前容器是否已经关闭，若已经关闭，抛出异常
	 * @throws ContainerClosedException
	 */
	default void checkClosed() throws ContainerClosedException {
		if (isClosed()) {
			throw new ContainerClosedException();
		}
	}
}
