package com.github.haroldjcastillo.btc.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.haroldjcastillo.btc.common.ObjectMapperFactory;
import com.github.haroldjcastillo.btc.dao.Book;
import com.github.haroldjcastillo.btc.dao.OrderBook;
import com.github.haroldjcastillo.btc.ui.AbstractController;
import com.github.haroldjcastillo.btc.ws.OrderManager;
import com.github.haroldjcastillo.btc.ws.OrderObserver;
import com.github.haroldjcastillo.business.config.HttpResponse;
import com.github.haroldjcastillo.business.http.ExecutorService;
import com.github.haroldjcastillo.rxws.WebSocket;

import javafx.collections.ObservableList;

/**
 *
 * @author harold.castillo
 */
public class OrderConverterTest {
	
	private static final String DIFF_ORDERS_CANCELLED = "{\"sequence\":39912890,\"payload\":[{\"r\":\"126489.93\",\"s\":\"cancelled\",\"d\":1510516314564,\"t\":1,\"o\":\"FC14t81NqwjdUb4E\"}],\"book\":\"btc_mxn\",\"type\":\"diff-orders\"}";

	private static final String DIFF_ORDERS = "{\"sequence\":39343865,\"payload\":[{\"a\":\"0.28857399\",\"r\":\"141573.28\",\"s\":\"open\",\"d\":1510289092333,\"t\":0,\"v\":\"40854.36628699\",\"o\":\"vNHRv65xbyxkZ0UX\"}],\"book\":\"btc_mxn\",\"type\":\"diff-orders\"}";

	private static final String ORDERS = "{\"success\":true,\"payload\":{\"updated_at\":\"2017-11-12T04:57:45+00:00\",\"bids\":[{\"book\":\"btc_mxn\",\"price\":\"119475.14\",\"amount\":\"0.00019991\"},{\"book\":\"btc_mxn\",\"price\":\"119317.76\",\"amount\":\"0.00039991\"},{\"book\":\"btc_mxn\",\"price\":\"119003.00\",\"amount\":\"0.12604725\"},{\"book\":\"btc_mxn\",\"price\":\"119002.00\",\"amount\":\"0.52099965\"},{\"book\":\"btc_mxn\",\"price\":\"119001.00\",\"amount\":\"0.02806944\"},{\"book\":\"btc_mxn\",\"price\":\"119000.00\",\"amount\":\"0.25407221\"},{\"book\":\"btc_mxn\",\"price\":\"118868.05\",\"amount\":\"0.02103173\"},{\"book\":\"btc_mxn\",\"price\":\"118800.00\",\"amount\":\"0.11771087\"},{\"book\":\"btc_mxn\",\"price\":\"118500.00\",\"amount\":\"0.04472575\"},{\"book\":\"btc_mxn\",\"price\":\"118400.00\",\"amount\":\"0.00844595\"},{\"book\":\"btc_mxn\",\"price\":\"118377.17\",\"amount\":\"0.26272144\"},{\"book\":\"btc_mxn\",\"price\":\"118256.00\",\"amount\":\"0.00042282\"},{\"book\":\"btc_mxn\",\"price\":\"118060.00\",\"amount\":\"0.80477935\"},{\"book\":\"btc_mxn\",\"price\":\"118033.00\",\"amount\":\"0.42368813\"},{\"book\":\"btc_mxn\",\"price\":\"118007.00\",\"amount\":\"0.00059319\"},{\"book\":\"btc_mxn\",\"price\":\"118003.00\",\"amount\":\"0.00025424\"},{\"book\":\"btc_mxn\",\"price\":\"118001.00\",\"amount\":\"0.29703139\"},{\"book\":\"btc_mxn\",\"price\":\"118000.09\",\"amount\":\"0.43603128\"},{\"book\":\"btc_mxn\",\"price\":\"118000.01\",\"amount\":\"0.25000000\"},{\"book\":\"btc_mxn\",\"price\":\"118000.00\",\"amount\":\"1.09651370\"},{\"book\":\"btc_mxn\",\"price\":\"117750.03\",\"amount\":\"0.01273885\"},{\"book\":\"btc_mxn\",\"price\":\"117750.02\",\"amount\":\"1.31204001\"},{\"book\":\"btc_mxn\",\"price\":\"117749.99\",\"amount\":\"0.09060723\"},{\"book\":\"btc_mxn\",\"price\":\"117650.00\",\"amount\":\"0.04249894\"},{\"book\":\"btc_mxn\",\"price\":\"117500.00\",\"amount\":\"1.91829211\"},{\"book\":\"btc_mxn\",\"price\":\"117311.09\",\"amount\":\"13.12040000\"},{\"book\":\"btc_mxn\",\"price\":\"117261.73\",\"amount\":\"0.02131983\"},{\"book\":\"btc_mxn\",\"price\":\"117205.01\",\"amount\":\"0.00426603\"},{\"book\":\"btc_mxn\",\"price\":\"117003.00\",\"amount\":\"0.11140314\"},{\"book\":\"btc_mxn\",\"price\":\"117002.00\",\"amount\":\"0.00854687\"},{\"book\":\"btc_mxn\",\"price\":\"117000.00\",\"amount\":\"4.71793200\"},{\"book\":\"btc_mxn\",\"price\":\"116901.99\",\"amount\":\"1.11295026\"},{\"book\":\"btc_mxn\",\"price\":\"116900.00\",\"amount\":\"0.02136870\"},{\"book\":\"btc_mxn\",\"price\":\"116790.00\",\"amount\":\"0.01712476\"},{\"book\":\"btc_mxn\",\"price\":\"116523.00\",\"amount\":\"0.00042910\"},{\"book\":\"btc_mxn\",\"price\":\"116500.00\",\"amount\":\"0.01562232\"},{\"book\":\"btc_mxn\",\"price\":\"116219.00\",\"amount\":\"0.01497200\"},{\"book\":\"btc_mxn\",\"price\":\"116200.00\",\"amount\":\"4.30292599\"},{\"book\":\"btc_mxn\",\"price\":\"116180.06\",\"amount\":\"0.10034287\"},{\"book\":\"btc_mxn\",\"price\":\"116061.01\",\"amount\":\"0.05000000\"},{\"book\":\"btc_mxn\",\"price\":\"116002.00\",\"amount\":\"0.00862055\"},{\"book\":\"btc_mxn\",\"price\":\"116000.00\",\"amount\":\"1.06117582\"},{\"book\":\"btc_mxn\",\"price\":\"115886.85\",\"amount\":\"0.00431456\"},{\"book\":\"btc_mxn\",\"price\":\"115230.00\",\"amount\":\"0.11116029\"},{\"book\":\"btc_mxn\",\"price\":\"115200.00\",\"amount\":\"0.02627362\"},{\"book\":\"btc_mxn\",\"price\":\"115192.75\",\"amount\":\"13.12040000\"},{\"book\":\"btc_mxn\",\"price\":\"115078.59\",\"amount\":\"0.12945718\"},{\"book\":\"btc_mxn\",\"price\":\"115054.26\",\"amount\":\"0.00633483\"},{\"book\":\"btc_mxn\",\"price\":\"115039.82\",\"amount\":\"0.00301504\"},{\"book\":\"btc_mxn\",\"price\":\"115039.70\",\"amount\":\"0.00633476\"}],\"asks\":[{\"book\":\"btc_mxn\",\"price\":\"119490.13\",\"amount\":\"0.36025996\"},{\"book\":\"btc_mxn\",\"price\":\"119503.25\",\"amount\":\"0.45321996\"},{\"book\":\"btc_mxn\",\"price\":\"119503.37\",\"amount\":\"0.08892915\"},{\"book\":\"btc_mxn\",\"price\":\"119947.27\",\"amount\":\"0.30000000\"},{\"book\":\"btc_mxn\",\"price\":\"119947.28\",\"amount\":\"1.31204000\"},{\"book\":\"btc_mxn\",\"price\":\"119947.30\",\"amount\":\"13.12040000\"},{\"book\":\"btc_mxn\",\"price\":\"119950.00\",\"amount\":\"0.00420000\"},{\"book\":\"btc_mxn\",\"price\":\"119999.99\",\"amount\":\"0.06101840\"},{\"book\":\"btc_mxn\",\"price\":\"120000.00\",\"amount\":\"0.48068991\"},{\"book\":\"btc_mxn\",\"price\":\"120050.00\",\"amount\":\"0.03327221\"},{\"book\":\"btc_mxn\",\"price\":\"120070.00\",\"amount\":\"0.50000000\"},{\"book\":\"btc_mxn\",\"price\":\"120646.36\",\"amount\":\"0.16509624\"},{\"book\":\"btc_mxn\",\"price\":\"120999.99\",\"amount\":\"2.15040984\"},{\"book\":\"btc_mxn\",\"price\":\"121500.00\",\"amount\":\"0.62178891\"},{\"book\":\"btc_mxn\",\"price\":\"121560.00\",\"amount\":\"0.00166814\"},{\"book\":\"btc_mxn\",\"price\":\"121800.00\",\"amount\":\"0.01163827\"},{\"book\":\"btc_mxn\",\"price\":\"121900.00\",\"amount\":\"0.18361469\"},{\"book\":\"btc_mxn\",\"price\":\"122000.00\",\"amount\":\"0.00098100\"},{\"book\":\"btc_mxn\",\"price\":\"122005.40\",\"amount\":\"0.20000000\"},{\"book\":\"btc_mxn\",\"price\":\"122422.46\",\"amount\":\"1.38621621\"},{\"book\":\"btc_mxn\",\"price\":\"122500.00\",\"amount\":\"0.06535506\"},{\"book\":\"btc_mxn\",\"price\":\"123000.00\",\"amount\":\"0.00030000\"},{\"book\":\"btc_mxn\",\"price\":\"123499.89\",\"amount\":\"0.04048587\"},{\"book\":\"btc_mxn\",\"price\":\"123500.00\",\"amount\":\"0.06085056\"},{\"book\":\"btc_mxn\",\"price\":\"123699.20\",\"amount\":\"0.00082451\"},{\"book\":\"btc_mxn\",\"price\":\"123900.00\",\"amount\":\"0.10658375\"},{\"book\":\"btc_mxn\",\"price\":\"125500.00\",\"amount\":\"1.01407759\"},{\"book\":\"btc_mxn\",\"price\":\"125625.00\",\"amount\":\"0.02057492\"},{\"book\":\"btc_mxn\",\"price\":\"125700.00\",\"amount\":\"0.22599362\"},{\"book\":\"btc_mxn\",\"price\":\"125700.01\",\"amount\":\"0.04408612\"},{\"book\":\"btc_mxn\",\"price\":\"125999.99\",\"amount\":\"0.39122796\"},{\"book\":\"btc_mxn\",\"price\":\"126000.00\",\"amount\":\"1.08000000\"},{\"book\":\"btc_mxn\",\"price\":\"126000.05\",\"amount\":\"0.30000000\"},{\"book\":\"btc_mxn\",\"price\":\"126053.55\",\"amount\":\"0.00751355\"},{\"book\":\"btc_mxn\",\"price\":\"127300.00\",\"amount\":\"0.02030419\"},{\"book\":\"btc_mxn\",\"price\":\"127799.00\",\"amount\":\"0.39411838\"},{\"book\":\"btc_mxn\",\"price\":\"127890.00\",\"amount\":\"0.37873417\"},{\"book\":\"btc_mxn\",\"price\":\"128000.00\",\"amount\":\"0.19398818\"},{\"book\":\"btc_mxn\",\"price\":\"128399.00\",\"amount\":\"0.50000000\"},{\"book\":\"btc_mxn\",\"price\":\"128500.00\",\"amount\":\"0.30274799\"},{\"book\":\"btc_mxn\",\"price\":\"128700.00\",\"amount\":\"0.00584625\"},{\"book\":\"btc_mxn\",\"price\":\"128900.00\",\"amount\":\"0.17667377\"},{\"book\":\"btc_mxn\",\"price\":\"128975.00\",\"amount\":\"0.02004051\"},{\"book\":\"btc_mxn\",\"price\":\"129000.00\",\"amount\":\"0.00204360\"},{\"book\":\"btc_mxn\",\"price\":\"129001.00\",\"amount\":\"0.50000000\"},{\"book\":\"btc_mxn\",\"price\":\"129062.12\",\"amount\":\"0.01544993\"},{\"book\":\"btc_mxn\",\"price\":\"129333.00\",\"amount\":\"0.00648320\"},{\"book\":\"btc_mxn\",\"price\":\"129399.00\",\"amount\":\"0.50000000\"},{\"book\":\"btc_mxn\",\"price\":\"129500.00\",\"amount\":\"0.00022355\"},{\"book\":\"btc_mxn\",\"price\":\"129600.00\",\"amount\":\"0.02034274\"}],\"sequence\":\"36784581\"}}";

	private static ObjectMapper mapper;
	
	private ObservableList<Book> bidsList = AbstractController.BIDS;
	
	@BeforeAll
	public static void beforeAll() {
		mapper = ObjectMapperFactory.objectMapper;
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		OrderManager.orderBook(ORDERS);
	}
	
	@Test
	public void loadCurrentOrders() {
		try {
			final HttpResponse response = ExecutorService
					.get("https://api.bitso.com/v3/order_book/?book=btc_mxn&aggregate=true");
			final String order = new String(response.getContent());
			assertNotNull(order);
			OrderManager.orderBook(order);
			assertNotNull(bidsList);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void loadDiffOrder() {
		OrderManager.orderBook(ORDERS);
		OrderManager.diffOrder(DIFF_ORDERS);
	}

	@Test
	public void loadDiffOrderCancelled() {
		OrderManager.orderBook(ORDERS);
		OrderManager.diffOrder(DIFF_ORDERS_CANCELLED);
	}

	@Test
	public void toOrders() {
		try {
			final String[] types = { "diff-orders" };
			final String[] channels = new String[types.length];
			for (int i = 0; i < types.length; i++) {
				final String frameMessage = "{ \"action\": \"subscribe\", \"book\": \"btc_mxn\", \"type\": \""
						+ types[i] + "\" }";
				channels[i] = frameMessage;
			}
			WebSocket.getInstance().connect(Arrays.asList(new OrderObserver())).addChannels(channels);
			Thread.sleep(20000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@RepeatedTest(5)
	public void jacksonTest() {
		final OrderBook order = getOrder(ORDERS);
		assertNotNull(order.getPayload());
	}

	@Test
	public void collectionsSorted() {
		final List<Book> currentOrder = new ArrayList<>();
		final List<Book> newOrder = getOrder(ORDERS).getPayload().getBids();
		currentOrder.addAll(newOrder);
		Collections.sort(currentOrder, (Book o1, Book o2) -> o2.compareTo(o1));
		assertNotNull(currentOrder);
	}

	@Test
	public void streamSorted() {
		final OrderBook order = getOrder(ORDERS);
		final List<Book> result = Stream.concat(order.getPayload().getBids().stream(), bidsList.stream())
				.sorted((o1, o2) -> o2.compareTo(o1))
				.limit(5)
				.collect(Collectors.toList());
		assertNotNull(result);
	}

	private OrderBook getOrder(final String json) {
		OrderBook order = null;
		try {
			order = mapper.readValue(json, OrderBook.class);
			assertNotNull(order);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return order;
	}
}
