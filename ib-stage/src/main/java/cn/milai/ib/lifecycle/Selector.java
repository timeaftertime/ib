package cn.milai.ib.lifecycle;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import cn.milai.common.thread.BlockCondition;
import cn.milai.common.thread.Condition;

/**
 * {@link Lifecycle} 选择器
 * @author milai
 * @date 2022.04.01
 */
public class Selector {

	private Set<Lifecycle> registered;
	private PriorityQueue<Lifecycle> waiters;
	private Set<Lifecycle> polled;

	private AtomicBoolean wakeUp;
	private Condition emptyWaiter;

	public Selector() {
		registered = Collections.newSetFromMap(new ConcurrentHashMap<>());
		waiters = new PriorityQueue<>(Comparator.comparing(c -> c.nextRefreshTime()));
		polled = Collections.newSetFromMap(new ConcurrentHashMap<>());
		wakeUp = new AtomicBoolean();
		emptyWaiter = new BlockCondition(() -> !waiters.isEmpty() || wakeUp.get(), c -> wakeUp.set(true));
	}

	/**
	 * 注册一个 {@link Lifecycle}。返回是否注册成功
	 * @param lifecycle
	 * @return
	 */
	public boolean register(Lifecycle lifecycle) {
		if (!registered.add(lifecycle)) {
			return false;
		}
		if (waiters.offer(lifecycle)) {
			emptyWaiter.toMet();
			return true;
		}
		return false;
	}

	/**
	 * 移除一个已经注册的 {@link Lifecycle}。返回是否实际进行了移除操作
	 * @param lifecycle
	 * @return
	 */
	public boolean unregister(Lifecycle lifecycle) {
		if (!registered.remove(lifecycle)) {
			return false;
		}
		return waiters.remove(lifecycle);
	}

	/**
	 * 获取当前需要刷新的所有 {@link Lifecycle}。
	 * 该方法将阻塞到 {@link #wakeUp()} 被调用或至少有一个 {@link Lifecycle} 为止
	 * @return
	 */
	public Set<Lifecycle> select() {
		waiters.addAll(polled);
		polled.clear();
		Set<Lifecycle> lifecycles = new HashSet<>();
		while (lifecycles.isEmpty() && !wakeUp.getAndSet(false)) {
			emptyWaiter.await();
			while (!waiters.isEmpty()) {
				if (waiters.peek().nextRefreshTime() > System.currentTimeMillis()) {
					break;
				}
				Lifecycle c = waiters.poll();
				polled.add(c);
				lifecycles.add(c);
			}
		}
		return lifecycles;
	}

	/**
	 * 让一次 {@link #select()} 的调用造成的阻塞立刻返回
	 */
	public void wakeUp() {
		emptyWaiter.toMet();
	}

	/**
	 * 获取目前已经注册的所有 {@link Lifecycle}
	 * @return
	 */
	public Set<Lifecycle> lifecycles() {
		return Collections.unmodifiableSet(registered);
	}

}
