package cn.milai.ib.container.plugin.control;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.plugin.control.cmd.Cmd;
import cn.milai.ib.item.BaseItem;
import cn.milai.ib.item.Controllable;

/**
 * 暂停实现器
 * @author milai
 * @date 2021.05.15
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class PauseSwitcher extends BaseItem implements Controllable {

	@Override
	public boolean exec(Cmd cmd) {
		if (cmd.getType() == Cmd.PAUSE) {
			container().switchPause();
			return false;
		}
		return true;
	}

	@Override
	public LifecycleContainer container() {
		return (LifecycleContainer) super.container();
	}

}
