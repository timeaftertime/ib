package cn.milai.ib.container.plugin.control;

import java.util.List;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.Order;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.plugin.TypeMonitorPlugin;
import cn.milai.ib.container.plugin.control.cmd.Cmd;
import cn.milai.ib.item.Controllable;

/**
 * {@link ControlPlugin} 抽象实现
 * @author milai
 * @date 2021.06.04
 */
public class AbstractControlPlugin extends TypeMonitorPlugin<Controllable> implements ControlPlugin {

	public AbstractControlPlugin() {
		super(Controllable.class);
	}

	@Override
	public void addCmd(Cmd cmd) {}

	@Override
	public void clearCmds() {}

	/**
	 * 获取关联 {@link Container}中按 {@link Order} 排序后的 {@link Controllable} 列表
	 * @return
	 */
	protected List<Controllable> sortedControllables() {
		List<Controllable> all = getAll();
		AnnotationAwareOrderComparator.sort(all);
		return all;
	}

	@Override
	public void doReset() {
		clearCmds();
	}

	@Override
	protected void afterRemoveListeners() {
		clearCmds();
	}

}
