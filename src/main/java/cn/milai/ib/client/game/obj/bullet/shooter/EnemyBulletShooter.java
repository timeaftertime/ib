package cn.milai.ib.client.game.obj.bullet.shooter;

import cn.milai.ib.client.game.obj.bullet.Bullet;
import cn.milai.ib.client.game.obj.bullet.EnemyBullet;
import cn.milai.ib.client.game.obj.plane.Plane;

public class EnemyBulletShooter implements BulletShooter {

	public Plane owner;

	public EnemyBulletShooter(Plane owner) {
		this.owner = owner;
	}

	@Override
	public Bullet[] shootBullets() {
		return new Bullet[] {
				new EnemyBullet(owner.getCenterX(), owner.getCenterY(), owner) };
	}

}
