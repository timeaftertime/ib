package cn.milai.ib.client.game.obj;

import java.awt.Graphics;
import java.awt.Image;

import cn.milai.ib.client.game.conf.AudioConf;
import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.obj.property.HasDamage;
import cn.milai.ib.client.game.obj.property.Paintable;

public class Bomb extends GameEntity implements Paintable {

	private Image img = ImageConf.BOMB;
	
	long endTime;

	public Bomb(int x, int y, GameForm container) {
		super(x, y, BattleConf.BOMB_WIDTH, BattleConf.BOMB_HEIGHT, container);
		this.endTime = System.currentTimeMillis() + BattleConf.BOMB_LAST_MILLISEC;
		AudioConf.BOMB.play();
	}
	
	@Override
	public boolean isAlive() {
		return System.currentTimeMillis() <= endTime;
	}

	@Override
	public void toDead() {}

	@Override
	public void onDead() {}

	@Override
	public void paintWith(Graphics g) {
		g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
	}

	@Override
	public void damagedBy(HasDamage attacker) {}

	@Override
	public int getScore() {
		return 0;
	}
	
}
