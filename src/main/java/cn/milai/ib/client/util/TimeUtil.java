package cn.milai.ib.client.util;

import cn.milai.ib.client.game.form.BattleForm;
import cn.milai.ib.client.game.form.listener.RefreshListener;

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
		public void afterRefresh(BattleForm form) {
			frame--;
			if(frame <= 0) {
				form.removeRefreshListener(this);
				thread.interrupt();
			}
		}
	}
	
	/**
	 * 使当前线程 Thread 到窗体经过指定帧数
	 * 
	 * @param form 等待的窗体对象
	 * @param frame 等待的帧数
	 */
	public static void wait(BattleForm form, long frame) {
		form.addRefreshListener(new CountDownInterrupter(frame, Thread.currentThread()));
		while(!Thread.interrupted()) {
			try {
				Thread.sleep(DEFAULT_TIME_OUT);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
