package cn.milai.ib.obj.bullet.shooter;

import cn.milai.ib.obj.bullet.Bullet;
import cn.milai.ib.obj.bullet.DownBullet;
import cn.milai.ib.obj.plane.Plane;

/**
 * 简单向下子弹的发射器
 *
 * 2019.11.21
 *
 * @author milai
 */
public class DownBulletShooter implements BulletShooter {

	public Plane owner;

	public DownBulletShooter(Plane owner) {
		this.owner = owner;
	}

	@Override
	public Bullet[] shootBullets() {
		return new Bullet[] { new DownBullet(owner.getCenterX(), owner.getCenterY(), owner) };
	}

}
