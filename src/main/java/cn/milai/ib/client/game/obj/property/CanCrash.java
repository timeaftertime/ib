package cn.milai.ib.client.game.obj.property;

/**
 * 可以在与其他对象发生碰撞时发生反应
 */
public interface CanCrash extends HasCamp {

	/**
	 * 与 gamObj 碰撞后调用
	 * 只有 sameCamp(gameObj) 返回 false 才会调用这个方法
	 * @param crashed
	 */
	void onCrash(CanCrashed crashed);
	
}
