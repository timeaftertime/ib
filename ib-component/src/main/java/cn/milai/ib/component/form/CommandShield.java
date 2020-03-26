package cn.milai.ib.component.form;

import java.awt.Graphics;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.listener.Command;
import cn.milai.ib.obj.AbstractIBObject;
import cn.milai.ib.obj.Controllable;

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
	protected int proratedIntProp(String key) {
		return 0;
	}

	@Override
	public void paintWith(Graphics g) {
	}

}
