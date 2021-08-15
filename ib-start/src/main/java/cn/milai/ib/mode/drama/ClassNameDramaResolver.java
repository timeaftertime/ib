package cn.milai.ib.mode.drama;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.milai.common.ex.unchecked.Uncheckeds;

/**
 * 使用全类名反射获取剧本的剧本解析器
 * @author milai
 * @date 2020.03.07
 */
@Order(1)
@Component
public class ClassNameDramaResolver implements DramaResolver {

	private static final Logger LOG = LoggerFactory.getLogger(ClassNameDramaResolver.class);

	@Override
	public Drama resolve(String dramaCode) {
		return Uncheckeds.logWith(
			LOG, () -> (Drama) Class.forName(dramaCode).newInstance(), "解析剧本失败：resolver = %s", getClass().getName()
		);
	}

}
