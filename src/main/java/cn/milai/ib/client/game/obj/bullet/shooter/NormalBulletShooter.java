package cn.milai.ib.client.game.obj.bullet.shooter;

import cn.milai.ib.client.game.obj.bullet.Bullet;
import cn.milai.ib.client.game.obj.bullet.NormalBullet;
import cn.milai.ib.client.game.obj.plane.Plane;

public class NormalBulletShooter implements BulletShooter {

	private Plane owner;

	public NormalBulletShooter(Plane owner) {
		this.owner = owner;
	}

	@Override
	public Bullet[] shootBullets() {
		return new Bullet[] { new NormalBullet(owner.getCenterX(), owner.getCenterY(), owner) };
	}

}
