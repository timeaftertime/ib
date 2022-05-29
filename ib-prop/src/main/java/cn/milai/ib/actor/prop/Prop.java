package cn.milai.ib.actor.prop;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.actor.Controllable;
import cn.milai.ib.actor.nature.holder.PainterHolder;
import cn.milai.ib.actor.prop.event.PropEvent;
import cn.milai.ib.publisher.Publisher;

/**
 * 道具
 * @author milai
 * @date 2022.05.22
 */
public interface Prop extends Actor, PainterHolder, Controllable {

	/**
	 * 获取按下 {@link PropEvent} 订阅器
	 * @return
	 */
	Publisher<PropEvent> onPressDown();

	/**
	 * 获取松开 {@link PropEvent} 订阅器
	 * @return
	 */
	Publisher<PropEvent> onPressUp();

	/**
	 * 获取进入 {@link PropEvent} 订阅器
	 * @return
	 */
	Publisher<PropEvent> onEnter();

	/**
	 * 获取离开 {@link PropEvent} 订阅器
	 * @param consumer
	 * @return
	 */
	Publisher<PropEvent> onExit();

}
