package cn.milai.ib.container.form;

import javax.swing.JFrame;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cn.milai.ib.conf.SystemConf;

/**
 * 战斗场景窗体容器
 * @author milai
 */
@Component
@Lazy
public class BattleFormContainer extends AbstractFormContainer {

	private static final String TITLE = "敌星弹雨";

	private static final int WIDTH = SystemConf.prorate(792);
	private static final int HEIGHT = SystemConf.prorate(984);

	@Override
	protected void initForm(JFrame form) {
		form.setTitle(TITLE);
		form.setSize(WIDTH, HEIGHT);
	}

}
