package cn.milai.ib.client.socket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SocketClient {

	private int port;
	private String host;
	private String token;
	private boolean isStarted = false;
	private EventLoopGroup group;

	public SocketClient(String host, int port, String token) {
		this.port = port;
		this.host = host;
		this.token = token;
	}

	public synchronized void start() {
		if (isStarted) {
			throw new RuntimeException("当前 Client 已启动，不能重复启动");
		}
		isStarted = true;
		group = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new MessageDecoder());
							ch.pipeline().addLast(new MessageEncoder());
							ch.pipeline().addLast(new SocketHandler(token));
						}
					});
			ChannelFuture f = bootstrap.connect(host, port).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}

}
