package cn.milai.ib.obj.character.explosion;

import cn.milai.ib.AudioConf;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.character.Explosion;

/**
 * 默认的爆炸
 *
 * 2019.11.29
 *
 * @author milai
 */
public class DefaultExplosion extends Explosion {

	public DefaultExplosion(int x, int y, Container container) {
		super(x, y, container);
		AudioConf.BOMB.play();
	}

}
