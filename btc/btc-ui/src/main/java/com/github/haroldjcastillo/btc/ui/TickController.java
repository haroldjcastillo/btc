package com.github.haroldjcastillo.btc.ui;

import java.util.Arrays;

import com.github.haroldjcastillo.btc.http.TradeObserver;
import com.github.haroldjcastillo.business.http.ScheduleHttp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class TickController extends AbstractController {
	
	@FXML
	private Label tickCount;
	
	@FXML
	private Label descriptionTick;
	
	@FXML
	private ImageView  upImage;
	
	@FXML
	private ImageView downImage;

	@FXML
	public void initialize() {
		startTrades(1);
//		tickCount.setText(String.valueOf(TICKS.get()));
//		descriptionTick.setText(String.valueOf(TICK_TYPE.get().getValue()));
	}

	public void startTrades(final long limit) {
		final String url = "https://api.bitso.com/v3/trades/?book=btc_mxn&marker&sort=desc&limit=1";
		final TradeObserver observer = new TradeObserver();
		schedule = new ScheduleHttp(url, 3, Arrays.asList(observer));
		schedule.start();
	}
//
//	public void stopTrades() {
//		schedule.stop();
//	}

}
