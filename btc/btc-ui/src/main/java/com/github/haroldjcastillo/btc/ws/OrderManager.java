package com.github.haroldjcastillo.btc.ws;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.haroldjcastillo.btc.common.ObjectMapperFactory;
import com.github.haroldjcastillo.btc.dao.Bitso;
import com.github.haroldjcastillo.btc.dao.Book;
import com.github.haroldjcastillo.btc.dao.DiffOrder;
import com.github.haroldjcastillo.btc.dao.OrderBook;
import com.github.haroldjcastillo.btc.ui.AbstractController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 *
 * @author harold.castillo
 */
public class OrderManager {

	private static final Logger LOGGER = LogManager.getLogger(OrderManager.class);

	private static final long SELL = 1;

	private static final long BUY = 0;

	private static final String OPEN = "open";

	public static void diffOrder(final String frame) {
		final JSONObject json = new JSONObject(frame);
		final ObjectMapper objectMapper = ObjectMapperFactory.objectMapper;

		if (json.has("type") && json.has("payload")) {
			final String type = json.getString("type");
			try {
				if (type.equals(Bitso.Channel.DIFF_ORDERS.getValue())) {
					final DiffOrder order = objectMapper.readValue(frame, DiffOrder.class);
					if (isValidSequence(order.getSequence())) {
						final List<Book> books = order.getPayload()
								.stream()
								.filter(p -> p.getS().equals(OPEN))
								.map(dffo -> {
									final Book book = new Book(order.getBook(), dffo.getR(), dffo.getA(), dffo.getO(),
											dffo.getV(), dffo.getT());
									return book;
								}).collect(Collectors.toList());
						final List<Book> bids = books.stream()
								.filter(b -> b.getType() == SELL)
								.collect(Collectors.toList());
						final List<Book> ask = books.stream()
								.filter(b -> b.getType() == BUY)
								.collect(Collectors.toList());
						toSortBook(AbstractController.BIDS, bids);
						toSortBook(AbstractController.ASKS, ask);
					}
				}
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	public static void orderBook(final String orderBookResponse) {
		try {
			final OrderBook order = ObjectMapperFactory.objectMapper.readValue(orderBookResponse, OrderBook.class);
			if (order.getPayload() != null) {
				toSortBook(AbstractController.BIDS, order.getPayload().getBids());
				toSortBook(AbstractController.ASKS, order.getPayload().getAsks());
				AbstractController.ORDER_BOOK.set(order);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private static void toSortBook(final ObservableList<Book> book, final List<Book> books) {
		// Prevent the concurrent modification in the ObservableList JavaFX data table
		final ObservableList<Book> copies = FXCollections.observableArrayList(book);
		final List<Book> sortedList = Stream.concat(books.stream(), copies.stream())
				.collect(
						collectingAndThen(toCollection(() -> new TreeSet<>(comparing(Book::getValue))), ArrayList::new))
				.stream().sorted((o1, o2) -> o2.compareTo(o1)).limit(AbstractController.BEST.intValue())
				.collect(Collectors.toList());
		updateOrderBook(book, sortedList);
	}

	private static boolean isValidSequence(final String sequence) {
		final double diffOrder = Double.valueOf(sequence);
		final double orderBook = Double.valueOf(AbstractController.ORDER_BOOK.get().getPayload().getSequence());
		if (diffOrder > orderBook) {
			return true;
		}
		return false;
	}

	private static void updateOrderBook(final ObservableList<Book> book, final List<Book> books) {
		new Task<Void>() {
			@Override
			public Void call() throws Exception {
				Platform.runLater(() -> {
					book.setAll(books);
				});
				return Void.TYPE.newInstance();
			}
		}.run();
	}
}
