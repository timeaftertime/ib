package cn.milai.ib;

import cn.milai.ib.drama.clip.ClipFactory;
import cn.milai.ib.form.StartForm;

public class InfinityBattle {

	public static void main(String[] args) {
		init();
		new StartForm();
	}

	private static void init() {
		ClipFactory.registerClipDecorator(new CompileClipDecorator());
	}

}
