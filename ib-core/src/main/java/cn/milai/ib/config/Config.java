package cn.milai.ib.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置
 * @author milai
 * @date 2021.06.05
 */
public class Config {

	private String code;

	private Map<String, String> kvs;

	private Map<String, Config> children;

	public Config(String code, Map<String, String> kvs) {
		this.code = code;
		this.kvs = kvs;
		children = new ConcurrentHashMap<>();
	}

	/**
	 * 获取当前 {@link Config} 数据的 {@link Map} 类型复制
	 * @return
	 */
	public Map<String, String> toMap() {
		return new HashMap<>(kvs);
	}

	/**
	 * 获取指定配置的值，若不存在该配置，返回 {@code null}
	 * @param name
	 * @return
	 */
	public String get(String name) {
		return kvs.get(name);
	}

	/**
	 * 获取所有配置名的 {@link Set}
	 * @return
	 */
	public Set<String> names() {
		return kvs.keySet();
	}

	/**
	 * 获取配置唯一标识
	 * @return
	 */
	public String code() {
		return code;
	}

	/**
	 * 设置一个子 {@link Config}
	 * @param name
	 * @param kvs
	 */
	public void setChild(String name, Map<String, String> kvs) {
		children.put(name, new Config(code + "." + name, kvs));
	}

	/**
	 * 获取指定 name 的子 {@link Config}，若不存在，返回 {@code null}
	 * @param name
	 * @return
	 */
	public Config getChild(String name) {
		return children.get(name);
	}

	/**
	 * 获取所有子配置 name -> {@link Config} 的映射
	 * @return
	 */
	public Map<String, Config> getChildren() { return children; }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Config {");
		for (String k : kvs.keySet()) {
			sb.append(k + "=" + kvs.get(k) + ", ");
		}
		sb.append("}");
		return sb.toString();
	}

}
