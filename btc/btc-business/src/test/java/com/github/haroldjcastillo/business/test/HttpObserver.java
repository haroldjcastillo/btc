package com.github.haroldjcastillo.business.test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.haroldjcastillo.business.config.HttpResponse;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HttpObserver implements Observer<HttpResponse> {

	public static final Logger LOGGER = LogManager.getLogger(HttpObserver.class);
	
	@Override
	public void onComplete() {
		LOGGER.info("Completed");
	}

	@Override
	public void onError(Throwable throwable) {
		LOGGER.error(throwable);
	}

	@Override
	public void onNext(HttpResponse response) {
		LOGGER.info(new String(response.getContent()));
		assertEquals(HttpStatus.SC_OK, response.getCode()); 
	}

	@Override
	public void onSubscribe(Disposable disposable) {
		LOGGER.debug("Subscribing, disposable " + disposable);
	}

}
