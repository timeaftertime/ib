package cn.milai.ib.client.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public final class JSONUtil {

	private JSONUtil() {}
	
	/**
	 * 返回设置了如下配置的 JSON 字符串
	 * 		序列化 enum 对象时使用 toString 而不是默认的 name 属性
	 * @param json
	 * @return
	 */
	public static String toConfiguredJSONString(JSONObject json) {
		return JSON.toJSONString(json,
				SerializerFeature.config(JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.WriteEnumUsingToString, true));
	}
	
}
