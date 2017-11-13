package com.github.haroldjcastillo.btc.dao;

public class OrderBook {

	private boolean success;

	private OrderBookPayload payload;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public OrderBookPayload getPayload() {
		return payload;
	}

	public void setPayload(OrderBookPayload payload) {
		this.payload = payload;
	}

}
