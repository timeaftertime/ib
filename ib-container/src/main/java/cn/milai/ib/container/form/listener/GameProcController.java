package cn.milai.ib.container.form.listener;

import org.springframework.core.annotation.Order;

import cn.milai.ib.container.form.FormContainer;

/**
 * 游戏进度控制键盘监听器
 * 2020.01.08
 * @author milai
 */
@Order
public class GameProcController implements KeyboardListener {

	private FormContainer container;

	public GameProcController(FormContainer container) {
		this.container = container;
	}

	@Override
	public boolean keyDown(Command e) {
		return false;
	}

	@Override
	public boolean keyUp(Command e) {
		if (e == Command.PAUSE) {
			container.pauseOrResume();
		}
		return true;
	}

}
