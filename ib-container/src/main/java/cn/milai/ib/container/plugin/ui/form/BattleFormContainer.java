package cn.milai.ib.container.plugin.ui.form;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cn.milai.ib.container.BaseDramaContainer;
import cn.milai.ib.container.DramaContainer;
import cn.milai.ib.container.listener.Listeners;
import cn.milai.ib.container.plugin.control.BaseControlPlugin;
import cn.milai.ib.container.plugin.media.BaseMediaPlugin;
import cn.milai.ib.container.plugin.metrics.BaseMetricPlugin;
import cn.milai.ib.container.plugin.physics.BasePhysicsPlugin;
import cn.milai.ib.container.plugin.role.BaseAliveCheckPlugin;
import cn.milai.ib.container.plugin.role.BaseExplosiblePlugin;
import cn.milai.ib.container.pluginable.ui.FormUIContainer;
import cn.milai.ib.control.button.CloseButton;

/**
 * Form 实现 UI 的战斗场景的 {@link DramaContainer}
 * @author milai
 */
@Component
@Lazy
public class BattleFormContainer extends BaseDramaContainer implements FormUIContainer {

	public BattleFormContainer() {
		CloseButton closeButton = new CloseButton(
			0, 0, this, () -> fire(FormUIPlugin.class, p -> p.getForm().dispose())
		);
		addObjectListener(Listeners.removedListener((c, o) -> {
			c.addObject(o);
		}, closeButton));

		addPlugin(new BaseControlPlugin());
		addPlugin(new BasePhysicsPlugin());
		//		addPlugin(new BaseMovablePlugin());
		//		addPlugin(new BaseCrashCheckPlugin());
		addPlugin(new BaseAliveCheckPlugin());
		addPlugin(new BaseExplosiblePlugin());
		addPlugin(new BaseMediaPlugin());
		addPlugin(new BaseFormUIPlugin());
		addPlugin(new BaseMetricPlugin());
		startAllPlugins();
	}

}
