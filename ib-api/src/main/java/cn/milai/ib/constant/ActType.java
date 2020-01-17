package cn.milai.ib.constant;

/**
 * Act  的枚举
 * 2019.12.16
 * @author milai
 */
public enum ActType {

	/**
	 * 添加对象
	 */
	NEW(0x1, "new"),

	/**
	 * 休眠
	 */
	SLEEP(0x2, "sleep"),
	
	/**
	 * 显示对话框
	 */
	DIALOG(0x3, "dialog"),

	/**
	 * 调用剧本片段
	 */
	CALL(0x4, "call"),;

	/**
	 * 唯一标识，全局唯一
	 */
	private int code;

	/**
	 * 可读名称
	 */
	private String name;

	ActType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static ActType findByCode(int code) {
		for (ActType act : ActType.values()) {
			if (act.code == code) {
				return act;
			}
		}
		return null;
	}

	public static ActType findByName(String name) {
		for (ActType act : ActType.values()) {
			if (act.name.equals(name)) {
				return act;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return getName();
	}

}
