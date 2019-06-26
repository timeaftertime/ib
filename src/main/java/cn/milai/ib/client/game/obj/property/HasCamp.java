package cn.milai.ib.client.game.obj.property;

public interface HasCamp extends Alive {

	enum Camp {
		PLAYER(0), PLAYER_BULLET(0, 0), ENEMY(1), ENEMY_BULLET(1, 0), HELPER(1);

		private int[] values;

		Camp(int... values) {
			this.values = values;
		}

		public int[] getValues() {
			return values;
		}

	}

	Camp getCamp();

	default boolean sameCamp(HasCamp h) {
		if (h == null) {
			// TODO
			System.out.println("到底是个什么bug!!!");
		}
		int[] values1 = getCamp().getValues();
		int[] values2 = h.getCamp().getValues();
		for (int i = 0; i < values1.length && i < values2.length; i++) {
			if (values1[i] == values2[i])
				return true;
		}
		return false;
	}

}
