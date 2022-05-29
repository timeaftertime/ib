package cn.milai.ib.plugin;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.plugin.actor.DresserCrew;

/**
 * 可能持有 {@link DresserCrew} 的 {@link Pluginable}
 * @author milai
 * @date 2022.05.24
 */
public interface DresserPluginable extends Pluginable<Crew> {

	/**
	 * 初始化指定 {@link Actor} 并返回该 {@link Actor}
	 * @param <T>
	 * @param actor
	 * @return
	 */
	default <T extends Actor> T makeUp(T actor) {
		fire(DresserCrew.class, dresser -> dresser.makeUp(actor));
		return actor;
	}

}
