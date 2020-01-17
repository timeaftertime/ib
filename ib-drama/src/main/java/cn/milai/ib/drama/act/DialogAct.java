package cn.milai.ib.drama.act;

import java.io.IOException;
import java.lang.reflect.Constructor;

import cn.milai.ib.component.IBComponent;
import cn.milai.ib.constant.ActType;
import cn.milai.ib.container.Container;
import cn.milai.ib.drama.Clip;
import cn.milai.ib.drama.runtime.Frame;
import cn.milai.ib.drama.util.ByteReader;
import cn.milai.ib.util.TimeUtil;

public class DialogAct extends AbstractAct {

	/**
	 * 需要生成对话框的全类名， utf8 常量
	 */
	private int characteClassIndex;

	/**
	 * x 坐标相对于容器 width 的百分比， float 类型常量
	 */
	private int xRateIndex;

	/**
	 * y 坐标相对于容器 height 的百分比，float 类型常量
	 */
	private int yRateIndex;

	/**
	 * 需要显示的对话文本的序号
	 */
	private int textIndex;

	@Override
	public ActType getCode() {
		return ActType.DIALOG;
	}

	@Override
	protected void action(Frame frame, Container container) throws Exception {
		Clip clip = frame.getClip();
		String dialogClass = clip.getUTF8Const(characteClassIndex);
		int x = (int) (clip.getFloatConst(xRateIndex) * container.getWidth());
		int y = (int) (clip.getFloatConst(yRateIndex) * container.getContentHeight());
		String text = clip.getUTF8Const(textIndex);
		container.addObject(createInstance(dialogClass, x, y, text, container));
		// 等待一帧以暂停后续的剧情
		TimeUtil.wait(container, 1L);
	}

	private IBComponent createInstance(String dialogClass, int x, int y, String text, Container container) throws Exception {
		return getIISCConstructot(dialogClass).newInstance(x, y, text, container);
	}

	@SuppressWarnings("unchecked")
	private Constructor<IBComponent> getIISCConstructot(String dialogClass) throws NoSuchMethodException, SecurityException, ClassNotFoundException {
		return (Constructor<IBComponent>) Class.forName(dialogClass).getConstructor(int.class, int.class, String.class, Container.class);
	}

	@Override
	protected void readOperands(ByteReader reader) throws IOException {
		characteClassIndex = reader.readUint16();
		xRateIndex = reader.readUint16();
		yRateIndex = reader.readUint16();
		textIndex = reader.readUint16();
	}

}
