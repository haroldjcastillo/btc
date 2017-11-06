package com.github.haroldjcastillo.btc.common;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import com.github.haroldjcastillo.btc.dao.TradePayloadResponse;

public class TradeCache {

	private static TradeCache INSTANCE = new TradeCache();

	private static Cache<Long, TradePayloadResponse> data;

	private TradeCache() {
		final CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("preConfigured", CacheConfigurationBuilder
						.newCacheConfigurationBuilder(Long.class, TradePayloadResponse.class, ResourcePoolsBuilder.heap(100)).build())
				.build(true);
		cacheManager.getCache("preConfigured", Long.class, TradePayloadResponse.class);
		data = cacheManager.createCache("trade", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Long.class, TradePayloadResponse.class, ResourcePoolsBuilder.heap(100))
				.build());
	}

	public static void put(final long id, final TradePayloadResponse payload) {
		data.put(id, payload);
	}

	public static TradePayloadResponse get(final long id) {
		return data.get(id);
	}

	public static TradeCache getInstance() {
		return INSTANCE;
	}

}
