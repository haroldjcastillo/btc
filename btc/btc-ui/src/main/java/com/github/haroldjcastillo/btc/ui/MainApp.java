package com.github.haroldjcastillo.btc.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		final Parent root = FXMLLoader.load(getClass().getResource("/fxml/splash.fxml"));
		final Scene scene = new Scene(root);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setTitle("Bitso");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
