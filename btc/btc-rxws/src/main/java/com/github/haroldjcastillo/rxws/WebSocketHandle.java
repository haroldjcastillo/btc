package com.github.haroldjcastillo.rxws;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.util.CharsetUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;

public class WebSocketHandle extends ChannelInboundHandlerAdapter {

	public static final Logger LOGGER = LoggerFactory.getLogger(WebSocket.class);
	private final WebSocketClientHandshaker handshaker;
	private ChannelPromise channelPromise;
	private PublisherSubject publisher = new PublisherSubject();
	private Observable<String> observable = publisher.getObservable();

	public WebSocketHandle(final WebSocketClientHandshaker handshaker, final List<? extends Observer<String>> observers) {
		this.handshaker = handshaker;
		observers.forEach(observer -> {
			observable.subscribe(observer);
		});
	}

	public ChannelFuture handshakeFuture() {
		return channelPromise;
	}

	@Override
	public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
		channelPromise = ctx.newPromise();
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		handshaker.handshake(ctx.channel());
	}

	@Override
	public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
		LOGGER.debug(String.format("[Inactive channel] %s", ctx.name()));
	}

	@Override
	public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object message) throws Exception {

		final Channel channel = channelHandlerContext.channel();

		if (!handshaker.isHandshakeComplete()) {
			FullHttpResponse response = (FullHttpResponse) message;
			handshaker.finishHandshake(channel, (FullHttpResponse) message);
			response.release();
			channelPromise.setSuccess();
		} else {

			if (message instanceof FullHttpResponse) {
				final FullHttpResponse response = (FullHttpResponse) message;
				response.release();
				throw new Exception("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content="
						+ response.content().toString(CharsetUtil.UTF_8) + ')');
			}

			if (message instanceof TextWebSocketFrame) {
				final TextWebSocketFrame textFrame = (TextWebSocketFrame) message;
				publisher.sendEvent(textFrame.text());
				textFrame.release();
			}

			if (message instanceof CloseWebSocketFrame) {
				((CloseWebSocketFrame) message).release();
				LOGGER.debug(String.format("[Close webSocket frame] %s", message));
			}
		}
	}

	@Override
	public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
		cause.printStackTrace();
		if (!channelPromise.isDone()) {
			channelPromise.setFailure(cause);
		}
		ctx.close();
	}
}
