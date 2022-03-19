package cn.milai.ib.geometry;

/**
 * 层级
 * @author milai
 * @date 2021.06.12
 */
public interface Layer {

	/**
	 * 获取层级 Z ，值越大越靠近屏幕
	 * @return
	 */
	int getZ();

}
