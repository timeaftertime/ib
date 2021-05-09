package cn.milai.ib.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.milai.common.thread.counter.BlockDownCounter;
import cn.milai.common.thread.counter.Counter;
import cn.milai.ib.IBObject;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.container.listener.ContainerListeners;
import cn.milai.ib.container.listener.ObjectListener;

/**
 * 等待同步相关工具类
 * @author milai
 */
public class Waits {

	private Waits() {}

	private static Map<Thread, Counter> registered = new ConcurrentHashMap<>();

	/**
	 * 使当前线程等待容器经过指定帧数或容器被关闭或重置
	 * 返回前将清除中断状态
	 * @param container 等待的容器
	 * @param frame 等待的帧数
	 */
	public static void wait(LifecycleContainer container, long frame) {
		Counter counter = registerCounter(frame);
		withListener(container, new RefreshCounter(container, counter), counter);
	}

	/**
	 * 使得当前线程等待到 obj 被从所属容器中移除或容器被关闭
	 * 返回前将清除中断状态
	 * @param obj
	 * @param 检查中断的间隔帧数
	 */
	public static void waitRemove(IBObject obj, long checkFrame) {
		Counter counter = registerCounter(1);
		withListener(obj.getContainer(), new RemoveCounter(counter, obj), counter);
	}

	private static Counter registerCounter(long cnt) {
		Counter counter = new BlockDownCounter(cnt);
		registered.put(Thread.currentThread(), counter);
		return counter;
	}

	/**
	 * 若指定线程正通过 {@link Waits} 等待某个 {@link Counter}，停止其等待
	 * @param thread
	 */
	public static void notify(Thread thread) {
		Counter counter = registered.get(thread);
		if (counter != null) {
			registered.remove(thread);
			counter.toMet();
		}
	}

	private static void withListener(Container container, ContainerListener listener, Counter counter) {
		ContainerListeners.add(container, listener);
		counter.await();
		ContainerListeners.remove(container, listener);
	}

	private static class ResetMonitor implements LifecycleListener {
		private Counter c;

		public ResetMonitor(Container container, Counter c) {
			this.c = c;
		}

		protected Counter getCounter() { return c; }

		@Override
		public void onEpochChanged(LifecycleContainer container) {
			c.toMet();
		}

		@Override
		public void onClosed(LifecycleContainer container) {
			c.toMet();
		}

	}

	private static class RefreshCounter extends ResetMonitor {

		public RefreshCounter(LifecycleContainer container, Counter c) {
			super(container, c);
		}

		@Override
		public void onRefresh(LifecycleContainer container) {
			getCounter().count();
		}

	}

	private static class RemoveCounter extends ResetMonitor implements ObjectListener {
		private IBObject target;

		public RemoveCounter(Counter counter, IBObject target) {
			super(target.getContainer(), counter);
			this.target = target;
		}

		@Override
		public void onObjectRemoved(Container container, List<IBObject> objs) {
			for (IBObject obj : objs) {
				if (this.target == obj) {
					getCounter().count();
				}
			}
		}
	}

}
