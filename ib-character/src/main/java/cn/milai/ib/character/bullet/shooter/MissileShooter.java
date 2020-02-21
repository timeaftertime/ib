package cn.milai.ib.character.bullet.shooter;

import cn.milai.ib.character.bullet.Bullet;
import cn.milai.ib.character.bullet.Missile;
import cn.milai.ib.character.property.Shootable;

public class MissileShooter implements BulletShooter {

	@Override
	public Bullet[] shootBullets(Shootable owner) {
		return new Bullet[] { new Missile((int) owner.getCenterX(), (int) owner.getCenterY(), owner) };
	}

}
