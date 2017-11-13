package com.github.haroldjcastillo.rxws;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.reactivex.Observer;

public class WebSocket {

	public static final Logger LOGGER = LoggerFactory.getLogger(WebSocket.class);
	private static final WebSocket INSTANCE = new WebSocket();
	private static final int port = 443;
	private URI uri;
	private SslContext sslContext;
	private boolean connnected;
	private Channel channel;
	private EventLoopGroup workerGroup;

	private WebSocket() {
		try {
			sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
			uri = new URI("wss://ws.bitso.com");
		} catch (SSLException | URISyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			throw new IllegalStateException(e);
		}
	}

	public static WebSocket getInstance() {
		return INSTANCE;
	}

	public WebSocket connect(final List<? extends Observer<String>> observers) {

		if (!isConnnected()) {

			this.workerGroup = new NioEventLoopGroup();
			final WebSocketHandle handler = new WebSocketHandle(WebSocketClientHandshakerFactory.newHandshaker(uri,
					WebSocketVersion.V08, null, false, new DefaultHttpHeaders()), observers);

			try {
				final Bootstrap bootstrap = new Bootstrap().group(this.workerGroup).channel(NioSocketChannel.class)
						.option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {
							@Override
							public void initChannel(SocketChannel socketChannel) throws Exception {
								ChannelPipeline channelPipeline = socketChannel.pipeline();
								channelPipeline
										.addLast(sslContext.newHandler(socketChannel.alloc(), uri.getHost(), port));
								channelPipeline.addLast(new HttpClientCodec(), new HttpObjectAggregator(8192), handler);
							}
						});
				this.channel = bootstrap.connect(uri.getHost(), port).sync().channel();
				handler.handshakeFuture().sync();
				this.connnected = true;
				LOGGER.debug(String.format("[Connected to %s]", uri));
			} catch (InterruptedException e) {
				LOGGER.info(e.getMessage(), e);
			}

		}

		return this;
	}

	public void disconnect() throws InterruptedException {
		this.connnected = false;
		this.channel.writeAndFlush(new CloseWebSocketFrame());
		this.channel.closeFuture().sync();
		this.workerGroup.shutdownGracefully();
		LOGGER.debug(String.format("[Disconnected from %s]", uri));
	}

	public WebSocket addChannels(final String... channels) {
		
		if (isConnnected()) {
			for (String data : channels) {
				this.channel.writeAndFlush(new TextWebSocketFrame(data));
				LOGGER.debug(String.format("[New channel] %s", this.channel));
			}
		} else {
			throw new IllegalAccessError("Websocket it's not connected");
		}

		return this;
	}

	public boolean isConnnected() {
		return this.connnected;
	}
}
