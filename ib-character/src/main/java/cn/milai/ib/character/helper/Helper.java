package cn.milai.ib.character.helper;

import cn.milai.ib.character.Camp;
import cn.milai.ib.character.IBCharacter;
import cn.milai.ib.character.PlayerCharacter;
import cn.milai.ib.character.property.CanCrash;

/**
 * 协助道具
 * @author milai
 * @date 2020.03.10
 */
public interface Helper extends IBCharacter, CanCrash {

	@Override
	default int getCamp() {
		return Camp.HELPER;
	}

	@Override
	default void onCrash(CanCrash crashed) {
		if (!(crashed instanceof PlayerCharacter)) {
			return;
		}
		toDead();
		makeFunction((PlayerCharacter) crashed);
	}

	/**
	 * 对 Player 产生效果，在 Player 获得当前道具是调用
	 * @param player
	 */
	void makeFunction(PlayerCharacter player);
}
