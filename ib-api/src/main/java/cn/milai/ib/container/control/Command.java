package cn.milai.ib.container.control;

/**
 * 指令
 * @author milai
 * @date 2020.12.10
 */
public class Command {

	private CommandType type;
	private int fromId;

	public Command(CommandType type, int fromId) {
		this.type = type;
		this.fromId = fromId;
	}

	/**
	 * 获取指令类型
	 * @return
	 */
	public CommandType getType() {
		return type;
	}

	/**
	 * 获取发出指令者的 id
	 * @return
	 */
	public int getFromId() {
		return fromId;
	}

}
