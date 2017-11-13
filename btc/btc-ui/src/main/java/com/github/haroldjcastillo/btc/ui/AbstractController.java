package com.github.haroldjcastillo.btc.ui;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.haroldjcastillo.btc.dao.Book;
import com.github.haroldjcastillo.btc.dao.OrderBook;
import com.github.haroldjcastillo.btc.dao.TradePayloadResponse;
import com.github.haroldjcastillo.btc.http.TickType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author harold.castillo
 */
public abstract class AbstractController {

	protected static final Logger LOGGER = LogManager.getLogger(AbstractController.class);
	
	public static final ObservableList<Book> BIDS = FXCollections.observableArrayList();
	
	public static final ObservableList<Book> ASKS = FXCollections.observableArrayList();

	public static final ObservableList<TradePayloadResponse> DOWN_DATA = FXCollections.observableArrayList();
	
	public static final ObservableList<TradePayloadResponse> UP_DATA = FXCollections.observableArrayList();
	
	public static final ObservableList<TradePayloadResponse> RECENT_TRADES = FXCollections.observableArrayList();
	
	public static final AtomicInteger BEST = new AtomicInteger(10);
	
	public static final AtomicInteger TICKS = new AtomicInteger(0);
	
	public static final AtomicReference<Double> CURRENT_PRICE = new AtomicReference<Double>(0.0);
	
	public static final AtomicReference<TickType> TICK_TYPE = new AtomicReference<TickType>(TickType.NEUTRAL);
	
	public static final AtomicInteger SELL = new AtomicInteger(0);
	
	public static final AtomicInteger BUY = new AtomicInteger(0);
	
	public static final AtomicInteger M = new AtomicInteger(2);
	
	public static final AtomicInteger N = new AtomicInteger(2);
	
	public static final AtomicReference<OrderBook> ORDER_BOOK = new AtomicReference<>();

	protected <T> T getControler(final String controller) {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(controller));

		try {
			loader.load();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}

		return loader.getController();
	}
	
	protected boolean isDigit(final String digit) {
		if (digit.matches("\\d{1,10}")) {
			return true;
		} return false;
	}
}
