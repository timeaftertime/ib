package cn.milai.ib.container.plugin.control;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import cn.milai.ib.Controllable;
import cn.milai.ib.InvisibleIBObject;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.plugin.control.cmd.Cmd;

/**
 * 暂停实现器
 * @author milai
 * @date 2021.05.15
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class PauseSwitcher extends InvisibleIBObject implements Controllable {

	public PauseSwitcher(Container container) {
		super(0, 0, container);
	}

	@Override
	public boolean exec(Cmd cmd) {
		if (cmd.getType() == Cmd.PAUSE) {
			getContainer().switchPause();
			return false;
		}
		return true;
	}

	@Override
	public LifecycleContainer getContainer() { return (LifecycleContainer) super.getContainer(); }

}
