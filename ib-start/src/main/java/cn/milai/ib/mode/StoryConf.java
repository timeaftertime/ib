package cn.milai.ib.mode;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * {@link StoryMode} 配置
 * @author milai
 * @date 2021.02.28
 */
@Configuration
@ConditionalOnProperty(prefix = StoryConf.PREFIX, name = StoryConf.ENABLE)
@ConfigurationProperties(prefix = StoryConf.PREFIX)
public class StoryConf {

	public static final String PREFIX = "ib.mode.story";

	public static final String ENABLE = "enable";

	/**
	 * 是否启动剧情模式
	 */
	private boolean enable = false;

	/**
	 * 关卡设置
	 */
	private String[] stages = new String[0];

	public String[] getStages() { return stages; }

	public void setStages(String[] stages) { this.stages = stages; }

	public boolean isEnable() { return enable; }

	public void setEnable(boolean enable) { this.enable = enable; }

}
