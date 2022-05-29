package cn.milai.ib.stage.monitor;

import java.util.ArrayList;
import java.util.List;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.stage.Stage;

/**
 * {@link Stage} 某类型 {@link Actor} 的监听器，容器关闭后监听到的列表不会清空
 * @author milai
 * @date 2021.02.10
 */
public class TypeMonitor<T extends Actor> extends ActorMonitor {

	private TypeMonitor(Stage stage, Class<T> clazz) {
		super(stage, o -> clazz.isInstance(o));
	}

	public static <T extends Actor> TypeMonitor<T> monitor(Stage stage, Class<T> clazz) {
		return new TypeMonitor<>(stage, clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() { return (List<T>) new ArrayList<>(super.getAll()); }

}
