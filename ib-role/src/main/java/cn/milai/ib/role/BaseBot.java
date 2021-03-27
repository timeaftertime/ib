package cn.milai.ib.role;

/**
 * {@link BotRole} 的抽象实现
 * @author milai
 * @date 2020.04.04
 */
public class BaseBot implements Bot {

	private PlayerRole target;

	@Override
	public void setAttackTarget(PlayerRole target) { this.target = target; }

	@Override
	public PlayerRole getAttackTarget() { return target; }

	@Override
	public void selectAttackTarget() {
		throw new UnsupportedOperationException();
	}

}
