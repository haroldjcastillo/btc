package com.github.haroldjcastillo.business.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.github.haroldjcastillo.business.config.Configuration;
import com.github.haroldjcastillo.business.config.HttpHost;

public class ConfigTest {

	private static final Logger LOGGER = Logger.getLogger(ConfigTest.class);

	@Test
	public void toJson() {
		final Configuration config = new Configuration();
		config.setMaxTotal(100);
		config.setDefaultMaxPerRoute(20);
		final HttpHost httpHost = new HttpHost("localhost", 8080);
		config.getHttpHost().add(httpHost);
		assertNotNull(config.toString());
		LOGGER.info(config.toString());
	}

	@Test
	public void toConfiguration() {

		try {
			final File file = new File("./config/pooling.json");
			final byte[] encoded = Files.readAllBytes(Paths.get(file.getCanonicalPath()));
			final String json = new String(encoded, StandardCharsets.UTF_8);
			assertNotNull(json);
			final Jsonb jsonb = JsonbBuilder.create();
			final Configuration configJson = jsonb.fromJson(json, Configuration.class);
			assertNotNull(configJson);
			assertEquals(configJson.getMaxTotal(), 100);
			LOGGER.info(json);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

}
