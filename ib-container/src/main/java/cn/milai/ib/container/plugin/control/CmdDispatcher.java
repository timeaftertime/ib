package cn.milai.ib.container.plugin.control;

import cn.milai.ib.container.plugin.control.cmd.Cmd;

/**
 * {@link Cmd} 分发器
 * @author milai
 * @date 2022.04.17
 */
public interface CmdDispatcher {

	/**
	 * 分发指定指令。
	 * @param cmd 要分发的指令，若为 <code>null</code>，将忽略
	 * @return 指令是否成功分发
	 */
	boolean dispatch(Cmd cmd);

}
