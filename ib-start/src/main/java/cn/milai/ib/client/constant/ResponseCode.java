package cn.milai.ib.client.constant;

public enum ResponseCode {

	SUCCESS(0),

	SERVER_ERROR(-1),
	
	MESSAGE_TYPE_NOT_FOUND(-2),

	PARAM_ILLEGAL(1),
	
	USERNAME_NOT_EXISTS(2),

	PASSWORD_ERROR(3),
	
	USER_NOT_ONLINE(4),
	
	USERNAME_ALREADY_EXISTS(5);

	private int code;

	private ResponseCode(int code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return Integer.toString(code);
	}
	
	public static ResponseCode parse(int code) {
		for(ResponseCode value : ResponseCode.values()) {
			if(value.code == code)
				return value;
		}
		throw new IllegalArgumentException("ResponseCode 不存在：" + code);
	}

}
