package cn.milai.ib.character;

/**
 * {@link BotCharacter} 的抽象实现
 * @author milai
 * @date 2020.04.04
 */
public class BaseBot implements Bot {

	private PlayerCharacter target;

	@Override
	public void setAttackTarget(PlayerCharacter target) { this.target = target; }

	@Override
	public PlayerCharacter getAttackTarget() { return target; }

	@Override
	public void selectAttackTarget() { throw new UnsupportedOperationException(); }

}
