package cn.milai.ib.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import cn.milai.ib.IBCore;

/**
 * 中央仓库配置
 * @author milai
 * @date 2021.02.28
 */
@Component
@ConfigurationProperties("ib.repo")
public class RepoConf {

	/**
	 * 远程仓库 url
	 */
	private String remoteUrl = "http://localhost:80";

	/**
	 * 获取资源文件后保存到的本地目录
	 */
	private String localResourcePath = IBCore.class.getResource("/").getPath();

	public String getRemoteUrl() { return remoteUrl; }

	public void setRemoteUrl(String remoteUrl) { this.remoteUrl = remoteUrl; }

	public String getLocalResourcePath() { return localResourcePath; }

	public void setLocalResourcePath(String localResourcePath) { this.localResourcePath = localResourcePath; }

}
