package cn.milai.ib.publisher;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * {@link Publisher} 默认实现
 * @author milai
 * @date 2022.05.14
 */
public class BasePublisher<T extends Event> implements Publisher<T> {

	private List<Consumer<? super T>> listeners = new CopyOnWriteArrayList<>();
	private Set<Consumer<? super T>> ones = Collections.newSetFromMap(new ConcurrentHashMap<>());

	@Override
	public Publisher<T> subscribe(Consumer<? super T> consumer) {
		listeners.add(consumer);
		return this;
	}

	@Override
	public Publisher<T> subscribeOne(Consumer<? super T> consumer) {
		ones.add(consumer);
		return subscribe(consumer);
	}

	@Override
	public Publisher<T> unsubscribe(Consumer<? super T> consumer) {
		listeners.remove(consumer);
		return this;
	}

	@Override
	public void publish(T e) {
		for (Consumer<? super T> listener : listeners) {
			if (ones.remove(listener)) {
				listeners.remove(listener);
				listener.accept(e);
				continue;
			}
			listener.accept(e);
		}
	}

	@Override
	public void clear() {
		listeners.clear();
	}

}
