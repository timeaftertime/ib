package cn.milai.ib.actor.event;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.publisher.Event;

/**
 * {@link Actor} 的 {@link Event}
 * @author milai
 * @date 2022.05.27
 */
public class ActorEvent implements Event {

	private Actor actor;

	public ActorEvent(Actor actor) {
		this.actor = actor;
	}

	public Actor actor() {
		return actor;
	}

}
