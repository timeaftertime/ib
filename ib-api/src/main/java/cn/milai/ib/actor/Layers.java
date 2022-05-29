package cn.milai.ib.actor;

import cn.milai.ib.geometry.Layer;

/**
 * 预定义 {@link Layer} 取值
 * @author milai
 * @date 2022.03.06
 */
public enum Layers {

	DEFAULT(0),
	CONTROL(100),
	CURTAIN(Integer.MAX_VALUE - 100),
	SUBTITLE(Integer.MAX_VALUE);

	private int z;

	private Layers(int z) {
		this.z = z;
	}

	public int getZ() { return z; }
}
