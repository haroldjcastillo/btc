package com.github.haroldjcastillo.btc.http;

import java.io.IOException;

import com.github.haroldjcastillo.btc.common.ObjectMapperFactory;
import com.github.haroldjcastillo.btc.common.TradeCache;
import com.github.haroldjcastillo.btc.dao.TradeResponse;
import com.github.haroldjcastillo.btc.ui.AbstractController;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

public class TradeManager {

	public static void manage(final String response) {

		try {
			final TradeResponse trade = ObjectMapperFactory.objectMapper.readValue(response, TradeResponse.class);
				trade.getPayload().forEach(payload -> {
					if(TradeCache.get(payload.gettId()) == null) {
						final double price = Double.valueOf(payload.getPrice());
						if (price > AbstractController.CURRENT_PRICE.get()) {
							AbstractController.TICKS.incrementAndGet();
						} else if (price < AbstractController.CURRENT_PRICE.get()) {
							AbstractController.TICKS.decrementAndGet();
						}
						updateTickName();
						TradeCache.put(payload.gettId(), payload);
					}
				});
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public static void updateTicks() {
		new Task<Void>() {
			@Override
			public Void call() throws Exception {
				Platform.runLater(() -> {
					AbstractController.ticksObject = new SimpleStringProperty(AbstractController.TICKS.toString());
					AbstractController.tickTypeObject = new SimpleStringProperty(AbstractController.TICK_TYPE.get().getValue());
				});
				return Void.TYPE.newInstance();
			}
		}.run();
	}

}
