package cn.milai.ib.container.autoconfig;

import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import cn.milai.ib.container.plugin.ui.BaseImage;
import cn.milai.ib.container.plugin.ui.Image;
import cn.milai.ib.container.plugin.ui.UIPlugin;
import cn.milai.ib.container.plugin.ui.form.BaseFormUIPlugin;
import cn.milai.ib.container.plugin.ui.form.FormUIPlugin;
import cn.milai.ib.container.plugin.ui.form.KeyMapping;
import cn.milai.ib.container.plugin.ui.form.MouseMapping;

/**
 * UI 相关自动装载类
 * @author milai
 * @date 2021.08.12
 */
public class UIAutoConfig {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConditionalOnMissingBean(Image.class)
	public Image defaultImage(BufferedImage... images) {
		return new BaseImage(images);
	}

	@Bean
	@ConditionalOnMissingBean(UIPlugin.class)
	@Autowired(required = false)
	public FormUIPlugin baseUIPlugin(KeyMapping keyMapping, MouseMapping mouseMapping) {
		BaseFormUIPlugin formUIPlugin = new BaseFormUIPlugin();
		if (keyMapping != null) {
			formUIPlugin.setKeyMapping(keyMapping);
		}
		if (mouseMapping != null) {
			formUIPlugin.setMouseMapping(mouseMapping);
		}
		return formUIPlugin;
	}
}
