package cn.milai.ib.lifecycle;

import java.util.concurrent.Future;

/**
 * {@link Lifecycle} 相关执行器
 * @author milai
 * @date 2022.06.06
 */
public interface LifecycleExecutor {

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

	/**
	 * 判断当前线程是否为执行 {@link Lifecycle} 生命周期方法的线程
	 * @return
	 */
	boolean inMainLoop();
}
