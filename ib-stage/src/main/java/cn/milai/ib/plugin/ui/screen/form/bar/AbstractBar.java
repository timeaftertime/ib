package cn.milai.ib.plugin.ui.screen.form.bar;

import javax.swing.JFrame;

/**
 * {@link Bar} 抽象实现
 * @author milai
 * @date 2022.04.17
 */
public abstract class AbstractBar implements Bar {

	private JFrame form;

	@Override
	public JFrame getForm() { return form; }

	@Override
	public void setForm(JFrame form) { this.form = form; }

}
