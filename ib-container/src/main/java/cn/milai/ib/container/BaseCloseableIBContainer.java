package cn.milai.ib.container;

import java.util.List;

import cn.milai.ib.IBObject;

/**
 * {@link CloseableIBContainer} 抽象实现
 * @author milai
 * @date 2021.02.04
 */
public class BaseCloseableIBContainer extends BaseContainer implements CloseableIBContainer {

	/**
	 * 容器是否已经关闭
	 */
	private boolean closed = false;

	@Override
	public void close() {
		closed = true;
	}

	@Override
	public boolean isClosed() { return closed; }

	@Override
	public final <T extends IBObject> List<T> getAll(Class<T> type) throws ContainerClosedException {
		checkClosed();
		return super.getAll(type);
	}

	@Override
	public boolean addObject(IBObject obj) throws ContainerClosedException {
		checkClosed();
		return super.addObject(obj);
	}

	@Override
	public boolean removeObject(IBObject obj) throws ContainerClosedException {
		checkClosed();
		return super.removeObject(obj);
	}

	@Override
	public void reset() throws ContainerClosedException {
		checkClosed();
		super.reset();
	}

	@Override
	public void checkClosed() throws ContainerClosedException {
		if (isClosed()) {
			throw new ContainerClosedException();
		}
	}

}