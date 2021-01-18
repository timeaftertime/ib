package cn.milai.ib;

import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 启动入口
 * @author milai
 */
public class InfinityBattle {

	/**
	 * 使用指定参数运行指定 {@link InfinityBattleApplication} 注解的类表示的应用程序
	 * @param mainClass
	 * @param args
	 */
	public static void run(Class<?> mainClass, String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(mainClass);
		builder.headless(false).run(args);
		new StartForm();
	}

}
