package cn.milai.ib.container.plugin.control.cmd;

/**
 * 与某一点相关 {@link Cmd}
 * @author milai
 * @date 2021.03.20
 */
public class PointCmd extends BaseCmd {

	private double x;

	private double y;

	/**
	 * 构造一个 {@link PointCmd}
	 * @param type
	 * @param fromId
	 * @param x 点击的 x 坐标
	 * @param y 点击的 y 坐标
	 */
	public PointCmd(CmdType type, int fromId, double x, double y) {
		super(type, fromId);
		this.x = x;
		this.y = y;
	}

	public double getX() { return x; }

	public double getY() { return y; }

}
