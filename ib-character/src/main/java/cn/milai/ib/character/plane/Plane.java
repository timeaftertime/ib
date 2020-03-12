package cn.milai.ib.character.plane;

import cn.milai.ib.character.WeaponType;
import cn.milai.ib.character.bullet.shooter.BulletShooter;
import cn.milai.ib.character.property.CanCrash;
import cn.milai.ib.character.property.Explosible;
import cn.milai.ib.character.property.Shootable;

/**
 * 飞机类型角色
 * @author milai
 * @date 2020.02.20
 */
public interface Plane extends Shootable, CanCrash, Explosible {

	/**
	 * 属性 可同时存在于所在容器的子弹最大数量 的 key
	 */
	String P_MAX_BULLET_NUM = "maxBulletNum";

	/**
	 * 属性 两次 shoot 之间的最小间隔帧数 的 key
	 */
	String P_SHOOT_INTERVAL = "shootInterval";

	/**
	 * 获取当前飞机初始的发射间隔
	 * @return
	 */
	int getInitShootInterval();

	/**
	 * 获取当前飞机可同时存在于容器中的子弹最大数量
	 * @return
	 */
	int getMaxBulletNum();

	/**
	 * 设置当前飞机可同时存在于容器中的子弹最大数量
	 * @param maxBulletNum
	 */
	void setMaxBulletNum(int maxBulletNum);

	/**
	 * 设置指定 type 武器的发射器
	 * @param shooter
	 * @param type
	 */
	void setBulletShooter(BulletShooter shooter, WeaponType type);

	/**
	 * 设置主武器的发射器为 shooter
	 * @param shooter
	 */
	default void setBulletShooter(BulletShooter shooter) {
		setBulletShooter(shooter, WeaponType.MAIN);
	}

	/**
	 * 获取当前飞机指定类型武器的发射器
	 * @param type
	 * @return
	 */
	BulletShooter getBulletShooter(WeaponType type);

	/**
	 * 获取当前飞机主武器的发射器
	 * @return
	 */
	BulletShooter getBulletShooter();

}
