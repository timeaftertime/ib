package cn.milai.ib.control;

/**
 * {@link Control} 监听器
 * @author milai
 * @date 2022.03.11
 */
public interface ControlListener {

	/**
	 * 被按下时调用
	 * @param e
	 */
	default void onPressDown(ControlEvent e) {
	}

	/**
	 * 被松开时调用
	 * @param e
	 */
	default void onPressUp(ControlEvent e) {
	}

	/**
	 * 按下状态进入时调用
	 * @param e
	 */
	default void onEnter(ControlEvent e) {
	}

	/**
	 * 按下状态离开时调用
	 * @param e
	 */
	default void onExit(ControlEvent e) {
	}
}
