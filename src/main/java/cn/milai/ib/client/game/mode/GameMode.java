package cn.milai.ib.client.game.mode;

public abstract class GameMode extends Thread {

	@Override
	public final synchronized void start() {
		super.start();
	}
	
}
