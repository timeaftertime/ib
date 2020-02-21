package cn.milai.ib;

import java.io.IOException;

import cn.milai.ib.drama.clip.ClipFactory;
import cn.milai.ib.form.StartForm;

public class InfinityBattle {

	public static void main(String[] args) throws IOException {
		init();
		new StartForm();
	}

	private static void init() {
		IBCore.init();
		ClipFactory.registerClipDecorator(new CompileClipDecorator());
	}

}
