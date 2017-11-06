package com.github.haroldjcastillo.rx.ws.test;

import java.net.URISyntaxException;
import java.util.Arrays;

import javax.net.ssl.SSLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.haroldjcastillo.rxws.WebSocket;

public class WebSocketTest {

	final static WebSocket ws = WebSocket.getInstance();

	@BeforeAll
	public static void beforeAll() throws SSLException, URISyntaxException {
		ws.connect(Arrays.asList(new ObserverTest()));
	}

	@Test
	public void test() throws InterruptedException {
		try {
			final String[] channels = { "trades", "diff-orders", "orders" };
			for (String bitsoChannel : channels) {
				final String frameMessage = "{ \"action\": \"subscribe\", \"book\": \"btc_mxn\", \"type\": \"" + bitsoChannel
						+ "\" }";
				ws.addChannels(frameMessage);
			}
			Thread.sleep(15000);
		} finally {
			ws.disconnect();
		}
	}

}
