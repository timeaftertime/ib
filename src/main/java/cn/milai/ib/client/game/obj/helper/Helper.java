package cn.milai.ib.client.game.obj.helper;

import java.awt.Image;

import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.obj.MovableGameEntity;
import cn.milai.ib.client.game.obj.plane.Plane;
import cn.milai.ib.client.game.obj.property.CanCrash;
import cn.milai.ib.client.game.obj.property.CanCrashed;
import cn.milai.ib.client.game.obj.property.HasDamage;

/**
 * 
 * @author milai
 * 
 *         援助道具
 *
 */
public abstract class Helper extends MovableGameEntity implements CanCrash {

	public Helper(int x, int y, int width, int height, int speedX, int speedY, int life, Image img,
			GameForm container) {
		super(x, y, width, height, speedX, speedY, life, img, container);
	}

	@Override
	public void onCrash(CanCrashed crashed) {
		if (!(crashed instanceof Plane)) {
			return;
		}
		toDead();
		makeFunction((Plane) crashed);
	}

	public abstract void makeFunction(Plane plane);

	@Override
	public int getScore() {
		return 0;
	}

	@Override
	protected void afterMove() {
		removeIfOutOfOwner();
	}

	private void removeIfOutOfOwner() {
		if (getY() > getContainer().getHeight())
			getContainer().removeGameObject(this);
	}

	@Override
	public void damagedBy(HasDamage attacker) {
		throw new IllegalStateException("Helper 不接受攻击");
	}

	@Override
	public Camp getCamp() {
		return Camp.HELPER;
	}

}
