package cn.milai.ib.geometry;

/**
 * 层级
 * @author milai
 * @date 2021.06.12
 */
public interface Layer {

	/**
	 * 默认 Z 坐标
	 */
	int DEFAULT_Z = 0;

	/**
	 * 获取层级 Z ，值越大越靠近屏幕
	 * @return
	 */
	default int getZ() { return DEFAULT_Z; }

}
