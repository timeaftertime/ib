package cn.milai.ib.container.plugin.ui.form;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cn.milai.ib.container.BaseDramaContainer;
import cn.milai.ib.container.DramaContainer;
import cn.milai.ib.container.plugin.character.BaseAliveCheckPlugin;
import cn.milai.ib.container.plugin.character.BaseCrashCheckPlugin;
import cn.milai.ib.container.plugin.character.BaseExplosiblePlugin;
import cn.milai.ib.container.plugin.character.BaseMovablePlugin;
import cn.milai.ib.container.plugin.control.BaseControlPlugin;
import cn.milai.ib.container.plugin.media.BaseMediaPlugin;
import cn.milai.ib.container.plugin.metrics.BaseMetricPlugin;
import cn.milai.ib.container.pluginable.ui.FormUIContainer;

/**
 * Form 实现 UI 的战斗场景的 {@link DramaContainer}
 * @author milai
 */
@Component
@Lazy
public class BattleFormContainer extends BaseDramaContainer implements FormUIContainer {

	public BattleFormContainer() {
		addPlugin(new BaseControlPlugin());
		addPlugin(new BaseMovablePlugin());
		addPlugin(new BaseCrashCheckPlugin());
		addPlugin(new BaseAliveCheckPlugin());
		addPlugin(new BaseExplosiblePlugin());
		addPlugin(new BaseMediaPlugin());
		addPlugin(new BaseFormUIPlugin());
		addPlugin(new BaseMetricPlugin());
		startAllPlugins();
	}

}
