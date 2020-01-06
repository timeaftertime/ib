package cn.milai.ib.drama.statics;

import java.io.DataInputStream;
import java.io.IOException;

import cn.milai.ib.drama.statics.constant.Constant;
import cn.milai.ib.drama.statics.constant.ConstantFactory;
import cn.milai.ib.drama.statics.constant.FloatConstant;
import cn.milai.ib.drama.statics.constant.IntConstant;
import cn.milai.ib.drama.statics.constant.LongConstant;
import cn.milai.ib.drama.statics.constant.UTF8Constant;

/**
 * 静态常量池，对应 .drama 文件里的常量池
 * 2019.12.30
 * @author milai
 */
public class ConstantPool {

	private Constant[] pool;

	public ConstantPool(DataInputStream reader) throws IOException {
		int constantCnt = reader.readUnsignedShort();
		pool = new Constant[constantCnt + 1];
		for (int i = 1; i <= constantCnt; i++) {
			pool[i] = ConstantFactory.create(reader.readUnsignedByte(), reader);
		}
	}

	public IntConstant getInt(int index) {
		return (IntConstant) pool[index];
	}
	
	public LongConstant getLong(int index) {
		return (LongConstant) pool[index];
	}
	
	public FloatConstant getFloat(int index) {
		return (FloatConstant) pool[index];
	}
	
	public UTF8Constant getUTF8(int index) {
		return (UTF8Constant) pool[index];
	}
}
