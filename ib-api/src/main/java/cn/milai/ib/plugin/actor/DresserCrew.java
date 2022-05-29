package cn.milai.ib.plugin.actor;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.plugin.Crew;

/**
 * 化妆师、服装师，负责调用 {@link Actor#makeup()} 的 {@link Crew}
 * @author milai
 * @date 2022.05.23
 */
public interface DresserCrew extends Crew {

	/**
	 * 初始化 {@link Actor} 并调用其 {@link Actor#makeup()}
	 * @param <T>
	 * @param actor
	 * @return
	 */
	<T extends Actor> T makeUp(T actor);

}
