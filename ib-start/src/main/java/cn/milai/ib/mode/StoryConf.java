package cn.milai.ib.mode;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * {@link StoryMode} 配置
 * @author milai
 * @date 2021.02.28
 */
@Component
@ConfigurationProperties(prefix = "ib.mode.story")
public class StoryConf {

	/**
	 * 关卡设置
	 */
	private String[] stages = new String[0];

	public String[] getStages() { return stages; }

	public void setStages(String[] stages) { this.stages = stages; }

}
