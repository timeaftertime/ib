package cn.milai.ib.container.plugin.control.cmd;

/**
 * {@link CmdType#CLICKED} 类型指令
 * @author milai
 * @date 2021.02.10
 */
public class ClickCmd extends BaseCmd {

	private double x;

	private double y;

	/**
	 * 构造一个点击指令
	 * @param fromId
	 * @param x 点击的 x 坐标
	 * @param y 点击的 y 坐标
	 */
	public ClickCmd(int fromId, double x, double y) {
		super(CmdType.CLICKED, fromId);
		this.x = x;
		this.y = y;
	}

	public double getX() { return x; }

	public double getY() { return y; }

}
