package cn.milai.ib.plugin.control;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import cn.milai.ib.actor.BaseActor;
import cn.milai.ib.actor.Controllable;
import cn.milai.ib.plugin.control.cmd.Cmd;

/**
 * 屏蔽所有指令的命令接收器
 * @author milai
 * @date 2020.03.26
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommandShield extends BaseActor implements Controllable {

	@Override
	public boolean exec(Cmd cmd) {
		return false;
	}

}
