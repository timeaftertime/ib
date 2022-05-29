package cn.milai.ib.publisher;

import java.util.function.Consumer;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.actor.event.ActorEvent;

/**
 * {@link Publisher} 工具类
 * @author milai
 * @date 2022.05.27
 */
public class Subscribers {

	private Subscribers() {
	}

	/**
	 * 获取一个设置 {@link Actor#centerXY(double, double)} 为指定值的订阅器
	 * @param x
	 * @param y
	 * @return
	 */
	public static Consumer<ActorEvent> centerXY(double x, double y) {
		return e -> e.actor().centerXY(x, y);
	}

}
