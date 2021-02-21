package cn.milai.ib.util;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.LongStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.ib.IBObject;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.CloseableContainer;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.lifecycle.LifecycleListener;
import cn.milai.ib.container.listener.ObjectListener;

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
		LifecycleListener listener = new WaitFrames(container, frame, Thread.currentThread());
		container.addLifecycleListener(listener);
		long startFrame = container.getFrame();
		doWait(container, LongStream.iterate(frame, pre -> 1).iterator());
		long realFrame = container.getFrame() - startFrame;
		if (realFrame < frame) {
			LOG.warn("wait 被提前中断: expectedFrame = {}, realFrame = {}", frame, realFrame);
		}
		container.removeLifecycleListener(listener);
	}

	/**
	 * 使得当前线程等待到 obj 被从所属容器中移除、线程被中断或容器被关闭
	 * 若当前线程处于中断状态，将立即返回
	 * 返回前将清除中断状态
	 * @param obj
	 * @param 检查中断的间隔帧数
	 */
	public static void waitRemove(IBObject obj, long checkFrame) {
		Container container = obj.getContainer();
		ObjectListener listener = new WaitRemoved(obj, Thread.currentThread());
		container.addObjectListener(listener);
		doWait(container, LongStream.iterate(checkFrame, pre -> checkFrame).iterator());
		container.removeObjectListener(listener);
	}

	private static abstract class ThreadNotifier {
		private AtomicBoolean notified = new AtomicBoolean();
		private Thread thread;

		public ThreadNotifier(Thread thread) {
			this.thread = thread;
		}

		protected synchronized void notifyThread() {
			if (!notified.compareAndSet(false, true)) {
				return;
			}
			thread.interrupt();
		}

	}

	private static class WaitFrames extends ThreadNotifier implements LifecycleListener {

		private long frame;

		public WaitFrames(LifecycleContainer container, long frame, Thread thread) {
			super(thread);
			this.frame = frame;
		}

		@Override
		public void afterRefresh(LifecycleContainer container) {
			if (--frame <= 0) {
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

	private static class WaitRemoved extends ThreadNotifier implements ObjectListener {

		private IBObject target;

		public WaitRemoved(IBObject target, Thread thread) {
			super(thread);
			this.target = target;
		}

		@Override
		public void onObjectRemoved(List<IBObject> objs) {
			for (IBObject obj : objs) {
				if (this.target == obj) {
					((LifecycleContainer) obj.getContainer()).removeObjectListener(this);
					notifyThread();
				}
			}
		}

	}

	private static void doWait(Container c, Iterator<Long> checkInterval) {
		boolean isCloseable = CloseableContainer.class.isInstance(c);
		while (!Thread.interrupted()) {
			if (isCloseable && ((CloseableContainer) c).isClosed()) {
				break;
			}
			try {
				long millisec = (checkInterval.hasNext() ? checkInterval.next() : 1) * SystemConf.refreshMillisec();
				Thread.sleep(millisec);
			} catch (InterruptedException e) {
				// 确保当前线程若是被 listener 以外的地方提前中断，也会在退出时清除线程中断状态
				Thread.interrupted();
				return;
			}
		}
	}

}
