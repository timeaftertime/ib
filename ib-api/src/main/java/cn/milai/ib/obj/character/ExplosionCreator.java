package cn.milai.ib.obj.character;

/**
 * 爆炸产生器，要求提供 public 的以 {@code Explosible } 为参数的构造方法·
 *
 * 2019.11.29
 *
 * @author milai
 */
public interface ExplosionCreator {

	/**
	 * 返回产生的爆炸
	 * @return
	 */
	Explosion[] createExplosions();

}
