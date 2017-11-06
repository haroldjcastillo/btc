package com.github.haroldjcastillo.rx.ws.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.haroldjcastillo.rxws.WebSocket;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ObserverTest implements Observer<String> {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(WebSocket.class);

	@Override
	public void onError(final Throwable throwable) {
		LOGGER.debug(throwable.getMessage(), throwable);
	}

	@Override
	public void onNext(final String frame) {
		LOGGER.debug(frame);
	}

	@Override
	public void onComplete() {
		LOGGER.debug("onComplete");
	}

	@Override
	public void onSubscribe(Disposable disposable) {
		LOGGER.debug("onSubscribe");
	}

}
