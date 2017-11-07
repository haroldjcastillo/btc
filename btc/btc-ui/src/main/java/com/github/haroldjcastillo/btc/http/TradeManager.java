package com.github.haroldjcastillo.btc.http;

import java.io.IOException;

import com.github.haroldjcastillo.btc.common.ObjectMapperFactory;
import com.github.haroldjcastillo.btc.common.TradeCache;
import com.github.haroldjcastillo.btc.dao.TradePayloadResponse;
import com.github.haroldjcastillo.btc.dao.TradeResponse;
import com.github.haroldjcastillo.btc.ui.AbstractController;

public class TradeManager {

	public static void manage(final String response) {

		try {
			final TradeResponse trade = ObjectMapperFactory.objectMapper.readValue(response, TradeResponse.class);
			if(!trade.getPayload().isEmpty()) {
				final TradePayloadResponse payload = trade.getPayload().get(0);
				if (TradeCache.get(payload.gettId()) == null) {
					final double price = Double.valueOf(payload.getPrice());
					if (price > AbstractController.CURRENT_PRICE.get()) {
						AbstractController.TICKS.incrementAndGet();
					} else if (price < AbstractController.CURRENT_PRICE.get()) {
						AbstractController.TICKS.decrementAndGet();
					}
					System.out.println(price);
					AbstractController.CURRENT_PRICE.set(price);
					updateTickName();
					TradeCache.put(payload.gettId(), payload);
				}
			}
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

}
