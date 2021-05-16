package cn.milai.ib;

import java.awt.Graphics;

import cn.milai.ib.container.Container;

/**
 * 不可见 {@link IBObject}
 * @author milai
 * @date 2021.05.15
 */
public abstract class InvisibleIBObject extends AbstractIBObject {

	public InvisibleIBObject(double x, double y, Container container) {
		super(x, y, container);
	}

	@Override
	public void paintWith(Graphics g) {}

	@Override
	public double getW() { return 0; }

	@Override
	public double getH() { return 0; }

	@Override
	public double doubleConf(String key) {
		return 0;
	}

}
