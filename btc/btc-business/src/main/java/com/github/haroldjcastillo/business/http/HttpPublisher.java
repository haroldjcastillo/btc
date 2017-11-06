package com.github.haroldjcastillo.business.http;

import com.github.haroldjcastillo.business.config.HttpResponse;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class HttpPublisher {
	
	private PublishSubject<HttpResponse> subject = PublishSubject.create();

	public void sendEvent(HttpResponse event) {
		 subject.onNext(event);
	}

	public Observable<HttpResponse> getObservable() {
        return subject;
    }

}
