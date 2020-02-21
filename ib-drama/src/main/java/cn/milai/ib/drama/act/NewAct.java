package cn.milai.ib.drama.act;

import java.io.IOException;
import java.lang.reflect.Constructor;

import cn.milai.ib.constant.ActType;
import cn.milai.ib.container.Container;
import cn.milai.ib.drama.act.ex.IllegalOperandsException;
import cn.milai.ib.drama.clip.Clip;
import cn.milai.ib.drama.runtime.Frame;
import cn.milai.ib.drama.util.ByteReader;
import cn.milai.ib.obj.IBCharacter;

/**
 * 添加对象的动作
 * 2019.12.16
 * @author milai
 */
public class NewAct extends AbstractAct {

	/**
	 * 需要生成角色的全类名， utf8 常量
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

	@Override
	protected void action(Frame frame, Container container) throws Exception {
		Clip clip = frame.getClip();
		String characteClass = clip.getUTF8Const(characteClassIndex);
		int x = (int) (clip.getFloatConst(xRateIndex) * container.getWidth());
		int y = (int) (clip.getFloatConst(yRateIndex) * container.getContentHeight());
		container.addObject(createInstance(characteClass, x, y, container));
	}

	private IBCharacter createInstance(String characteClass, int x, int y, Container container) throws Exception {
		return iicConstructor(characteClass).newInstance(x, y, container);
	}

	/**
	 * 获取 characterClass 对应类的 (int, int Container) 类型构造方法
	 * @return
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	private Constructor<? extends IBCharacter> iicConstructor(String characteClass) throws NoSuchMethodException,
		ClassNotFoundException {
		Class<?> clazz = Class.forName(characteClass);
		if (!IBCharacter.class.isAssignableFrom(clazz)) {
			throw new IllegalOperandsException(this,
				String.format("New 指令的参数必须为 %s 子类的全类名, characterClass = %s", IBCharacter.class.getName(), clazz
					.getName()));
		}
		return (Constructor<? extends IBCharacter>) clazz.getConstructor(int.class, int.class, Container.class);
	}

	@Override
	protected void readOperands(ByteReader reader) throws IOException {
		characteClassIndex = reader.readUint16();
		xRateIndex = reader.readUint16();
		yRateIndex = reader.readUint16();
	}

	@Override
	public ActType getCode() {
		return ActType.NEW;
	}

}
