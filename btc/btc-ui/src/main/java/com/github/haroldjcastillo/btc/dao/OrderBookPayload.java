package com.github.haroldjcastillo.btc.dao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

public class OrderBookPayload {

	private List<Book> asks;

	private List<Book> bids;

	@JsonAlias("updated_at")
	private String updatedAt;

	private String sequence;

	public List<Book> getAsks() {
		return asks;
	}

	public void setAsks(List<Book> asks) {
		this.asks = asks;
	}

	public List<Book> getBids() {
		return bids;
	}

	public void setBids(List<Book> bids) {
		this.bids = bids;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

}
