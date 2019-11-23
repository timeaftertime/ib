package cn.milai.ib.constant;

public enum Camp {
	PLAYER(0), PLAYER_BULLET(0, 0), ENEMY(1), ENEMY_BULLET(1, 0), HELPER(1);

	private int[] values;

	Camp(int... values) {
		this.values = values;
	}

	public int[] getValues() {
		return values;
	}

}