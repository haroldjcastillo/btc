package com.github.haroldjcastillo.business.http;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.haroldjcastillo.business.config.HttpResponse;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class ScheduleHttp {

	public static final Logger LOGGER = LogManager.getLogger(ScheduleHttp.class);
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private HttpPublisher publisher = new HttpPublisher();
	private Observable<HttpResponse> observable = publisher.getObservable();
	private ScheduledFuture<?> beeperHandle;
	private final String service;
	private final long delay;

	public ScheduleHttp(final String service, final long delay,
			final List<? extends Observer<HttpResponse>> observers) {
		this.service = service;
		this.delay = delay;
		observers.forEach(observer -> {
			observable.subscribe(observer);
		});
	}

	public void start() {
		final Runnable beeper = new Runnable() {
			public void run() {
				try {
					final HttpResponse response = ExecutorService.get(service);
					publisher.sendEvent(response);
				} catch (IOException e) {
					LOGGER.error(e);
				}
			}
		};
		this.beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, delay, SECONDS);
	}

	public void stop() {
		beeperHandle.cancel(true);
		scheduler.shutdownNow();
	}
}
