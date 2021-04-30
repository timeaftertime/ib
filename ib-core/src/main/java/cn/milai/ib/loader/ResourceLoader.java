package cn.milai.ib.loader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.milai.ib.conf.PathConf;

/**
 * 资源加载管理器
 * @author milai
 * @date 2021.04.30
 */
public class ResourceLoader {

	/**
	 * 已经加载到内存的缓存资源
	 * drama -> { resource -> 资源字节数组 } 
	 */
	private final Map<String, Map<String, byte[]>> RESOURCES = new ConcurrentHashMap<>();

	/**
	 * 获取指定命名空间下所有资源的相对路径到字节数据的映射
	 * @param code
	 * @param cache 是否需要缓存加载的资源命名空间
	 * @return
	 */
	public Map<String, byte[]> load(String code, boolean cache) {
		Map<String, byte[]> data = RESOURCES.computeIfAbsent(
			code, c -> DirectoryLoader.load(PathConf.codePath(code))
		);
		if (!cache) {
			RESOURCES.remove(code);
		}
		return data;
	}

	/**
	 * 获取指定命名空间下所有资源的相对路径到字节数据的映射，不使用缓存
	 * @param code
	 * @return
	 */
	public Map<String, byte[]> load(String code) {
		return load(code, false);
	}

	/**
	 * 卸载缓存的指定 code 的所有资源，返回之前缓存的资源
	 * @param code
	 * @return
	 */
	public Map<String, byte[]> unload(String code) {
		return RESOURCES.remove(code);
	}

}
