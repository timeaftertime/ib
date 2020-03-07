package cn.milai.ib.drama;

/**
 * 剧本解析器
 * @author milai
 * @date 2020.03.05
 */
public interface DramaResolver {

	/**
	 * 解析并返回 dramaCode 对应的剧本实例
	 * @param dramaCode
	 * @return
	 */
	Drama resolve(String dramaCode);

}
