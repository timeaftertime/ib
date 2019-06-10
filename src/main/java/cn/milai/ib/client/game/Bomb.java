package cn.milai.ib.client.game;

import java.awt.Graphics;
import java.awt.Image;

import cn.milai.ib.client.game.conf.AudioConf;
import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.property.HasDamage;
import cn.milai.ib.client.game.property.Paintable;

public class Bomb extends GameObject implements Paintable {

	private Image img = ImageConf.BOMB;
	
	long endTime;

	private GameForm container;
	
	public Bomb(int x, int y, GameForm container) {
		super(x, y, BattleConf.BOMB_WIDTH, BattleConf.BOMB_HEIGHT);
		this.endTime = System.currentTimeMillis() + BattleConf.BOMB_LAST_MILLISEC;
		this.container = container;
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
	public void paint(Graphics g) {
		g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
	}

	@Override
	public GameForm getContainer() {
		return container;
	}

	@Override
	public void damagedBy(HasDamage attacker) {}

	@Override
	public int getScore() {
		return 0;
	}
	
}
