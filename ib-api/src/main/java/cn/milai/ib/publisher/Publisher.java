package cn.milai.ib.publisher;

import java.util.function.Consumer;

/**
 * 事件发布器
 * @author milai
 * @date 2022.05.13
 */
public interface Publisher<T extends Event> {

	/**
	 * 添加一个监听器
	 * @param consumer
	 * @return 返回自身
	 */
	Publisher<T> subscribe(Consumer<? super T> consumer);

	/**
	 * 添加一个只监听一次就会被移除的监听器
	 * @param consumer
	 * @return
	 */
	Publisher<T> subscribeOne(Consumer<? super T> consumer);

	/**
	 * 移除一个监听器
	 * @param consumer
	 * @return 返回自身
	 */
	Publisher<T> unsubscribe(Consumer<? super T> consumer);

	/**
	 * 发布一个 {@link Event}
	 * @param e
	 */
	void publish(T e);

	/**
	 * 清空所有监听器
	 */
	void clear();

}
