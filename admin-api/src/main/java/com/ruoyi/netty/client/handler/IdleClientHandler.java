package com.ruoyi.netty.client.handler;

import com.ruoyi.netty.client.NettyClient;
import com.ruoyi.netty.common.protobuf.MessageProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class IdleClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
	private static final Logger log = LoggerFactory.getLogger(IdleClientHandler.class);

	private NettyClient nettyClient;
	private int heartbeatCount = 0;
	private final static String CLIENTID = "123456789";

	/**
	 * @param nettyClient
	 */
	public IdleClientHandler(NettyClient nettyClient) {
		this.nettyClient = nettyClient;
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			String type = "";
			if (event.state() == IdleState.READER_IDLE) {
				type = "read idle";
			} else if (event.state() == IdleState.WRITER_IDLE) {
				type = "write idle";
			} else if (event.state() == IdleState.ALL_IDLE) {
				type = "all idle";
			}
			log.debug(ctx.channel().remoteAddress() + "超时类型：" + type);
//			sendPingMsg(ctx);
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	/**
	 * 发送ping消息
	 * @param context
	 */
//	protected void sendPingMsg(ChannelHandlerContext context) {
//		context.writeAndFlush(
//				MessageBase.newBuilder()
//				.setClientId(context.channel().id().toString())
////				.setCmd(CommandType.PING)
////				.setData("This is a ping msg")
//				.build()
//				);
//		heartbeatCount++;
//		log.info("Client sent ping msg to " + context.channel().remoteAddress() + ", count: " + heartbeatCount);
//	}

	/**
	 * 处理断开重连
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		final EventLoop eventLoop = ctx.channel().eventLoop();
		eventLoop.schedule(() -> nettyClient.doConnect(new Bootstrap(), eventLoop), 10L, TimeUnit.SECONDS);
		super.channelInactive(ctx);  
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

	}
}
