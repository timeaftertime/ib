package cn.milai.ib.client.game.obj.bullet.shooter;

import cn.milai.ib.client.game.obj.bullet.Bullet;
import cn.milai.ib.client.game.obj.bullet.EnemyBullet;
import cn.milai.ib.client.game.obj.plane.Plane;

public class DoubleEnemyBulletShooter implements BulletShooter {

	private Plane owner;

	public DoubleEnemyBulletShooter(Plane owner) {
		this.owner = owner;
	}

	@Override
	public Bullet[] shootBullets() {
		return new Bullet[] { new EnemyBullet(owner.getCenterX() - owner.getWidth() / 4, owner.getCenterY(), owner),
				new EnemyBullet(owner.getCenterX() + owner.getWidth() / 4, owner.getCenterY(), owner) };
	}

}
