package cn.milai.ib.client.game.plane;

import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.form.GameForm;

public class WelcomePlane extends EnemyPlane {
	
	public WelcomePlane(int x, int y, GameForm container) {
		super(x, y,
				BattleConf.WELCOME_PLAYER_WIDTH, BattleConf.WELCOME_PLAYER_HEIGHT,
				BattleConf.WELCOME_PLANE_SPEED_X, BattleConf.WELCOME_PLANE_SPEED_Y,
				BattleConf.WELCOME_PLANE_MAX_BULLET,
				BattleConf.WELCOME_PLANE_LIFE,
				ImageConf.WELCOME_PLAYER,
				container
				);
	}

	@Override
	public int getScore() {
		return BattleConf.WELCOME_PLANE_SCORE;
	}

	@Override
	protected void beforeMove() {
		
	}

	@Override
	protected void afterMove() {
		removeIfOutOfOwner();
	}

	private void removeIfOutOfOwner() {
		if(getY() > getContainer().getHeight())
			getContainer().removeGameObject(this);
	}
	
}
