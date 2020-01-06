package cn.milai.ib.drama.statics.constant;

import java.io.DataInputStream;
import java.io.IOException;

import cn.milai.ib.constant.ConstantType;

/**
 * Constant 工厂类
 * 2019.12.31
 * @author milai
 */
public abstract class ConstantFactory {

	public ConstantFactory() {

	}

	/**
	 * 根据 code 判断 Constant 类型，并从 reader 中读取对应参数构造 Constant
	 * @param code
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static Constant create(int code, DataInputStream reader) throws IOException {
		switch (ConstantType.findByCode(code)) {
			case INT :
				return new IntConstant(reader.readInt());
			case FLOAT :
				return new FloatConstant(reader.readFloat());
			case LONG :
				return new LongConstant(reader.readLong());
			case UTF8 :
				return new UTF8Constant(reader.readUTF());
			default :
				throw new UnknownConstantException(code);
		}
	}
}
