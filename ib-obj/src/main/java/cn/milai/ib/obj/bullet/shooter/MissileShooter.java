package cn.milai.ib.obj.bullet.shooter;

import cn.milai.ib.obj.bullet.Bullet;
import cn.milai.ib.obj.bullet.Missile;
import cn.milai.ib.obj.plane.Plane;

public class MissileShooter implements BulletShooter {

	private Plane owner;

	public MissileShooter(Plane owner) {
		this.owner = owner;
	}

	@Override
	public Bullet[] shootBullets() {
		return new Bullet[] { new Missile(owner.getCenterX(), owner.getCenterY(), owner) };
	}

}