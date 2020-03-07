package cn.milai.ib.component.form.listener;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 用于屏蔽被包装监听器以外所有键盘监听器的类
 * 2020.01.16
 * @author milai
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class KeyShield implements KeyboardListener {

	private KeyboardListener listener;

	/**
	 * 返回包装了 listener 的，屏蔽其他监听器的键盘监听器
	 * @param listener
	 */
	public KeyShield(KeyboardListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean keyDown(Command e) {
		listener.keyDown(e);
		return true;
	}

	@Override
	public boolean keyUp(Command e) {
		listener.keyUp(e);
		return true;
	}

}
