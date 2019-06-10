package cn.milai.ib.client.game.property;

public interface HasCamp extends Alive {

	enum Camp {
		PLAYER, ENEMY
	}
	
	Camp getCamp();
	
	default boolean sameCamp(HasCamp h) {
		return getCamp() == h.getCamp();
	}
	
}
