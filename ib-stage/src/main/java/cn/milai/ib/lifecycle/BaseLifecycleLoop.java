package cn.milai.ib.lifecycle;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.SingleThreadEventExecutor;

/**
 * {@link LifecycleLoop} 默认实现
 * @author milai
 * @date 2020.03.25
 */
public class BaseLifecycleLoop extends SingleThreadEventExecutor implements LifecycleLoop {

	private ExecutorService deputyExecutor;
	private Selector selector;

	public BaseLifecycleLoop(EventExecutorGroup parent, Executor executor, Executor deputyExecutor,
		EventQueueFactory queueFactory, RejectedExecutionHandler rejectHandler) {
		super(parent, executor, false, queueFactory.newEventQueue(0), rejectHandler);
		this.deputyExecutor = toExecutorService(deputyExecutor);
		selector = new Selector();
	}

	@Override
	public Future<?> register(Lifecycle lifecycle) {
		return submit(() -> {
			lifecycle.unsafe().register(this);
			selector.register(lifecycle);
		});
	}

	@Override
	public Future<?> unregister(Lifecycle lifecycle) {
		return submit(() -> {
			lifecycle.unsafe().unregister();
			selector.unregister(lifecycle);
		});
	}

	@Override
	public void run() {
		while (true) {
			for (Lifecycle lifecycle : selector.select()) {
				lifecycle.refresh();
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
	public boolean inMainLoop() {
		return inEventLoop();
	}

	@Override
	protected void cleanup() {
		for (Lifecycle lifecycle : selector.lifecycles()) {
			lifecycle.close();
		}
	}

	@Override
	public java.util.concurrent.Future<?> submitDeputy(Runnable r) {
		return deputyExecutor.submit(r);
	}

	@Override
	protected void wakeup(boolean inEventLoop) {
		selector.wakeUp();
	}

	private static ExecutorService toExecutorService(Executor executor) {
		if (executor instanceof ExecutorService) {
			return (ExecutorService) executor;
		}
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
