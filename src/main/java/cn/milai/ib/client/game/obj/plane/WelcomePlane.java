package cn.milai.ib.client.game.obj.plane;

import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.conf.gameprops.LifeConf;
import cn.milai.ib.client.game.conf.gameprops.SizeConf;
import cn.milai.ib.client.game.conf.gameprops.ScoreConf;
import cn.milai.ib.client.game.conf.gameprops.SpeedConf;
import cn.milai.ib.client.game.form.GameForm;

public class WelcomePlane extends EnemyPlane {

	public WelcomePlane(int x, int y, GameForm container) {
		super(x, y, SizeConf.WELCOME_PLAYER_WIDTH, SizeConf.WELCOME_PLAYER_HEIGHT, SpeedConf.WELCOME_PLANE_SPEED_X,
				SpeedConf.WELCOME_PLANE_SPEED_Y, BattleConf.WELCOME_PLANE_MAX_BULLET, LifeConf.WELCOME_PLANE_LIFE, null,
				ImageConf.WELCOME_PLAYER, container);
	}

	@Override
	public int getScore() {
		return ScoreConf.WELCOME_PLANE_SCORE;
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
			getContainer().removeGameObject(this);
	}

}
