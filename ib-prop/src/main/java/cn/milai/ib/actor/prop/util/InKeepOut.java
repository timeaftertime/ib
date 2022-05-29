package cn.milai.ib.actor.prop.util;

import java.util.function.Consumer;

import cn.milai.ib.actor.prop.Prop;
import cn.milai.ib.stage.Stage;
import cn.milai.ib.stage.event.StageRefreshedEvent;

/**
 * 渐入、保持、渐出的 {@link Prop}
 * @author milai
 * @date 2022.05.24
 */
public class InKeepOut {

	private Prop prop;

	private long inFrame;
	private long keepFrame;
	private long outFrame;

	private PassCaculator pass;
	private final Consumer<StageRefreshedEvent> REFRESH_HANDLER = e -> this.pass.refresh();

	/**
	 * 创建一个目标为指定 {@link Prop} 的 {@link InKeepOut}
	 * @param prop
	 * @param inFrame
	 * @param keepFrame
	 * @param outFrame
	 */
	public InKeepOut(Prop prop, long inFrame, long keepFrame, long outFrame) {
		this.prop = prop;
		this.inFrame = inFrame;
		this.keepFrame = keepFrame;
		this.outFrame = outFrame;
	}

	public void start(Stage stage) {
		pass = new PassCaculator(
			inFrame, keepFrame, outFrame, p -> {
				prop.exit();
				stage.onRefreshed().unsubscribe(REFRESH_HANDLER);
			}
		);
		stage.onRefreshed().subscribe(REFRESH_HANDLER);
	}

	/**
	 * 是否出于 keep 状态
	 * @return
	 */
	public boolean isKeep() { return pass.isKeep(); }

	/**
	 * 获取此时的透明度 (0完全透明 ~ 255完全不透明)
	 * @return
	 */
	public int getTransparency() { return pass.getTransparency(); }

}
