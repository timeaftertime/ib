package cn.milai.ib.container.plugin.control;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import cn.milai.ib.character.Controllable;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.plugin.BaseObjectPlugin;
import cn.milai.ib.container.plugin.ObjectLifecycleListener;
import cn.milai.ib.container.plugin.control.cmd.Cmd;

/**
 * {@link ControlPlugin} 默认实现
 * @author milai
 * @date 2020.12.12
 */
public class BaseControlPlugin extends BaseObjectPlugin<Controllable> implements ControlPlugin {

	/**
	 * 每帧每个 fromId 最多执行多少条指令
	 */
	private static final int CMD_PER_FRAME = 5;

	/**
	 * 默认的待处理队列最大大小
	 */
	public static final int DEF_MAX_COMMAND_QUEUE_SIZE = 100;

	private int maxCommandQueueSize;

	private Map<Integer, Queue<Cmd>> cmdQueues;

	/**
	 * 创建持有大小为指定大小的待处理指令队列的指令分发器
	 * @param maxCommandQueueSize 某个 fromId 待处理指令队列的最大大小
	 */
	public BaseControlPlugin(int maxCommandQueueSize) {
		super(Controllable.class);
		this.maxCommandQueueSize = maxCommandQueueSize;
		cmdQueues = new ConcurrentHashMap<>();
	}

	@Override
	protected ObjectLifecycleListener newEventListener() {
		return new ObjectLifecycleListener() {
			@Override
			public void afterRefresh(LifecycleContainer container) {
				for (int i = 0; i < CMD_PER_FRAME; i++) {
					for (Queue<Cmd> q : cmdQueues.values()) {
						Cmd cmd = q.poll();
						if (cmd == null) {
							continue;
						}
						List<Controllable> all = getAll();
						AnnotationAwareOrderComparator.sort(all);
						for (Controllable c : all) {
							if (c.accept(cmd) && !c.exec(cmd)) {
								break;
							}
						}
					}
				}
			}
		};
	}

	/**
	 * 创建持有大小为 {@link #DEF_MAX_COMMAND_QUEUE_SIZE} 的待处理指令队列的指令分发器
	 */
	public BaseControlPlugin() {
		this(DEF_MAX_COMMAND_QUEUE_SIZE);
	}

	/**
	 * 添加一个待处理指令
	 * 若指令 fromId 对应的待处理队列达到 maxCommandQueueSize，将丢弃最前面的指令再添加当前指令
	 * @param c 待处理指令
	 */
	@Override
	public void addCommand(Cmd c) {
		Queue<Cmd> q = cmdQueues.computeIfAbsent(c.getFromId(), id -> new ConcurrentLinkedQueue<>());
		synchronized (q) {
			if (q.size() >= maxCommandQueueSize) {
				q.poll();
			}
			q.add(c);
		}
	}

	@Override
	public void onReset() {
		super.onReset();
		clearCommands();
	}

	@Override
	public void clearCommands() {
		cmdQueues.clear();
	}

}
