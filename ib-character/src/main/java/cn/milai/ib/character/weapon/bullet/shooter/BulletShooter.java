package cn.milai.ib.character.weapon.bullet.shooter;

import cn.milai.ib.character.weapon.Weapon;
import cn.milai.ib.character.weapon.bullet.Bullet;
import cn.milai.ib.container.Container;

/**
 * 子弹发射器
 * @author milai
 */
public interface BulletShooter extends Weapon {

	@Override
	default void attack() {
		Container container;
		Bullet[] bullets;
		synchronized (this) {
			if (canShoot()) {
				container = getOwner().getContainer();
				bullets = createBullets();
				for (Bullet bullet : bullets) {
					container.addObject(bullet);
				}
			}
		}
	}

	/**
	 * 当前子弹发射器是否可以发射子弹
	 * @param attacker 
	 * @return
	 */
	boolean canShoot();

	/**
	 * 构造要发射的子弹的数组
	 * @return
	 */
	Bullet[] createBullets();

}
