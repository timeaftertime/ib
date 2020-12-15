package cn.milai.ib.container.ui.form;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 战斗场景窗体容器
 * @author milai
 */
@Component
@Lazy
public class BattleFormContainer extends AbstractFormContainer {

	/**
	 * 默认标题
	 */
	private static final String TITLE = "敌星弹雨";

	/**
	 * 默认宽度
	 */
	private static final int WIDTH = 554;

	/**
	 * 默认高度
	 */
	private static final int HEIGHT = 689;

	@Override
	protected void initForm() {
		setTitle(TITLE);
		resizeWithUI(WIDTH, HEIGHT);
	}

}
