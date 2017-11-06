package com.github.haroldjcastillo.btc.ui;

import java.util.Arrays;

import com.github.haroldjcastillo.btc.http.TradeObserver;
import com.github.haroldjcastillo.business.http.ScheduleHttp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class TickController extends AbstractController {

	@FXML private Label tickCount;
	@FXML private Label descriptionTick;
	@FXML private ImageView upImage;
	@FXML private ImageView downImage;
	protected ScheduleHttp schedule;

	@FXML
	public void initialize() {
		this.service(1).run();
		tickCount.textProperty().bind(ticksObject);
		descriptionTick.textProperty().bind(tickTypeObject);
	}

	public Runnable service(final long delay) {

		return new Runnable() {
			public void run() {
				final String url = "https://api.bitso.com/v3/trades/?book=btc_mxn&marker&sort=desc&limit=1";
				final TradeObserver observer = new TradeObserver();
				schedule = new ScheduleHttp(url, delay, Arrays.asList(observer));
				schedule.start();
			}
		};
	}

	public void shutDown() {
		schedule.stop();
	}

}
