package cn.milai.ib.character.bullet.shooter;

import cn.milai.ib.character.bullet.Bullet;
import cn.milai.ib.character.bullet.Missile;
import cn.milai.ib.character.plane.Plane;

public class MissileShooter implements BulletShooter {

	private Plane owner;

	public MissileShooter(Plane owner) {
		this.owner = owner;
	}

	@Override
	public Bullet[] shootBullets() {
		return new Bullet[] { new Missile((int) owner.getCenterX(), (int) owner.getCenterY(), owner) };
	}

}
