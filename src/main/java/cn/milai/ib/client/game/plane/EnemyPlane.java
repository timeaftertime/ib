package cn.milai.ib.client.game.plane;

import java.awt.Image;

import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.property.Alive;
import cn.milai.ib.client.game.property.CanCrash;

public abstract class EnemyPlane extends Plane implements CanCrash {
	
	public EnemyPlane(int x, int y, int width, int height, int speedX, int speedY, int maxBulletNum,  int life,
			Image image, GameForm container) {
		super(x, y, width, height, speedX, speedY, maxBulletNum, life, Camp.ENEMY, image, container);
	}

	@Override
	public void onKill(Alive alive) {
		// 这个方法一般用于玩家飞机得分，这里不做反应
	}

	@Override
	public int getDamage() {
		return 1;
	}

}
