package cn.milai.ib.container.lifecycle;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * {@link LifecycleContainer} 生命周期循环
 * @author milai
 * @date 2022.03.31
 */
public interface EventLoop {

	/**
	 * 注册一个 {@link LifecycleContainer}
	 * @param container
	 * @return
	 */
	Future<?> register(LifecycleContainer container);

	/**
	 * 取消一个 {@link LifecycleContainer} 的注册
	 * @param container
	 * @return
	 */
	Future<?> unregister(LifecycleContainer container);

	/**
	 * 提交一个待执行任务
	 * @param r
	 * @return
	 */
	Future<?> submit(Runnable r);

	/**
	 * 提交一个定时任务
	 * @param command
	 * @param delay
	 * @param unit
	 * @return
	 */
	ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);

}
