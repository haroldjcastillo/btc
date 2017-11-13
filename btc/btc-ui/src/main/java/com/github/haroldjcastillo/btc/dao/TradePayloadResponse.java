package com.github.haroldjcastillo.btc.dao;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TradePayloadResponse implements Comparable<TradePayloadResponse> {

	private String book;

	@JsonAlias("created_at")
	private String createdAt;

	private String amount;

	@JsonAlias("maker_side")
	private String makerSide;

	private String price;

	@JsonAlias("tid")
	private long tId;

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMakerSide() {
		return makerSide;
	}

	public void setMakerSide(String makerSide) {
		this.makerSide = makerSide;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public long gettId() {
		return tId;
	}

	public void settId(long tId) {
		this.tId = tId;
	}

	@Override
	public int compareTo(TradePayloadResponse o) {
		final Double from = Double.valueOf(this.getPrice()) * Double.valueOf(this.getAmount());
		final Double to = Double.valueOf(o.getPrice()) * Double.valueOf(o.getAmount());
		return from.compareTo(to);
	}

}
