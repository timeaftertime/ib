package cn.milai.ib.character.bullet.shooter;

import cn.milai.ib.character.bullet.Bullet;
import cn.milai.ib.character.property.Shootable;

/**
 * 子弹发射器
 * @author milai
 */
public interface BulletShooter {

	/**
	 * 为 owner 发射一次子弹
	 * @param character
	 * @return
	 */
	Bullet[] shootBullets(Shootable owner);

}
