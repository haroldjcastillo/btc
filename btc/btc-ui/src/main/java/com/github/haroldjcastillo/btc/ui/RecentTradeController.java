package com.github.haroldjcastillo.btc.ui;

import com.github.haroldjcastillo.btc.dao.TradePayloadResponse;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

public class RecentTradeController extends AbstractController {

	@FXML
	private TableView<TradePayloadResponse> recentTrades;

	@FXML
	private Label recentValue;

	public void initialize() {
		background().run();
	}

	public Runnable background() {
		return new Runnable() {
			@Override
			public void run() {
				recentTrades.setItems(RECENT_TRADES);
				addColumns(recentTrades);
				final Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						recentValue.setText(String.valueOf(BEST.get()));
					}
				}), new KeyFrame(Duration.seconds(1)));
				timeline.setCycleCount(Timeline.INDEFINITE);
				timeline.play();
			}
		};
	}

	private void addColumns(final TableView<TradePayloadResponse> table) {
		final TableColumn<TradePayloadResponse, Long> tId = new TableColumn<>("Transaction id");
		tId.setCellValueFactory(
				(CellDataFeatures<TradePayloadResponse, Long> cellData) -> new ReadOnlyObjectWrapper<Long>(
						cellData.getValue().getTId()));
		tId.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(tId);

		final TableColumn<TradePayloadResponse, String> book = new TableColumn<>("Book");
		book.setCellValueFactory(new PropertyValueFactory<>("book"));
		book.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(book);

		final TableColumn<TradePayloadResponse, String> price = new TableColumn<>("Price");
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		price.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(price);

		final TableColumn<TradePayloadResponse, String> amount = new TableColumn<>("Amount");
		amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		amount.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(amount);

		final TableColumn<TradePayloadResponse, String> createdAt = new TableColumn<>("Created at");
		createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
		createdAt.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(createdAt);
	}

}
