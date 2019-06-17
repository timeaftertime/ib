package cn.milai.ib.client.game.obj.bullet;

import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.obj.plane.Plane;
import cn.milai.ib.client.game.obj.property.CanCrashed;
import cn.milai.ib.client.game.obj.property.HasDamage;

public class EnemyBullet extends Bullet {

	private int speed = BattleConf.NORMAL_BULLET_SPEED;
	
	private int life = 1;
	
	public EnemyBullet(int x, int y, GameForm container, Camp camp, Plane owner) {
		super(x, y,
				BattleConf.ENEMY_BULLET_WIDTH, BattleConf.ENEMY_BULLET_HEIGHT,
				BattleConf.ENEMY_BULLET_POWER,
				ImageConf.ENEMY_BULLET,
				camp,
				owner,
				container
				);
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
	public void onDead() {
		
	}

	@Override
	public void simpleMove() {
		setY(getY() + speed);
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
	public void onCrash(CanCrashed crashed) {
		crashed.damagedBy(this);
		toDead();
	}

	@Override
	public int getDamage() {
		return BattleConf.ENEMY_BULLET_POWER;
	}

}
