package com.github.haroldjcastillo.btc.common;

public enum Contoller {
	
	ORDER("/fxml/orders.fxml"), TICK("/fxml/ticks.fxml");
	
	private String value;
	
	Contoller(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
