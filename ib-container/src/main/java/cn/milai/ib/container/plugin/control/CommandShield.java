package cn.milai.ib.container.plugin.control;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import cn.milai.ib.Controllable;
import cn.milai.ib.InvisibleIBObject;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.plugin.control.cmd.Cmd;

/**
 * 屏蔽所有指令的命令接收器
 * @author milai
 * @date 2020.03.26
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommandShield extends InvisibleIBObject implements Controllable {

	public CommandShield(Container container) {
		super(0, 0, container);
	}

	@Override
	public boolean exec(Cmd cmd) {
		return false;
	}

}
