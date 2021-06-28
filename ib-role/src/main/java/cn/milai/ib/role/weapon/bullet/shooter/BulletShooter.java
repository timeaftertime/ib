package cn.milai.ib.role.weapon.bullet.shooter;

import cn.milai.ib.container.Container;
import cn.milai.ib.role.weapon.Weapon;
import cn.milai.ib.role.weapon.bullet.Bullet;

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
				container = getOwner().container();
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
