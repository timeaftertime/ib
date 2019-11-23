package cn.milai.ib.property;

import cn.milai.ib.constant.BulletType;

/**
 * 可发射子弹的
 *
 * @author milai
 */
public interface Shootable {

	/**
	 * 发射指定类型的子弹
	 * @param type
	 */
	void shoot(BulletType type);

}
