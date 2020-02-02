package cn.milai.ib.drama.clip;

import java.util.Map;

import com.google.common.collect.Maps;

import cn.milai.ib.drama.statics.DramaMetadata;

/**
 * Clip 的抽象基类
 * 2019.12.24
 * @author milai
 */
public class DefaultClip implements Clip {

	protected DramaMetadata drama;
	protected final Map<String, String> PARAMS;

	public DefaultClip(byte[] data) {
		drama = new DramaMetadata(data);
		PARAMS = Maps.newHashMap();
	}

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
