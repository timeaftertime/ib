package cn.milai.ib.obj.character;

import java.awt.Graphics;
import java.awt.Image;
import java.lang.reflect.InvocationTargetException;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.IBObject;
import cn.milai.ib.property.Alive;
import cn.milai.ib.property.Explosible;

/**
 * 参与到游戏中的游戏角色
 *
 * @author milai
 */
public abstract class IBCharacter extends IBObject implements Alive {

	private static final String P_LIFE = "life";

	private int life;

	private Image img;

	public IBCharacter(int x, int y, Container container) {
		super(x, y, container);
		this.life = SystemConf.getInt(getClass(), P_LIFE);
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
	public void setImage(Image img) {
		this.img = img;
	}

	@Override
	public Image getImage() {
		return img == null ? (img = getImageLoader().loadImage(this.getClass(), getStatus())) : img;
	}

	@Override
	public void paintWith(Graphics g) {
		if (!isAlive()) {
			return;
		}
		super.paintWith(g);
	}

	@Override
	public void onDead() {
		if (this instanceof Explosible) {
			try {
				ExplosionCreator explosionCreator = ((Explosible) this).getExplosionCreator()
						.getConstructor(Explosible.class).newInstance(this);
				for (Explosion explosion : explosionCreator.createExplosions()) {
					getContainer().addObject(explosion);
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}

		}
	}

}
