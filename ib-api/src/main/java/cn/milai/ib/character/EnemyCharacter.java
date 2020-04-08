package cn.milai.ib.character;

import cn.milai.ib.Enemy;

/**
 * 敌方角色
 * @author milai
 * @date 2020.04.04
 */
public interface EnemyCharacter extends Enemy, IBCharacter {

	@Override
	default int getCamp() {
		return Camp.ENEMY;
	}
}
