package cn.milai.ib.character.plane;

import cn.milai.ib.container.Container;

public class WelcomePlane extends EnemyPlane {

	public WelcomePlane(int x, int y, Container container) {
		super(x, y, container);
	}

	@Override
	protected void beforeMove() {

	}

	@Override
	protected void afterMove() {
		removeIfOutOfOwner();
	}

	private void removeIfOutOfOwner() {
		if (getY() > getContainer().getHeight())
			getContainer().removeObject(this);
	}

}
