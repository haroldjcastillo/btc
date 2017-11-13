package com.github.haroldjcastillo.btc.ui;

import java.io.IOException;
import java.util.Arrays;

import com.github.haroldjcastillo.btc.dao.Book;
import com.github.haroldjcastillo.btc.ws.OrderManager;
import com.github.haroldjcastillo.btc.ws.OrderObserver;
import com.github.haroldjcastillo.business.config.HttpResponse;
import com.github.haroldjcastillo.business.http.ExecutorService;
import com.github.haroldjcastillo.rxws.WebSocket;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderController extends AbstractController {

	@FXML
	private TickController tickController;

	@FXML
	private JFXTextField top;

	@FXML
	private TableView<Book> bidsTableView;

	@FXML
	private TableView<Book> asksTableView;

	private static boolean started = false;

	public void shutDown() {
		try {
			WebSocket.getInstance().disconnect();
		} catch (InterruptedException ex) {
			LOGGER.error(ex.getMessage());
		}
	}

	private void loadListeners() {
		top.textProperty().addListener((obsservable, oldValue, newValue) -> {
			if (newValue.matches("\\d{1,10}")) {
				BEST.set(Integer.valueOf(newValue));
			} else {
				top.setText(oldValue);
			}
		});
	}

	@FXML
	public void initialize() {
		top.setText(BEST.toString());
		addOrderPayloadColumn(bidsTableView);
		addOrderPayloadColumn(asksTableView);
		asksTableView.setItems(ASKS);
		bidsTableView.setItems(BIDS);
		loadListeners();
		service().run();
	}

	public Runnable service() {
		return new Runnable() {
			@Override
			public void run() {
				if (!started) {
					started = loadCurrentOrders() && connectWebsocket();
				}
			}
		};
	}

	private boolean connectWebsocket() {
		final String[] types = { "diff-orders" };
		final String[] channels = new String[types.length];
		for (int i = 0; i < types.length; i++) {
			final String frameMessage = "{ \"action\": \"subscribe\", \"book\": \"btc_mxn\", \"type\": \"" + types[i]
					+ "\" }";
			channels[i] = frameMessage;
		}
		WebSocket.getInstance().connect(Arrays.asList(new OrderObserver())).addChannels(channels);
		return true;
	}

	private boolean loadCurrentOrders() {
		try {
			final HttpResponse response = ExecutorService
					.get("https://api.bitso.com/v3/order_book/?book=btc_mxn&aggregate=true");
			final String order = new String(response.getContent());
			OrderManager.orderBook(order);
			return true;
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
	}

	private void addOrderPayloadColumn(final TableView<Book> table) {
		final TableColumn<Book, String> oid = new TableColumn<>("Order id");
		oid.setCellValueFactory(new PropertyValueFactory<>("oid"));
		oid.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(oid);

		final TableColumn<Book, String> book = new TableColumn<>("Book");
		book.setCellValueFactory(new PropertyValueFactory<>("book"));
		book.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(book);

		final TableColumn<Book, String> price = new TableColumn<>("Price");
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		price.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(price);

		final TableColumn<Book, String> amount = new TableColumn<>("Amount");
		amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		amount.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(amount);

		final TableColumn<Book, String> value = new TableColumn<>("Value");
		value.setCellValueFactory(new PropertyValueFactory<>("value"));
		value.prefWidthProperty().bind(table.widthProperty().divide(5));
		table.getColumns().add(value);
	}

}
