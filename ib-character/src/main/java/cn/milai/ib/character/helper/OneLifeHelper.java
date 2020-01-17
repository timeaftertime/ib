package cn.milai.ib.character.helper;

import cn.milai.ib.character.Player;
import cn.milai.ib.container.Container;

public class OneLifeHelper extends Helper {

	private static final int GAIN_LIFE = 1;

	public OneLifeHelper(int x, int y, Container container) {
		super(x, y, container);
	}

	@Override
	public void makeFunction(Player plane) {
		plane.gainLife(GAIN_LIFE);
	}

}
