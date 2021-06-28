package cn.milai.ib.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

/**
 * {@link PropertiesUtil} 测试类
 * @author milai
 * @date 2021.06.05
 */
public class PropertiesUtilTest {

	@Test
	public void testLoad() throws IOException {
		Map<String, String> map = PropertiesUtil.load(
			PropertiesUtilTest.class.getResourceAsStream("/testPropertiesUtil.properties")
		);
		assertEquals("19", map.get("age"));
		assertEquals("中文", map.get("language"));
		assertEquals("张三", map.get("姓名"));
	}
}
