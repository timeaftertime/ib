package cn.milai.ib.client.socket;

import java.util.List;

import cn.milai.ib.client.socket.Network.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MessageDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		byte[] data = new byte[in.readableBytes()];
		in.readBytes(data);
		Message message = Message.parseFrom(data);
		out.add(message);
	}

}
