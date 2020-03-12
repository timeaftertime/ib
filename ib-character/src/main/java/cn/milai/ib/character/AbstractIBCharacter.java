package cn.milai.ib.character;

import java.awt.Graphics;

import cn.milai.ib.container.Container;
import cn.milai.ib.obj.AbstractIBObject;
import cn.milai.ib.obj.IBCharacter;

/**
 * IBCharacter 的抽象实现
 * @author milai
 */
public abstract class AbstractIBCharacter extends AbstractIBObject implements IBCharacter {

	private int life;

	private IBCharacter lastAttacker;

	public AbstractIBCharacter(int x, int y, Container container) {
		super(x, y, container);
		this.life = intProp(P_LIFE);
	}

	@Override
	public boolean isAlive() {
		return life > 0;
	}

	@Override
	public synchronized void toDead() {
		if (!isAlive()) {
			return;
		}
		lastAttacker = null;
		life = 0;
	}

	@Override
	public synchronized void gainLife(IBCharacter character, int life) {
		if (life < 0) {
			throw new IllegalArgumentException("life 必须大于 0");
		}
		if (!isAlive()) {
			return;
		}
		this.life += life;
	}

	@Override
	public synchronized void loseLife(IBCharacter character, int life) throws IllegalArgumentException {
		if (life < 0) {
			throw new IllegalArgumentException("life 必须大于 0");
		}
		if (!isAlive()) {
			return;
		}
		this.life -= life;
		if (this.life < 0) {
			this.life = 0;
		}
		lastAttacker = character;
	}

	@Override
	public IBCharacter getLastAttacker() {
		return lastAttacker;
	}

	@Override
	public int getLife() {
		return life;
	}

	@Override
	public void paintWith(Graphics g) {
		if (!isAlive()) {
			return;
		}
		super.paintWith(g);
	}

}
