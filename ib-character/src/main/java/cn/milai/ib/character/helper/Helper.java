package cn.milai.ib.character.helper;

import cn.milai.ib.character.property.CanCrash;
import cn.milai.ib.obj.Camp;
import cn.milai.ib.obj.IBCharacter;
import cn.milai.ib.obj.Player;

/**
 * 协助道具
 * @author milai
 * @date 2020.03.10
 */
public interface Helper extends IBCharacter, CanCrash {

	@Override
	default Camp getCamp() {
		return Camp.HELPER;
	}

	@Override
	default void onCrash(CanCrash crashed) {
		if (!(crashed instanceof Player)) {
			return;
		}
		toDead();
		makeFunction((Player) crashed);
	}

	/**
	 * 对 Player 产生效果，在 Player 获得当前道具是调用
	 * @param player
	 */
	void makeFunction(Player player);
}
