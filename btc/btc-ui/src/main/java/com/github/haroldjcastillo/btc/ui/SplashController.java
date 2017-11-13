package com.github.haroldjcastillo.btc.ui;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashController {

	private static final Logger LOGGER = LogManager.getLogger(SplashController.class);

	@FXML
	private AnchorPane rootPane;

	@FXML
	public void initialize() {
		new SplashScreen().start();
	}

	class SplashScreen extends Thread {

		@Override
		public void run() {
			try {
				Thread.sleep(5000);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							final Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
							final Scene scene = new Scene(root);
							final Stage stage = new Stage();
							stage.initStyle(StageStyle.TRANSPARENT);
							stage.setTitle("Bitso");
							stage.setScene(scene);
							stage.show();
							rootPane.getScene().getWindow().hide();
						} catch (IOException e) {
							LOGGER.error(e.getMessage(), e);
						}
					}
				});

			} catch (InterruptedException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}

	}

}
