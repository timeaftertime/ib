package cn.milai.ib.drama;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.milai.ib.drama.ex.ClipReadException;
import cn.milai.ib.drama.statics.DramaMetadata;
import cn.milai.ib.drama.util.ByteUtils;

/**
 * Clip 的抽象基类
 * 2019.12.24
 * @author milai
 */
public abstract class AbstractClip implements Clip {

	protected DramaMetadata drama;
	protected final Map<String, String> PARAMS;

	public AbstractClip(String clipCode) {
		URL clipURL = null;
		try {
			clipURL = getClipURL(clipCode);
			drama = new DramaMetadata(ByteUtils.toBytes(clipURL.openStream()));
		} catch (IOException e) {
			throw new ClipReadException(clipURL.toString());
		}
		PARAMS = Maps.newHashMap();
	}

	/**
	 * 返回剧本资源的 URL
	 * @param clipCode 
	 * @return
	 */
	protected abstract URL getClipURL(String clipCode);

	@Override
	public void setVariable(String key, String value) {
		PARAMS.put(key, value);
	}

	@Override
	public String getVariable(String key) {
		return PARAMS.get(key);
	}

	@Override
	public byte[] getBytes() {
		return drama.getClipBytes();
	}

	@Override
	public int getIntConst(int index) {
		return drama.getIntConst(index);
	}

	@Override
	public long getLongConst(int index) {
		return drama.getLongConst(index);
	}

	@Override
	public float getFloatConst(int index) {
		return drama.getFloatConst(index);
	}

	@Override
	public String getUTF8Const(int index) {
		return drama.getUTF8Const(index);
	}

}
