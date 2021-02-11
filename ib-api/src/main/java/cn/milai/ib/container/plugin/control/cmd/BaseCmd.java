package cn.milai.ib.container.plugin.control.cmd;

/**
 * {@link Cmd} 基础实现
 * @author milai
 * @date 2020.12.10
 */
public class BaseCmd implements Cmd {

	private CmdType type;
	private int fromId;

	public BaseCmd(CmdType type, int fromId) {
		this.type = type;
		this.fromId = fromId;
	}

	/**
	 * 获取指令类型
	 * @return
	 */
	public CmdType getType() { return type; }

	/**
	 * 获取发出指令者的 id
	 * @return
	 */
	public int getFromId() { return fromId; }

}
