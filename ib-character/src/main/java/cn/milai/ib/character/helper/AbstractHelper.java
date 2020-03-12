package cn.milai.ib.character.helper;

import cn.milai.ib.character.MovableIBCharacter;
import cn.milai.ib.container.Container;

/**
 * Helper 的抽象基类
 * @author milai
 */
public abstract class AbstractHelper extends MovableIBCharacter implements Helper {

	public AbstractHelper(int x, int y, Container container) {
		super(x, y, container);
	}

	@Override
	protected void afterMove() {
		if (getY() > getContainer().getHeight()) {
			getContainer().removeObject(this);
		}
	}

}
