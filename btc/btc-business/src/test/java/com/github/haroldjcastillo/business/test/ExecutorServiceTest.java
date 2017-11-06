package com.github.haroldjcastillo.business.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.RepeatedTest;

import com.github.haroldjcastillo.business.config.HttpResponse;
import com.github.haroldjcastillo.business.http.ExecutorService;

class ExecutorServiceTest {

	private static final Logger LOGGER = Logger.getLogger(ExecutorService.class);

	@RepeatedTest(value = 10)
	void testGet() throws IOException {
		final HttpResponse response = ExecutorService.get("https://api.bitso.com/v3/available_books/");
		final String content = new String(response.getContent());
		assertTrue(response.getCode() == HttpStatus.SC_OK, content);
		LOGGER.info(content);
	}

}