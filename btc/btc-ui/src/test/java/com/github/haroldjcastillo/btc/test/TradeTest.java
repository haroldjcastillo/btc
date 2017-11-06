package com.github.haroldjcastillo.btc.test;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.github.haroldjcastillo.btc.http.TradeManager;
import com.github.haroldjcastillo.btc.http.TradeObserver;
import com.github.haroldjcastillo.business.http.ScheduleHttp;

public class TradeTest {
	
	public static final Logger LOGGER = LogManager.getLogger(TradeObserver.class);
	
	public static final String RESPONSE = "{\"success\":true,\"payload\":[{\"book\":\"btc_mxn\",\"created_at\":\"2017-11-06T15:58:54+0000\",\"amount\":\"0.03152472\",\"maker_side\":\"buy\",\"price\":\"140483.15\",\"tid\":1856026}]}";
	
	@Test
	public void tradeObserverTest() {
		
		ScheduleHttp schedule = null;

		try {
			final String url = "https://api.bitso.com/v3/trades/?book=btc_mxn&marker&sort=desc&limit=1";
			final TradeObserver observer = new TradeObserver();
			schedule = new ScheduleHttp(url, 1, Arrays.asList(observer));
			schedule.start();
			Thread.sleep(60000);
		} catch (Exception e) {
			LOGGER.error(e);
		} finally {
			if (schedule != null)
				schedule.stop();
		}
	}
	
	public void tradeConverterTest() {
		TradeManager.manage(String.valueOf(RESPONSE));
	}

}
