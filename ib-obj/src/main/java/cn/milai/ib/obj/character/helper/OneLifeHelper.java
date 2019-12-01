package cn.milai.ib.obj.character.helper;

import cn.milai.ib.container.Container;
import cn.milai.ib.obj.character.plane.Plane;

public class OneLifeHelper extends Helper {

	private static final int GAIN_LIFE = 1;

	public OneLifeHelper(int x, int y, Container container) {
		super(x, y, container);
	}

	@Override
	public void makeFunction(Plane plane) {
		plane.gainLife(GAIN_LIFE);
	}

}
