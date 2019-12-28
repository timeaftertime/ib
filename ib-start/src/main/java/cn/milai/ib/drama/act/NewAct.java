package cn.milai.ib.drama.act;

import java.io.IOException;
import java.lang.reflect.Constructor;

import cn.milai.ib.constant.ActType;
import cn.milai.ib.container.Container;
import cn.milai.ib.drama.ByteReader;
import cn.milai.ib.drama.Frame;
import cn.milai.ib.obj.character.IBCharacter;

/**
 * 添加对象的动作
 * 2019.12.16
 * @author milai
 */
public class NewAct extends AbstractAct {

	/**
	 * 需要生成角色的全类名
	 */
	private String characteClass;

	/**
	 * x 坐标相对于容器 width 的百分比
	 */
	private float xRate;

	/**
	 * y 坐标相对于容器 height 的百分比
	 */
	private float yRate;

	@Override
	protected void action(Frame frame, Container container) throws Exception {
		container.addObject(createInstance(container));
	}

	private IBCharacter createInstance(Container container) throws Exception {
		return (IBCharacter) iicConstructor().newInstance(
				(int) xRate * container.getWidth(),
				(int) yRate * container.getContentHeight(), container);
	}

	/**
	 * 获取 characterClass 对应类的 (int, int Container) 类型构造方法
	 * @return
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 */
	private Constructor<?> iicConstructor()
			throws NoSuchMethodException, ClassNotFoundException {
		return Class.forName(characteClass).getConstructor(Integer.class,
				Integer.class, Container.class);
	}

	@Override
	protected void readOperands(ByteReader reader) throws IOException {
		characteClass = reader.readUTF8();
		xRate = reader.readFloat();
		yRate = reader.readFloat();
	}

	@Override
	public ActType getCode() {
		return ActType.NEW;
	}

}
