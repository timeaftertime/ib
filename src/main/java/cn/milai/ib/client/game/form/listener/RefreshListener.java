package cn.milai.ib.client.game.form.listener;

import cn.milai.ib.client.game.form.BattleForm;

/**
 * 监听 @see BattleForm 的监听者
 * 
 * @author milai
 *
 */
public interface RefreshListener {
	
	/**
	 * 在游戏窗口完成一次刷新时调用
	 * 
	 * @param form 发生刷新的窗口
	 */
	void afterRefresh(BattleForm form);
}
