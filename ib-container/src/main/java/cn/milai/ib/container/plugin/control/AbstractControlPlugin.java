package cn.milai.ib.container.plugin.control;

import java.util.List;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.Order;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.plugin.BaseExclusiveContainerPlugin;
import cn.milai.ib.container.plugin.control.cmd.Cmd;
import cn.milai.ib.container.pluginable.PluginableContainer;
import cn.milai.ib.item.Controllable;

/**
 * {@link ControlPlugin} 抽象实现
 * @author milai
 * @date 2021.06.04
 */
public class AbstractControlPlugin extends BaseExclusiveContainerPlugin implements ControlPlugin {

	@Override
	public void addCmd(Cmd cmd) {
	}

	@Override
	public void clearCmds() {
	}

	/**
	 * 获取关联 {@link Container}中按 {@link Order} 排序后的 {@link Controllable} 列表
	 * @return
	 */
	protected List<Controllable> sortedControllables(PluginableContainer container) {
		List<Controllable> all = container.getAll(Controllable.class);
		AnnotationAwareOrderComparator.sort(all);
		return all;
	}

}
