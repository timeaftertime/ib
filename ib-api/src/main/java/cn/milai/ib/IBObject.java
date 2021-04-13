package cn.milai.ib;

import java.awt.Graphics;

import cn.milai.ib.container.Container;
import cn.milai.ib.geometry.Bounds;

/**
 * 所有游戏对象的接口
 * @author milai
 * @date 2020.02.20
 */
public interface IBObject extends Paintable, Bounds {

	/**
	 * 属性 width 的 key
	 */
	String P_WIDTH = "width";

	/**
	 * 属性 height 的 key
	 */
	String P_HEIGHT = "height";

	@Override
	default void paintWith(Graphics g) {
		g.drawImage(getNowImage(), getIntX(), getIntY(), getIntW(), getIntH(), null);
	}

	/**
	 * 获取所属容器
	 * @return
	 */
	Container getContainer();

	/**
	 * 获取对象的 int 类型配置信息
	 * @return
	 */
	int intConf(String key);

	/**
	 * 获取对象的 long 类型配置信息
	 * @return
	 */
	long longConf(String key);

	/**
	 * 获取对象的 double 类型配置信息
	 * @param key
	 * @return
	 */
	double doubleConf(String key);

	/**
	 * 获取对象的 String 类型配置信息
	 * @param key
	 * @return
	 */
	String conf(String key);

}
