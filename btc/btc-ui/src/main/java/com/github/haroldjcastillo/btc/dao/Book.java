package com.github.haroldjcastillo.btc.dao;

public class Book implements Comparable<Book> {

	private String book;
	private String price;
	private String amount;
	private String oid;
	private String value;
	private long type;
	
	public Book() {
		super();
	}
	
	public Book(final String book, final String price, final String amount, final String oid, final String value, final long type) {
		super();
		this.book = book;
		this.price = price;
		this.amount = amount;
		this.oid = oid;
		this.value = value;
		this.type = type;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
	
	public String getValue() {
		this.value = String.valueOf(Double.parseDouble(price) * Double.parseDouble(amount));
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	@Override
	public int compareTo(Book o) {
		final Double from = Double.valueOf(this.getPrice()) * Double.valueOf(this.getAmount());
		final Double to = Double.valueOf(o.getPrice()) * Double.valueOf(o.getAmount());
		return from.compareTo(to);
	}

}
