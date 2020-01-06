package cn.milai.ib.drama.statics.constant;

/**
 * 代表一个普通值的常量
 * 2019.12.31
 * @author milai
 */
public abstract class ValueConstant<T> implements Constant {

	private T value;

	public ValueConstant(T value) {
		this.value = value;
	}

	/**
	 * 返回常量所代表的值
	 * @return
	 */
	public T getValue() {
		return value;
	}

}
