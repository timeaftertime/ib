package cn.milai.ib.lifecycle;

import java.util.concurrent.Future;

/**
 * {@link Lifecycle} 循环
 * @author milai
 * @date 2022.03.31
 */
public interface LifecycleLoop extends LifecycleExecutor {

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

}
