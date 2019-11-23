package cn.milai.ib.mode;

public abstract class GameMode extends Thread {

	@Override
	public final synchronized void start() {
		super.start();
	}
	
}
