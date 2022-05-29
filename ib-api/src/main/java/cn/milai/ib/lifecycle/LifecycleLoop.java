package cn.milai.ib.lifecycle;

import java.util.concurrent.Future;

/**
 * {@link Lifecycle} 循环
 * @author milai
 * @date 2022.03.31
 */
public interface LifecycleLoop {

	/**
	 * 注册一个 {@link Lifecycle}
	 * @param container
	 * @return
	 */
	Future<?> register(Lifecycle container);

	/**
	 * 取消一个 {@link Lifecycle} 的注册
	 * @param container
	 * @return
	 */
	Future<?> unregister(Lifecycle container);

	/**
	 * 判断当前线程是否为执行 {@link Lifecycle} 生命周期方法的线程
	 * @return
	 */
	boolean inMainLoop();

	/**
	 * 提交一个待执行任务
	 * @param r
	 * @return
	 */
	Future<?> submitDeputy(Runnable r);

	/**
	 * 提交一个由 {@link #inMainLoop()} 的线程执行的任务
	 * @param r
	 * @return
	 */
	Future<?> submit(Runnable r);

}
