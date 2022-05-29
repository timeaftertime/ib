package cn.milai.ib.lifecycle;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.MultithreadEventExecutorGroup;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.RejectedExecutionHandlers;

/**
 * {@link BaseLifecycleLoop} 组
 * @author milai
 * @date 2022.04.16
 */
public class LifecycleLoopGroup extends MultithreadEventExecutorGroup implements EventExecutorGroup {

	/**
	 * 创建一个使用默认 {@link Executor} 的事件循环
	 * @param size
	 */
	public LifecycleLoopGroup(int size) {
		this(size, null, null);
	}

	/**
	 * 创建一个事件循环
	 * @param size
	 * @param executor 执行主事件循环的 {@link Executor}
	 */
	public LifecycleLoopGroup(int size, Executor executor, Executor eventExecutor) {
		this(size, executor, eventExecutor, s -> new ConcurrentLinkedQueue<>(), RejectedExecutionHandlers.reject());
	}

	/**
	 * 创建一个 {@link Lifecycle} 事件循环
	 * @param size {@link EventExecutor} 数量
	 * @param executor 执行器
	 * @param eventExecutor 事件执行器
	 * @param queueFactory 任务队列构造器
	 * @param rejectedHandler 拒绝处理器
	 */
	public LifecycleLoopGroup(int size, Executor executor, Executor eventExecutor,
		EventQueueFactory queueFactory,
		RejectedExecutionHandler rejectedHandler) {
		super(size, executor, eventExecutor(eventExecutor), queueFactory, rejectedHandler);
	}

	private static Executor eventExecutor(Executor eventExecutor) {
		if (eventExecutor != null) {
			return eventExecutor;
		}
		return new ThreadPoolExecutor(
			0, Runtime.getRuntime().availableProcessors(), 60, TimeUnit.SECONDS, new SynchronousQueue<>(),
			new DefaultThreadFactory("ContainerEventLoopGroup", true)
		);
	}

	@Override
	protected EventExecutor newChild(Executor executor, Object... args) throws Exception {
		Executor eventExecutor = (Executor) args[0];
		EventQueueFactory queueFactory = (EventQueueFactory) args[1];
		RejectedExecutionHandler rejectedHandler = (RejectedExecutionHandler) args[2];
		return new BaseLifecycleLoop(this, executor, eventExecutor, queueFactory, rejectedHandler);
	}

	@Override
	public BaseLifecycleLoop next() {
		return (BaseLifecycleLoop) super.next();
	}

}
