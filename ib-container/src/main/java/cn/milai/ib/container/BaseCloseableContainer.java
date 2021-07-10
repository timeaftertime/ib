package cn.milai.ib.container;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import cn.milai.ib.item.Item;

/**
 * {@link CloseableContainer} 抽象实现
 * @author milai
 * @date 2021.02.04
 */
public class BaseCloseableContainer extends BaseContainer implements CloseableContainer {

	/**
	 * 容器是否已经关闭
	 */
	private AtomicBoolean closed = new AtomicBoolean();

	@Override
	public boolean close() {
		return closed.compareAndSet(false, true);
	}

	@Override
	public boolean isClosed() { return closed.get(); }

	@Override
	public final <T extends Item> List<T> getAll(Class<T> type) throws ContainerClosedException {
		checkClosed();
		return super.getAll(type);
	}

	@Override
	public final boolean addObject(Item obj) throws ContainerClosedException {
		checkClosed();
		return super.addObject(obj);
	}

	@Override
	public final boolean removeObject(Item obj) throws ContainerClosedException {
		checkClosed();
		return super.removeObject(obj);
	}

	@Override
	public void reset() throws ContainerClosedException {
		checkClosed();
		super.reset();
	}

}
