package cn.milai.ib.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.lifecycle.ContainerEventListener;
import cn.milai.ib.container.lifecycle.LifecycleContainer;

/**
 * 等待同步相关工具类
 * @author milai
 */
public class WaitUtil {

	private static final Logger LOG = LoggerFactory.getLogger(WaitUtil.class);

	/**
	 * 使当前线程等待容器经过指定帧数、被中断或容器被关闭
	 * 若当前线程处于中断状态，将立即返回
	 * 返回前将清除中断状态
	 * @param container 等待的容器
	 * @param frame 等待的帧数
	 */
	public static void wait(LifecycleContainer container, long frame) {
		waitInterruption(new CountDownInterrupter(container, frame, Thread.currentThread()), container, frame / 2);
	}

	private static void waitInterruption(ContainerEventListener listener, LifecycleContainer c, long checkInterval) {
		while (!Thread.interrupted() && !c.isClosed()) {
			try {
				Thread.sleep(checkInterval);
			} catch (InterruptedException e) {
				try {
					// 确保当前线程若是被 listener 以外的地方提前中断，也会在退出时清除线程中断状态
					c.removeEventListener(listener);
				} catch (ContainerClosedException e1) {
					LOG.warn("容器已经关闭", e1);
				}
				Thread.interrupted();
				return;
			}
		}
	}

	/**
	 * 使得当前线程等待到 obj 被从所属容器中移除、线程被中断或容器被关闭
	 * 若当前线程处于中断状态，将立即返回
	 * 返回前将清除中断状态
	 * @param obj
	 * @param 检查中断的间隔帧数
	 */
	public static void waitRemove(IBObject obj, long checkFrame) {
		waitInterruption(
			new RemoveObjInterrupter(obj, Thread.currentThread()),
			(LifecycleContainer) obj.getContainer(),
			checkFrame
		);
	}

	private static class ThreadNotifier {
		private boolean notified = false;
		private Thread thread;

		public ThreadNotifier(Thread thread) {
			this.thread = thread;
		}

		protected synchronized void notifyThread() {
			if (notified) {
				return;
			}
			thread.interrupt();
		}

	}

	private static class CountDownInterrupter extends ThreadNotifier implements ContainerEventListener {

		private long frame;

		public CountDownInterrupter(LifecycleContainer container, long frame, Thread thread) {
			super(thread);
			this.frame = frame;
			container.addEventListener(this);
		}

		@Override
		public void afterRefresh(LifecycleContainer container) {
			if (--frame <= 0) {
				container.removeEventListener(this);
				notifyThread();
			}
		}

		@Override
		public void afterEpochChanged(LifecycleContainer container) {
			notifyThread();
		}

		@Override
		public void onContainerClosed() {
			notifyThread();
		}

	}

	private static class RemoveObjInterrupter extends ThreadNotifier implements ContainerEventListener {

		private IBObject obj;

		public RemoveObjInterrupter(IBObject obj, Thread thread) {
			super(thread);
			this.obj = obj;
			Container c = obj.getContainer();
			if (!LifecycleContainer.class.isInstance(c)) {
				throw new IllegalArgumentException("对象所属容器必须为 " + LifecycleContainer.class);
			}
			((LifecycleContainer) c).addEventListener(this);
		}

		@Override
		public void onObjectRemoved(IBObject obj) {
			if (this.obj == obj) {
				((LifecycleContainer) obj.getContainer()).removeEventListener(this);
				notifyThread();
			}
		}

		@Override
		public void afterEpochChanged(LifecycleContainer container) {
			notifyThread();
		}

	}

}
