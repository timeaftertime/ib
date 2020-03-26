package cn.milai.ib.mode;

/**
 * GameMode 抽象基类
 * @author milai
 * @date 2020.03.24
 */
public abstract class AbstractGameMode extends Thread implements GameMode {

	@Override
	public synchronized void start() {
		init();
		super.start();
	}

}
