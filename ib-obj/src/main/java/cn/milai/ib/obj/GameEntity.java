package cn.milai.ib.obj;

import java.awt.Graphics;

import cn.milai.ib.GameObject;
import cn.milai.ib.container.Container;
import cn.milai.ib.property.Alive;
import cn.milai.ib.property.Explosible;

/**
 * 有存活状态的游戏对象
 *
 * @author milai
 */
public abstract class GameEntity extends GameObject implements Alive {

	private int life;

	public GameEntity(int x, int y, int width, int height, int life, Container container) {
		super(x, y, width, height, container);
		this.life = life;
	}

	@Override
	public boolean isAlive() {
		return life > 0;
	}

	@Override
	public void toDead() {
		this.life = 0;
	}

	public synchronized void gainLife(int add) {
		if (add <= 0) {
			throw new IllegalArgumentException("add 必须大于 0");
		}
		life += add;
	}

	public synchronized void loseLife(int sub) {
		if (sub <= 0) {
			throw new IllegalArgumentException("sub 必须大于 0");
		}
		life -= sub;
		if (life < 0) {
			life = 0;
		}
	}

	public int getLife() {
		return life;
	}

	@Override
	public void onDead() {
		if (this instanceof Explosible) {
			getContainer().addGameObject(new Bomb(getX(), getY(), getContainer()));
		}
	}

	@Override
	public void paintWith(Graphics g) {
		if (!isAlive()) {
			return;
		}
		g.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);
	}

}
