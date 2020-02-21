package cn.milai.ib.drama.clip;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import cn.milai.ib.conf.PathConf;
import cn.milai.ib.drama.ex.ClipParamInvalidExcecption;
import cn.milai.ib.util.FileUtil;
import cn.milai.ib.util.HttpUtil;

/**
 * 产生 Clip 的工厂类
 * 2019.12.14
 * @author milai
 */
public class ClipFactory {

	private static final Logger log = LoggerFactory.getLogger(ClipFactory.class);

	private static List<ClipDecorator> decorators = Lists.newArrayList();

	/**
	 * 注册一个剧本数据装饰器
	 * @param decorator
	 */
	public static void registerClipDecorator(ClipDecorator decorator) {
		decorators.add(decorator);
	}

	/**
	 * 加载指定的剧本片段
	 * @param clipCode
	 * @param params 需要传给剧本片段的参数，为 null 表示不需要传递
	 * @return
	 */
	public static Clip newClip(String clipCode, Map<String, String> params) {
		Clip clip = new DefaultClip(resolveClip(clipCode));
		copyParams(clip, params);
		return clip;
	}

	private static byte[] resolveClip(String clipCode) {
		String path = PathConf.dramaPath(clipCode);
		File file = new File(path);
		if (!file.exists()) {
			log.info("剧本文件 {} 不存在，尝试从远程服务器获取……", path);
			FileUtil.save(path, HttpUtil.getFile(PathConf.dramaRepo(clipCode)));
		}
		byte[] data = FileUtil.read(file);
		for (ClipDecorator decorator : decorators) {
			data = decorator.decorate(data);
		}
		return data;
	}

	/**
	 * 检查并复制 params 中属性到 clip
	 * @param clip
	 * @param params 需要复制的参数 map ，若为 null 表示不需要复制
	 * @return
	 */
	private static final void copyParams(Clip clip, Map<String, String> params) {
		if (params == null) {
			return;
		}
		for (String key : params.keySet()) {
			String value = params.get(key);
			if (value == null) {
				throw new ClipParamInvalidExcecption(key, value);
			}
			clip.setVariable(key, value);
		}
	}

}
