package cn.milai.ib.stage.monitor;

import java.util.List;

import cn.milai.common.collection.Mapping;
import cn.milai.ib.actor.Actor;
import cn.milai.ib.actor.nature.Nature;
import cn.milai.ib.stage.Stage;

/**
 * 监听有指定 {@link Nature} 的 {@link Actor} 的  {@link ActorMonitor}
 * @author milai
 * @date 2021.03.29
 */
public class PropertyMonitor<T extends Nature> extends ActorMonitor {

	private String name;

	protected PropertyMonitor(Stage stage, String name) {
		super(stage, o -> o.hasNature(name));
		this.name = name;
	}

	/**
	 * 构造一个监听指定 {@link Stage} 中含有指定 {@link Nature} 的 {@link PropertyMonitor}
	 * @param stage
	 * @param name
	 */
	public static <T extends Nature> PropertyMonitor<T> monitor(Stage stage, String name) {
		return new PropertyMonitor<>(stage, name);
	}

	/**
	 * 获取监听到的所有属性
	 * @return
	 */
	public List<T> getProps() { return Mapping.list(getAll(), r -> r.getNature(name)); };

	@Override
	public List<? extends Actor> getAll() { return super.getAll(); }

}
