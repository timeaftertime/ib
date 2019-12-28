package cn.milai.ib.drama;

import cn.milai.ib.container.Container;

/**
 * 剧情类
 * 2019.12.08
 * @author milai
 */
public class Drama {

	private boolean started = false;
	private DramaInterpreter interpreter;

	/**
	 * 加载并创建指定剧本
	 * @param dramaCode 剧本名
	 */
	public Drama(String dramaCode, Container container) {
		interpreter = new DramaInterpreter(dramaCode, container);
	}

	/**
	 * 开始剧本
	 */
	public synchronized void start() {
		if (started) {
			throw new IllegalStateException("一个剧本实例只能启动一次");
		}
		started = true;
		new Thread(interpreter).start();
	}

	/**
	 * 中止剧情
	 */
	public void interrupt() {
		interpreter.interrunpt();
	}

}
