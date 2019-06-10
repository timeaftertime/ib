package cn.milai.ib.client.game.bullet;

import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.plane.Plane;
import cn.milai.ib.client.game.property.CanCrashed;
import cn.milai.ib.client.game.property.HasDamage;

public class NormalBullet extends Bullet {
	
	private GameForm container;
	
	private int speed = -BattleConf.NORMAL_BULLET_SPEED;
	
	private int life = 1;
	
	public NormalBullet(int x, int y, GameForm container, Camp camp, Plane owner) {
		super(x, y,
				BattleConf.NORMAL_BULLET_WIDTH, BattleConf.NORMAL_BULLET_HEIGHT,
				BattleConf.NORMAL_BULLET_POWER,
				ImageConf.NORMAL_BULLET,
				camp,
				owner
				);
		this.container = container;
	}

	@Override
	public void simpleMove() {
		setY(getY() + speed);
	}

	@Override
	public GameForm getContainer() {
		return this.container;
	}

	@Override
	public boolean isAlive() {
		return this.life > 0;
	}
	
	@Override
	public void toDead() {
		this.life = 0;
	}

	@Override
	public void damagedBy(HasDamage attacker) {
		if(!isAlive())
			return;
		synchronized (this) {
			this.life -= attacker.getDamage();
			if(!isAlive())
				attacker.onKill(this);
		}
	}

	@Override
	public int getDamage() {
		return BattleConf.NORMAL_BULLET_POWER;
	}
	@Override
	public void onCrash(CanCrashed crashed) {
		crashed.damagedBy(this);
		toDead();
	}
	
}
