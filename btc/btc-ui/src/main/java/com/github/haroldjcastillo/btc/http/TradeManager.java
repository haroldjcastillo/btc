package com.github.haroldjcastillo.btc.http;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.github.haroldjcastillo.btc.common.ObjectMapperFactory;
import com.github.haroldjcastillo.btc.common.TradeCache;
import com.github.haroldjcastillo.btc.dao.TradePayloadResponse;
import com.github.haroldjcastillo.btc.dao.TradeResponse;
import com.github.haroldjcastillo.btc.ui.AbstractController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class TradeManager {

	public static void manage(final String response) {

		try {
			final TradeResponse trade = ObjectMapperFactory.objectMapper.readValue(response, TradeResponse.class);
			if (!trade.getPayload().isEmpty()) {
				final TradePayloadResponse payload = trade.getPayload().get(0);
				if (TradeCache.get(payload.getTId()) == null) {
					final double price = Double.valueOf(payload.getPrice());
					if (price > AbstractController.CURRENT_PRICE.get()) {
						AbstractController.TICKS.incrementAndGet();
					} else if (price < AbstractController.CURRENT_PRICE.get()) {
						AbstractController.TICKS.decrementAndGet();
					}
					
					AbstractController.CURRENT_PRICE.set(price);
					updateTickName();
					trade(payload);
					addRecentTrade(payload);
					TradeCache.put(payload.getTId(), payload);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void addRecentTrade(final TradePayloadResponse trade) {
		updateTrades(AbstractController.RECENT_TRADES, trade);
		Platform.runLater(() -> {
			final ObservableList<TradePayloadResponse> copies = FXCollections
					.observableArrayList(AbstractController.RECENT_TRADES);
			final List<TradePayloadResponse> sortedList = copies.stream()
					.sorted((o1, o2) -> o2.compareTo(o1))
					.limit(AbstractController.BEST.intValue())
					.collect(Collectors.toList());
			AbstractController.RECENT_TRADES.setAll(sortedList);
		});
	}

	private static void trade(final TradePayloadResponse payload) {
		if (AbstractController.TICK_TYPE.get().equals(TickType.UP)
				&& AbstractController.M.get() == AbstractController.TICKS.get()) {
			updateTicks(AbstractController.SELL);
			updateTrades(AbstractController.UP_DATA, payload);
		} else if (AbstractController.TICK_TYPE.get().equals(TickType.DOWN)
				&& AbstractController.N.get() == Math.abs(AbstractController.TICKS.get())) {
			updateTicks(AbstractController.BUY);
			updateTrades(AbstractController.DOWN_DATA, payload);
		}
	}

	private static void updateTicks(final AtomicInteger type) {
		type.incrementAndGet();
		AbstractController.TICKS.set(0);
		AbstractController.TICK_TYPE.set(TickType.NEUTRAL);
	}

	private static void updateTickName() {
		if (AbstractController.TICKS.get() > 0) {
			AbstractController.TICK_TYPE.set(TickType.UP);
		} else if (AbstractController.TICKS.get() < 0) {
			AbstractController.TICK_TYPE.set(TickType.DOWN);
		} else {
			AbstractController.TICK_TYPE.set(TickType.NEUTRAL);
		}
	}
	
	private static void updateTrades(final ObservableList<TradePayloadResponse> observable, final TradePayloadResponse data) {
        new Task<Void>() {
            @Override
            public Void call() throws Exception {
                Platform.runLater(() -> {
                    observable.add(data);
                });
                return Void.TYPE.newInstance();
            }
        }.run();
    }

}
