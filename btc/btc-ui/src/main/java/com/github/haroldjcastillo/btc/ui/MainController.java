package com.github.haroldjcastillo.btc.ui;

import com.github.haroldjcastillo.btc.common.Contoller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainController extends AbstractController {

	@FXML
	private ImageView closeButton;

	@FXML
	TickController ticksController;

	@FXML
	OrderController ordersController;

	@FXML
	public void initialize() {
		ticksController = getControler(Contoller.TICK.getValue());
		ordersController = getControler(Contoller.ORDER.getValue());
	}

	@FXML
	private void closeeButtonAction(MouseEvent event) {
		ticksController.shutDown();
		ordersController.shutDown();
		final Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

}
