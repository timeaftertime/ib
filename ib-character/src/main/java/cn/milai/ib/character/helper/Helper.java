package cn.milai.ib.character.helper;

import cn.milai.ib.character.MovableIBCharacter;
import cn.milai.ib.character.Player;
import cn.milai.ib.constant.Camp;
import cn.milai.ib.container.Container;
import cn.milai.ib.property.CanCrash;
import cn.milai.ib.property.CanCrashed;
import cn.milai.ib.property.HasDamage;

/**
 * 援助道具
 * 
 * @author milai
 *
 */
public abstract class Helper extends MovableIBCharacter implements CanCrash {

	public Helper(int x, int y, Container container) {
		super(x, y, container);
	}

	@Override
	public void onCrash(CanCrashed crashed) {
		if (!(crashed instanceof Player)) {
			return;
		}
		toDead();
		makeFunction((Player) crashed);
	}

	public abstract void makeFunction(Player plane);

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
			getContainer().removeObject(this);
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
