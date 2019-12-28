package cn.milai.ib.drama.act;

import cn.milai.ib.constant.ActType;
import cn.milai.ib.container.Container;
import cn.milai.ib.drama.Frame;
import cn.milai.ib.drama.ByteReader;

/**
 * 剧情里的一个动作
 *
 * 2019.12.08
 *
 * @author milai
 */
public interface Act {

	/**
	 * 获取 Act 的唯一标识
	 * 
	 * @return
	 */
	ActType getCode();

	/**
	 * 执行动作
	 * 
	 * @param frame 执行命令时所在帧
	 * @param container 执行命令的对象容器
	 */
	void execute(Frame frame, Container container);

	/**
	 * 初始化 Act ，例如从 reader 中读取操作数
	 */
	void initiailze(ByteReader reader);

}
