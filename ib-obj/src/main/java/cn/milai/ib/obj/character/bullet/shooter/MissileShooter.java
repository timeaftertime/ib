package cn.milai.ib.obj.character.bullet.shooter;

import cn.milai.ib.obj.character.bullet.Bullet;
import cn.milai.ib.obj.character.bullet.Missile;
import cn.milai.ib.obj.character.plane.Plane;

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
