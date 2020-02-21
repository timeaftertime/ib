package cn.milai.ib.character.bullet.shooter;

import cn.milai.ib.character.bullet.Bullet;
import cn.milai.ib.character.bullet.DownBullet;
import cn.milai.ib.character.property.Shootable;

/**
 * 简单向下子弹的发射器
 * 2019.11.21
 * @author milai
 */
public class DownBulletShooter implements BulletShooter {

	@Override
	public Bullet[] shootBullets(Shootable owner) {
		return new Bullet[] { new DownBullet((int) owner.getCenterX(), owner.getY() + owner.getHeight(), owner) };
	}

}
