package cn.milai.ib.character;

/**
 * {@link PlayerCharacter} 的抽象实现
 * @author milai
 * @date 2020.04.04
 */
public class BasePlayer implements Player {

	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean a = false;

	@Override
	public void setUp() {
		up = true;
	}

	@Override
	public void clearUp() {
		up = false;
	}

	@Override
	public void setDown() {
		down = true;
	}

	@Override
	public void clearDown() {
		down = false;
	}

	@Override
	public void setLeft() {
		left = true;
	}

	@Override
	public void clearLeft() {
		left = false;
	}

	@Override
	public void setRight() {
		right = true;
	}

	@Override
	public void clearRight() {
		right = false;
	}

	@Override
	public void setA() {
		a = true;
	}

	@Override
	public void clearA() {
		a = false;
	}

	@Override
	public boolean isUp() {
		return up;
	}

	@Override
	public boolean isDown() {
		return down;
	}

	@Override
	public boolean isLeft() {
		return left;
	}

	@Override
	public boolean isRight() {
		return right;
	}

	@Override
	public boolean isA() {
		return a;
	}

	@Override
	public void pushStatus(boolean createNew) {
		throw new UnsupportedOperationException();
	}

}
