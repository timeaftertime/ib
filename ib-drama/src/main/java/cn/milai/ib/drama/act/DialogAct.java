package cn.milai.ib.drama.act;

import java.awt.Image;
import java.io.IOException;
import java.lang.reflect.Constructor;

import cn.milai.ib.ImageLoader;
import cn.milai.ib.component.IBComponent;
import cn.milai.ib.constant.ActType;
import cn.milai.ib.container.Container;
import cn.milai.ib.drama.clip.Clip;
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
	 * 说话者全类名的序号，utf8 常量
	 */
	private int speakerClassIndex;

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
		Image speaker = resolveSpeaker(clip);
		String text = clip.getUTF8Const(textIndex);
		container.addObject(createInstance(dialogClass, x, y, speaker, text, container));
		// 等待一帧以暂停后续的剧情
		TimeUtil.wait(container, 1L);
	}

	private Image resolveSpeaker(Clip clip) throws ClassNotFoundException {
		if (speakerClassIndex == 0) {
			return null;
		}
		return ImageLoader.getContextImageLoader().loadImage(Class.forName(clip.getUTF8Const(speakerClassIndex)));
	}

	private IBComponent createInstance(String dialogClass, int x, int y, Image speaker, String text, Container container) throws Exception {
		return getIIISCConstructot(dialogClass).newInstance(x, y, speaker, text, container);
	}

	/**
	 * 获取 dialogClass 对应类型的 int，int，Image，String，Container 参数的构造方法
	 * @param dialogClass
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	private Constructor<IBComponent> getIIISCConstructot(String dialogClass) throws NoSuchMethodException, SecurityException, ClassNotFoundException {
		return (Constructor<IBComponent>) Class.forName(dialogClass).getConstructor(int.class, int.class, Image.class, String.class, Container.class);
	}

	@Override
	protected void readOperands(ByteReader reader) throws IOException {
		characteClassIndex = reader.readUint16();
		xRateIndex = reader.readUint16();
		yRateIndex = reader.readUint16();
		speakerClassIndex = reader.readUint16();
		textIndex = reader.readUint16();
	}

}
