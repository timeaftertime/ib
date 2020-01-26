package cn.milai.ib.mode;

import cn.milai.ib.form.LoginForm;

/**
 * 联网游戏模式
 * 2020.01.25
 * @author milai
 */
public class OnlineMode extends Thread {

	@Override
	public void run() {
		new LoginForm();
	}
}
