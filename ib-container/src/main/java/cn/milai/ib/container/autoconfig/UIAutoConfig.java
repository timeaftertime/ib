package cn.milai.ib.container.autoconfig;

import java.awt.image.BufferedImage;
import java.util.Collection;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import cn.milai.ib.container.plugin.ui.BaseImage;
import cn.milai.ib.container.plugin.ui.BaseUIPlugin;
import cn.milai.ib.container.plugin.ui.Image;
import cn.milai.ib.container.plugin.ui.Screen;
import cn.milai.ib.container.plugin.ui.UIPlugin;
import cn.milai.ib.container.plugin.ui.screen.form.BaseFormScreen;
import cn.milai.ib.container.plugin.ui.screen.form.FormScreen;
import cn.milai.ib.container.plugin.ui.screen.form.KeyMapping;
import cn.milai.ib.container.plugin.ui.screen.form.MouseMapping;

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
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public UIPlugin baseUIPlugin(Collection<Screen> screens) {
		UIPlugin uiPlugin = new BaseUIPlugin();
		for (Screen screen : screens) {
			uiPlugin.addScreen(screen);
		}
		return uiPlugin;
	}

	@Bean
	@ConditionalOnBean(UIPlugin.class)
	@ConditionalOnMissingBean(Screen.class)
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public FormScreen baseScreen(KeyMapping keyMapping, MouseMapping mouseMapping) {
		FormScreen formScreen = new BaseFormScreen();
		if (keyMapping != null) {
			formScreen.setKeyMapping(keyMapping);
		}
		if (mouseMapping != null) {
			formScreen.setMouseMapping(mouseMapping);
		}
		return formScreen;
	}
}
