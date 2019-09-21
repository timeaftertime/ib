package cn.milai.ib.client.game.obj.property;

/**
 * 一个表明本对象可以撞击 @see CanCrashed 对象的接口
 */
public interface CanCrash extends HasCamp, HasLocation {

	/**
	 * 与 gameEntity 碰撞后调用 只有 sameCamp(gameEntity) 返回 false 才会调用这个方法
	 * 
	 * @param crashed
	 */
	void onCrash(CanCrashed crashed);

}
