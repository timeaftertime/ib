package cn.milai.ib.stage.event;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.stage.Stage;

/**
 * 添加 {@link Actor} 的事件
 * @author milai
 * @date 2022.05.14
 */
public class AddActorEvent extends StageEvent {

	private Actor actor;

	public AddActorEvent(Stage stage, Actor actor) {
		super(stage);
		this.actor = actor;
	}

	public Actor actor() {
		return actor;
	}

}
