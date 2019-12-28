package cn.milai.ib.drama;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.milai.ib.drama.ex.ClipReadException;

/**
 * Clip 的抽象基类
 * 2019.12.24
 * @author milai
 */
public abstract class AbstractClip implements Clip {

	private DataInputStream reader;
	private byte[] clipBytes;

	protected final Map<String, String> PARAMS;

	public AbstractClip(URL clip) {
		try {
			InputStream input = clip.openConnection().getInputStream();
			clipBytes = new byte[input.available()];
		} catch (IOException e) {
			throw new ClipReadException(clip.toString());
		}
		reader = new DataInputStream(new ByteArrayInputStream(clipBytes));
		PARAMS = Maps.newHashMap();
	}

	@Override
	public void setPos(int offset) throws IOException {
		reader = new DataInputStream(new ByteArrayInputStream(clipBytes));
		reader.skip(offset);
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
	public byte readInt8() throws IOException {
		return reader.readByte();
	}

	@Override
	public int readUint8() throws IOException {
		return reader.readUnsignedByte();
	}

	@Override
	public short readInt16() throws IOException {
		return reader.readShort();
	}

	@Override
	public int readUint16() throws IOException {
		return reader.readUnsignedShort();
	}

	@Override
	public int readInt32() throws IOException {
		return reader.readInt();
	}

	@Override
	public long readInt64() throws IOException {
		return reader.readLong();
	}

	@Override
	public float readFloat() throws IOException {
		return reader.readFloat();
	}

	@Override
	public String readUTF8() throws IOException {
		return reader.readUTF();
	}

}
