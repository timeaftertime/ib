package cn.milai.ib.lifecycle;

import java.util.Queue;

/**
 * 事件队列工厂类
 * @author milai
 * @date 2022.04.24
 */
public interface EventQueueFactory {

	/**
	 * 构造一个指定大小的事件队列
	 * @param size
	 * @return
	 */
	Queue<Runnable> newEventQueue(int size);

}
