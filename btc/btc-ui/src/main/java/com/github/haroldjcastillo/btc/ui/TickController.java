package com.github.haroldjcastillo.btc.ui;

import java.util.Arrays;

import com.github.haroldjcastillo.btc.http.TradeObserver;
import com.github.haroldjcastillo.business.http.ScheduleHttp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class TickController extends AbstractController {

	@FXML
	private Label tickCount;
	@FXML
	private Label descriptionTick;
	@FXML
	private ImageView upImage;
	@FXML
	private ImageView downImage;

	private static ScheduleHttp schedule;

	@FXML
	public void initialize() {
		service(2).run();
	}

	public Runnable service(final long delay) {

		return new Runnable() {
			public void run() {
				if (schedule == null) {
					final String url = "https://api.bitso.com/v3/trades/?book=btc_mxn&limit=1";
					final TradeObserver observer = new TradeObserver();
					schedule = new ScheduleHttp(url, delay, Arrays.asList(observer));
					schedule.start();
				}

				final Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						tickCount.setText(String.valueOf(Math.abs(TICKS.get())));
						descriptionTick.setText(TICKS.get() > 1 ? TICK_TYPE.get().getValue().concat("s") : TICK_TYPE.get().getValue());
					}
				}), new KeyFrame(Duration.seconds(2)));
				timeline.setCycleCount(Timeline.INDEFINITE);
				timeline.play();
			}
		};
	}

	public void shutDown() {
		schedule.stop();
	}

}
