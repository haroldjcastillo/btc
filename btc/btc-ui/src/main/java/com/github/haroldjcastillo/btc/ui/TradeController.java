package com.github.haroldjcastillo.btc.ui;

import com.github.haroldjcastillo.btc.dao.TradePayloadResponse;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

public class TradeController extends AbstractController {

	@FXML
	private TableView<TradePayloadResponse> uptickTable;
	
	@FXML
	private TableView<TradePayloadResponse> downtickTable;
	
	@FXML
	private Label buy;
	
	@FXML
	private Label sell;
	
	@FXML
	private JFXTextField m;
	
	@FXML
	private JFXTextField n;

	@FXML
	public void initialize() {
		m.setText(M.toString());
		n.setText(N.toString());
		addTradeColumn(uptickTable);
		addTradeColumn(downtickTable);
		uptickTable.setItems(UP_DATA);
		downtickTable.setItems(DOWN_DATA);
		loadListeners();
		
		final Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				buy.setText(BUY.toString());
				sell.setText(SELL.toString());
			}
		}), new KeyFrame(Duration.seconds(2)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	
	private void loadListeners() {
		m.textProperty().addListener((obsservable, oldValue, newValue) -> {
			if (isDigit(newValue)) {
				M.set(Integer.valueOf(newValue));
			} else {
				m.setText(oldValue);
			}
		});
		
		n.textProperty().addListener((obsservable, oldValue, newValue) -> {
			if (isDigit(newValue)) {
				N.set(Integer.valueOf(newValue));
			} else {
				n.setText(oldValue);
			}
		});
	}

	private void addTradeColumn(final TableView<TradePayloadResponse> table) {
		
		final TableColumn<TradePayloadResponse, Long> tId = new TableColumn<>("Trade id");
		tId.setCellValueFactory(
				(CellDataFeatures<TradePayloadResponse, Long> cellData) -> new ReadOnlyObjectWrapper<Long>(
						cellData.getValue().getTId()));
		tId.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(tId);
		
		final TableColumn<TradePayloadResponse, String> book = new TableColumn<>("Book");
		book.setCellValueFactory(new PropertyValueFactory<>("book"));
		book.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(book);
		
		final TableColumn<TradePayloadResponse, String> price = new TableColumn<>("price");
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		price.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(price);
		
		final TableColumn<TradePayloadResponse, String> makerSide = new TableColumn<>("Maker side");
		makerSide.setCellValueFactory(new PropertyValueFactory<>("makerSide"));
		makerSide.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(makerSide);
		
		final TableColumn<TradePayloadResponse, String> createdAt = new TableColumn<>("Created at");
		createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
		createdAt.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(createdAt);
	}

}
