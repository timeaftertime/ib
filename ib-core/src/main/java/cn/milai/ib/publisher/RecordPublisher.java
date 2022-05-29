package cn.milai.ib.publisher;

import java.util.function.Consumer;

/**
 * {@link #subscribe(java.util.function.Consumer)} 时立刻对其发布一次上次(如果有)事件的 {@link Publisher}
 * @author milai
 * @date 2022.05.26
 */
public class RecordPublisher<T extends Event> extends BasePublisher<T> {

	private T lastEvent;

	@Override
	public Publisher<T> subscribe(Consumer<? super T> consumer) {
		super.subscribe(consumer);
		if (lastEvent != null) {
			consumer.accept(lastEvent);
		}
		return this;
	}

	@Override
	public void publish(T e) {
		lastEvent = e;
		super.publish(e);
	}

}
