package cn.milai.ib.plugin.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.Order;

import cn.milai.ib.Roster;
import cn.milai.ib.actor.Controllable;
import cn.milai.ib.plugin.BaseExclusiveCrew;
import cn.milai.ib.plugin.control.cmd.Cmd;
import cn.milai.ib.stage.Stage;

/**
 * {@link ControlCrew} 抽象实现
 * @author milai
 * @date 2021.06.04
 */
public class AbstractControlCrew extends BaseExclusiveCrew implements ControlCrew {

	@Override
	public void addCmd(Cmd cmd) {
	}

	@Override
	public void clearCmds() {
	}

	/**
	 * 获取关联 {@link Roster}中按 {@link Order} 排序后的 {@link Controllable} 列表
	 * @return
	 */
	protected List<Controllable> sortedControllables(Stage stage) {
		List<Controllable> all = new ArrayList<>(stage.getAll(Controllable.class));
		AnnotationAwareOrderComparator.sort(all);
		return all;
	}

}
