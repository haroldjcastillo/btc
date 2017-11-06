package com.github.haroldjcastillo.rxws;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class PublisherSubject {

	private PublishSubject<String> subject = PublishSubject.create();

	public void sendEvent(String event) {
		 subject.onNext(event);
	}

	public Observable<String> getObservable() {
        return subject;
    }

}
