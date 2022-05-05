package cn.milai.ib.container.lifecycle;

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
 * {@link LifecycleContainer} 选择器
 * @author milai
 * @date 2022.04.01
 */
public class Selector {

	private Set<LifecycleContainer> registered;
	private PriorityQueue<LifecycleContainer> waiters;
	private Set<LifecycleContainer> polled;

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
	 * 注册一个 {@link LifecycleContainer}。返回是否注册成功
	 * @param container
	 * @return
	 */
	public boolean register(LifecycleContainer container) {
		if (!registered.add(container)) {
			return false;
		}
		if (waiters.offer(container)) {
			emptyWaiter.toMet();
			return true;
		}
		return false;
	}

	/**
	 * 移除一个已经注册的 {@link LifecycleContainer}。返回是否实际进行了移除操作
	 * @param container
	 * @return
	 */
	public boolean unregister(LifecycleContainer container) {
		if (!registered.remove(container)) {
			return false;
		}
		return waiters.remove(container);
	}

	/**
	 * 获取当前需要刷新的所有 {@link LifecycleContainer}。
	 * 该方法将阻塞到 {@link #wakeUp()} 被调用或至少有一个 {@link LifecycleContainer} 为止
	 * @return
	 */
	public Set<LifecycleContainer> select() {
		waiters.addAll(polled);
		polled.clear();
		Set<LifecycleContainer> containers = new HashSet<>();
		while (containers.isEmpty() && !wakeUp.getAndSet(false)) {
			emptyWaiter.await();
			while (!waiters.isEmpty()) {
				if (waiters.peek().nextRefreshTime() > System.currentTimeMillis()) {
					break;
				}
				LifecycleContainer c = waiters.poll();
				polled.add(c);
				containers.add(c);
			}
		}
		return containers;
	}

	/**
	 * 让一次 {@link #select()} 的调用造成的阻塞立刻返回
	 */
	public void wakeUp() {
		emptyWaiter.toMet();
	}

	/**
	 * 获取目前已经注册的所有 {@link LifecycleContainer}
	 * @return
	 */
	public Set<LifecycleContainer> containers() {
		return Collections.unmodifiableSet(registered);
	}

}
