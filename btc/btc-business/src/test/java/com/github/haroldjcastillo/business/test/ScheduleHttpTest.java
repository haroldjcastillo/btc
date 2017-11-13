package com.github.haroldjcastillo.business.test;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.github.haroldjcastillo.business.http.ScheduleHttp;

@RunWith(JUnitPlatform.class)
public class ScheduleHttpTest {

	@Test
	void test() throws InterruptedException {
		ScheduleHttp beeper = null;
		try {
			final HttpObserver observer = new HttpObserver();
			beeper = new ScheduleHttp("https://api.bitso.com/v3/available_books/", 5, Arrays.asList(observer));
			beeper.start();
			Thread.sleep(20000);
		} finally {
			if(beeper != null) {
				beeper.stop();
			}
		}
	}

}
