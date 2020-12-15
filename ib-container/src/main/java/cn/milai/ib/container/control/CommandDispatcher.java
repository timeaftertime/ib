package cn.milai.ib.container.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import cn.milai.ib.container.control.Command;
import cn.milai.ib.container.control.CommandType;
import cn.milai.ib.container.control.Controllable;

/**
 * 指令分发器
 * @author milai
 * @date 2020.12.12
 */
public class CommandDispatcher {

	/**
	 * 默认的待处理队列最大大小
	 */
	public static final int DEF_MAX_COMMAND_QUEUE_SIZE = 100;

	private int maxCommandQueueSize;

	private Map<Integer, Queue<CommandType>> commandQueues;

	private List<Controllable> controllables;
	private Set<Controllable> added;

	/**
	 * 创建持有大小为指定大小的待处理指令队列的指令分发器
	 * @param maxCommandQueueSize 某个 fromId 待处理指令队列的最大大小
	 */
	public CommandDispatcher(int maxCommandQueueSize) {
		this.maxCommandQueueSize = maxCommandQueueSize;
		commandQueues = new ConcurrentHashMap<>();
		controllables = Collections.synchronizedList(new ArrayList<>());
		added = Collections.synchronizedSet(new HashSet<>());
	}

	/**
	 * 创建持有大小为 {@link #DEF_MAX_COMMAND_QUEUE_SIZE} 的待处理指令队列的指令分发器
	 */
	public CommandDispatcher() {
		this(DEF_MAX_COMMAND_QUEUE_SIZE);
	}

	/**
	 * 添加一个指令接收者，多次添加同一个对象时，第一次之后的操作会被忽略
	 * @param c
	 */
	public void addControllable(Controllable c) {
		if (!added.add(c)) {
			return;
		}
		controllables.add(c);
		AnnotationAwareOrderComparator.sort(controllables);
	}

	/**
	 * 移除一个指令接收者
	 * @param c
	 */
	public void removeControllable(Controllable c) {
		controllables.remove(c);
		added.remove(c);
	}

	/**
	 * 添加一个待处理指令
	 * 若指令 fromId 对应的待处理队列达到 maxCommandQueueSize，将丢弃最前面的指令再添加当前指令
	 * @param c 待处理指令
	 */
	public void addCommand(Command c) {
		Queue<CommandType> q = commandQueues.computeIfAbsent(c.getFromId(), id -> new ConcurrentLinkedQueue<>());
		synchronized (q) {
			if (q.size() >= maxCommandQueueSize) {
				q.poll();
			}
		}
		q.add(c.getType());
	}

	/**
	 * 对每个 fromId 对应的队列，分发一个待处理指令
	 */
	public void dispathOne() {
		for (Integer fromId : commandQueues.keySet()) {
			Queue<CommandType> q = commandQueues.get(fromId);
			CommandType type = null;
			synchronized (q) {
				if (q.isEmpty()) {
					continue;
				}
				type = q.poll();
			}
			for (Controllable c : controllables) {
				if (c.accept(fromId)) {
					if (!c.exec(type)) {
						break;
					}
				}
			}
		}
	}

}
