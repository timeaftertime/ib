package cn.milai.ib.container;

import cn.milai.ib.container.pluginable.media.MediaContainer;
import cn.milai.ib.container.pluginable.ui.UIContainer;
import cn.milai.ib.geometry.slot.BoundsSlot;
import cn.milai.ib.mode.drama.Drama;

/**
 * 舞台，{@link Drama } 中用到的 {@link Container } 
 * @author milai
 * @date 2020.12.02
 */
public interface Stage extends BoundsSlot, UIContainer, MediaContainer {

	@Override
	default void resize(double w, double h) {
		BoundsSlot.super.resize(w, h);
		UIContainer.super.resize(w, h);
	}
}
