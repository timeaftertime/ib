package cn.milai.ib.obj.bullet.shooter;

import cn.milai.ib.obj.bullet.Bullet;
import cn.milai.ib.obj.bullet.UpBullet;
import cn.milai.ib.obj.plane.Plane;

/**
 * 简单向上子弹的发射器
 *
 * 2019.11.21
 *
 * @author milai
 */
public class UpBulletShooter implements BulletShooter {

	private Plane owner;

	public UpBulletShooter(Plane owner) {
		this.owner = owner;
	}

	@Override
	public Bullet[] shootBullets() {
		return new Bullet[] { new UpBullet(owner.getCenterX(), owner.getCenterY(), owner) };
	}

}
