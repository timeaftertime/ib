package cn.milai.ib.util;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.listener.RefreshListener;

public class TimeUtil {

	private static final int DEFAULT_TIME_OUT = 10000;

	private static class CountDownInterrupter implements RefreshListener {

		private long frame;
		private Thread thread;

		public CountDownInterrupter(long frame, Thread thread) {
			this.frame = frame;
			this.thread = thread;
		}

		@Override
		public void afterRefresh(Container container) {
			frame--;
			if (frame <= 0) {
				container.removeRefreshListener(this);
				thread.interrupt();
			}
		}
	}

	/**
	 * 使当前线程 Thread 到窗体经过指定帧数
	 * 
	 * @param container 等待的窗体对象
	 * @param frame 等待的帧数
	 */
	public static void wait(Container container, long frame) {
		container.addRefreshListener(new CountDownInterrupter(frame, Thread.currentThread()));
		while (!Thread.interrupted()) {
			try {
				Thread.sleep(DEFAULT_TIME_OUT);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
