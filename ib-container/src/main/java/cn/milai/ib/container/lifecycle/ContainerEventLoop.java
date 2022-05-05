package cn.milai.ib.container.lifecycle;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.SingleThreadEventExecutor;

/**
 * {@link LifecycleContainer} 容器的刷新线程
 * @author milai
 * @date 2020.03.25
 */
public class ContainerEventLoop extends SingleThreadEventExecutor implements EventLoop {

	private ExecutorService eventExecutor;
	private Selector selector;

	public ContainerEventLoop(EventExecutorGroup parent, Executor executor, Executor eventExecutor,
		EventQueueFactory queueFactory, RejectedExecutionHandler rejectHandler) {
		super(parent, executor, false, queueFactory.newEventQueue(0), rejectHandler);
		this.eventExecutor = toExecutorService(eventExecutor);
		selector = new Selector();
	}

	@Override
	public Future<?> register(LifecycleContainer container) {
		return submit(() -> {
			container.unsafe().register(this);
			selector.register(container);
		});
	}

	@Override
	public Future<?> unregister(LifecycleContainer container) {
		return submit(() -> {
			container.unsafe().unregister();
			selector.unregister(container);
		});
	}

	@Override
	public void run() {
		while (true) {
			for (LifecycleContainer container : selector.select()) {
				container.refresh();
			}
			runAllTasks();
			if (isShuttingDown()) {
				while (!confirmShutdown()) {
					// 等待停止
				}
				break;
			}
		}
	}

	@Override
	protected void cleanup() {
		for (LifecycleContainer container : selector.containers()) {
			container.close();
		}
	}

	@Override
	public void execute(Runnable task) {
		super.execute(() -> eventExecutor.execute(task));
	}

	@Override
	public Future<?> submit(Runnable task) {
		return super.submit(() -> eventExecutor.submit(task).get());
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return super.submit(() -> eventExecutor.submit(task).get());
	}

	@Override
	protected void wakeup(boolean inEventLoop) {
		selector.wakeUp();
	}

	private static ExecutorService toExecutorService(Executor executor) {
		return new AbstractExecutorService() {

			@Override
			public void execute(Runnable command) {
				executor.execute(command);
			}

			@Override
			public List<Runnable> shutdownNow() {
				throw new UnsupportedOperationException();
			}

			@Override
			public void shutdown() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean isTerminated() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean isShutdown() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
				throw new UnsupportedOperationException();
			}
		};
	}

}
