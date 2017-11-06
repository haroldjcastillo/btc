package com.github.haroldjcastillo.btc.http;

public enum TickType {
	
	UP("uptick"), DOWN("downticks"), NEUTRAL("ticks"),;
	
	private final String value;

    private TickType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
