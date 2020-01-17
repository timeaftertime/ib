package cn.milai.ib.character.bullet.shooter;

import cn.milai.ib.character.bullet.Bullet;
import cn.milai.ib.character.bullet.DownBullet;
import cn.milai.ib.character.plane.Plane;

/**
 * 连续产生两个平行简单向下子弹的反射器
 *
 * 2019.11.21
 *
 * @author milai
 */
public class DoubleDownBulletShooter implements BulletShooter {

	private Plane owner;

	public DoubleDownBulletShooter(Plane owner) {
		this.owner = owner;
	}

	@Override
	public Bullet[] shootBullets() {
		return new Bullet[] {
				new DownBullet((int) (owner.getCenterX() - owner.getWidth() / 4.0), (int) owner.getCenterY(), owner),
				new DownBullet((int) (owner.getCenterX() + owner.getWidth() / 4.0), (int) owner.getCenterY(), owner)
		};
	}

}
