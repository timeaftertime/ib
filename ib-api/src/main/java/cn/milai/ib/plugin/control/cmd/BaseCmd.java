package cn.milai.ib.plugin.control.cmd;

/**
 * {@link Cmd} 基础实现
 * @author milai
 * @date 2020.12.10
 */
public class BaseCmd implements Cmd {

	private int type;

	public BaseCmd(int type) {
		this.type = type;
	}

	/**
	 * 获取指令类型
	 * @return
	 */
	public int getType() { return type; }

	/**
	 * 设置指令类型
	 * @param type
	 */
	public void setType(int type) { this.type = type; }

}
