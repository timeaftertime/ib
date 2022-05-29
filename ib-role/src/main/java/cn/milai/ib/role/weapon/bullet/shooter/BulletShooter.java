package cn.milai.ib.role.weapon.bullet.shooter;

import cn.milai.ib.actor.config.ItemConfigApplier;
import cn.milai.ib.role.weapon.Weapon;
import cn.milai.ib.role.weapon.bullet.Bullet;
import cn.milai.ib.stage.Stage;

/**
 * 子弹发射器
 * @author milai
 */
public interface BulletShooter extends Weapon, ItemConfigApplier {

	@Override
	default void attack() {
		Stage stage;
		Bullet[] bullets;
		synchronized (this) {
			if (canShoot()) {
				stage = getOwner().stage();
				bullets = createBullets();
				for (Bullet bullet : bullets) {
					stage.addActor(bullet);
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
