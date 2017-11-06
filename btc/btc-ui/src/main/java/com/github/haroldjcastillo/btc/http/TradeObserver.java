package com.github.haroldjcastillo.btc.http;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.haroldjcastillo.business.config.HttpResponse;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TradeObserver implements Observer<HttpResponse> {
	
	public static final Logger LOGGER = LogManager.getLogger(TradeObserver.class);
	
	@Override
	public void onComplete() {
		LOGGER.info("Observer task completed");
	}

	@Override
	public void onError(Throwable throwable) {
		LOGGER.error(throwable);
	}

	@Override
	public void onNext(HttpResponse response) {
		if(response.getCode() == HttpStatus.SC_OK)
			TradeManager.manage(new String(response.getContent()));
	}

	@Override
	public void onSubscribe(Disposable disposable) {
		LOGGER.debug("Subscribing, disposable: " + disposable);
	}

}
