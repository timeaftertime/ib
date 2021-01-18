package cn.milai.ib.drama;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 使用全类名反射获取剧本的剧本解析器
 * @author milai
 * @date 2020.03.07
 */
@Order(1)
@Component
public class ClassNameDramaResolver implements DramaResolver {

	private Logger log = LoggerFactory.getLogger(ClassNameDramaResolver.class);

	@Override
	public Drama resolve(String dramaCode) {
		try {
			return (Drama) Class.forName(dramaCode).newInstance();
		} catch (Exception e) {
			log.info("尝试解析剧本失败：resolver = {}, error = {}", getClass().getName(), e);
			return null;
		}
	}

}
