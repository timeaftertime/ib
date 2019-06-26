package cn.milai.ib.client.game.obj;

import cn.milai.ib.client.game.conf.AudioConf;
import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.conf.gameprops.SizeConf;
import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.obj.property.HasDamage;
import cn.milai.ib.client.game.obj.property.Paintable;

public class Bomb extends GameEntity {

	long endTime;

	public Bomb(int x, int y, GameForm container) {
		super(x, y, SizeConf.BOMB_WIDTH, SizeConf.BOMB_HEIGHT, 0, ImageConf.BOMB, container);
		this.endTime = System.currentTimeMillis() + BattleConf.BOMB_LAST_MILLISEC;
		AudioConf.BOMB.play();
	}

	@Override
	public boolean isAlive() {
		return System.currentTimeMillis() <= endTime;
	}

	@Override
	public void toDead() {
		endTime = System.currentTimeMillis();
	}

	@Override
	public void onDead() {
	}

	@Override
	public void damagedBy(HasDamage attacker) {
	}

	@Override
	public int getScore() {
		return 0;
	}

	@Override
	public int getPaintLayer() {
		return Paintable.BOMB_LAYER;
	}

}
