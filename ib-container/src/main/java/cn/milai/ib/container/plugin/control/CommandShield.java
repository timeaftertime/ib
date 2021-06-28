package cn.milai.ib.container.plugin.control;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import cn.milai.ib.container.plugin.control.cmd.Cmd;
import cn.milai.ib.obj.BaseObject;
import cn.milai.ib.obj.Controllable;

/**
 * 屏蔽所有指令的命令接收器
 * @author milai
 * @date 2020.03.26
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommandShield extends BaseObject implements Controllable {

	@Override
	public boolean exec(Cmd cmd) {
		return false;
	}

}
