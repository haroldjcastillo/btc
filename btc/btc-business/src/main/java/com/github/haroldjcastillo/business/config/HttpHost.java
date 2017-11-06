package com.github.haroldjcastillo.business.config;

public class HttpHost {

	private String hostName;

	private int port;

	public HttpHost() {
		
	}

	public HttpHost(String hostName, int port) {
		super();
		this.hostName = hostName;
		this.port = port;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
