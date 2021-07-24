package cn.milai.ib.role;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@link PlayerRole} 的抽象实现
 * @author milai
 * @date 2020.04.04
 */
public class BasePlayer implements Player {

	private AtomicBoolean up = new AtomicBoolean();
	private AtomicBoolean down = new AtomicBoolean();
	private AtomicBoolean left = new AtomicBoolean();
	private AtomicBoolean right = new AtomicBoolean();
	private AtomicBoolean a = new AtomicBoolean();

	@Override
	public boolean setUp() {
		return up.compareAndSet(false, true);
	}

	@Override
	public boolean clearUp() {
		return up.compareAndSet(true, false);
	}

	@Override
	public boolean setDown() {
		return down.compareAndSet(false, true);
	}

	@Override
	public boolean clearDown() {
		return down.compareAndSet(true, false);
	}

	@Override
	public boolean setLeft() {
		return left.compareAndSet(false, true);
	}

	@Override
	public boolean clearLeft() {
		return left.compareAndSet(true, false);
	}

	@Override
	public boolean setRight() {
		return right.compareAndSet(false, true);
	}

	@Override
	public boolean clearRight() {
		return right.compareAndSet(true, false);
	}

	@Override
	public boolean setA() {
		return a.compareAndSet(false, true);
	}

	@Override
	public boolean clearA() {
		return a.compareAndSet(true, false);
	}

	@Override
	public boolean isUp() { return up.get(); }

	@Override
	public boolean isDown() { return down.get(); }

	@Override
	public boolean isLeft() { return left.get(); }

	@Override
	public boolean isRight() { return right.get(); }

	@Override
	public boolean isA() { return a.get(); }

	@Override
	public void pushStatus(boolean createNew) {
		throw new UnsupportedOperationException();
	}

}
