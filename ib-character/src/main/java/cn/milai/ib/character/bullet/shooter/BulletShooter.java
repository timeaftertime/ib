package cn.milai.ib.character.bullet.shooter;

import cn.milai.ib.character.bullet.Bullet;

/**
 * 子弹发射器，要求提供 public 的以 {@code Plane} 为参数的构造方法
 *
 * @author milai
 */
public interface BulletShooter {

	Bullet[] shootBullets();

}
