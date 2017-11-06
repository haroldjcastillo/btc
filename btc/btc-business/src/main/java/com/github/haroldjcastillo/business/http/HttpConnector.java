package com.github.haroldjcastillo.business.http;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.github.haroldjcastillo.business.config.Configuration;
import com.github.haroldjcastillo.business.exception.BusinessException;

public class HttpConnector {

	public static final HttpConnector INSTANCE = new HttpConnector();

	protected CloseableHttpClient httpClient;

	private HttpConnector() {

		try {
			final Configuration config = getConfiguration();
			final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(config.getMaxTotal());
			cm.setDefaultMaxPerRoute(config.getDefaultMaxPerRoute());
			final RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(config.getConnectTimeout())
					.setSocketTimeout(config.getSocketTimeout())
					.setConnectionRequestTimeout(config.getConnectionRequestTimeout()).build();
			config.getHttpHost().forEach(http -> {
				final HttpHost httpHost = new HttpHost(http.getHostName(), http.getPort());
				cm.setMaxPerRoute(new HttpRoute(httpHost), 50);
			});
			this.httpClient = HttpClients.custom()
					.setConnectionManager(cm)
					.setDefaultRequestConfig(requestConfig)
					.build();
		} catch (BusinessException e) {
			throw new IllegalStateException(e);
		}
	}

	private Configuration getConfiguration() throws BusinessException {

		Configuration config = null;

		try {
			final File file = new File("./config/pooling.json");
			final byte[] encoded = Files.readAllBytes(Paths.get(file.getCanonicalPath()));
			final String json = new String(encoded, StandardCharsets.UTF_8);
			final Jsonb jsonb = JsonbBuilder.create();
			config = jsonb.fromJson(json, Configuration.class);
		} catch (IOException e) {
			throw new BusinessException("Failed to load the configuration", e);
		}

		return config;
	}
	
	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
}
