package cn.milai.ib.plugin.control;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import cn.milai.ib.actor.BaseActor;
import cn.milai.ib.actor.Controllable;
import cn.milai.ib.plugin.control.cmd.Cmd;

/**
 * 暂停实现器
 * @author milai
 * @date 2021.05.15
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class PauseSwitcher extends BaseActor implements Controllable {

	@Override
	public boolean exec(Cmd cmd) {
		if (cmd.getType() == Cmd.PAUSE) {
			stage().switchPause();
			return false;
		}
		return true;
	}

}