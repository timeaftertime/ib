package cn.milai.ib.client.game.obj.property;

public interface HasDamage {

	/**
	 * 使 alive 死亡时调用
	 * @param alive
	 */
	void onKill(Alive alive);
	
	int getDamage();
	
}
