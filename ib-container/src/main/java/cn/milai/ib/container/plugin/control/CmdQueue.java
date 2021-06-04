package cn.milai.ib.container.plugin.control;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import cn.milai.ib.Controllable;
import cn.milai.ib.container.plugin.control.cmd.Cmd;

/**
 * {@link Cmd} 队列
 * @author milai
 * @date 2021.06.04
 */
public class CmdQueue {

	/**
	 * 默认的待处理队列最大大小
	 */
	public static final int DEF_MAX_QUEUE_SIZE = 100;

	private int maxQueueSize;

	private Queue<Cmd> queue;

	public CmdQueue(int maxQueueSize) {
		this.maxQueueSize = maxQueueSize;
		this.queue = new ConcurrentLinkedQueue<>();
	}

	/**
	 * 创建待处理队列最大大小为 {@link #DEF_MAX_QUEUE_SIZE} 的 {@link CmdQueue}
	 */
	public CmdQueue() {
		this(DEF_MAX_QUEUE_SIZE);
	}

	/**
	 * 添加一个 {@link Cmd} 到队列。若超过 {@link #maxQueueSize} ，将被忽略
	 * @param cmd
	 * @return 是否真正添加了 {@link Cmd}
	 */
	public boolean add(Cmd cmd) {
		if (queue.size() >= maxQueueSize) {
			return false;
		}
		return queue.add(cmd);
	}

	/**
	 * 清空 {@link Cmd} 队列
	 */
	public void clear() {
		queue.clear();
	}

	/**
	 *  移除队列最前面的 {@link Cmd} 并在指定 {@link Controllable} 列表执行
	 * @param controllables
	 * @return 是否真的分发了指令
	 */
	public boolean runNext(List<Controllable> controllables) {
		Cmd cmd = queue.poll();
		if (cmd == null) {
			return false;
		}
		for (Controllable c : controllables) {
			if (!c.exec(cmd)) {
				break;
			}
		}
		return true;
	}

}
