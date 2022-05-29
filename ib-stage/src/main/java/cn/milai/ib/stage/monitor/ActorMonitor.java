package cn.milai.ib.stage.monitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.stage.Stage;
import cn.milai.ib.stage.event.AddActorEvent;
import cn.milai.ib.stage.event.RemoveActorEvent;

/**
 * {@link Stage} 监听器
 * @author milai
 * @date 2021.03.28
 */
public class ActorMonitor {

	private Set<Actor> monitored = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private Consumer<AddActorEvent> onAddActor;
	private Consumer<RemoveActorEvent> onRemoveActor;
	private Stage stage;

	/**
	 * 创建一个监听指定中期中所有满足指定 {@link Predicate} 的对象的 {@link ActorMonitor} 并开始监听
	 * @param stage
	 * @param filter
	 */
	public ActorMonitor(Stage stage, Predicate<Actor> filter) {
		this.stage = stage;
		onAddActor = e -> {
			if (filter.test(e.actor())) {
				monitored.add(e.actor());
			}
		};
		onRemoveActor = e -> monitored.removeAll(e.actors());
		stage.onAddActor().subscribe(onAddActor);
		stage.onRemoveActor().subscribe(onRemoveActor);
		for (Actor actor : stage.getAll()) {
			if (filter.test(actor)) {
				monitored.add(actor);
			}
		}
	}

	/**
	 * 获取当前监听到的所有 {@link Actor}
	 * @return
	 */
	protected List<? extends Actor> getAll() { return new ArrayList<>(monitored); }

	/**
	 * 停止监听
	 */
	public void stop() {
		stage.onAddActor().unsubscribe(onAddActor);
		stage.onRemoveActor().unsubscribe(onRemoveActor);
	}

}
