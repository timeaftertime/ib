package cn.milai.ib.util;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.LifecycleContainer;
import cn.milai.ib.container.listener.ContainerEventListener;

/**
 * 等待同步相关工具类
 * @author milai
 */
public class WaitUtil {

	/**
	 * 使当前线程等待容器经过指定帧数或被中断
	 * 若当前线程处于中断状态，将立即返回
	 * 返回前将清除中断状态
	 * @param container 等待的容器
	 * @param frame 等待的帧数
	 */
	public static void wait(Container container, long frame) {
		waitInterruption(new CountDownInterrupter(container, frame, Thread.currentThread()), container, frame / 2);
	}

	private static void waitInterruption(ContainerEventListener listener, Container container, long checkInterval) {
		while (!Thread.interrupted() && !((LifecycleContainer) container).isClosed()) {
			try {
				Thread.sleep(checkInterval);
			} catch (InterruptedException e) {
				// 确保当前线程若是被 listener 以外的地方提前中断，也会在退出时清除线程中断状态
				container.removeEventListener(listener);
				Thread.interrupted();
				return;
			}
		}
	}

	/**
	 * 使得当前线程等待到 obj 被从所属容器中移除或线程被中断
	 * 若当前线程处于中断状态，将立即返回
	 * 返回前将清除中断状态
	 * @param obj
	 * @param 检查中断的间隔帧数
	 */
	public static void waitRemove(IBObject obj, long checkFrame) {
		waitInterruption(new RemoveObjInterrupter(obj, Thread.currentThread()), obj.getContainer(), checkFrame);
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

		public CountDownInterrupter(Container container, long frame, Thread thread) {
			super(thread);
			this.frame = frame;
			container.addEventListener(this);
		}

		@Override
		public void afterRefresh(Container container) {
			if (--frame <= 0) {
				container.removeEventListener(this);
				notifyThread();
			}
		}

		@Override
		public void afterEpochChanged(Container container) {
			notifyThread();
		}

	}

	private static class RemoveObjInterrupter extends ThreadNotifier implements ContainerEventListener {

		private IBObject obj;

		public RemoveObjInterrupter(IBObject obj, Thread thread) {
			super(thread);
			this.obj = obj;
			obj.getContainer().addEventListener(this);
		}

		@Override
		public void onObjectRemoved(IBObject obj) {
			if (this.obj == obj) {
				obj.getContainer().removeEventListener(this);
				notifyThread();
			}
		}

		@Override
		public void afterEpochChanged(Container container) {
			notifyThread();
		}

	}

}
