package cn.milai.ib.stage;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import cn.milai.ib.conf.IBConf;
import cn.milai.ib.lifecycle.Lifecycle;
import cn.milai.ib.lifecycle.LifecycleListener;
import cn.milai.ib.lifecycle.LifecycleLoop;

/**
 * {@link Lifecycle} 的默认实现
 * @author milai
 * @date 2020.03.25
 */
public class BaseLifecycle implements Lifecycle {

	/**
	 * 纪元，即调用 {@link #reset()} 的次数
	 */
	private AtomicInteger epoch = new AtomicInteger(1);

	/**
	 * 帧数，即容器启动到现在刷新的次数
	 */
	private AtomicLong frame = new AtomicLong();

	/**
	 * 容器是否已经启动
	 */
	private AtomicBoolean started = new AtomicBoolean();

	/**
	 * 容器是否已经关闭
	 */
	private AtomicBoolean closed = new AtomicBoolean();

	/**
	 * 是否正在运行
	 */
	private boolean running = false;

	/**
	 * 上次开始刷新的毫秒数
	 */
	private long lastStartMillisec;

	private LifecycleListener target;

	private LifecycleLoop loop;

	private final Unsafe unsafe = new BaseUnsafe();

	public BaseLifecycle(LifecycleListener target) {
		this.target = target;
	}

	@Override
	public boolean isClosed() { return closed.get(); }

	@Override
	public long getFrame() { return frame.get(); }

	@Override
	public int getEpoch() { return epoch.get(); }

	@Override
	public final void reset() {
		target.onReseted(this, epoch.incrementAndGet());
	}

	@Override
	public final void start() {
		if (!started.compareAndSet(false, true)) {
			return;
		}
		target.onStarted(this);
		running = true;
	}

	@Override
	public void refresh() {
		if (!isRunning()) {
			return;
		}
		lastStartMillisec = System.currentTimeMillis();
		target.onRefreshed(this, frame.incrementAndGet());
	}

	@Override
	public final boolean close() {
		running = false;
		if (closed.compareAndSet(false, true)) {
			loop().unregister(this);
			target.onClosed(this);
			return true;
		}
		return false;
	}

	@Override
	public Unsafe unsafe() {
		return unsafe;
	}

	@Override
	public boolean isRunning() { return running; }

	@Override
	public LifecycleLoop loop() {
		return loop;
	}

	@Override
	public long nextRefreshTime() {
		return lastStartMillisec + IBConf.refreshMillisec();
	}

	private class BaseUnsafe implements Unsafe {
		@Override
		public void register(LifecycleLoop loop) {
			if (BaseLifecycle.this.loop != null) {
				throw new IllegalStateException("已绑定 LifecycleLoop: " + BaseLifecycle.this.loop);
			}
			start();
			BaseLifecycle.this.loop = loop;
		}

		@Override
		public void unregister() {
			BaseLifecycle.this.loop = null;
		}
	}

}
