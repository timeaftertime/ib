package cn.milai.ib.actor.prop.button;

import cn.milai.ib.actor.prop.text.Label;
import cn.milai.ib.actor.prop.text.Selections;

/**
 * 选项按钮
 * @author milai
 * @date 2021.06.12
 */
public class Selection extends Label {

	public Selection(Selections owner, int value, String text) {
		setText(text);
		onPressUp().subscribe(e -> owner.select(value));
	}

}