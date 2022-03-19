package cn.milai.ib.control;

/**
 * {@link Control} 事件
 * @author milai
 * @date 2022.03.12
 */
public class ControlEvent {

	private Control owner;
	private double absX;
	private double absY;
	private double x;
	private double y;
	private boolean pressed;

	/**
	 * 创建一个 {@link ControlEvent}
	 * @param owner 事件关联的 {@link Control}
	 * @param x 事件发生位置
	 * @param y 事件发生位置
	 * @param pressed 是否处于按下状态
	 */
	public ControlEvent(Control owner, double x, double y, boolean pressed) {
		this.owner = owner;
		this.absX = x;
		this.absY = y;
		this.x = x - owner.getX();
		this.y = y - owner.getY();
		this.pressed = pressed;
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

	public Control getOwner() { return owner; }

	public boolean isPressed() { return pressed; }
}
