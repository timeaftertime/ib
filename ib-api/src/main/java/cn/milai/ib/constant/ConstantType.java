package cn.milai.ib.constant;

/**
 * 常量池中常量类型的枚举
 * 2019.12.31
 * @author milai
 */
public enum ConstantType {

	/**
	 * int 常量
	 */
	INT(1, "int"),

	/**
	 * long 常量
	 */
	LONG(2, "long"),

	/**
	 * float 常量
	 */
	FLOAT(3, "float"),

	/**
	 * UTF8 字符串常量
	 */
	UTF8(4, "utf8");

	private int code;
	private String name;

	ConstantType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * 返回常量类型唯一标识
	 * @return
	 */
	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * 根据 code 查找对应 ConstantType ，若不存在，返回 null
	 * @param code
	 * @return
	 */
	public static ConstantType findByCode(int code) {
		for (ConstantType type : ConstantType.values()) {
			if (type.code == code) {
				return type;
			}
		}
		return null;
	}

}
