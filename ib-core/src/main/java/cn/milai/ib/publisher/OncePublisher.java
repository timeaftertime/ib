package cn.milai.ib.publisher;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * 只发布一次的 {@link Publisher}
 * @author milai
 * @date 2022.05.27
 */
public class OncePublisher<T extends Event> extends BasePublisher<T> {

	private AtomicReference<T> event = new AtomicReference<>();

	@Override
	public Publisher<T> subscribe(Consumer<? super T> consumer) {
		if (event.get() != null) {
			consumer.accept(event.get());
			return this;
		}
		return super.subscribe(consumer);
	}

	@Override
	public Publisher<T> subscribeOne(Consumer<? super T> consumer) {
		return subscribe(consumer);
	}

	@Override
	public synchronized void publish(T e) {
		if (!event.compareAndSet(null, e)) {
			return;
		}
		super.publish(e);
		clear();
	}

}
