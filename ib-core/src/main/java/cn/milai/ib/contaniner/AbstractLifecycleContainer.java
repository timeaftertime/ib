package cn.milai.ib.contaniner;

import java.util.List;

import com.google.common.collect.Lists;

import cn.milai.ib.container.Audio;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.LifecycleContainer;
import cn.milai.ib.container.listener.ContainerEventListener;
import cn.milai.ib.container.listener.ContainerLifecycleListener;
import cn.milai.ib.ex.IBContainerException;
import cn.milai.ib.obj.IBObject;

/**
 * Container 的抽象基类
 * @author milai
 * @date 2020.03.25
 */
public abstract class AbstractLifecycleContainer extends AbstractContainer implements LifecycleContainer {

	/**
	 * 容器是否已经启动
	 */
	private boolean started = false;

	/**
	 * 容器是否已经关闭
	 */
	private boolean closed = false;

	/**
	 * 游戏对象是否被固定住
	 */
	private volatile boolean pined;

	private List<ContainerLifecycleListener> lifecycleListeners;
	private ContainerRefreshThread refresher = new ContainerRefreshThread(this);
	private ContainerAudioPlayThread audioPlayer = new ContainerAudioPlayThread(this);

	public AbstractLifecycleContainer() {
		lifecycleListeners = Lists.newArrayList();
		pined = false;
	}

	@Override
	public final void resetContainer() {
		if (isClosed()) {
			throw new ContainerClosedException();
		}
		synchronized (lifecycleListeners) {
			lifecycleListeners.clear();
		}
		pined = false;
		// 确保重置后处于非暂停状态
		refresher.cancelPause();
		resetLifecycleContainer();
	}

	@Override
	public final <T> List<T> getAll(Class<T> type) throws IBContainerException {
		if (isClosed()) {
			throw new ContainerClosedException();
		}
		return super.getAll(type);
	}

	@Override
	public final void addObject(IBObject obj) throws IBContainerException {
		if (closed) {
			throw new ContainerClosedException();
		}
		super.addObject(obj);
	}

	@Override
	public final void removeObject(IBObject obj) throws IBContainerException {
		if (closed) {
			throw new ContainerClosedException();
		}
		super.removeObject(obj);
	}

	@Override
	public final void playAudio(Audio audio) throws IBContainerException {
		if (closed) {
			throw new ContainerClosedException();
		}
		if (audio == null) {
			return;
		}
		audioPlayer.addAudio(audio);
	}

	@Override
	public final void stopAudio(String code) throws IBContainerException {
		if (closed) {
			throw new ContainerClosedException();
		}
		audioPlayer.removeAudio(code);
	}

	@Override
	public final void addLifeCycleListener(ContainerLifecycleListener listener) {
		if (closed) {
			throw new ContainerClosedException();
		}
		synchronized (lifecycleListeners) {
			this.lifecycleListeners.add(listener);
		}
	}

	@Override
	public final void addEventListener(ContainerEventListener listener) {
		if (closed) {
			throw new ContainerClosedException();
		}
		super.addEventListener(listener);
	}

	@Override
	public final void start() {
		if (isClosed()) {
			throw new ContainerClosedException();
		}
		if (started) {
			return;
		}
		synchronized (this) {
			if (started) {
				return;
			}
			started = true;
		}
		refresher.start();
		audioPlayer.start();
		startContainer();
	}

	@Override
	public final void close() {
		if (isClosed()) {
			return;
		}
		closed = true;
		for (ContainerLifecycleListener listener : syncContainerLifecycleListeners()) {
			listener.onContainerClosed();
		}
		closeContainer();
	}

	@Override
	public final void switchPause() {
		if (isClosed()) {
			throw new ContainerClosedException();
		}
		refresher.switchPause();
	}

	@Override
	public final void setPined(boolean pined) throws ContainerClosedException {
		if (isClosed()) {
			throw new ContainerClosedException();
		}
		this.pined = pined;
	}

	@Override
	public boolean isClosed() {
		return closed;
	}

	@Override
	public boolean isPaused() {
		return refresher.isPaused();
	}

	/**
	 * 同步获取 ContainerLifecycleListener 列表的副本
	 * @return
	 */
	private List<ContainerLifecycleListener> syncContainerLifecycleListeners() {
		synchronized (lifecycleListeners) {
			return Lists.newArrayList(lifecycleListeners);
		}
	}

	/**
	 * 是否为 pined 状态
	 * @return
	 */
	protected boolean isPined() {
		return pined;
	}

	/**
	 * 进行实际的刷新动作
	 */
	protected abstract void doRefresh();

	/**
	 * 启动容器，{@link #start()} 被调用后调用
	 */
	protected abstract void startContainer();

	/**
	 * 关闭容器，在 {@link #close()} 被调用后调用
	 */
	protected abstract void closeContainer();

	/**
	 * 重置有生命周期的容器，在 {@link #resetContainer()} 被调用后调用
	 */
	protected abstract void resetLifecycleContainer();

}
