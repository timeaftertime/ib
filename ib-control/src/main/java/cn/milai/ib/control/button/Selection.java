package cn.milai.ib.control.button;

import cn.milai.ib.control.text.Label;
import cn.milai.ib.control.text.Selections;

/**
 * 选项按钮
 * @author milai
 * @date 2021.06.12
 */
public class Selection extends Label {

	public Selection(Selections owner, int value, String text) {
		setText(text);
		onPressUp(e -> owner.select(value));
	}

}