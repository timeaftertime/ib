package cn.milai.ib.role.helper;

import cn.milai.ib.role.Camp;
import cn.milai.ib.role.PlayerRole;
import cn.milai.ib.role.nature.holder.AwareMovableHolder;
import cn.milai.ib.role.nature.holder.ColliderHolder;

/**
 * 协助道具
 * @author milai
 * @date 2020.03.10
 */
public interface Helper extends ColliderHolder, AwareMovableHolder {

	@Override
	default int getCamp() { return Camp.HELPER; }

	/**
	 * 对 Player 产生效果，在 Player 获得当前道具是调用
	 * @param player
	 */
	void makeFunction(PlayerRole player);
}
