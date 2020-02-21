package cn.milai.ib.character.property;

import cn.milai.ib.obj.IBCharacter;

/**
 * 可移动角色
 * @author milai
 * @date 2020.02.20
 */
public interface Movable extends IBCharacter {

	/**
	 * 使当前角色移动一次
	 */
	void move();

}
