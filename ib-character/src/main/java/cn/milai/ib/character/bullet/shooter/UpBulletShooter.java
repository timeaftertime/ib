package cn.milai.ib.character.bullet.shooter;

import cn.milai.ib.character.bullet.Bullet;
import cn.milai.ib.character.bullet.UpBullet;
import cn.milai.ib.character.property.Shootable;

/**
 * 简单向上子弹的发射器
 * 2019.11.21
 * @author milai
 */
public class UpBulletShooter implements BulletShooter {

	@Override
	public Bullet[] shootBullets(Shootable owner) {
		return new Bullet[] { new UpBullet((int) owner.getCenterX(), (int) owner.getCenterY(), owner) };
	}

}
