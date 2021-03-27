package cn.milai.ib.role.helper;

import cn.milai.ib.role.Camp;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.CanCrash;
import cn.milai.ib.role.PlayerRole;

/**
 * 协助道具
 * @author milai
 * @date 2020.03.10
 */
public interface Helper extends Role, CanCrash {

	@Override
	default int getCamp() {
		return Camp.HELPER;
	}

	@Override
	default void onCrash(CanCrash crashed) {
		if (!(crashed instanceof PlayerRole)) {
			return;
		}
		toDead();
		makeFunction((PlayerRole) crashed);
	}

	/**
	 * 对 Player 产生效果，在 Player 获得当前道具是调用
	 * @param player
	 */
	void makeFunction(PlayerRole player);
}
