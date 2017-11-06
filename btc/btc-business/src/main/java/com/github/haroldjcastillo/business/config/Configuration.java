package com.github.haroldjcastillo.business.config;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.spi.JsonbProvider;

public class Configuration {
	
	private int maxTotal;
	
	private int defaultMaxPerRoute;
	
	private List<HttpHost> httpHost;
	
	private int connectTimeout;
	
	private int socketTimeout;
	
	private int connectionRequestTimeout;

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getDefaultMaxPerRoute() {
		return defaultMaxPerRoute;
	}

	public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}

	public List<HttpHost> getHttpHost() {
		if(httpHost == null) {
			httpHost = new ArrayList<>();
		} 
		return httpHost;
	}

	public void setHttpHost(List<HttpHost> httpHost) {
		this.httpHost = httpHost;
	}
	
	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	@Override
	public String toString() {
		return JsonbProvider.provider().create().build().toJson(this);
	}
	
}
