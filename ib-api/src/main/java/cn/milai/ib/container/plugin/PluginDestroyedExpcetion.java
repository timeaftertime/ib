package cn.milai.ib.container.plugin;

/**
 * 插件已销毁
 * @author milai
 * @date 2022.01.14
 */
public class PluginDestroyedExpcetion extends IllegalStateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PluginDestroyedExpcetion() {
		super("插件已销毁");
	}

}
