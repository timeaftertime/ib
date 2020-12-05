package cn.milai.ib.component;

import java.awt.Graphics;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import cn.milai.ib.AbstractIBObject;
import cn.milai.ib.character.Controllable;
import cn.milai.ib.container.Container;
import cn.milai.ib.container.listener.Command;

/**
 * 禁用暂停以外所有指令的命令接收器
 * @author milai
 * @date 2020.03.26
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommandShield extends AbstractIBObject implements Controllable {

	public CommandShield(Container container) {
		super(0, 0, container);
	}

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public boolean onReceive(Command command) {
		return false;
	}

	@Override
	public boolean onCancel(Command command) {
		return false;
	}

	@Override
	protected int intProp(String key) {
		return 0;
	}

	@Override
	public void paintWith(Graphics g) {
	}
}
