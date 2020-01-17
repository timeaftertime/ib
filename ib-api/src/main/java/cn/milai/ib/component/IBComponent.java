package cn.milai.ib.component;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.Container;

/**
 * 用于交互的组件
 * 2020.01.17
 * @author milai
 */
public abstract class IBComponent extends IBObject{

	public IBComponent(int x, int y, Container container) {
		super(x, y, container);
	}

}
