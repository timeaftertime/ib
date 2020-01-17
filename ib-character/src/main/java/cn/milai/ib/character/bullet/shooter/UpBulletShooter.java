package cn.milai.ib.character.bullet.shooter;

import cn.milai.ib.character.bullet.Bullet;
import cn.milai.ib.character.bullet.UpBullet;
import cn.milai.ib.character.plane.Plane;

/**
 * 简单向上子弹的发射器
 * 2019.11.21
 * @author milai
 */
public class UpBulletShooter implements BulletShooter {

	private Plane owner;

	public UpBulletShooter(Plane owner) {
		this.owner = owner;
	}

	@Override
	public Bullet[] shootBullets() {
		return new Bullet[] { new UpBullet((int) owner.getCenterX(), (int) owner.getCenterY(), owner) };
	}

}
