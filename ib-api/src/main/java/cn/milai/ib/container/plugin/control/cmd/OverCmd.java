package cn.milai.ib.container.plugin.control.cmd;

/**
 * (鼠标)移动 {@link Cmd}
 * @author milai
 * @date 2021.03.20
 */
public class OverCmd extends PointCmd {

	public OverCmd(int fromId, double x, double y) {
		super(CmdType.OVER, fromId, x, y);
	}

}
