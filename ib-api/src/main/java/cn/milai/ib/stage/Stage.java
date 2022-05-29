package cn.milai.ib.stage;

import java.util.List;
import java.util.Set;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.actor.nature.Nature;
import cn.milai.ib.geometry.slot.BoundsSlot;
import cn.milai.ib.lifecycle.Lifecycle;
import cn.milai.ib.plugin.AudioPluginable;
import cn.milai.ib.plugin.Crew;
import cn.milai.ib.plugin.DresserPluginable;
import cn.milai.ib.plugin.UIPluginable;
import cn.milai.ib.publisher.StagePublishers;
import cn.milai.ib.stage.event.ResizedEvent;

/**
 * 舞台
 * @author milai
 * @date 2020.12.02
 */
public interface Stage extends BoundsSlot, AudioPluginable, UIPluginable, DresserPluginable, StagePublishers {

	/**
	 * 获取关联的 {@link Lifecycle}
	 * @return
	 */
	Lifecycle lifecycle();

	/**
	 * 暂停或继续
	 */
	void switchPause();

	/**
	 * 是否出于暂停状态
	 * @return
	 */
	boolean isPaused();

	/**
	 * 设置是否固定
	 * @param pined
	 */
	void setPined(boolean pined);

	/**
	 * 返回是否为固定状态
	 * @return
	 */
	boolean isPined();

	/**
	 * 添加一个 {@link Actor}，将立刻返回
	 * @param actor
	 * @return
	 */
	Stage addActor(Actor actor);

	/**
	 * 移除一个 {@link Actor}，将立刻返回
	 * @param actor
	 * @return
	 */
	Stage removeActor(Actor actor);

	/**
	 * 清空 {@link Actor}，将立刻返回
	 * @return
	 */
	Stage clearActor();

	/**
	 * 添加一个 {@link Actor} ，阻塞到完成添加才返回
	 * @param actor
	 * @return
	 */
	Stage addActorSync(Actor actor);

	/**
	 * 移除一个 {@link Actor}，阻塞到完成移除才返回
	 * @param actor
	 * @return
	 */
	Stage removeActorSync(Actor actor);

	/**
	 * 清空 {@link Actor}，阻塞到完成清空才返回
	 * @return
	 */
	Stage clearActorSync();

	/**
	 * 是否包含指定 {@link Actor}
	 * @param actor
	 * @return
	 */
	boolean containsActor(Actor actor);

	/**
	 * 获取指定类型的所有 {@link Actor}
	 * @param <T>
	 * @param type
	 * @return
	 */
	<T extends Actor> Set<T> getAll(Class<T> type);

	/**
	 * 获取所有 {@link Actor}
	 * @return
	 */
	Set<Actor> getAll();

	/**
	 * 获取所有 {@link Actor} 的指定 {@link Nature}
	 * @param name
	 * @return
	 */
	<T extends Nature> List<T> getNatures(String name);

	/**
	 * 添加一个 {@link Crew}，相关监听器将被注册到当前 {@link Stage}
	 * @param crew
	 * @return
	 */
	Stage addCrew(Crew crew);

	/**
	 * 移除一个 {@link Crew}，相关监听器将从当前 {@link Stage} 移除
	 * @param crew
	 * @return
	 */
	Stage removeCrew(Crew crew);

	@SuppressWarnings("unchecked")
	@Override
	default Stage resize(double w, double h) {
		BoundsSlot.super.resize(w, h);
		onResized().publish(new ResizedEvent(this));
		return this;
	}

}
