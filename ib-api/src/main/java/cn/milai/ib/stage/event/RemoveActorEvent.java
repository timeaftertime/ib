package cn.milai.ib.stage.event;

import java.util.Collections;
import java.util.Set;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.stage.Stage;

/**
 * 移除 {@link Actor} 事件
 * @author milai
 * @date 2022.05.14
 */
public class RemoveActorEvent extends StageEvent {

	private Set<Actor> actors;

	public RemoveActorEvent(Stage stage, Set<Actor> actors) {
		super(stage);
		this.actors = Collections.unmodifiableSet(actors);
	}

	public Set<Actor> actors() {
		return actors;
	}

}
