package cn.milai.ib.character.weapon.bullet;

import cn.milai.ib.Paintable;
import cn.milai.ib.character.IBCharacter;
import cn.milai.ib.character.MovableIBCharacter;
import cn.milai.ib.character.property.CanCrash;

/**
 * 子弹类游戏对象的抽象基类
 * @author milai
 */
public abstract class AbstractBullet extends MovableIBCharacter implements Bullet {

	public static final String P_SPEED = "speed";

	private IBCharacter owner;

	protected AbstractBullet(int centerX, int centerY, IBCharacter owner) {
		super(centerX, centerY, owner.getContainer());
		this.owner = owner;
		setDirection(owner.getDirection());
		setSpeedX((int) (getSpeed() * Math.sin(owner.getDirection())));
		setSpeedY((int) (-getSpeed() * Math.cos(owner.getDirection())));
	}

	@Override
	public IBCharacter getOwner() {
		return owner;
	}

	@Override
	protected void afterMove() {
		if (outOfContainer()) {
			toDead();
		}
	}

	private boolean outOfContainer() {
		if (this.getX() > getContainer().getWidth()) {
			return true;
		}
		if (this.getY() > getContainer().getHeight()) {
			return true;
		}
		if (this.getX() + getWidth() < 0) {
			return true;
		}
		if (this.getY() + this.getHeight() < 0) {
			return true;
		}
		return false;
	}

	@Override
	protected int initRatedSpeedX() {
		return getSpeed();
	}

	@Override
	protected int initRatedSpeedY() {
		return getSpeed();
	}

	/**
	 * 获取子弹前进速度
	 * @return
	 */
	protected int getSpeed() {
		return intProp(P_SPEED);
	}

	@Override
	public int getCamp() {
		return owner.getCamp();
	}

	@Override
	public void onCrash(CanCrash crashed) {
		if (crashed instanceof Bullet) {
			return;
		}
		crashed.loseLife(this, getDamage());
	}

	@Override
	public int getDamage() {
		return intProp(P_POWER);
	}

	@Override
	public int getZ() {
		return Paintable.DEFAULT_Z - 1;
	}
}
