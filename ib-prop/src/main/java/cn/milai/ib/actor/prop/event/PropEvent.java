package cn.milai.ib.actor.prop.event;

import cn.milai.ib.actor.prop.Prop;
import cn.milai.ib.publisher.Event;

/**
 * {@link Prop} 事件
 * @author milai
 * @date 2022.05.22
 */
public class PropEvent implements Event {

	private Prop prop;
	private double absX;
	private double absY;
	private double x;
	private double y;

	/**
	 * 创建一个 {@link PropEvent}
	 * @param owner 事件关联的 {@link Prop}
	 * @param x 事件发生位置
	 * @param y 事件发生位置
	 */
	public PropEvent(Prop prop, double x, double y) {
		this.prop = prop;
		this.absX = x;
		this.absY = y;
		this.x = x - prop.getX();
		this.y = y - prop.getY();
	}

	/**
	 * 获取相对于 owner 的 x 坐标
	 * @return
	 */
	public double getX() { return x; }

	/**
	 * 获取相对于 owner 的 y 坐标
	 * @return
	 */
	public double getY() { return y; }

	public double getAbsX() { return absX; }

	public double getAbsY() { return absY; }

	public Prop prop() {
		return prop;
	}

}
