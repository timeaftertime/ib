package cn.milai.ib.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testToFirstUpper() {
		assertEquals("PlayerPlane", StringUtil.toFirstUpper("playerPlane"));
		assertEquals("MissileBoss", StringUtil.toFirstUpper("MissileBoss"));
		assertEquals("Bomb", StringUtil.toFirstUpper("bomb"));
	}

	@Test
	public void testToFirstLower() {
		assertEquals("gameObject", StringUtil.toFirstLower("GameObject"));
		assertEquals("bomb", StringUtil.toFirstLower("Bomb"));
		assertEquals("downBullet", StringUtil.toFirstLower("downBullet"));
	}

	@Test
	public void testToCamel() {
		assertEquals("GameObject", StringUtil.toUpperCamel("game_object"));
		assertEquals("gameObject", StringUtil.toLowerCamel("game_object"));
		assertEquals("SnakeCaseStr", StringUtil.toUpperCamel("snake_case_str"));
		assertEquals("snakeCaseStr", StringUtil.toLowerCamel("snake_case_str"));
		assertEquals("Plane", StringUtil.toUpperCamel("plane"));
		assertEquals("plane", StringUtil.toLowerCamel("plane"));
	}

	@Test
	public void testToSnake() {
		assertEquals("game_object", StringUtil.toSnake("GameObject"));
		assertEquals("game_object", StringUtil.toSnake("gameObject"));
		assertEquals("snake_case_str", StringUtil.toSnake("SnakeCaseStr"));
		assertEquals("snake_case_str", StringUtil.toSnake("snakeCaseStr"));
		assertEquals("plane", StringUtil.toSnake("Plane"));
		assertEquals("plane", StringUtil.toSnake("plane"));
	}
}
