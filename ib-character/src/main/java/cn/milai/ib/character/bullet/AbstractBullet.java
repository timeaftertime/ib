package cn.milai.ib.character.bullet;

import cn.milai.ib.character.MovableIBCharacter;
import cn.milai.ib.character.property.CanCrashed;
import cn.milai.ib.character.property.Shootable;
import cn.milai.ib.constant.Camp;
import cn.milai.ib.obj.Paintable;

/**
 * 子弹类游戏对象的抽象基类
 * @author milai
 */
public abstract class AbstractBullet extends MovableIBCharacter implements Bullet {

	private Shootable owner;
	private int power;

	protected AbstractBullet(int centerX, int centerY, Shootable owner) {
		super(centerX, centerY, owner.getContainer());
		this.power = intProp(P_POWER);
		this.owner = owner;
	}

	@Override
	public Shootable getOwner() {
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
	public Camp getCamp() {
		return owner.getCamp();
	}

	@Override
	public void onCrash(CanCrashed crashed) {
		crashed.loseLife(this, getDamage());
	}

	@Override
	public int getDamage() {
		return power;
	}

	@Override
	public int getPaintLayer() {
		return Paintable.BULLET_LAYER;
	}
}
