package cn.milai.ib.role;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.item.Item;
import cn.milai.ib.item.property.holder.PainterHolder;
import cn.milai.ib.role.property.holder.HealthHolder;

/**
 * 参与到游戏中的游戏角色
 * @author milai
 * @date 2020.02.20
 */
public interface Role extends Item, HealthHolder, PainterHolder {

	/**
	 * 获取当前角色所属阵营
	 * @return
	 */
	default int getCamp() { return Camp.NO_CAMP; }

	/**
	 * 获取当前游戏角色的朝向与 y 轴负方向的夹角弧度
	 * 顺时针为正
	 * @return
	 */
	double getDirection();

	/**
	 * 设置当前游戏角色的朝向
	 * 弧度将转化为 (-PI/2, PI/2] 之间
	 * @param radian 新朝向与 y 轴负方向的夹角弧度
	 * @return
	 */
	void setDirection(double radian);

	@Override
	LifecycleContainer container();

}
