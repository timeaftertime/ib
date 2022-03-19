package cn.milai.ib.control;

import java.util.function.Consumer;

import cn.milai.ib.item.Controllable;
import cn.milai.ib.item.Item;
import cn.milai.ib.item.property.holder.PainterHolder;

/**
 * 控件
 * @author milai
 * @date 2020.02.20
 */
public interface Control extends Item, PainterHolder, Controllable {

	/**
	 * 添加一个 {@link ControlListener}
	 * @param listener
	 * @return 是否添加成功
	 */
	boolean addControlListener(ControlListener listener);

	/**
	 * 移除一个 {@link Control}
	 * @param listener
	 * @return 是否添加成功
	 */
	boolean removeControlListener(ControlListener listener);

	/**
	 * 添加一个仅监听按下事件的 {@link ControlListener}
	 * @param consumer
	 * @return
	 */
	default Control onPressDown(Consumer<ControlEvent> consumer) {
		addControlListener(new ControlListener() {
			@Override
			public void onPressDown(ControlEvent e) {
				consumer.accept(e);
			}
		});
		return this;
	}

	/**
	 * 添加一个仅监听松开事件的 {@link ControlListener}
	 * @param consumer
	 * @return
	 */
	default Control onPressUp(Consumer<ControlEvent> consumer) {
		addControlListener(new ControlListener() {
			@Override
			public void onPressUp(ControlEvent e) {
				consumer.accept(e);
			}
		});
		return this;
	}

	/**
	 * 添加一个仅监听进入事件的 {@link ControlListener}
	 * @param consumer
	 * @return
	 */
	default Control onEnter(Consumer<ControlEvent> consumer) {
		addControlListener(new ControlListener() {
			@Override
			public void onEnter(ControlEvent e) {
				consumer.accept(e);
			}
		});
		return this;
	}

	/**
	 * 添加一个仅监听离开事件的 {@link ControlListener}
	 * @param consumer
	 * @return
	 */
	default Control onExit(Consumer<ControlEvent> consumer) {
		addControlListener(new ControlListener() {
			@Override
			public void onExit(ControlEvent e) {
				consumer.accept(e);
			}
		});
		return this;
	}

}
