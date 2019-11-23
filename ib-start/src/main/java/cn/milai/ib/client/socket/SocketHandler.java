package cn.milai.ib.client.socket;

import cn.milai.ib.Network.Message;
import cn.milai.ib.Network.Message.MessageType;
import cn.milai.ib.client.util.RequestUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SocketHandler extends ChannelInboundHandlerAdapter {

	private final String TOKEN = "token";
	private String token;
	
	public SocketHandler(String token) {
		this.token = token;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Message message = Message.newBuilder()
				.setType(MessageType.LOGIN)
				.setData(RequestUtil.param(TOKEN, token))
				.build();
		ctx.writeAndFlush(message);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//TODO 处理消息
	}

}
