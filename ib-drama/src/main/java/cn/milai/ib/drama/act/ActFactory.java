package cn.milai.ib.drama.act;

import cn.milai.ib.constant.ActType;
import cn.milai.ib.drama.act.ex.ActNotExistsException;

/**
 * 动作工厂类
 * 2019.12.14
 * @author milai
 */
public class ActFactory {

	/**
	 * 构造 code 对应的 Act
	 * @param code
	 */
	public static Act create(int code) {
		switch (ActType.findByCode(code)) {
			case NEW : {
				return new NewAct();
			}
			case SLEEP : {
				return new SleepAct();
			}
			case DIALOG: {
				return new DialogAct();
			}
			default : {
				throw new ActNotExistsException(code);
			}
		}
	}

}
