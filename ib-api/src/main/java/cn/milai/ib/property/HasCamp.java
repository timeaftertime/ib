package cn.milai.ib.property;

import cn.milai.ib.constant.Camp;

public interface HasCamp extends Alive {

	Camp getCamp();

	default boolean sameCamp(HasCamp h) {
		int[] values1 = getCamp().getValues();
		int[] values2 = h.getCamp().getValues();
		for (int i = 0; i < values1.length && i < values2.length; i++) {
			if (values1[i] == values2[i])
				return true;
		}
		return false;
	}

}
