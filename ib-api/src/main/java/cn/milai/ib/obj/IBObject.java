package cn.milai.ib.obj;

import cn.milai.ib.container.Container;

/**
 * 所有游戏对象的接口
 * @author milai
 * @date 2020.02.20
 */
public interface IBObject extends Paintable, Locable {

	/**
	 * 属性 width 的 key
	 */
	String P_WIDTH = "width";

	/**
	 * 属性 height 的 key
	 */
	String P_HEIGHT = "height";

	/**
	 * 是否与 IBObj 接触
	 * @param gameObj
	 * @return
	 */
	boolean intersects(IBObject IBObj);

	/**
	 * 判断指定点是否在游戏对象中
	 * @param x
	 * @param y
	 * @return
	 */
	boolean containsPoint(int x, int y);

	/**
	 * 获取所属容器
	 * @return
	 */
	Container getContainer();

	/**
	 * 确保当前游戏对象出于容器中，若不在，将其移动到容器之中
	 */
	void ensureInContainer();

}
