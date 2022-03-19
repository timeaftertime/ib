package cn.milai.ib.control;

import cn.milai.ib.container.Stage;

/**
 * {@link Stage} 的 {@link Control}
 * @author milai
 * @date 2022.03.03
 */
public interface StageControl extends Control {

	/**
	 * 获取持有的 {@link Stage}
	 * @return
	 */
	default Stage stage() {
		return (Stage) container();
	}
}
