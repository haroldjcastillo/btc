package com.github.haroldjcastillo.btc.ws;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author harold.castillo
 */
public class OrderObserver implements Observer<String> {

    private static final Logger LOGGER = LogManager.getLogger(OrderObserver.class);
    
    @Override
    public void onSubscribe(Disposable disposable) {
        LOGGER.debug("Subscribing, is disposable" + disposable.isDisposed());
    }

    @Override
    public void onNext(final String frame) {
        OrderManager.diffOrder(frame);
    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.error(throwable.getMessage(), throwable);
    }

    @Override
    public void onComplete() {
        LOGGER.debug("The task has been completed");
    }

}
