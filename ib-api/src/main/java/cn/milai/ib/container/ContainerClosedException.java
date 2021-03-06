package cn.milai.ib.container;

import cn.milai.ib.ex.IBException;

/**
 * 容器已经关闭的异常
 * @author milai
 * @date 2020.03.24
 */
public class ContainerClosedException extends IBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContainerClosedException() {
		super("容器已经被关闭");
	}
}
