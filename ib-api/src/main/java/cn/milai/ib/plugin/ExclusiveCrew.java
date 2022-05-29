package cn.milai.ib.plugin;

import cn.milai.ib.stage.Stage;

/**
 * 只能关联到一个 {@link Stage} 的 {@link Crew} 
 * @author milai
 * @date 2022.03.18
 */
public interface ExclusiveCrew extends Crew {

	/**
	 * 获取所关联的 {@link Stage}，没有关联时返回 null
	 * @return
	 */
	Stage stage();
}
