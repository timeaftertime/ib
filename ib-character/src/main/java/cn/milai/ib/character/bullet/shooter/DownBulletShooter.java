package cn.milai.ib.character.bullet.shooter;

import cn.milai.ib.character.bullet.Bullet;
import cn.milai.ib.character.bullet.DownBullet;
import cn.milai.ib.character.plane.Plane;

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
		return new Bullet[] { new DownBullet((int) owner.getCenterX(), owner.getY() + owner.getHeight(), owner) };
	}

}
